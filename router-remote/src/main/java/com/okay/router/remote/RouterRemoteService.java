package com.okay.router.remote;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcel;

import com.okay.router.module.RemoteRule;
import com.okay.router.protocol.IService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Remote service to store route rules.
 * @author okay
 */
public class RouterRemoteService extends Service{

    private static RemoteVerify verify = new DefaultVerify();

    public static void setVerify(RemoteVerify verify) {
        RouterRemoteService.verify = verify;
    }

    IService.Stub stub = new IService.Stub() {
        Map<String, RemoteRule> activities = new HashMap<>();
        Map<String, RemoteRule> actions = new HashMap<>();
        List<String> plugins = new ArrayList<>();

        @Override
        public void register(String pluginName) {
            if (!plugins.contains(pluginName)) {
                plugins.add(pluginName);
            }
        }

        @Override
        public boolean isRegister(String pluginName) {
            return plugins.contains(pluginName);
        }

        @Override
        public void addActivityRules(Map rules) {
            activities.putAll(rules);
        }

        @Override
        public void addActionRules(Map rules) {
            actions.putAll(rules);
        }

        @Override
        public RemoteRule getActionRule(Uri uri) {
            return findRule(uri, actions);
        }

        @Override
        public RemoteRule getActivityRule(Uri uri) {
            return findRule(uri, activities);
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
            try {
                // check for security verification
                if (verify != null && !verify.verify(getApplicationContext())) {
                    return false;
                }
                return super.onTransact(code, data, reply, flags);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    };

    private RemoteRule findRule(Uri uri, Map<String, RemoteRule> rules) {
        String route = uri.getScheme() + "://" + uri.getHost() + uri.getPath();
        for (String key:rules.keySet()) {
            if (format(key).equals(format(route))) {
                return rules.get(key);
            }
        }
        return null;
    }

    private String format(String url) {
        if (url.endsWith("/")){
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
