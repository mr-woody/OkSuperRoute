
package com.woodys.router.route.impl;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.woodys.router.Router;
import com.woodys.router.configs.RouterConfiguration;
import com.woodys.router.exception.InterceptorException;
import com.woodys.router.extras.RouteBundleExtras;
import com.woodys.router.interceptors.RouteInterceptor;
import com.woodys.router.interceptors.RealInterceptorChain;
import com.woodys.router.interceptors.RouteInterceptorAction;
import com.woodys.router.launcher.Launcher;
import com.woodys.router.module.ActionRequest;
import com.woodys.router.module.RouteRule;
import com.woodys.router.parser.URIParser;
import com.woodys.router.route.IBaseRoute;
import com.woodys.router.route.IRoute;
import com.woodys.router.callback.InternalCallback;
import com.woodys.router.tools.Cache;
import com.woodys.router.tools.Preconditions;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class BaseRoute<T extends IBaseRoute> implements IRoute, IBaseRoute<T>, RouteInterceptorAction<T> {
    protected Bundle bundle;
    InternalCallback callback;
    protected Uri uri;
    protected Bundle remote;
    protected RouteRule routeRule = null;
    protected Launcher launcher;

    public final IRoute create(Uri uri, RouteRule rule, Bundle remote, InternalCallback callback) {
        try {
            this.uri = uri;
            this.remote = remote;
            this.callback = callback;
            this.routeRule = rule;
            this.bundle = Preconditions.parseToBundle(new URIParser(uri));
            this.bundle.putParcelable(Router.RAW_URI, uri);
            this.launcher = obtainLauncher();
            return this;
        } catch (Throwable e) {
            callback.onOpenFailed(e);
            return new EmptyRoute(callback);
        }
    }

    // =========Unify method of IBaseRoute
    @Override
    public final void open(Context context) {
        try {
            getActionInterceptorChain(context,uri, callback.getExtras());
            launcher.set(uri, bundle, callback.getExtras(), routeRule, remote);
            launcher.open(context);
            callback.onOpenSuccess(routeRule);
        } catch (Throwable e) {
            callback.onOpenFailed(e);
        }

        callback.invoke(context);
    }

    @Override
    public T addExtras(Bundle extras) {
        this.callback.getExtras().addExtras(extras);
        return (T) this;
    }

    // =============RouteInterceptor operation===============
    @Override
    public T addInterceptor(RouteInterceptor interceptor) {
        if (callback.getExtras() != null) {
            callback.getExtras().addInterceptor(interceptor);
        }
        return (T) this;
    }

    @Override
    public T removeInterceptor(RouteInterceptor interceptor) {
        if (callback.getExtras() != null) {
            callback.getExtras().removeInterceptor(interceptor);
        }
        return (T) this;
    }

    @Override
    public T removeAllInterceptors() {
        if (callback.getExtras() != null) {
            callback.getExtras().removeAllInterceptors();
        }
        return (T) this;
    }

    @Override
    public List<RouteInterceptor> getInterceptors() {

        List<RouteInterceptor> interceptors = new ArrayList<>();
        // add global interceptor
        if (RouterConfiguration.get().getInterceptor() != null) {
            interceptors.add(RouterConfiguration.get().getInterceptor());
        }

        // add extra interceptors
        if (callback.getExtras() != null) {
            interceptors.addAll(callback.getExtras().getInterceptors());
        }

        // add interceptors in rule
        for (Class<RouteInterceptor> interceptor : routeRule.getInterceptors()) {
            if (interceptor != null) {
                try {
                    interceptors.add(interceptor.newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(String.format("The interceptor class [%s] should provide a default empty construction", interceptor));
                }
            }
        }

        return interceptors;
    }

    // ========getter/setter============
    public void replaceExtras(RouteBundleExtras extras) {
        this.callback.setExtras(extras);
    }

    public static RouteRule findRule(Uri uri, int type) {
        return Cache.getRouteMapByUri(new URIParser(uri), type);
    }

    // ============abstract methods============
    protected abstract Launcher obtainLauncher() throws Exception;


    protected void getActionInterceptorChain(Context context,Uri uri, RouteBundleExtras extras) {
        ActionRequest request = new ActionRequest(context,uri,extras);
        RouteInterceptor.ActionChain chain = new RealInterceptorChain(getInterceptors(), 0, request);
        chain.proceed(request);
    }

}
