package com.d.lib.orm.sqlite.dao;

import android.database.Cursor;

import com.d.lib.orm.sqlite.bean.Book;
import com.d.lib.orm.sqlite.db.AbstractDao;
import com.d.lib.orm.sqlite.db.AbstractDatabase;
import com.d.lib.orm.sqlite.internal.TableStatements;

/**
 * DAO for table "BOOK".
 */
public class BookDao extends AbstractDao<Book, Long> {

    public BookDao(AbstractDatabase appDB) {
        super(appDB);
    }

    @Override
    public Long getKey(Book entity) {
        return entity.isbn;
    }

    @Override
    protected TableStatements getTableStatement(Book entity) {
        TableStatements statement = new TableStatements();
        statement.put("isbn", entity.isbn);
        statement.put("name", entity.name);
        statement.put("author", entity.author);
        statement.put("date", entity.date);
        statement.put("priceX", entity.price);
        return statement;
    }

    @Override
    protected Book readEntity(Cursor cursor) {
        return new Book(cursor.getLong(cursor.getColumnIndex("isbn")),
                cursor.getString(cursor.getColumnIndex("name")),
                cursor.getString(cursor.getColumnIndex("author")),
                cursor.getLong(cursor.getColumnIndex("date")),
                cursor.getDouble(cursor.getColumnIndex("priceX")));
    }
}
