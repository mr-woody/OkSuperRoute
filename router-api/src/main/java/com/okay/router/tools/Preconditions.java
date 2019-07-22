
package com.okay.router.tools;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.okay.router.parser.URIParser;


import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class Preconditions {

    /**
     * Adjust if the scheme is http or https
     * @param scheme scheme for uri
     * @return return true if is http or https
     */
    public static boolean isHttp (String scheme) {
        return "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme);
    }

    public static String format(String url) {
        if (url.endsWith("/")){
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

    public static Bundle parseToBundle(URIParser parser) {
        Bundle bundle = new Bundle();
        Map<String, String> params = parser.getParams();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            bundle.putString(key, params.get(key));
        }
        return bundle;
    }

    public static boolean isValid(Activity activity) {
        return activity != null
                && !activity.isFinishing()
                && !(Build.VERSION.SDK_INT >= 17 && activity.isDestroyed());
    }

    public static boolean isValidUri(Uri uri) {
        return uri != null && !TextUtils.isEmpty(uri.getScheme()) && !TextUtils.isEmpty(uri.getHost());
    }
}
