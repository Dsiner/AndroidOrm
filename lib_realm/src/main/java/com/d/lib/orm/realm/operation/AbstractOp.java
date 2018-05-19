package com.d.lib.orm.realm.operation;

import java.util.List;

import io.realm.Realm;

/**
 * 抽象基本操作
 * Created by D on 2017/11/8.
 */
public abstract class AbstractOp<T, ID> {
    public Realm realm;

    public AbstractOp(Realm realm) {
        this.realm = realm;
    }

    protected abstract void insert(T o);

    protected abstract void insertOrReplace(T o);

    protected abstract void insert(List<T> list, boolean transaction);

    protected abstract void insertOrReplace(List<T> list, boolean transaction);

    protected abstract void deleteAll();

    protected abstract void deleteById(ID id);

    protected abstract void delete(T o);

    protected abstract void update(T o);

    protected abstract List<T> queryAll();
}
