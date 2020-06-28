package com.d.lib.orm.sqlite.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.d.lib.orm.sqlite.bean.Book;
import com.d.lib.orm.sqlite.db.AbstractDatabase;

import java.util.LinkedHashMap;

/**
 * DAO for table "BOOK".
 */
public class BookDao extends AbstractDao<Book, Long> {
    public static final String TABLENAME = "Book";

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"" + TABLENAME + "\" (" +
                "\"" + "isbn" + "\" INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "\"" + "name" + "\" TEXT NOT NULL ," +
                "\"" + "author" + "\" TEXT," +
                "\"" + "date" + "\" INTEGER," +
                "\"" + "price" + "\" REAL);");
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"" + TABLENAME + "\"";
        db.execSQL(sql);
    }

    public BookDao(AbstractDatabase appDB) {
        super(appDB);
    }

    @Override
    protected String getTableName() {
        return TABLENAME;
    }

    @Override
    protected String getKeyName() {
        return "isbn";
    }

    @Override
    protected ContentValues getContentValues(Book o) {
        ContentValues values = new ContentValues();
        values.put("isbn", o.isbn);
        values.put("name", o.name);
        values.put("author", o.author);
        values.put("date", o.date);
        values.put("price", o.price);
        return values;
    }

    @Override
    public Long getKey(Book entity) {
        return entity != null ? entity.isbn : null;
    }

    @Override
    public boolean hasKey(Book entity) {
        return entity.isbn != null;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Book readEntity(Cursor cursor) {
        LinkedHashMap<String, Object> map = map(cursor);
        return new Book(
                (Long) map.get("isbn"),
                (String) map.get("name"),
                (String) map.get("author"),
                (Long) map.get("date"),
                (Double) map.get("price")
        );
    }
}
