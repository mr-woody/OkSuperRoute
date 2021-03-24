
package com.woodys.router.compiler;

import com.woodys.router.module.ActionRouteRule;
import com.woodys.router.module.ActivityRouteRule;
import com.woodys.router.module.CreatorRouteRule;

import java.util.Map;

/**
 * @auther by yuetao on 2019/07/16
 * @description:注解生成的代理类会实现此接口，谨慎修改接口内容及路径
 */
public interface RouterMapCreator {

    /**
     * create route rules for ActivityRoute
     * @return A map that contains of all Activity route rules.
     */
    Map<String, ActivityRouteRule> createActivityRouteRules();

    /**
     * create route rules for ActionRoute
     * @return A map that contains of all Action route rules.
     */
    Map<String, ActionRouteRule> createActionRouteRules();

    Map<String, CreatorRouteRule> createCreatorRouteRule();

}
