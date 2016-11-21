package com.annotations;

/**
 * Created by MonsterX on 16.10.2016.
 */
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Retention(RUNTIME)
@Target(METHOD)
public @interface Name {
    String value() default "name";
}
