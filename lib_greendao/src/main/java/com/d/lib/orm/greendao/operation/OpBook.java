package com.d.lib.orm.greendao.operation;

import com.d.lib.orm.greendao.bean.Book;
import com.d.lib.orm.greendao.dao.BookDao;

import java.util.List;

/**
 * Book操作
 * Created by D on 2017/11/8.
 */
public class OpBook extends AbstractOp {
    private BookDao dao;

    public OpBook(BookDao dao) {
        this.dao = dao;
    }

    /**
     * 插入一条记录
     */
    public void insertBook(Book book) {
        insertOrReplace(dao, book);
    }

    /**
     * 插入一组记录
     */
    public void insertBook(List<Book> list) {
        insertOrReplace(dao, list, true);
    }

    /**
     * 删除一条记录
     */
    public void deleteBook(Book book) {
        delete(dao, book);
    }

    /**
     * 删除记录
     */
    public void deleteBooks(String isbn) {
        List<Book> list = dao.queryBuilder().where(BookDao.Properties.Isbn.eq(isbn)).list();
        if (list != null && list.size() > 0) {
            dao.deleteInTx(list);
        }
    }

    /**
     * 清空记录
     */
    public void deleteAllBooks() {
        deleteAll(dao);
    }

    public List<Book> queryAll() {
        return dao.queryBuilder().orderAsc(BookDao.Properties.Isbn).list();
    }

    /**
     * 获取记录，本条记录之前的10条
     * 1. > : gt
     * 2. < : lt
     * 3. >= : ge
     * 4. <= : le
     */
    public List<Book> queryLtBook(String author, long date) {
        return dao.queryBuilder().where(BookDao.Properties.Author.eq(author),
                BookDao.Properties.Date.lt(date))
                .orderDesc(BookDao.Properties.Date).limit(10).list();
    }

    /**
     * 获取记录，start - end
     */
    public List<Book> queryGeBook(String author, long start) {
        return dao.queryBuilder().where(BookDao.Properties.Author.eq(author),
                BookDao.Properties.Date.ge(start))
                .orderDesc(BookDao.Properties.Date).list();
    }

    /**
     * 获取该作者最新出版时间戳
     */
    public long getDate(String author) {
        List<Book> list = dao.queryBuilder().where(BookDao.Properties.Author.eq(author))
                .orderDesc(BookDao.Properties.Date).limit(1).list();
        long date = 0;
        if (list != null && list.size() > 0) {
            date = list.get(list.size() - 1).getDate();
        }
        return date;
    }
}
