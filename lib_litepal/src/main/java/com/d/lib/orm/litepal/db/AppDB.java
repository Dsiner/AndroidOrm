package com.d.lib.orm.litepal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.litepal.LitePal;

/**
 * AppDB
 * Created by D on 2017/7/25.
 */
public abstract class AppDB {
    protected SQLiteDatabase db;
    protected Gson gson;

    protected AppDB(Context context) {
        // litepal setting step 3-3
        LitePal.initialize(context.getApplicationContext());
        db = LitePal.getDatabase();
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }
}
