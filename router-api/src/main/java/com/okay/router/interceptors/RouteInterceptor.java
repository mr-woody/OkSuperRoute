package com.okay.router.interceptors;

import android.support.annotation.NonNull;

import com.okay.router.module.ActionRequest;

/**
 * 拦截器
 * @author Created by woodys on 2020/7/29.
 * @email yuetao@okay.cn
 */
public interface RouteInterceptor {
    void intercept(@NonNull ActionChain chain);

    interface ActionChain {
        // 表示当前拦截自己处理，不继续传递给下一个拦截器
        void onIntercept();

        // 分发给下一个拦截器
        void proceed(@NonNull ActionRequest actionPost);

        // 获取 ActionRequest
        ActionRequest action();
    }
}
