package com.d.lib.orm.sqlite.internal;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.d.lib.orm.sqlite.annotations.Entity;
import com.d.lib.orm.sqlite.annotations.Property;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * DaoHelper
 * Created by D on 2020/9/11.
 */
public class DaoHelper {

    public static class GsonFormat {
        private static class Singleton {
            private static final Gson gson = new Gson();
        }

        public static <T> T fromMap(LinkedHashMap<String, Object> map, Type type) {
            return Singleton.gson.fromJson(Singleton.gson.toJson(map), type);
        }

        public static <T> LinkedHashMap<String, Object> toHashMap(T entity) {
            String json = Singleton.gson.toJson(entity);
            Type type = new TypeToken<LinkedHashMap<String, String>>() {
            }.getType();
            return Singleton.gson.fromJson(json, type);
        }
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, Class clz, boolean ifNotExists) {
        if (!clz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Undefined table name.");
        }
        String constraint = ifNotExists ? " IF NOT EXISTS " : "";
        Entity entity = (Entity) clz.getAnnotation(Entity.class);
        String tableName = !TextUtils.isEmpty(entity.nameInDb()) ?
                entity.nameInDb() : clz.getSimpleName();
        StringBuilder sql = new StringBuilder("CREATE TABLE" + constraint + "\"" + tableName + "\"" + " (");

        List<Field> fields = getPropertyFields(clz);
        if (fields.size() <= 0) {
            throw new IllegalArgumentException("No table fields are defined in the "
                    + tableName + " table.");
        }
        for (Field field : fields) {
            Property property = (Property) field.getAnnotation(Property.class);
            String columnName = !TextUtils.isEmpty(property.nameInDb()) ?
                    property.nameInDb() : field.getName();
            Class type = property.columnType() != void.class ?
                    property.columnType() : field.getType();
            sql.append("\"" + columnName + "\""
                    + " " + ColumnType.getColumnType(type)
                    + (property.id() ? " PRIMARY KEY" : "")
                    + (property.autoincrement() ? " AUTOINCREMENT" : "")
                    + (property.notNull() ? " NOT NULL" : "")
                    + (property.nullable() ? " NULL" : "")
                    + (property.unique() ? " UNIQUE" : "")
                    + ",");
        }
        int index = sql.lastIndexOf(",");
        sql.replace(index, index + 1, ");");
        db.execSQL(sql.toString());
        Log.d("Sql", "Sql createTable:" + sql.toString());
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, Class clz, boolean ifExists) {
        if (!clz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Undefined table name.");
        }
        Entity entity = (Entity) clz.getAnnotation(Entity.class);
        String tableName = !TextUtils.isEmpty(entity.nameInDb()) ?
                entity.nameInDb() : clz.getSimpleName();
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"" + tableName + "\"";
        db.execSQL(sql);
    }

    @NonNull
    public static List<Field> getPropertyFields(Class clz) {
        List<Field> fields = new ArrayList<>();
        Field[] fieldArray = clz.getDeclaredFields();
        if (fieldArray != null) {
            for (Field field : fieldArray) {
                if (field.isAnnotationPresent(Property.class)) {
                    fields.add(field);
                }
            }
        }
        Collections.sort(fields, new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                int index1 = ((Property) o1.getAnnotation(Property.class)).index();
                int index2 = ((Property) o2.getAnnotation(Property.class)).index();
                return (index1 < index2) ? -1 : ((index1 == index2) ? 0 : 1);
            }
        });
        return fields;
    }
}
