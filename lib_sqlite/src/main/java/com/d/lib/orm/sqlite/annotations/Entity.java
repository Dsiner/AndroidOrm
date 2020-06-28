package com.d.lib.orm.sqlite.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {

    /**
     * Specifies the name on the DB side (e.g. table name) this entity maps to. By default, the name is based on the entities class name.
     */
    String nameInDb() default "";
}
