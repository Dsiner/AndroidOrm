package com.d.lib.orm.sqlite.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.d.lib.orm.sqlite.db.AppDB;

import java.util.List;

public abstract class AbstractDao<T, ID> {
    private AppDB appDB;

    public AbstractDao(AppDB appDB) {
        this.appDB = appDB;
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
    }

    public abstract Long readKey(Cursor cursor, int offset);

    public abstract T readEntity(Cursor cursor, int offset);

    public abstract void readEntity(Cursor cursor, T entity, int offset);

    protected final Long updateKeyAfterInsert(T entity, long rowId) {
        return rowId;
    }

    public abstract ID getKey(T entity);

    public abstract boolean hasKey(T entity);

    protected final boolean isEntityUpdateable() {
        return true;
    }

    public abstract void insert(T o);

    protected abstract void insertNotTx(T o);

    public abstract void insertOrReplace(T o);

    public abstract void insertInTx(List<T> list);

    public abstract void insertOrReplaceInTx(List<T> list);

    public abstract void deleteAll();

    public abstract void deleteByKey(Long id);

    public abstract void delete(T o);

    public abstract void update(T o);

    protected abstract void updateNotTx(T o);

    protected abstract T queryNotTx(ID id);

    public abstract List<T> loadAll();

    protected SQLiteDatabase open() {
        return appDB.open();
    }

    protected void close() {
        appDB.close();
    }
}
