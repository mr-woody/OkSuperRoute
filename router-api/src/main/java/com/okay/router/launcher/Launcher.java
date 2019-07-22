
package com.okay.router.launcher;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.okay.router.Router;
import com.okay.router.activityresult.ActivityResultCallback;
import com.okay.router.extras.RouteBundleExtras;
import com.okay.router.module.RouteRule;
import com.okay.router.configs.Constants;

import java.util.Random;

/**
 * The base launcher class.
 */
public abstract class Launcher {
    private static Random sCodeGenerator = new Random();

    protected Uri uri;
    protected Bundle bundle;
    protected RouteBundleExtras extras;
    protected RouteRule rule;
    protected Bundle remote;

    protected Activity resumeContext;
    protected ActivityResultCallback resultCallback;
    protected Bundle options;

    /**
     * Requires to open with this launcher.
     * @param context context
     * @throws Exception Some error occurs.
     */
    public abstract void open(Context context) throws Exception;

    /**
     * Set all of extras data to used.
     * @param uri The route uri.
     * @param bundle The bundle data that is parsed by uri params.
     * @param extras The extras data you set via {@link Router#getRoute()}
     * @param rule The rule that associate with the uri.
     */
    public final void set(Uri uri, Bundle bundle, RouteBundleExtras extras,  RouteRule rule, Bundle remote) {
        this.uri = uri;
        this.bundle = bundle;
        this.extras = extras;
        this.rule = rule;
        this.remote = remote;

        resumeContext = extras.getValue(Constants.KEY_RESUME_CONTEXT);
        resultCallback = extras.getValue(Constants.KEY_RESULT_CALLBACK);
        options = extras.getValue(Constants.KEY_ACTIVITY_OPTIONS);

        int requestCode = extras.getRequestCode();
        if (resultCallback != null && requestCode == -1) {
            extras.setRequestCode(sCodeGenerator.nextInt(0x0000ffff));
        }
    }

}
