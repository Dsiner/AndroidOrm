package com.d.lib.orm.sqlite.operation;

import com.d.lib.orm.sqlite.dao.AbstractDao;

import java.util.List;


/**
 * 抽象基本操作
 * Created by D on 2017/11/8.
 */
public class AbstractOp<T> {
    /**
     * 插入一条记录
     */
    protected void insert(AbstractDao dao, T o) {
        if (dao == null || o == null) {
            return;
        }
        dao.insert(o);
    }

    /**
     * 插入一条记录
     */
    protected void insertOrReplace(AbstractDao dao, T o) {
        if (dao == null || o == null) {
            return;
        }
        dao.insertOrReplace(o);
    }

    /**
     * @param dao:dao
     * @param transaction:true-开启一次事务
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
     * @param dao:dao
     * @param transaction:true-开启一次事务
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
     * 删除表中所有记录
     */
    protected void deleteAll(AbstractDao dao) {
        if (dao == null) {
            return;
        }
        dao.deleteAll();
    }

    /**
     * 删除表中一条记录
     *
     * @param id:主键
     */
    protected void deleteById(AbstractDao dao, Long id) {
        if (dao == null) {
            return;
        }
        dao.deleteByKey(id);
    }

    /**
     * 删除表中一条记录 -根据主键
     */
    protected void delete(AbstractDao dao, T o) {
        if (dao == null || o == null) {
            return;
        }
        dao.delete(o);
    }

    protected void update(AbstractDao dao, T o) {
        if (dao == null || o == null) {
            return;
        }
        dao.update(o);
    }

    /**
     * 查询表，默认条件：主键id递增
     *
     * @return List<D>：查询结果集
     */
    protected List<T> queryAll(AbstractDao dao) {
        if (dao == null) {
            return null;
        }
        return dao.loadAll();
    }
}
