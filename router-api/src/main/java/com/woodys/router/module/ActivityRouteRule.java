
package com.woodys.router.module;


import android.app.Activity;

import com.woodys.router.launcher.ActivityLauncher;

public class ActivityRouteRule extends RouteRule<ActivityRouteRule, ActivityLauncher> {

    public <T extends Activity> ActivityRouteRule(Class<T> clz) {
        super(clz.getCanonicalName());
    }

    public ActivityRouteRule(String clzName) {
        super(clzName);
    }
}
