package com.d.lib.orm.sqlite.internal;

import android.support.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ColumnType {
    public static final String INTEGER = "INTEGER";
    public static final String REAL = "REAL";
    public static final String TEXT = "TEXT";

    public static String getColumnType(Class type) {
        if (type == int.class
                || type == long.class
                || type == Integer.class
                || type == Long.class) {
            return INTEGER;
        } else if (type == float.class
                || type == double.class
                || type == Float.class
                || type == Double.class) {
            return REAL;
        } else if (type == String.class) {
            return TEXT;
        }
        throw new IllegalArgumentException("Unsupported data type.");
    }

    @StringDef({INTEGER, REAL, TEXT})
    @Target({ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type {

    }
}
