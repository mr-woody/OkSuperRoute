package com.woodys.router.compiler.model;

import com.woodys.router.annotation.RouteConfig;
import com.woodys.router.compiler.util.Utils;

public class BasicConfigurations {
    public String baseUrl;
    public String pack = "com.woodys.router";

    public BasicConfigurations(RouteConfig config) {
        if (config == null) return;

        if (!Utils.isEmpty(config.pack())) {
            this.pack = config.pack();
        }
        this.baseUrl = parseBaseUrl(config);
    }

    private String parseBaseUrl(RouteConfig config) {
        if (!Utils.isEmpty(config.baseUrl())) {
            return config.baseUrl();
        }
        return "";
    }
}
