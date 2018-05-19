package com.d.androidorm;

import android.app.Application;
import android.content.Context;

import com.d.lib.common.data.preference.Preferences;
import com.d.lib.orm.ormlite.bean.Book;
import com.d.lib.orm.ormlite.utils.AppDBUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * App
 * Created by D on 2018/5/15.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initOrmlite(getApplicationContext());
    }

    private void initOrmlite(Context appContext) {
        if (Preferences.getIns(appContext).getIsFirst()) {
            Preferences.getIns(appContext).putIsFirst(false);
            List<Book> list = new ArrayList<>();
            for (int i = 0; i < 66; i++) {
                Book book = new Book((long) i, "N" + i, "A" + i, 0L, 0D);
                list.add(book);
            }
            AppDBUtil.getInstance(appContext).optBook().insertOrReplace(list);
        }
    }
}
