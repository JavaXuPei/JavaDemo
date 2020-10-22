package com.bjy.javademo.httpaspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author bjy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HttpFrequency {
    //访问路径
    String      key();
    // 受限次数
    int         frequency() default  1;
    // 有效时间
    int         time() default 1;
}
