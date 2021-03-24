package com.woodys.router.module;

import android.content.Context;
import android.net.Uri;

import com.woodys.router.extras.RouteBundleExtras;

public class ActionRequest {
    public Context context;
    public Uri uri;
    public RouteBundleExtras extras;

    public ActionRequest(Context context, Uri uri, RouteBundleExtras extras) {
        this.context = context;
        this.uri = uri;
        this.extras = extras;
    }
}
