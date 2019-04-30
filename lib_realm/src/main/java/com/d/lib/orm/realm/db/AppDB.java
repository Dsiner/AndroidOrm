package com.d.lib.orm.realm.db;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * AppDB
 * Created by D on 2017/7/25.
 */
public abstract class AppDB {
    protected Realm realm;
    protected Gson gson;

    protected AppDB(Context context) {
        Realm.init(context.getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("realm.realm")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getInstance(config);
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }
}
