package com.d.lib.orm.ormlite.operation;

import com.d.lib.orm.ormlite.bean.Book;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Book操作
 * Created by D on 2017/11/8.
 */
public class OpBook extends AbstractOp<Book, Long> {
    private Dao<Book, Long> dao;

    public OpBook(Dao<Book, Long> dao) {
        this.dao = dao;
    }

    /**
     * 插入一条记录
     */
    public void insert(Book book) {
        if (book == null) {
            return;
        }
        try {
            List<Book> list = dao.queryBuilder().orderBy("isbn", false).limit(1L).query();
            if (list != null && list.size() > 0) {
                book.isbn = list.get(0).getIsbn() + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.insert(dao, book);
    }

    /**
     * 插入一条记录
     */
    public void insertOrReplace(Book book) {
        super.insertOrReplace(dao, book);
    }

    /**
     * 插入一组记录
     */
    public void insertOrReplace(List<Book> list) {
        super.insertOrReplace(dao, list);
    }

    /**
     * 删除一条记录
     */
    public void deleteBook(Book book) {
        super.delete(dao, book);
    }

    /**
     * 清空记录
     */
    public void deleteAll() {
        super.deleteAll(dao);
    }

    public void update(Book book) {
        super.update(dao, book);
    }

    public List<Book> queryAll() {
        try {
            return dao.queryBuilder().orderBy("isbn", true).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
