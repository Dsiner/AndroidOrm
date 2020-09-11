package com.d.lib.orm.sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.d.lib.orm.sqlite.bean.Book;
import com.d.lib.orm.sqlite.dao.BookDao;
import com.d.lib.orm.sqlite.internal.DaoHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AbstractDatabase
 * Created by D on 2017/7/25.
 */
public abstract class AbstractDatabase extends SQLiteOpenHelper {
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private SQLiteDatabase mDatabase;
    protected Gson mGson;

    protected final BookDao bookDao;

    public AbstractDatabase(Context context) {
        super(context, "sqlite.db", null, 1);
        mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        mDatabase = getReadableDatabase();

        bookDao = new BookDao(this);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DaoHelper.createTable(db, Book.class, true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DaoHelper.dropTable(db, Book.class, true);
        onCreate(db);
    }

    public synchronized SQLiteDatabase open() {
        if (mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            mDatabase = getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void close() {
        if (mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            try {
                mDatabase.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {
            }
        }
    }
}