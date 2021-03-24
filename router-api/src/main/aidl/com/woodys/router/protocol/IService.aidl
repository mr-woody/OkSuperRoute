package com.woodys.router.protocol;

import android.content.Intent;
import android.net.Uri;

import com.woodys.router.module.RemoteRule;

/**
 * Remote Service for Host app. it provided a bridge to do multi-module supported.
 */
interface IService {

    void register(String pluginName);
    boolean isRegister(String pluginName);

    void addActivityRules(in Map rules);
    void addActionRules(in Map rules);

    RemoteRule getActionRule(in Uri uri);
    RemoteRule getActivityRule(in Uri uri);
}