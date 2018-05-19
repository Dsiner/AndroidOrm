package com.d.androidorm;

import android.app.Application;
import android.content.Context;

import com.d.lib.common.data.preference.Preferences;
import com.d.lib.orm.room.bean.Book;
import com.d.lib.orm.room.db.AppDB;

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
        initRoom(getApplicationContext());
    }

    private void initRoom(Context appContext) {
        if (Preferences.getIns(appContext).getIsFirst()) {
            Preferences.getIns(appContext).putIsFirst(false);
            List<Book> list = new ArrayList<>();
            for (int i = 0; i < 66; i++) {
                Book book = new Book((long) i, "N" + 1, "A" + i, 0L, 0D);
                list.add(book);
            }
            Book[] books = new Book[list.size()];
            list.toArray(books);
            AppDB.getInstance(appContext).optBook().insertOrReplace(books);
        }
    }
}
