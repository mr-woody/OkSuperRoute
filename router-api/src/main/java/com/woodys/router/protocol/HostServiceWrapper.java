
package com.woodys.router.protocol;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;

import com.woodys.router.configs.RouterConfiguration;
import com.woodys.router.module.RemoteRule;
import com.woodys.router.module.RouteRule;
import com.woodys.router.route.impl.ActionRoute;
import com.woodys.router.route.impl.ActivityRoute;
import com.woodys.router.route.IRoute;
import com.woodys.router.callback.InternalCallback;
import com.woodys.router.tools.Cache;

import java.util.HashMap;
import java.util.Map;

public class HostServiceWrapper {

    private static Context context;
    private static IService service;
    private static String pluginName;

    private static ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            HostServiceWrapper.service = IService.Stub.asInterface(service);
            // register rules to remote service.
            registerRulesToHostService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            HostServiceWrapper.service = null;
        }
    };

    /**
     * Set a host package name. it will be used to bind a remote service in host app.
     *
     * @param hostPackage the package name of host.
     * @param context the context to start remote service.
     * @param pluginName The plugin name to register to remote service. to help to judge if it should be
     */
    public static void startHostService(String hostPackage, Context context, String pluginName) {
        if (service != null) {
            throw new RuntimeException("You've bind a remote service before");
        }
        if (TextUtils.isEmpty(hostPackage)) {
            throw new IllegalArgumentException("Please provide a valid host package name.");
        }
        HostServiceWrapper.context = context.getApplicationContext();
        HostServiceWrapper.pluginName = TextUtils.isEmpty(pluginName) ? context.getPackageName() : pluginName;

        Intent intent = new Intent();
        intent.setPackage(hostPackage);
        intent.setAction("com.woodys.router.action.REMOTE");
        context.bindService(intent, connection, Service.BIND_AUTO_CREATE);
    }

    public static IRoute create(Uri uri, InternalCallback callback) {
        try {
            return createWithThrow(uri, callback);
        } catch (Exception e) {
            return new IRoute.EmptyRoute(callback);
        }
    }

    private static IRoute createWithThrow(Uri uri, InternalCallback callback) throws Exception{
        RemoteRule rule;
        if ((rule = service.getActivityRule(uri)) != null) {
            return new ActivityRoute().create(uri, rule.getRule(), rule.getExtra(), callback);
        } else if ((rule = service.getActionRule(uri)) != null) {
            return new ActionRoute().create(uri, rule.getRule(), rule.getExtra(), callback);
        } else {
            return new IRoute.EmptyRoute(callback);
        }
    }

    public static boolean isRegister(String pluginName) {
        try {
            return service.isRegister(pluginName);
        } catch (Exception e) {
            return false;
        }
    }

    public static void registerRulesToHostService() {
        try {
            if (service == null) {
                return;
            }
            service.register(pluginName);
            service.addActionRules(transform(Cache.getActionRules()));
            service.addActivityRules(transform(Cache.getActivityRules()));
        } catch (Exception e) {
            // ignore
        }
    }

    private static Map<String, RemoteRule> transform(Map<String, ? extends RouteRule> source){
        Map<String, RemoteRule> dest = new HashMap<>();
        for (String route : source.keySet()) {
            RouteRule rule = source.get(route);
            RemoteRule remote = RemoteRule.create(rule, getRemote(context, rule));
            dest.put(route, remote);
        }
        return dest;
    }

    private static Bundle getRemote(Context context, RouteRule rule){
        IRemoteFactory factory = RouterConfiguration.get().getRemoteFactory();
        return factory == null ? null : factory.createRemote(context, rule);
    }

}
