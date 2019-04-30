package com.d.lib.orm.litepal.operation;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 抽象基本操作
 * Created by D on 2017/11/8.
 */
public class AbstractOp<T extends DataSupport> {
    /**
     * 插入一条记录
     */
    protected void insert(T model) {
        if (model == null) {
            return;
        }
        model.save();
    }

    /**
     * 插入一条记录
     */
    protected void insertOrReplace(T model, String... conditions) {
        if (model == null || conditions == null) {
            return;
        }
        model.saveOrUpdate(conditions);
    }

    /**
     * 插入/更新一组记录
     */
    protected void insert(final List<T> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        final int size = list.size();
        for (int i = 0; i < size; i++) {
            insert(list.get(i));
        }
    }

    /**
     * 插入/更新一组记录
     */
    protected void insertOrReplace(final List<T> list, String... conditions) {
        if (list == null || list.size() <= 0) {
            return;
        }
        final int size = list.size();
        for (int i = 0; i < size; i++) {
            insertOrReplace(list.get(i));
        }
    }

    /**
     * 删除表中所有记录
     */
    protected void deleteAll(Class<?> modelClass) {
        if (modelClass == null) {
            return;
        }
        DataSupport.deleteAll(modelClass);
    }

    /**
     * 删除表中一条记录
     *
     * @param id:主键
     */
    protected void deleteById(T model, Long id) {
        if (model == null) {
            return;
        }
        DataSupport.deleteAll(model.getClass(), "id = " + id);
    }

    /**
     * 删除表中一条记录 -根据主键
     */
    protected void delete(T model) {
        if (model == null) {
            return;
        }
        model.delete();
    }

    protected void update(T model, long id) {
        if (model == null) {
            return;
        }
        model.update(id);
    }

    /**
     * 查询表，默认条件：主键id递增
     *
     * @return List<D>：查询结果集
     */
    protected List<T> queryAll(Class<T> modelClass) {
        if (modelClass == null) {
            return null;
        }
        return DataSupport.findAll(modelClass);
    }

    /**
     * 查询表，默认条件
     *
     * @param offset：偏移量
     * @param limit：从偏移量始，查询条数
     * @return List<D>：查询结果集
     */
    protected List<?> queryAllOrderDescByPage(Class<T> modelClass, int offset, int limit) {
        if (modelClass == null) {
            return null;
        }
        return DataSupport.offset(offset).limit(limit).find(modelClass);
    }
}
