package com.d.androidorm.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.d.lib.common.component.loader.AbsPresenter;
import com.d.lib.taskscheduler.TaskScheduler;
import com.d.lib.taskscheduler.callback.Observer;
import com.d.lib.taskscheduler.callback.Task;
import com.d.lib.taskscheduler.schedule.Schedulers;

import java.util.List;

/**
 * OrmPresenter
 * Created by D on 2018/5/14.
 */
public abstract class OrmPresenter<T> extends AbsPresenter<T> {

    public OrmPresenter(Context context) {
        super(context);
    }

    public void insert(final T bean) {
        TaskScheduler.create(new Task<Boolean>() {
            @Override
            public Boolean run() {
                insertDB(bean);
                return true;
            }
        }).subscribeOn(Schedulers.io())
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

    public void delete(final T bean) {
        TaskScheduler.create(new Task<Boolean>() {
            @Override
            public Boolean run() {
                deleteDB(bean);
                return true;
            }
        }).subscribeOn(Schedulers.io())
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

    public void update(final T bean) {
        TaskScheduler.create(new Task<Boolean>() {
            @Override
            public Boolean run() {
                updateDB(bean);
                return true;
            }
        }).subscribeOn(Schedulers.io())
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

    public void queryAll() {
        TaskScheduler.create(new Task<List<T>>() {
            @Override
            public List<T> run() {
                List<T> datas = queryAllDB();
                return datas;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.mainThread())
                .subscribe(new Observer<List<T>>() {
                    @Override
                    public void onNext(@NonNull List<T> result) {
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

    protected abstract void insertDB(T bean);

    protected abstract void deleteDB(T bean);

    protected abstract void updateDB(T bean);

    protected abstract List<T> queryAllDB();
}
