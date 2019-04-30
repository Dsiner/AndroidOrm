package com.d.lib.orm.sqlite.operation;

import com.d.lib.orm.sqlite.bean.Book;
import com.d.lib.orm.sqlite.dao.BookDao;

import java.util.List;

/**
 * Book操作
 * Created by D on 2017/11/8.
 */
public class OpBook extends AbstractOp<Book> {
    private BookDao dao;

    public OpBook(BookDao dao) {
        this.dao = dao;
    }

    /**
     * 插入一条记录
     */
    public void insert(Book book) {
        insertOrReplace(dao, book);
    }

    /**
     * 插入一组记录
     */
    public void insert(List<Book> list) {
        insertOrReplace(dao, list, true);
    }

    /**
     * 删除一条记录
     */
    public void delete(Book book) {
        delete(dao, book);
    }

    /**
     * 删除记录
     */
    public void delete(Long isbn) {
        deleteById(dao, isbn);
    }

    /**
     * 清空记录
     */
    public void deleteAll() {
        deleteAll(dao);
    }

    public void update(Book book) {
        update(dao, book);
    }

    public List<Book> queryAll() {
        return queryAll(dao);
    }
}
