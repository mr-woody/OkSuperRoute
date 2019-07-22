
package com.okay.router.route;

import com.okay.router.callback.InternalCallback;

import java.util.concurrent.Executor;

/**
 * <p>
 *  Base on the {@link IBaseRoute}
 * </p>
 */
public interface IActionRoute extends IBaseRoute<IActionRoute>{

    void setExecutor(Executor executor);

    class EmptyActionRoute extends EmptyBaseRoute<IActionRoute> implements IActionRoute {

        public EmptyActionRoute(InternalCallback internal) {
            super(internal);
        }

        @Override
        public void setExecutor(Executor executor) { }
    }
}
