package com.d.lib.orm.ormlite.operation;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * 抽象基本操作
 * Created by D on 2017/11/8.
 */
public class AbstractOp<T, ID> {

    /**
     * 插入一条记录
     */
    protected void insert(Dao<T, ID> dao, T o) {
        if (dao == null || o == null) {
            return;
        }
        try {
            dao.create(o);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入一条记录
     */
    protected void insertOrReplace(Dao<T, ID> dao, T o) {
        if (dao == null || o == null) {
            return;
        }
        try {
            dao.createOrUpdate(o);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void insert(final Dao<T, ID> dao, final List<T> list) {
        if (dao == null || list == null || list.size() <= 0) {
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            insert(dao, list.get(i));
        }
    }

    protected void insertOrReplace(final Dao<T, ID> dao, final List<T> list) {
        if (dao == null || list == null || list.size() <= 0) {
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            insertOrReplace(dao, list.get(i));
        }
    }

    /**
     * 删除表中所有记录
     */
    protected void deleteAll(Dao<T, ID> dao) {
        if (dao == null) {
            return;
        }
        try {
            dao.deleteBuilder().delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除表中一条记录
     *
     * @param id:主键
     */
    protected void deleteById(Dao<T, ID> dao, ID id) {
        if (dao == null) {
            return;
        }
        try {
            dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除表中一条记录 -根据主键
     */
    protected void delete(Dao<T, ID> dao, T o) {
        if (dao == null || o == null) {
            return;
        }
        try {
            dao.delete(o);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void update(Dao<T, ID> dao, T o) {
        if (dao == null || o == null) {
            return;
        }
        try {
            dao.update(o);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询表，默认条件：主键id递增
     *
     * @return List<D>：查询结果集
     */
    protected List<T> queryAll(Dao<T, ID> dao) {
        if (dao == null) {
            return null;
        }
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询表，默认条件
     *
     * @param offset：偏移量
     * @param limit：从偏移量始，查询条数
     * @return List<D>：查询结果集
     */
    protected List<T> queryAllOrderDescByPage(Dao<T, ID> dao, String columnName, boolean ascending, long offset, long limit) {
        if (dao == null) {
            return null;
        }
        try {
            return dao.queryBuilder().orderBy(columnName, ascending).offset(offset).limit(limit).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
