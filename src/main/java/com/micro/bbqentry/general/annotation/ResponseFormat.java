package com.micro.bbqentry.general.annotation;

import java.lang.annotation.*;


/**
 * 返回格式-注解
 *
 * @author jockeys
 * @since 2020/3/6
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ResponseFormat {
}
