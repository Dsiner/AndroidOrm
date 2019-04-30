package com.d.lib.orm.litepal.operation;

import com.d.lib.orm.litepal.bean.Book;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Book操作
 * Created by D on 2017/11/8.
 */
public class OpBook extends AbstractOp<Book> {

    public OpBook() {
    }

    public void insertOrReplace(Book book) {
        if (book == null) {
            return;
        }
        super.insertOrReplace(book, "isbn = " + book.getIsbn());
    }

    public void insertOrReplace(List<Book> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        for (Book book : list) {
            insertOrReplace(book);
        }
    }

    @Override
    public void delete(Book book) {
        super.delete(book);
    }

    /**
     * 删除记录
     */
    public void deleteBooks(String isbn) {
        if (isbn == null) {
            return;
        }
        DataSupport.deleteAll(Book.class, "isbn = " + isbn);
    }

    /**
     * 清空记录
     */
    public void deleteAll() {
        deleteAll(Book.class);
    }

    @Override
    protected void update(Book book, long id) {
        super.update(book, id);
    }

    public long maxIsbn() {
        return DataSupport.max(Book.class, "isbn", Long.class);
    }

    public List<Book> queryAll() {
        return queryAll(Book.class);
    }
}
