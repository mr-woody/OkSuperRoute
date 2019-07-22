
package com.okay.router.launcher;

import com.okay.router.module.ActionRouteRule;
import com.okay.router.tools.Cache;
import com.okay.router.configs.Constants;

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
