package com.d.lib.orm.sqlite.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Property {

    /**
     * Name of the database column for this property. Default is field name.
     */
    String nameInDb() default "";

    /**
     * Marks field is the primary key of the entity's table
     */
    int index() default -1;

    /**
     * Marks field is the primary key of the entity's table
     */
    boolean id() default false;

    /**
     * Specifies that id should be auto-incremented (works only for Long/long fields)
     * Autoincrement on SQLite introduces additional resources usage and usually can be avoided
     *
     * @see <a href="https://www.sqlite.org/autoinc.html">SQLite documentation</a>
     */
    boolean autoincrement() default false;

    /**
     * Marks property should have a UNIQUE constraint during table creation.
     */
    boolean unique() default false;

    /**
     * Specifies that property is not null
     */
    boolean notNull() default false;

    /**
     * Specifies that property is null
     */
    boolean nullable() default false;

    /**
     * Transient fields are not persisted in the database.
     */
    boolean columnTransient() default false;

    /**
     * Class of the column which can be persisted in DB.
     * This is limited to all java classes which are supported natively by greenDAO.
     */
    Class columnType() default void.class;
}
