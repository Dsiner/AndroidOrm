package com.d.lib.orm.sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.d.lib.orm.sqlite.bean.Book;
import com.d.lib.orm.sqlite.dao.BookDao;
import com.d.lib.orm.sqlite.internal.DaoHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AbstractDatabase
 * Created by D on 2017/7/25.
 */
public abstract class AbstractDatabase extends SQLiteOpenHelper {
    public static final int SCHEMA_VERSION = 1;

    private final AtomicInteger mOpenCounter = new AtomicInteger();
    private SQLiteDatabase mDB;

    protected final BookDao bookDao;

    public AbstractDatabase(Context context, @NonNull String name) {
        super(context, name, null, SCHEMA_VERSION);
        mDB = openDatabase();

        // Init dao
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

    public SQLiteDatabase getSQLiteDatabase() {
        return mDB;
    }

    @Deprecated
    private synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            mDB = getReadableDatabase();
        }
        return mDB;
    }

    @Deprecated
    private synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            try {
                mDB.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {
            }
        }
    }
}