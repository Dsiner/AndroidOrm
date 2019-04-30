package com.d.lib.orm.sqlite.utils;

import android.content.Context;

import com.d.lib.orm.sqlite.db.AppDB;
import com.d.lib.orm.sqlite.operation.OpBook;

/**
 * AppDBUtil
 * Created by D on 2017/7/25.
 */
public class AppDBUtil extends AppDB {
    private static AppDBUtil instance;

    private OpBook opBook;

    private AppDBUtil(Context context) {
        super(context);
        initOps();
    }

    /**
     * 初始化操作句柄
     */
    private void initOps() {
        opBook = new OpBook(bookDao);
    }

    public static AppDBUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDBUtil.class) {
                if (instance == null) {
                    instance = new AppDBUtil(context);
                }
            }
        }
        return instance;
    }

    /****************************** Book ******************************/
    public OpBook optBook() {
        return opBook;
    }
}