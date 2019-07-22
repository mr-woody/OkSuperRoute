
package com.okay.router.compiler;

import com.okay.router.module.ActionRouteRule;
import com.okay.router.module.ActivityRouteRule;
import com.okay.router.module.CreatorRouteRule;

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
