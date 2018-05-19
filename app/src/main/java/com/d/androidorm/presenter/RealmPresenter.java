package com.d.androidorm.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.d.lib.orm.realm.bean.Book;
import com.d.lib.orm.realm.utils.AppDBUtil;
import com.d.lib.taskscheduler.TaskScheduler;
import com.d.lib.taskscheduler.callback.Observer;
import com.d.lib.taskscheduler.callback.Task;
import com.d.lib.taskscheduler.schedule.Schedulers;

import java.util.List;

/**
 * GreenDaoPresenter
 * Created by D on 2018/5/14.
 */
public class RealmPresenter extends OrmPresenter<Book> {

    public RealmPresenter(Context context) {
        super(context);
    }

    @Override
    public void insert(final Book bean) {
        TaskScheduler.create(new Task<Boolean>() {
            @Override
            public Boolean run() {
                insertDB(bean);
                return true;
            }
        }).subscribeOn(Schedulers.mainThread())
                .observeOn(Schedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onNext(@NonNull Boolean result) {
                        if (getView() == null) {
                            return;
                        }
                        getView().getData();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void delete(final Book bean) {
        TaskScheduler.create(new Task<Boolean>() {
            @Override
            public Boolean run() {
                deleteDB(bean);
                return true;
            }
        }).subscribeOn(Schedulers.mainThread())
                .observeOn(Schedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onNext(@NonNull Boolean result) {
                        if (getView() == null) {
                            return;
                        }
                        getView().getData();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void update(final Book bean) {
        TaskScheduler.create(new Task<Boolean>() {
            @Override
            public Boolean run() {
                updateDB(bean);
                return true;
            }
        }).subscribeOn(Schedulers.mainThread())
                .observeOn(Schedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onNext(@NonNull Boolean result) {
                        if (getView() == null) {
                            return;
                        }
                        getView().getData();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void queryAll() {
        TaskScheduler.create(new Task<List<Book>>() {
            @Override
            public List<Book> run() {
                List<Book> datas = queryAllDB();
                return datas;
            }
        }).subscribeOn(Schedulers.mainThread())
                .observeOn(Schedulers.mainThread())
                .subscribe(new Observer<List<Book>>() {
                    @Override
                    public void onNext(@NonNull List<Book> result) {
                        if (getView() == null) {
                            return;
                        }
                        getView().setData(result);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    protected void insertDB(Book bean) {
        AppDBUtil.getInstance(mContext).optBook().insert(bean);
    }

    @Override
    protected void deleteDB(Book bean) {
        AppDBUtil.getInstance(mContext).optBook().delete(bean);
    }

    @Override
    protected void updateDB(Book bean) {
        AppDBUtil.getInstance(mContext).optBook().update(bean);
    }

    @Override
    protected List<Book> queryAllDB() {
        return AppDBUtil.getInstance(mContext).optBook().queryAll();
    }
}
