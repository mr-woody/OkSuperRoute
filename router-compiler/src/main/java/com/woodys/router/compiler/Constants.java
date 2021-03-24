package com.woodys.router.compiler;

public class Constants {

    // Application
    public static final String CLASSNAME_APPLICATION = "android.app.Application";
    // Activity
    public static final String CLASSNAME_ACTIVITY = "android.app.Activity";
    // ActionSupport
    public static final String CLASSNAME_ACTION_SUPPORT = "com.woodys.router.route.ActionSupport";
    // RouteRule
    public static final String CLASSNAME_ROUTE_MAP = "com.woodys.router.module.RouteRule";
    // ActivityRouteRule
    public static final String CLASSNAME_ACTIVITY_ROUTE_MAP = "com.woodys.router.module.ActivityRouteRule";
    // ActionRouteRule
    public static final String CLASSNAME_ACTION_ROUTE_MAP = "com.woodys.router.module.ActionRouteRule";
    // CreatorRouteRule
    public static final String CLASSNAME_CREATOR_ROUTE_MAP = "com.woodys.router.module.CreatorRouteRule";
    // RouteCreator
    public static final String CLASSNAME_ROUTE_CREATOR = "com.woodys.router.compiler.RouterMapCreator";
    // MainThreadExecutor
    public static final String CLASSNAME_MAINTHREADEXECUTOR = "com.woodys.router.executors.MainThreadExecutor";
    // DefaultActivityLauncher
    public static final String CLASSNAME_ACTIVITY_LAUNCHER = "com.woodys.router.launcher.ActivityLauncher";
    // DefaultActionLauncher
    public static final String CLASSNAME_ACTION_LAUNCHER = "com.woodys.router.launcher.ActionLauncher";

    // RouteCreator.createActivityRouteRule
    public static final String METHODNAME_CREATE_ACTIVITY_ROUTER = "createActivityRouteRules";
    // RouteCreator.createActionRouteRule
    public static final String METHODNAME_CREATE_ACTION_ROUTER = "createActionRouteRules";
    // RouteCreator.createCreatorRouteRule
    public static final String METHODNAME_CREATE_CREATOR_ROUTER = "createCreatorRouteRule";

}
