package com.d.lib.orm.sqlite.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.d.lib.orm.sqlite.bean.Book;
import com.d.lib.orm.sqlite.db.AppDB;
import com.d.lib.orm.sqlite.property.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO for table "BOOK".
 */
public class BookDao extends AbstractDao<Book, Long> {

    public static final String TABLENAME = "BOOK";

    /**
     * Properties of entity Book.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Isbn = new Property(0, Long.class, "isbn", true, "ISDN");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Author = new Property(2, String.class, "author", false, "AUTHOR");
        public final static Property Date = new Property(3, Long.class, "date", false, "DATE");
        public final static Property Price = new Property(4, Double.class, "price", false, "PRICE");
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"BOOK\" (" + //
                "\"ISDN\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: isbn
                "\"NAME\" TEXT NOT NULL ," + // 1: name
                "\"AUTHOR\" TEXT," + // 2: author
                "\"DATE\" INTEGER," + // 3: date
                "\"PRICE\" REAL);"); // 4: price
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BOOK\"";
        db.execSQL(sql);
    }

    public BookDao(AppDB appDB) {
        super(appDB);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    public Book readEntity(Cursor cursor, int offset) {
        Book entity = new Book( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // isbn
                cursor.getString(offset + 1), // name
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // author
                cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // date
                cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4) // price
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, Book entity, int offset) {
        entity.setIsbn(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setAuthor(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDate(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setPrice(cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4));
    }

    @Override
    public Long getKey(Book entity) {
        if (entity != null) {
            return entity.getIsbn();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Book entity) {
        return entity.getIsbn() != null;
    }

    @Override
    public void insert(Book o) {
        SQLiteDatabase db = open();
        db.beginTransaction();

        insertNotTx(o);

        db.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
        db.endTransaction(); // 处理完成
        close();
    }

    @Override
    protected void insertNotTx(Book o) {
        SQLiteDatabase db = open();

//        if (!hasKey(o)) {
//            Cursor cursor = db.rawQuery("select max(" + Properties.Isbn.field + ") from " + TABLENAME, null);
//            if (cursor != null && cursor.moveToFirst()) {
//                Long isbn = cursor.isNull(0) ? null : cursor.getLong(0);
//                o.setIsbn(isbn != null ? isbn + 1 : null);
//                cursor.close();
//            }
//        }

        ContentValues values = new ContentValues();
        values.put(Properties.Isbn.field, o.isbn);
        values.put(Properties.Name.field, o.name);
        values.put(Properties.Author.field, o.author);
        values.put(Properties.Date.field, o.date);
        values.put(Properties.Price.field, o.price);
        db.insertOrThrow(TABLENAME, null, values);
        close();
    }

    @Override
    public void insertOrReplace(Book o) {
        SQLiteDatabase db = open();
        db.beginTransaction();

        if (!hasKey(o) || queryNotTx(o.getIsbn()) == null) {
            insertNotTx(o);
        } else {
            updateNotTx(o);
        }

        db.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
        db.endTransaction(); // 处理完成
        close();
    }

    @Override
    public void insertInTx(List<Book> list) {
        SQLiteDatabase db = open();
        db.beginTransaction();

        for (Book o : list) {
            ContentValues values = new ContentValues();
            values.put(Properties.Isbn.field, o.isbn);
            values.put(Properties.Name.field, o.name);
            values.put(Properties.Author.field, o.author);
            values.put(Properties.Date.field, o.date);
            values.put(Properties.Price.field, o.price);
            db.insertOrThrow(TABLENAME, null, values);
        }

        db.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
        db.endTransaction(); // 处理完成
        close();
    }

    @Override
    public void insertOrReplaceInTx(List<Book> list) {
        SQLiteDatabase db = open();
        db.beginTransaction();

        for (Book o : list) {
            ContentValues values = new ContentValues();
            values.put(Properties.Isbn.field, o.isbn);
            values.put(Properties.Name.field, o.name);
            values.put(Properties.Author.field, o.author);
            values.put(Properties.Date.field, o.date);
            values.put(Properties.Price.field, o.price);

            if (!hasKey(o) || queryNotTx(o.getIsbn()) == null) {
                insertNotTx(o);
            } else {
                updateNotTx(o);
            }
        }

        db.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
        db.endTransaction(); // 处理完成
        close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = open();
        db.beginTransaction();

        db.delete(TABLENAME, null, null);

        db.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
        db.endTransaction(); // 处理完成
        close();
    }

    @Override
    public void deleteByKey(Long id) {
        if (id == null) {
            return;
        }
        SQLiteDatabase db = open();
        db.beginTransaction();

        String whereClause = Properties.Isbn.field + " = ?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        db.delete(TABLENAME, whereClause, whereArgs);

        db.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
        db.endTransaction(); // 处理完成
        close();
    }

    @Override
    public void delete(Book o) {
        deleteByKey(o.getIsbn());
    }

    @Override
    public void update(Book o) {
        SQLiteDatabase db = open();
        db.beginTransaction();

        updateNotTx(o);

        db.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
        db.endTransaction(); // 处理完成
        close();
    }

    @Override
    protected void updateNotTx(Book o) {
        ContentValues values = new ContentValues();
        values.put(Properties.Isbn.field, o.isbn);
        values.put(Properties.Name.field, o.name);
        values.put(Properties.Author.field, o.author);
        values.put(Properties.Date.field, o.date);
        values.put(Properties.Price.field, o.price);

        String whereClause = Properties.Isbn.field + " = ?";
        String[] whereArgs = new String[]{String.valueOf(o.getIsbn())};
        SQLiteDatabase db = open();
        db.update(TABLENAME, values, whereClause, whereArgs);
        close();
    }

    @Override
    protected Book queryNotTx(Long id) {
        if (id == null) {
            return null;
        }

        String whereClause = Properties.Isbn.field + " = ?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery("select * from " + TABLENAME
                + " where " + whereClause, whereArgs);
        close();
        if (cursor != null && cursor.moveToFirst()) {
            return readEntity(cursor, 0);
        }
        return null;
    }

    @Override
    public List<Book> loadAll() {
        SQLiteDatabase db = open();
        db.beginTransaction();

        String orderBy = Properties.Isbn.field;
        Cursor cursor = db.query(TABLENAME, null, null, null, null, null, orderBy);

        db.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
        db.endTransaction(); // 处理完成

        if (cursor == null) {
            close();
            return null;
        }
        List<Book> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(readEntity(cursor, 0));
        }
        cursor.close();
        close();
        return list;
    }
}
