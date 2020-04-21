package com.pwj.admin.aop.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChangeSource {
    String value() default "dataSource1";
}
