package com.d.lib.orm.sqlite.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.d.lib.orm.sqlite.db.AbstractDatabase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class AbstractDao<T, KEY> {
    private AbstractDatabase database;

    public AbstractDao(AbstractDatabase database) {
        this.database = database;
    }

    protected abstract String getTableName();

    protected abstract String getKeyName();

    protected abstract ContentValues getContentValues(T entity);

    public abstract KEY getKey(T entity);

    public abstract boolean hasKey(T entity);

    protected abstract T readEntity(Cursor cursor);

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

    public List<T> loadAll() {
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
        return database.open();
    }

    protected void close() {
        database.close();
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
