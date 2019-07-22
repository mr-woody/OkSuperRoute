
package com.okay.router.callback;

import android.net.Uri;

import com.okay.router.exception.NotFoundException;
import com.okay.router.module.ActionRouteRule;
import com.okay.router.module.ActivityRouteRule;
import com.okay.router.module.RouteRule;

/**
 * The route callback to notify the status of routing event.
 */
public interface RouteCallback {

    /**
     * This method will be invoked when there is no routing matched with uri.
     * @param uri uri the uri to open
     * @param e {@link NotFoundException}
     */
    void notFound(Uri uri, NotFoundException e);

    /**
     * This method will be invoked when the routing task opened successful
     *
     * @param uri the uri to open
     * @param rule The uri matching rule, it could be {@link ActionRouteRule} or {@link ActivityRouteRule}
     */
    void onOpenSuccess(Uri uri, RouteRule rule);

    /**
     * A callback method to notice that you occurs some exception.<br>
     *     exclude <i>{@link NotFoundException}</i>
     * @param uri the uri to open
     * @param e the exception
     */
    void onOpenFailed(Uri uri, Throwable e);


}
