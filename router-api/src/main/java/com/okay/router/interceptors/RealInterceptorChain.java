package com.okay.router.interceptors;


import com.okay.router.exception.InterceptorException;
import com.okay.router.module.ActionRequest;

import java.util.ArrayList;
import java.util.List;

public class RealInterceptorChain<T> implements RouteInterceptor.ActionChain {
    // 是否被拦截了
    private boolean isInterrupt = false;
    private List<RouteInterceptor> interceptors = new ArrayList<>();
    private ActionRequest request;
    private int index;

    public RealInterceptorChain(List<RouteInterceptor> interceptors, int index, ActionRequest request){
        if(interceptors == null) {
            interceptors = new ArrayList<>();
        }
        this.interceptors = interceptors;
        this.request = request;
        this.index = index;
    }


    @Override
    public ActionRequest action() {
        return request;
    }

    /**
     * 拦截器启动运行
     * @return
     */
    @Override
    public void proceed(ActionRequest request) {
        if (isInterrupt){
            throw new InterceptorException(interceptors.get(index-1));
        }
        // If there's another interceptor in the chain, call that.
        if (index < interceptors.size()) {
            RouteInterceptor interceptor = interceptors.get(index);
            RouteInterceptor.ActionChain next = new RealInterceptorChain(interceptors,index + 1, request);
            interceptor.intercept(next);
        }
    }

    @Override
    public void onIntercept() {
        this.isInterrupt = true;
    }
}
