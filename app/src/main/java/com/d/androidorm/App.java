package com.d.androidorm;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.d.lib.common.data.preference.Preferences;
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
        if (Preferences.getIns(getApplicationContext()).getIsFirst()) {
            Preferences.getIns(getApplicationContext()).putIsFirst(false);
            initGreendao(getApplicationContext());
            initSqlite(getApplicationContext());
            initRoom(getApplicationContext());
            initOrmlite(getApplicationContext());
            initLitepal(getApplicationContext());
            initRealm(getApplicationContext());
        }
    }

    private void initGreendao(final Context context) {
        TaskScheduler.create(new Task<List<com.d.lib.orm.greendao.bean.Book>>() {
            @Override
            public List<com.d.lib.orm.greendao.bean.Book> run() {
                List<com.d.lib.orm.greendao.bean.Book> list = new ArrayList<>();
                for (int i = 0; i < 66; i++) {
                    com.d.lib.orm.greendao.bean.Book book = new com.d.lib.orm.greendao.bean.Book((long) i,
                            "N" + 1, "A" + i, 0L, 0D);
                    list.add(book);
                }
                return list;
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<com.d.lib.orm.greendao.bean.Book>>() {
                    @Override
                    public void onNext(@NonNull List<com.d.lib.orm.greendao.bean.Book> list) {
                        com.d.lib.orm.greendao.utils.AppDBUtil.getInstance(context).optBook().insertBook(list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void initSqlite(final Context context) {
        TaskScheduler.create(new Task<List<com.d.lib.orm.sqlite.bean.Book>>() {
            @Override
            public List<com.d.lib.orm.sqlite.bean.Book> run() {
                List<com.d.lib.orm.sqlite.bean.Book> list = new ArrayList<>();
                for (int i = 0; i < 66; i++) {
                    com.d.lib.orm.sqlite.bean.Book book = new com.d.lib.orm.sqlite.bean.Book((long) i,
                            "N" + 1, "A" + i, 0L, 0D);
                    list.add(book);
                }
                return list;
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<com.d.lib.orm.sqlite.bean.Book>>() {
                    @Override
                    public void onNext(@NonNull List<com.d.lib.orm.sqlite.bean.Book> list) {
                        com.d.lib.orm.sqlite.utils.AppDBUtil.getInstance(context).optBook().insert(list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void initRoom(final Context context) {
        TaskScheduler.create(new Task<List<com.d.lib.orm.room.bean.Book>>() {
            @Override
            public List<com.d.lib.orm.room.bean.Book> run() {
                List<com.d.lib.orm.room.bean.Book> list = new ArrayList<>();
                for (int i = 0; i < 66; i++) {
                    com.d.lib.orm.room.bean.Book book = new com.d.lib.orm.room.bean.Book((long) i,
                            "N" + 1, "A" + i, 0L, 0D);
                    list.add(book);
                }
                return list;
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<com.d.lib.orm.room.bean.Book>>() {
                    @Override
                    public void onNext(@NonNull List<com.d.lib.orm.room.bean.Book> list) {
                        com.d.lib.orm.room.bean.Book[] books = new com.d.lib.orm.room.bean.Book[list.size()];
                        list.toArray(books);
                        com.d.lib.orm.room.db.AppDB.getInstance(context).optBook().insertOrReplace(books);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void initOrmlite(final Context context) {
        TaskScheduler.create(new Task<List<com.d.lib.orm.ormlite.bean.Book>>() {
            @Override
            public List<com.d.lib.orm.ormlite.bean.Book> run() {
                List<com.d.lib.orm.ormlite.bean.Book> list = new ArrayList<>();
                for (int i = 0; i < 66; i++) {
                    com.d.lib.orm.ormlite.bean.Book book = new com.d.lib.orm.ormlite.bean.Book((long) i,
                            "N" + 1, "A" + i, 0L, 0D);
                    list.add(book);
                }
                return list;
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<com.d.lib.orm.ormlite.bean.Book>>() {
                    @Override
                    public void onNext(@NonNull List<com.d.lib.orm.ormlite.bean.Book> list) {
                        com.d.lib.orm.ormlite.utils.AppDBUtil.getInstance(context).optBook().insertOrReplace(list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void initLitepal(final Context context) {
        TaskScheduler.create(new Task<List<com.d.lib.orm.litepal.bean.Book>>() {
            @Override
            public List<com.d.lib.orm.litepal.bean.Book> run() {
                List<com.d.lib.orm.litepal.bean.Book> list = new ArrayList<>();
                for (int i = 0; i < 66; i++) {
                    com.d.lib.orm.litepal.bean.Book book = new com.d.lib.orm.litepal.bean.Book((long) i,
                            "N" + 1, "A" + i, 0L, 0D);
                    list.add(book);
                }
                return list;
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<com.d.lib.orm.litepal.bean.Book>>() {
                    @Override
                    public void onNext(@NonNull List<com.d.lib.orm.litepal.bean.Book> list) {
                        com.d.lib.orm.litepal.utils.AppDBUtil.getInstance(context).optBook().insertOrReplace(list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void initRealm(final Context context) {
        TaskScheduler.create(new Task<List<com.d.lib.orm.realm.bean.Book>>() {
            @Override
            public List<com.d.lib.orm.realm.bean.Book> run() {
                List<com.d.lib.orm.realm.bean.Book> list = new ArrayList<>();
                for (int i = 0; i < 66; i++) {
                    com.d.lib.orm.realm.bean.Book book = new com.d.lib.orm.realm.bean.Book((long) i,
                            "N" + 1, "A" + i, 0L, 0D);
                    list.add(book);
                }
                return list;
            }
        }).subscribeOn(Schedulers.mainThread())
                .subscribe(new Observer<List<com.d.lib.orm.realm.bean.Book>>() {
                    @Override
                    public void onNext(@NonNull List<com.d.lib.orm.realm.bean.Book> list) {
                        com.d.lib.orm.realm.utils.AppDBUtil.getInstance(context).optBook().insert(list, true);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
