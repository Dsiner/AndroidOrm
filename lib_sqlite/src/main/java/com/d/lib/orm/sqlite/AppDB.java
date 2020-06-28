package com.d.lib.orm.sqlite;

import android.content.Context;

import com.d.lib.orm.sqlite.db.AbstractDatabase;
import com.d.lib.orm.sqlite.operation.OpBook;

/**
 * AppDB
 * Created by D on 2017/7/25.
 */
public class AppDB extends AbstractDatabase {
    private volatile static AppDB INSTANCE;

    public final OpBook opBook;

    private AppDB(Context context) {
        super(context);
        opBook = new OpBook(bookDao);
    }

    public static AppDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppDB(context);
                }
            }
        }
        return INSTANCE;
    }
}