package com.d.lib.orm.sqlite;

import android.content.Context;

import com.d.lib.orm.sqlite.db.AbstractDatabase;
import com.d.lib.orm.sqlite.operation.OpBook;

/**
 * DBManager
 * Created by D on 2017/7/25.
 */
public class DBManager extends AbstractDatabase {
    private volatile static DBManager INSTANCE;

    public final OpBook opBook;

    private DBManager(Context context) {
        super(context);
        opBook = new OpBook(bookDao);
    }

    public static DBManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DBManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DBManager(context);
                }
            }
        }
        return INSTANCE;
    }
}