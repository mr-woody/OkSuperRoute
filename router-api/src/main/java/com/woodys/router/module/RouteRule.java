
package com.woodys.router.module;

import android.app.Activity;

import com.woodys.router.interceptors.RouteInterceptor;
import com.woodys.router.launcher.Launcher;
import com.woodys.router.route.ActionSupport;

import java.util.HashMap;

/**
 * An entity to contains some data for route
 *
 */
public class RouteRule<R extends RouteRule, L extends Launcher>{

    public RouteRule(String clzName) {
        this.clzName = clzName;
    }

    /** The class name must be subclass of {@link Activity} or {@link ActionSupport}*/
    private String clzName;
    private HashMap<String,Integer> params = new HashMap<>();
    private Class<? extends L> launcher;
    private Class<? extends RouteInterceptor>[] interceptors = new Class[0];

    public String getRuleClz() {
        return clzName;
    }

    public HashMap<String,Integer> getParams() {
        return params;
    }

    R setParams(HashMap<String,Integer> params) {
        if (params != null) {
            this.params = params;
        }
        return (R) this;
    }

    /**
     * Set a serial of {@link RouteInterceptor} to used, it means when you launch this routing, the interceptors will be triggered.
     * @param classes The array of {@link RouteInterceptor}
     * @return RouteRule
     */
    public R setInterceptors(Class<? extends RouteInterceptor> ... classes) {
        if (classes != null) {
            this.interceptors = classes;
        }
        return (R) this;
    }

    public Class<? extends RouteInterceptor>[] getInterceptors() {
        return interceptors;
    }

    public R setLauncher(Class<? extends L> launcher) {
        this.launcher = launcher;
        return (R) this;
    }

    public Class<? extends L> getLauncher() {
        return launcher;
    }
}
