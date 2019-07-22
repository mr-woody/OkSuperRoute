
package com.okay.router.route.impl;

import com.okay.router.configs.RouterConfiguration;
import com.okay.router.launcher.ActionLauncher;
import com.okay.router.launcher.Launcher;
import com.okay.router.module.ActionRouteRule;
import com.okay.router.configs.Constants;
import com.okay.router.route.IActionRoute;

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
