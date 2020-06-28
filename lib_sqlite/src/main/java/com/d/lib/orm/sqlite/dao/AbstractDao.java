package com.d.lib.orm.sqlite.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.d.lib.orm.sqlite.annotations.Entity;
import com.d.lib.orm.sqlite.annotations.Property;
import com.d.lib.orm.sqlite.db.AbstractDatabase;
import com.d.lib.orm.sqlite.property.ColumnType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDao<T, KEY> {
    protected final AbstractDatabase mDatabase;
    protected final String mTableName;
    protected final LinkedHashMap<String, String> mColumnNames = new LinkedHashMap<>();
    protected String mKeyName;

    static class GsonFormat {
        private static class Singleton {
            private static final Gson gson = new Gson();
        }

        static <T> T fromMap(LinkedHashMap<String, Object> map, Type type) {
            return Singleton.gson.fromJson(Singleton.gson.toJson(map), type);
        }

        static <T> LinkedHashMap<String, Object> toHashMap(T entity) {
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
        StringBuilder sql = new StringBuilder("CREATE TABLE" + constraint + tableName + " (");

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
            sql.append(columnName
                    + " " + ColumnType.getColumnType(type)
                    + (property.id() ? " PRIMARY KEY" : "")
                    + (property.autoincrement() ? " AUTOINCREMENT" : "")
                    + (property.notNull() ? " NOT NULL" : "")
                    + (property.unique() ? " UNIQUE" : "")
                    + ",");
        }
        int index = sql.lastIndexOf(",");
        sql.replace(index, index + 1, ");");
        db.execSQL(sql.toString());
        Log.d("sql", sql.toString());
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
    private static List<Field> getPropertyFields(Class clz) {
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

    public AbstractDao(AbstractDatabase database) {
        this.mDatabase = database;
        Class clz = (Class) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
        Entity entity = (Entity) clz.getAnnotation(Entity.class);
        mTableName = !TextUtils.isEmpty(entity.nameInDb()) ?
                entity.nameInDb() : clz.getSimpleName();

        List<Field> fields = getPropertyFields(clz);
        for (Field field : fields) {
            Property property = (Property) field.getAnnotation(Property.class);
            String columnName = !TextUtils.isEmpty(property.nameInDb()) ?
                    property.nameInDb() : field.getName();
            mColumnNames.put(field.getName(), columnName);
            if (property.id()) {
                mKeyName = columnName;
            }
        }
    }

    public String getTableName() {
        return mTableName;
    }

    public String getKeyName() {
        return mKeyName;
    }

    public KEY getKey(T entity) {
        LinkedHashMap<String, Object> map = GsonFormat.toHashMap(entity);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (TextUtils.equals(mKeyName, entry.getKey())) {
                return (KEY) entry.getValue();
            }
        }
        return null;
    }

    public boolean hasKey(T entity) {
        return getKey(entity) != null;
    }

    protected ContentValues getContentValues(T entity) {
        ContentValues values = new ContentValues();
        LinkedHashMap<String, Object> map = GsonFormat.toHashMap(entity);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String columnName = mColumnNames.get(entry.getKey());
            if (TextUtils.isEmpty(columnName)) {
                continue;
            }
            Object value = entry.getValue();
            String columnType = ColumnType.getColumnType(value.getClass());
            if (TextUtils.equals(ColumnType.INTEGER, columnType)) {
                values.put(columnName, (long) value);
            } else if (TextUtils.equals(ColumnType.REAL, columnType)) {
                values.put(columnName, (double) value);
            } else if (TextUtils.equals(ColumnType.TEXT, columnType)) {
                values.put(columnName, (String) value);
            }
        }
        return values;
    }

    protected T readEntity(Cursor cursor) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        LinkedHashMap<String, Object> columns = map(cursor);
        for (Map.Entry<String, String> entry : mColumnNames.entrySet()) {
            String filedName = entry.getKey();
            String columnName = entry.getValue();
            map.put(filedName, columns.get(columnName));
        }
        Type type = ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
        return GsonFormat.fromMap(map, type);
    }

    public void insert(final T entity) {
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                ContentValues values = getContentValues(entity);
                db.insertOrThrow(getTableName(), null, values);
                return null;
            }
        });
    }

    public void insertOrReplace(final T entity) {
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                if (!hasKey(entity) || query(getKey(entity)) == null) {
                    insert(entity);
                } else {
                    update(entity);
                }
                return null;
            }
        });
    }

    public void insertInTx(final List<T> list) {
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                for (T entity : list) {
                    ContentValues values = getContentValues(entity);
                    db.insertOrThrow(getTableName(), null, values);
                }
                return null;
            }
        });
    }

    public void insertOrReplaceInTx(final List<T> list) {
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                for (T entity : list) {
                    if (!hasKey(entity) || query(getKey(entity)) == null) {
                        insert(entity);
                    } else {
                        update(entity);
                    }
                }
                return null;
            }
        });
    }

    public void delete(final T entity) {
        deleteByKey(getKey(entity));
    }

    public void deleteByKey(final KEY key) {
        if (key == null) {
            return;
        }
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                String whereClause = getKeyName() + " = ?";
                String[] whereArgs = new String[]{String.valueOf(key)};
                db.delete(getTableName(), whereClause, whereArgs);
                return null;
            }
        });
    }

    public void delete(final List<T> list) {
        if (list == null || list.size() <= 0 || TextUtils.isEmpty(getKeyName())) {
            return;
        }
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                String whereClause = getKeyName() + " = ?";
                for (T entity : list) {
                    KEY key = getKey(entity);
                    if (key == null) {
                        continue;
                    }
                    String[] whereArgs = new String[]{String.valueOf(key)};
                    db.delete(getTableName(), whereClause, whereArgs);
                }
                return null;
            }
        });
    }

    @Deprecated
    public void deleteInTx(final List<T> list) {
        if (list == null || list.size() <= 0 || TextUtils.isEmpty(getKeyName())) {
            return;
        }
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                StringBuilder args = new StringBuilder("(");
                for (T entity : list) {
                    args.append(getKey(entity) + ",");
                }
                int index = args.lastIndexOf(",");
                args.replace(index, index + 1, ")");
                db.rawQuery("delete from " + getTableName()
                        + " where " + getKeyName() + " in "
                        + args.toString(), null);
                return null;
            }
        });
    }

    public void deleteAll() {
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                db.delete(getTableName(), null, null);
                return null;
            }
        });
    }

    public void update(final T entity) {
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                ContentValues values = getContentValues(entity);
                String whereClause = getKeyName() + " = ?";
                String[] whereArgs = new String[]{String.valueOf(getKey(entity))};
                db.update(getTableName(), values, whereClause, whereArgs);
                return null;
            }
        });
    }

    public T query(final KEY key) {
        if (key == null) {
            return null;
        }
        final Object[] entity = new Object[1];
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                String whereClause = getKeyName() + " = ?";
                String[] whereArgs = new String[]{String.valueOf(key)};
                Cursor cursor = db.rawQuery("select * from " + getTableName()
                        + " where " + whereClause, whereArgs);
                if (cursor != null && cursor.moveToFirst()) {
                    entity[0] = readEntity(cursor);
                }
                return cursor;
            }
        });
        return (T) entity[0];
    }

    @NonNull
    public List<T> queryLimit(final int limit) {
        final List<T> list = new ArrayList<>();
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                Cursor cursor = db.rawQuery("select * from " + getTableName()
                        + " limit " + limit, null);
                while (cursor.moveToNext()) {
                    list.add(readEntity(cursor));
                }
                return cursor;
            }
        });
        return list;
    }

    @NonNull
    public List<T> queryOffset(final int beginIndex, final int endIndex) {
        final List<T> list = new ArrayList<>();
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                Cursor cursor = db.rawQuery("select * from " + getTableName()
                        + " limit " + beginIndex + "," + endIndex, null);
                while (cursor.moveToNext()) {
                    list.add(readEntity(cursor));
                }
                return cursor;
            }
        });
        return list;
    }

    @NonNull
    public List<T> queryAll() {
        final List<T> list = new ArrayList<>();
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                Cursor cursor = db.query(getTableName(),
                        null, null, null, null, null,
                        getKeyName());
                while (cursor.moveToNext()) {
                    list.add(readEntity(cursor));
                }
                return cursor;
            }
        });
        return list;
    }

    protected void executeInTx(@NonNull final Callback callback) {
        SQLiteDatabase db = open();
        db.beginTransaction();
        Cursor cursor = null;
        try {
            cursor = callback.execute(db);
            db.setTransactionSuccessful();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            closeQuietly(cursor);
            close();
        }
    }

    protected SQLiteDatabase open() {
        return mDatabase.open();
    }

    protected void close() {
        mDatabase.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public static LinkedHashMap<String, Object> map(Cursor cursor) {
        if (cursor == null) {
            return new LinkedHashMap<>();
        }
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            String key = cursor.getColumnName(i);
            switch (cursor.getType(i)) {
                case Cursor.FIELD_TYPE_STRING:
                    map.put(key, cursor.getString(i));
                    break;

                case Cursor.FIELD_TYPE_INTEGER:
                    map.put(key, cursor.getLong(i));
                    break;

                case Cursor.FIELD_TYPE_FLOAT:
                    map.put(key, cursor.getDouble(i));
                    break;

                case Cursor.FIELD_TYPE_BLOB:
                    map.put(key, cursor.getBlob(i));
                    break;

                case Cursor.FIELD_TYPE_NULL:
                    break;
            }
        }
        return map;
    }

    /**
     * Closes {@code closeable}, ignoring any checked exceptions. Does nothing if {@code closeable} is
     * null.
     */
    public static void closeQuietly(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {
            }
        }
    }

    public interface Callback {
        Cursor execute(SQLiteDatabase db);
    }
}
