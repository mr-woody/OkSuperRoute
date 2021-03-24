
package com.woodys.router.route.impl;

import com.woodys.router.configs.RouterConfiguration;
import com.woodys.router.launcher.ActionLauncher;
import com.woodys.router.launcher.Launcher;
import com.woodys.router.module.ActionRouteRule;
import com.woodys.router.configs.Constants;
import com.woodys.router.route.IActionRoute;

import java.util.concurrent.Executor;

public class ActionRoute extends BaseRoute<IActionRoute> implements IActionRoute {

    @Override
    protected Launcher obtainLauncher() throws Exception{
        ActionRouteRule rule = (ActionRouteRule) routeRule;
        Class<? extends ActionLauncher> launcher = rule.getLauncher();
        if (launcher == null) {
            launcher = RouterConfiguration.get().getActionLauncher();
        }
        return launcher.newInstance();
    }

    @Override
    public void setExecutor(Executor executor) {
        callback.getExtras().putValue(Constants.KEY_ACTION_EXECUTOR, executor);
    }
}
