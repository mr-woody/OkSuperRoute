
package com.woodys.router;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.woodys.router.activityresult.ActivityResultCallback;
import com.woodys.router.exception.NotFoundException;
import com.woodys.router.extras.RouteBundleExtras;
import com.woodys.router.interceptors.RouteInterceptor;
import com.woodys.router.module.RouteRule;
import com.woodys.router.protocol.HostServiceWrapper;
import com.woodys.router.route.impl.ActionRoute;
import com.woodys.router.route.impl.ActivityRoute;
import com.woodys.router.route.impl.BaseRoute;
import com.woodys.router.route.impl.BrowserRoute;
import com.woodys.router.route.IActionRoute;
import com.woodys.router.route.IActivityRoute;
import com.woodys.router.route.IBaseRoute;
import com.woodys.router.route.IRoute;
import com.woodys.router.callback.InternalCallback;
import com.woodys.router.callback.RouteCallback;
import com.woodys.router.tools.Cache;
import com.woodys.router.configs.Constants;
import com.woodys.router.tools.Preconditions;

import java.util.concurrent.Executor;


/**
 * Entry of Router。
 *
 */
public final class Router{

    /**
     * The key of raw uri. you can obtains it uri by this key, for eg:
     * <pre>
     *      <i><b>bundle.getParcelable(Router.RAW_URI)</b></i>
     * </pre>
     */
    public static final String RAW_URI = "_ROUTER_RAW_URI_KEY_";
    public static boolean DEBUG = false;

    private Uri uri;
    private InternalCallback internalCallback;

    private Router(Uri uri) {
        this.uri = uri;
        internalCallback = new InternalCallback(this.uri);
    }

    /**
     * Create Router by url string
     * @param url The url to create Router
     * @return new Router
     */
    public static Router create(String url) {
        return new Router(Uri.parse(url == null?"":url));
    }

    /**
     * Create Router by uri
     * @param uri the uri to create Router
     * @return new Router
     */
    public static Router create(Uri uri) {
        return new Router(uri);
    }

    public static InstanceRouter createInstanceRouter(String url) {
        return InstanceRouter.build(url);
    }

    /**
     * Set a callback to notify the user when the routing were success or failure.
     * @param callback The callback you set.
     * @return Router itself
     */
    public Router setCallback (RouteCallback callback) {
        this.internalCallback.setCallback(callback);
        return this;
    }

    public Router addInterceptor(RouteInterceptor interceptor) {
        this.internalCallback.getExtras().addInterceptor(interceptor);
        return this;
    }

    public Router requestCode(int requestCode) {
        this.internalCallback.getExtras().setRequestCode(requestCode);
        return this;
    }

    public Router resultCallback(ActivityResultCallback callback) {
        this.internalCallback.getExtras().putValue(Constants.KEY_RESULT_CALLBACK, callback);
        return this;
    }

    public Router addFlags(int flag) {
        this.internalCallback.getExtras().addFlags(flag);
        return this;
    }

    public Router setAnim(int enterAnim, int exitAnim) {
        this.internalCallback.getExtras().setInAnimation(enterAnim);
        this.internalCallback.getExtras().setOutAnimation(exitAnim);
        return this;
    }

    public Router setOptions(Bundle options) {
        this.internalCallback.getExtras().putValue(Constants.KEY_ACTIVITY_OPTIONS, options);
        return this;
    }

    public Router addExtras(Bundle extras) {
        this.internalCallback.getExtras().addExtras(extras);
        return this;
    }

    public Router setExecutor(Executor executor) {
        this.internalCallback.getExtras().putValue(Constants.KEY_ACTION_EXECUTOR, executor);
        return this;
    }

    /**
     * Restore a Routing event from last uri and extras.
     * @param uri last uri
     * @param extras last extras
     * @return The restored routing find by {@link Router#getRoute()}
     */
    public static IRoute resume(Uri uri, RouteBundleExtras extras) {
        IRoute route = Router.create(uri).getRoute();
        if (route instanceof BaseRoute) {
            ((BaseRoute) route).replaceExtras(extras);
        }
        return route;
    }

