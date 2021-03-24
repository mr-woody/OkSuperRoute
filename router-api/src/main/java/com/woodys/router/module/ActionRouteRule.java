
package com.woodys.router.module;

import com.woodys.router.executors.MainThreadExecutor;
import com.woodys.router.launcher.ActionLauncher;
import com.woodys.router.route.ActionSupport;

import java.util.concurrent.Executor;

public class ActionRouteRule extends RouteRule<ActionRouteRule, ActionLauncher> {

    private Class<? extends Executor> executor = MainThreadExecutor.class;

    public <T extends ActionSupport> ActionRouteRule(Class<T> clz) {
        super(clz.getCanonicalName());
    }

    public ActionRouteRule(String clzName) {
        super(clzName);
    }

    public ActionRouteRule setExecutorClass(Class<? extends Executor> executor) {
        if (executor != null) {
            this.executor = executor;
        }
        return this;
    }

    public Class<? extends Executor> getExecutor() {
        return executor;
    }
}
