package com.yc.springframework.stereotype;

import java.lang.annotation.*;

@Target(value = {ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Resource {
    String name() default "";
}