    /**
     * launch routing task.
     * @param context context to launched
     */
    public void open(Context context) {
        getRoute().open(context);
    }


    /**
     * Get route by uri, you should get a route by this way and set some extras data before open
     * @return
     *  An IRoute object.it will be {@link BrowserRoute}, {@link ActivityRoute} or {@link ActionRoute}.<br>
     *  and it also will be {@link IRoute.EmptyRoute} if it not found
     */
    public IRoute getRoute () {
        IRoute route = getLocalRoute();
        if (!(route instanceof IRoute.EmptyRoute)) {
            return route;
        }
        route = HostServiceWrapper.create(uri, internalCallback);
        if (route instanceof IRoute.EmptyRoute) {
            notifyNotFound(String.format("find Route by %s failed:",uri));
        }
        return route;
    }

    private IRoute getLocalRoute() {
        try {
            RouteRule rule;
            if (!Preconditions.isValidUri(uri)) {
                return new IRoute.EmptyRoute(internalCallback);
            } else if ((rule = ActionRoute.findRule(uri, Cache.TYPE_ACTION_ROUTE)) != null) {
                return new ActionRoute().create(uri, rule, new Bundle(), internalCallback);
            } else if ((rule = ActivityRoute.findRule(uri, Cache.TYPE_ACTIVITY_ROUTE)) != null) {
                return new ActivityRoute().create(uri, rule, new Bundle(), internalCallback);
            } else if (BrowserRoute.canOpenRouter(uri)) {
                return BrowserRoute.getInstance().setUri(uri);
            } else {
                return new IRoute.EmptyRoute(internalCallback);
            }
        } catch (Exception e) {
            internalCallback.onOpenFailed(e);
            return new IRoute.EmptyRoute(internalCallback);
        }
    }

    /**
     * <p>
     * Get {@link IBaseRoute} by uri, it could be one of {@link IActivityRoute} or {@link IActionRoute}.
     * and you can add some {@link android.os.Bundle} data and {@link RouteInterceptor} into it.
     * </p>
     * @return returns an {@link IBaseRoute} finds by uri or {@link IBaseRoute.EmptyBaseRoute} for not found
     */
    public IBaseRoute getBaseRoute() {
        IRoute route = getRoute();
        if (route instanceof IBaseRoute) {
            return (IBaseRoute) route;
        }

        notifyNotFound(String.format("find BaseRoute by %s failed, but is %s",uri, route.getClass().getSimpleName()));
        return new IBaseRoute.EmptyBaseRoute(internalCallback);
    }

    /**
     * Get {@link IActivityRoute} by uri,you should get a route by this way and set some extras data before open
     * @return returns an {@link IActivityRoute} finds by uri or {@link IActivityRoute.EmptyActivityRoute} for not found.
     */
    public IActivityRoute getActivityRoute() {
        IRoute route = getRoute();
        if (route instanceof IActivityRoute) {
            return (IActivityRoute) route;
        }

        // return an empty route to avoid NullPointException
        notifyNotFound(String.format("find ActivityRoute by %s failed, but is %s",uri, route.getClass().getSimpleName()));
        return new IActivityRoute.EmptyActivityRoute(internalCallback);
    }

    /**
     * Get {@link IActionRoute} by uri,you should get a route by this way and set some extras data before open
     * @return returns an {@link IActionRoute} finds by uri or {@link IActionRoute.EmptyActionRoute} for not found.
     */
    public IActionRoute getActionRoute() {
        IRoute route = getRoute();
        if (route instanceof IActionRoute) {
            return (IActionRoute) route;
        }

        notifyNotFound(String.format("find ActionRoute by %s failed, but is %s",uri, route.getClass().getSimpleName()));
        // return a empty route to avoid NullPointException
        return new IActionRoute.EmptyActionRoute(internalCallback);
    }

    private void notifyNotFound(String msg) {
        internalCallback.onOpenFailed(new NotFoundException(msg));
    }
}
