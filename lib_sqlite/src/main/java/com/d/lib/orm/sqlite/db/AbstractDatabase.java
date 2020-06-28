package com.d.lib.orm.sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.d.lib.orm.sqlite.dao.BookDao;
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
        mDatabase = getWritableDatabase();

        bookDao = new BookDao(this);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        BookDao.createTable(db, true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        BookDao.dropTable(db, true);
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
            mDatabase.close();
        }
    }
}