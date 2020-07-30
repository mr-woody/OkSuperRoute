
package com.okay.router.route;

import android.os.Bundle;

import com.okay.router.extras.RouteBundleExtras;
import com.okay.router.interceptors.RouteInterceptor;
import com.okay.router.interceptors.RouteInterceptorAction;
import com.okay.router.callback.InternalCallback;

import java.util.List;

/**
 * Base on {@link IRoute} and {@link RouteInterceptorAction}, it subclass could be one of the <br>
 *     {@link IActionRoute} and {@link IActivityRoute}
 * @param <T> The real type
 */
public interface IBaseRoute<T extends IBaseRoute> extends IRoute, RouteInterceptorAction<T>{
    /**
     * add extra bundle data to {@link RouteBundleExtras}
     * @param extras bundle data
     * @return {@link IBaseRoute}
     * @see IActionRoute
     * @see IActivityRoute
     */
    T addExtras(Bundle extras);

    @SuppressWarnings("unchecked")
    class EmptyBaseRoute<T extends IBaseRoute> extends EmptyRoute implements IBaseRoute<T> {

        public EmptyBaseRoute(InternalCallback internal) {
            super(internal);
        }

        @Override
        public T addExtras(Bundle extras) {
            internal.getExtras().addExtras(extras);
            return (T) this;
        }

        @Override
        public T addInterceptor(RouteInterceptor interceptor) {
            internal.getExtras().addInterceptor(interceptor);
            return (T) this;
        }

        @Override
        public T removeInterceptor(RouteInterceptor interceptor) {
            internal.getExtras().removeInterceptor(interceptor);
            return (T) this;
        }

        @Override
        public T removeAllInterceptors() {
            internal.getExtras().removeAllInterceptors();
            return (T) this;
        }

        @Override
        public List<RouteInterceptor> getInterceptors() {
            return internal.getExtras().getInterceptors();
        }
    }
}
