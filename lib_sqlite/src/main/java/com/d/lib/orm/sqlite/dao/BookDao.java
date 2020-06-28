package com.d.lib.orm.sqlite.dao;

import com.d.lib.orm.sqlite.bean.Book;
import com.d.lib.orm.sqlite.db.AbstractDatabase;

/**
 * DAO for table "BOOK".
 */
public class BookDao extends AbstractDao<Book, Long> {

    public BookDao(AbstractDatabase appDB) {
        super(appDB);
    }
}
