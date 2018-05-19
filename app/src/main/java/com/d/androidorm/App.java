package com.d.androidorm;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.d.lib.common.data.preference.Preferences;
import com.d.lib.orm.realm.bean.Book;
import com.d.lib.orm.realm.utils.AppDBUtil;
import com.d.lib.taskscheduler.TaskScheduler;
import com.d.lib.taskscheduler.callback.Observer;
import com.d.lib.taskscheduler.callback.Task;
import com.d.lib.taskscheduler.schedule.Schedulers;

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
        initRealm(getApplicationContext());
    }

    private void initRealm(final Context appContext) {
        if (Preferences.getIns(appContext).getIsFirst()) {
            Preferences.getIns(appContext).putIsFirst(false);
            TaskScheduler.create(new Task<List<Book>>() {
                @Override
                public List<Book> run() {
                    List<Book> list = new ArrayList<>();
                    for (int i = 0; i < 66; i++) {
                        Book book = new Book((long) i, "N" + 1, "A" + i, 0L, 0D);
                        list.add(book);
                    }
                    return list;
                }
            }).subscribeOn(Schedulers.mainThread())
                    .subscribe(new Observer<List<Book>>() {
                        @Override
                        public void onNext(@NonNull List<Book> result) {
                            AppDBUtil.getInstance(appContext).optBook().insert(result, true);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }
    }
}
