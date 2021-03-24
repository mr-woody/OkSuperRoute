
package com.woodys.router.route.impl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.woodys.router.route.IRoute;
import com.woodys.router.tools.Preconditions;

/**
 * A route tool to open uri by browser
 */
public class BrowserRoute implements IRoute {

    Uri uri;

    private static final BrowserRoute route = new BrowserRoute();

    public static BrowserRoute getInstance () {
        return route;
    }

    @Override
    public void open(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static boolean canOpenRouter(Uri uri) {
        return Preconditions.isHttp(uri.getScheme());
    }

    public IRoute setUri(Uri uri) {
        this.uri = uri;
        return this;
    }

}
