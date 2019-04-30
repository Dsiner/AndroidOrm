package com.d.lib.orm.ormlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.d.lib.orm.ormlite.bean.Book;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * AppDB
 * Created by D on 2017/7/25.
 */
public abstract class AppDB extends OrmLiteSqliteOpenHelper {
    protected SQLiteDatabase db;
    protected Gson gson;
    protected Dao<Book, Long> bookDao;

    protected AppDB(Context context) {
        super(context, "ormlite.db", null, 1);
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        initDaos();
    }

    private void initDaos() {
        bookDao = getBookDao();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Book.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, Book.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Dao<Book, Long> getBookDao() {
        if (bookDao == null) {
            try {
                bookDao = getDao(Book.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bookDao;
    }

    @Override
    public void close() {
        super.close();
        bookDao = null;
    }
}
