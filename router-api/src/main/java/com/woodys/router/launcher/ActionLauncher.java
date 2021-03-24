
package com.woodys.router.launcher;

import com.woodys.router.module.ActionRouteRule;
import com.woodys.router.tools.Cache;
import com.woodys.router.configs.Constants;

import java.util.concurrent.Executor;

/**
 * The base class of <i><b>Action Launcher</b></i>
 *
 * <p>
 *     The default impl is {@link DefaultActionLauncher}
 * </p>
 */
public abstract class ActionLauncher extends Launcher {

    /**
     * @return returns a executor instance to switching thread.
     */
    protected Executor getExecutor() {
        Executor executor = extras.getValue(Constants.KEY_ACTION_EXECUTOR);
        if (executor == null) {
            executor = Cache.findOrCreateExecutor(((ActionRouteRule) rule).getExecutor());
        }
        return executor;
    }
}
