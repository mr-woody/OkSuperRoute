package com.okay.router;

import android.net.Uri;
import android.os.Bundle;

import com.okay.router.module.CreatorRouteRule;
import com.okay.router.parser.URIParser;
import com.okay.router.callback.ICreatorInjector;
import com.okay.router.tools.Cache;
import com.okay.router.tools.Debugger;
import com.okay.router.tools.Preconditions;

import java.util.Map;

public class InstanceRouter {

    private Uri uri;
    private Bundle extra = new Bundle();

    private InstanceRouter(Uri uri) {
        this.uri = uri;
    }

    static InstanceRouter build(String url) {
        return new InstanceRouter(Uri.parse(url));
    }

    public InstanceRouter addExtras(Bundle extra){
        if (extra != null) {
            this.extra.putAll(extra);
        }
        return this;
    }

    public <T> T createInstance() {
        try {
            Map<String, CreatorRouteRule> rules = Cache.getCreatorRules();
            URIParser parser = new URIParser(uri);
            String route = parser.getRoute();
            CreatorRouteRule rule = rules.get(route);
            if (rule == null) {
                Debugger.d("Could not match rule for this uri");
                return null;
            }

            Object instance = rule.getTarget().newInstance();

            if (instance instanceof ICreatorInjector) {
                Bundle bundle = Preconditions.parseToBundle(parser);
                ((ICreatorInjector) instance).inject(bundle);
            }

            return (T) instance;
        } catch (Throwable e) {
            Debugger.e("Create target class from InstanceRouter failed. cause by:" + e.getMessage(), e);
            return null;
        }
    }
}
