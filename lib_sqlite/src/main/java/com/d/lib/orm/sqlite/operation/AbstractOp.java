package com.d.lib.orm.sqlite.operation;

import android.support.annotation.NonNull;

import com.d.lib.orm.sqlite.db.AbstractDao;

import java.util.List;

/**
 * Created by D on 2017/11/8.
 */
public class AbstractOp<D extends AbstractDao, T> {
    @NonNull
    protected D dao;

    public AbstractOp(@NonNull D dao) {
        this.dao = dao;
    }

    /**
     * Insert a record
     */
    public void insert(T entity) {
        if (entity == null) {
            return;
        }
        dao.insert(entity);
    }

    /**
     * Insert a record
     */
    public void insertOrReplace(T entity) {
        if (entity == null) {
            return;
        }
        dao.insertOrReplace(entity);
    }

    public void insert(final List<T> list, boolean transaction) {
        if (list == null || list.size() <= 0) {
            return;
        }
        final int size = list.size();
        if (!transaction) {
            for (int i = 0; i < size; i++) {
                insert(list.get(i));
            }
            return;
        }
        dao.insertInTx(list);
    }

    protected void insertOrReplace(final List<T> list, boolean transaction) {
        if (list == null || list.size() <= 0) {
            return;
        }
        final int size = list.size();
        if (!transaction) {
            for (int i = 0; i < size; i++) {
                insertOrReplace(list.get(i));
            }
            return;
        }
        dao.insertOrReplaceInTx(list);
    }

    /**
     * Delete a record in the table-according to the primary key
     */
    public void delete(T entity) {
        if (entity == null) {
            return;
        }
        dao.delete(entity);
    }

    /**
     * Delete a record in the table
     *
     * @param key: Primary key
     */
    public void deleteById(Long key) {
        dao.deleteByKey(key);
    }

    public void delete(List<T> list) {
        dao.delete(list);
    }

    /**
     * Delete all records in the table
     */
    public void deleteAll() {
        dao.deleteAll();
    }

    public void update(T entity) {
        if (entity == null) {
            return;
        }
        dao.update(entity);
    }

    @NonNull
    public List<T> queryLimit(int limit) {
        return dao.queryLimit(limit);
    }

    @NonNull
    public List<T> queryOffset(final int beginIndex, final int endIndex) {
        return dao.queryOffset(beginIndex, endIndex);
    }

    /**
     * Query table, default condition: primary key id is increasing
     *
     * @return Query result set
     */
    @NonNull
    public List<T> queryAll() {
        return dao.queryAll();
    }

    public long queryCount() {
        return dao.queryCount();
    }
}
