package com.d.lib.orm.sqlite.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.d.lib.orm.sqlite.annotations.Entity;
import com.d.lib.orm.sqlite.annotations.Property;
import com.d.lib.orm.sqlite.internal.ColumnType;
import com.d.lib.orm.sqlite.internal.DaoHelper;
import com.d.lib.orm.sqlite.internal.SqlUtils;
import com.d.lib.orm.sqlite.internal.TableStatements;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDao<T, KEY> {
    protected final AbstractDatabase mDatabase;
    protected final String mTableName;
    protected final LinkedHashMap<String, String> mColumnNames = new LinkedHashMap<>();
    protected String mKeyName;

    public AbstractDao(AbstractDatabase database) {
        this.mDatabase = database;
        Class clz = (Class) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
        Entity entity = (Entity) clz.getAnnotation(Entity.class);
        mTableName = !TextUtils.isEmpty(entity.nameInDb()) ?
                entity.nameInDb() : clz.getSimpleName();

        List<Field> fields = DaoHelper.getPropertyFields(clz);
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

    public String getTableName() {
        return mTableName;
    }

    public String getKeyName() {
        return mKeyName;
    }

    public KEY getKey(T entity) {
        LinkedHashMap<String, Object> map = DaoHelper.GsonFormat.toHashMap(entity);
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

    protected TableStatements getTableStatement(T entity) {
        TableStatements statement = new TableStatements();
        LinkedHashMap<String, Object> map = DaoHelper.GsonFormat.toHashMap(entity);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String columnName = mColumnNames.get(entry.getKey());
            if (TextUtils.isEmpty(columnName)) {
                continue;
            }
            Object value = entry.getValue();
            String columnType = ColumnType.getColumnType(value.getClass());
            if (TextUtils.equals(ColumnType.INTEGER, columnType)) {
                statement.put(columnName, value);
            } else if (TextUtils.equals(ColumnType.REAL, columnType)) {
                statement.put(columnName, value);
            } else if (TextUtils.equals(ColumnType.TEXT, columnType)) {
                statement.put(columnName, value);
            }
        }
        return statement;
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
        return DaoHelper.GsonFormat.fromMap(map, type);
    }

    public void insert(final T entity) {
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                TableStatements statement = getTableStatement(entity);
                String sql = SqlUtils.createSqlInsert("INSERT INTO ", getTableName(), statement.getColumns());
                db.execSQL(sql, statement.getBindArgs());
                return null;
            }
        });
    }

    public void insertOrReplace(final T entity) {
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                TableStatements statement = getTableStatement(entity);
                String sql = SqlUtils.createSqlInsert("INSERT OR REPLACE INTO ", getTableName(), statement.getColumns());
                db.execSQL(sql, statement.getBindArgs());
                return null;
            }
        });
    }

    public void insertInTx(final List<T> list) {
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                for (T entity : list) {
                    TableStatements statement = getTableStatement(entity);
                    String sql = SqlUtils.createSqlInsert("INSERT INTO ", getTableName(), statement.getColumns());
                    db.execSQL(sql, statement.getBindArgs());
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
                    TableStatements statement = getTableStatement(entity);
                    String sql = SqlUtils.createSqlInsert("INSERT OR REPLACE INTO ", getTableName(), statement.getColumns());
                    db.execSQL(sql, statement.getBindArgs());
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
                String sql = SqlUtils.createSqlDelete(getTableName(),
                        new String[]{getKeyName()});
                db.execSQL(sql, new Object[]{key});
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
                for (T entity : list) {
                    KEY key = getKey(entity);
                    if (key == null) {
                        continue;
                    }
                    String sql = SqlUtils.createSqlDelete(getTableName(),
                            new String[]{getKeyName()});
                    db.execSQL(sql, new Object[]{key});
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
                    args.append("\"" + getKey(entity) + "\"" + ",");
                }
                int index = args.lastIndexOf(",");
                args.replace(index, index + 1, ")");
                db.execSQL("DELETE FROM " + "\"" + getTableName() + "\""
                        + " WHERE " + "\"" + getKeyName() + "\"" + " IN "
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
                TableStatements statement = getTableStatement(entity);
                String sql = SqlUtils.createSqlUpdate(getTableName(),
                        statement.getColumns(), new String[]{getKeyName()});
                db.execSQL(sql, SqlUtils.mergerObject(statement.getBindArgs(),
                        new Object[]{getKey(entity)}));
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
                String whereClause = "\"" + getKeyName() + "\"" + " = ?";
                String[] whereArgs = new String[]{String.valueOf(key)};
                Cursor cursor = db.rawQuery("SELECT * FROM " + "\"" + getTableName() + "\""
                        + " WHERE " + whereClause, whereArgs);
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
                Cursor cursor = db.rawQuery("SELECT * FROM " + getTableName()
                        + " LIMIT " + limit, null);
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
                Cursor cursor = db.rawQuery("SELECT * FROM " + getTableName()
                        + " LIMIT " + beginIndex + "," + endIndex, null);
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
                        null);
                while (cursor.moveToNext()) {
                    list.add(readEntity(cursor));
                }
                return cursor;
            }
        });
        return list;
    }

    public long queryCount() {
        final Long[] counts = new Long[1];
        counts[0] = 0L;
        executeInTx(new Callback() {
            @Override
            public Cursor execute(SQLiteDatabase db) {
                String sql = SqlUtils.createSqlCount(getTableName());
                Cursor cursor = db.rawQuery(sql, null);
                if (cursor != null && cursor.moveToFirst()) {
                    counts[0] = cursor.getLong(0);
                }
                return cursor;
            }
        });
        return counts[0];
    }

    protected void executeInTx(@NonNull final Callback callback) {
        synchronized (AbstractDao.class) {
            SQLiteDatabase db = mDatabase.getSQLiteDatabase();
            db.beginTransaction();
            Cursor cursor = null;
            try {
                cursor = callback.execute(db);
                db.setTransactionSuccessful();
            } catch (Throwable e) {
                Log.e("Sql", "Sql executeInTx error: " + e);
                e.printStackTrace();
            } finally {
                db.endTransaction();
                closeQuietly(cursor);
            }
        }
    }

    public interface Callback {
        Cursor execute(SQLiteDatabase db);
    }
}
