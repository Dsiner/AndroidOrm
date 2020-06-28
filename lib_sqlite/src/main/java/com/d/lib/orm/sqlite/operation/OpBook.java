package com.d.lib.orm.sqlite.operation;

import com.d.lib.orm.sqlite.bean.Book;
import com.d.lib.orm.sqlite.dao.BookDao;

/**
 * Operating Table Book
 * Created by D on 2017/11/8.
 */
public class OpBook extends AbstractOp<BookDao, Book> {

    public OpBook(BookDao dao) {
        super(dao);
    }
}
