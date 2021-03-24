
package com.woodys.router.route;

import android.content.Context;

import com.woodys.router.callback.InternalCallback;
import com.woodys.router.route.impl.BrowserRoute;

/**
 * The top interface of routing operations. The subclass could be:<br>
 *     {@link BrowserRoute} / {@link IActionRoute} or {@link IActivityRoute}
 */
public interface IRoute {

    /**
     * open route with uri by context
     * @param context The context to launch routing event
     */
    void open(Context context);

    class EmptyRoute implements IRoute{
        protected InternalCallback internal;

        public EmptyRoute(InternalCallback internal) {
            this.internal = internal;
        }

        public InternalCallback getInternal() {
            return internal;
        }

        @Override
        public void open(Context context) {
            internal.invoke(context);
        }
    }
}
