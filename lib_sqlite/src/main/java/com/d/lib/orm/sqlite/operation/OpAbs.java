package com.d.lib.orm.sqlite.operation;

import com.d.lib.orm.sqlite.dao.AbstractDao;

import java.util.List;

/**
 * Created by D on 2017/11/8.
 */
public class OpAbs<D extends AbstractDao, T> extends AbstractOp {
    protected D dao;

    public OpAbs(D dao) {
        this.dao = dao;
    }

    public void insert(T entity) {
        insertOrReplace(dao, entity);
    }

    public void insert(List<T> list) {
        insertOrReplace(dao, list, true);
    }

    public void delete(T entity) {
        delete(dao, entity);
    }

    public void deleteAll() {
        deleteAll(dao);
    }

    public void update(T entity) {
        update(dao, entity);
    }

    public List<T> queryAll() {
        return queryAll(dao);
    }
}
