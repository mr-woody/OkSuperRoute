package com.woodys.router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation defined some basic configurations
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface RouteConfig {

    /**
     * @return a base url to combine with the route you set via {@link Route}.
     */
    String baseUrl() default "";

    /**
     * Defined a basic package value.when you haven't set a package value in {@link Route},this word should be used instead
     * @return a basic package value.
     */
    String pack() default "";
}
