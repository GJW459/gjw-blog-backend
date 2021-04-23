package com.gjw.gjwblog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gjw
 * 并发锁
 */
// 此注解运行时发挥作用
@Retention(RetentionPolicy.RUNTIME)
// 此注解使用到方法上
@Target(ElementType.METHOD)
public @interface ThreadLock {
    String method() default "";
}
