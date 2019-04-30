package com.d.androidorm.presenter;

import android.content.Context;

import com.d.lib.orm.room.bean.Book;
import com.d.lib.orm.room.db.AppDB;

import java.util.List;

/**
 * GreenDaoPresenter
 * Created by D on 2018/5/14.
 */
public class RoomPresenter extends OrmPresenter<Book> {

    public RoomPresenter(Context context) {
        super(context);
    }

    @Override
    protected void insertDB(Book bean) {
        AppDB.getInstance(mContext).optBook().insertOrReplace(bean);
    }

    @Override
    protected void deleteDB(Book bean) {
        AppDB.getInstance(mContext).optBook().delete(bean);
    }

    @Override
    protected void updateDB(Book bean) {
        AppDB.getInstance(mContext).optBook().update(bean);
    }

    @Override
    protected List<Book> queryAllDB() {
        return AppDB.getInstance(mContext).optBook().queryAll();
    }
}
