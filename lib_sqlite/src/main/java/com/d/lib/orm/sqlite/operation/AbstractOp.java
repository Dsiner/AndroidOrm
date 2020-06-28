package com.d.lib.orm.sqlite.operation;

import com.d.lib.orm.sqlite.dao.AbstractDao;

import java.util.List;

/**
 * AbstractOp
 * Created by D on 2017/11/8.
 */
public class AbstractOp<T> {

    /**
     * Insert a record
     */
    protected void insert(AbstractDao dao, T entity) {
        if (dao == null || entity == null) {
            return;
        }
        dao.insert(entity);
    }

    /**
     * Insert a record
     */
    protected void insertOrReplace(AbstractDao dao, T entity) {
        if (dao == null || entity == null) {
            return;
        }
        dao.insertOrReplace(entity);
    }

    /**
     * @param dao:         Dao
     * @param transaction: true: transaction
     */
    protected void insert(final AbstractDao dao, final List<T> list, boolean transaction) {
        if (dao == null || list == null || list.size() <= 0) {
            return;
        }
        final int size = list.size();
        if (!transaction) {
            for (int i = 0; i < size; i++) {
                insert(dao, list.get(i));
            }
            return;
        }
        dao.insertInTx(list);
    }

    /**
     * @param dao:         Dao
     * @param transaction: true: transaction
     */
    protected void insertOrReplace(final AbstractDao dao, final List<T> list, boolean transaction) {
        if (dao == null || list == null || list.size() <= 0) {
            return;
        }
        final int size = list.size();
        if (!transaction) {
            for (int i = 0; i < size; i++) {
                insertOrReplace(dao, list.get(i));
            }
            return;
        }
        dao.insertOrReplaceInTx(list);
    }

    /**
     * Delete all records in the table
     */
    protected void deleteAll(AbstractDao dao) {
        if (dao == null) {
            return;
        }
        dao.deleteAll();
    }

    /**
     * Delete a record in the table
     *
     * @param key: Primary key
     */
    protected void deleteById(AbstractDao dao, Long key) {
        if (dao == null) {
            return;
        }
        dao.deleteByKey(key);
    }

    /**
     * Delete a record in the table-according to the primary key
     */
    protected void delete(AbstractDao dao, T entity) {
        if (dao == null || entity == null) {
            return;
        }
        dao.delete(entity);
    }

    protected void update(AbstractDao dao, T entity) {
        if (dao == null || entity == null) {
            return;
        }
        dao.update(entity);
    }

    /**
     * Query table, default condition: primary key id is increasing
     *
     * @return Query result set
     */
    protected List<T> queryAll(AbstractDao dao) {
        if (dao == null) {
            return null;
        }
        return dao.loadAll();
    }
}
