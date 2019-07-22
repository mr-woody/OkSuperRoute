
package com.okay.router.route.impl;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.okay.router.configs.RouterConfiguration;
import com.okay.router.activityresult.ActivityResultCallback;
import com.okay.router.launcher.ActivityLauncher;
import com.okay.router.launcher.Launcher;
import com.okay.router.module.ActivityRouteRule;
import com.okay.router.configs.Constants;
import com.okay.router.route.IActivityRoute;

/**
 * A route tool to check route rule by uri and launch activity
 */
public class ActivityRoute extends BaseRoute<IActivityRoute> implements IActivityRoute {

    @Override
    public Intent createIntent(Context context) {
        ActivityLauncher activityLauncher = (ActivityLauncher) launcher;
        activityLauncher.set(uri, bundle, callback.getExtras(), routeRule, remote);
        return activityLauncher.createIntent(context);
    }

    @Override
    public IActivityRoute requestCode(int requestCode) {
        this.callback.getExtras().setRequestCode(requestCode);
        return this;
    }

    @Override
    public IActivityRoute resultCallback(ActivityResultCallback callback) {
        this.callback.getExtras().putValue(Constants.KEY_RESULT_CALLBACK, callback);
        return this;
    }

    @Override
    public IActivityRoute setOptions(Bundle options) {
        this.callback.getExtras().putValue(Constants.KEY_ACTIVITY_OPTIONS, options);
        return this;
    }

    @Override
    public IActivityRoute setAnim(int enterAnim, int exitAnim) {
        this.callback.getExtras().setInAnimation(enterAnim);
        this.callback.getExtras().setOutAnimation(exitAnim);
        return this;
    }

    @Override
    public IActivityRoute addFlags(int flag) {
        this.callback.getExtras().addFlags(flag);
        return this;
    }

    @Override
    public void open(Fragment fragment) {
        try {
            checkInterceptor(uri, callback.getExtras(), fragment.getActivity(), getInterceptors());
            ActivityLauncher activityLauncher = (ActivityLauncher) launcher;
            activityLauncher.set(uri, bundle, callback.getExtras(), routeRule, remote);
            activityLauncher.open(fragment);
            callback.onOpenSuccess(routeRule);
        } catch (Throwable e) {
            callback.onOpenFailed(e);
        }

        callback.invoke(fragment.getActivity());
    }

    @Override
    protected Launcher obtainLauncher() throws Exception{
        ActivityRouteRule rule = (ActivityRouteRule) routeRule;
        Class<? extends ActivityLauncher> launcher = rule.getLauncher();
        if (launcher == null) {
            launcher = RouterConfiguration.get().getActivityLauncher();
        }
        return launcher.newInstance();
    }
}
