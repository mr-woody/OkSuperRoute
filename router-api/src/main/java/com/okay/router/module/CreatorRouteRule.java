package com.okay.router.module;

import com.okay.router.launcher.Launcher;

/**
 * @author okay on 2018/5/25
 */
public class CreatorRouteRule extends RouteRule<CreatorRouteRule, Launcher> {

    private Class target;

    public CreatorRouteRule(Class clz) {
        super(clz.getCanonicalName());
        this.target = clz;
    }

    public Class getTarget() {
        return target;
    }
}
