package com.d.androidorm.presenter;

import android.content.Context;

import com.d.lib.orm.sqlite.bean.Book;
import com.d.lib.orm.sqlite.AppDB;

import java.util.List;

/**
 * GreenDaoPresenter
 * Created by D on 2018/5/14.
 */
public class qlitePresenter extends OrmPresenter<Book> {

    public qlitePresenter(Context context) {
        super(context);
    }

    @Override
    protected void insertDB(Book bean) {
        AppDB.getInstance(mContext).opBook.insert(bean);
    }

    @Override
    protected void deleteDB(Book bean) {
        AppDB.getInstance(mContext).opBook.delete(bean);
    }

    @Override
    protected void updateDB(Book bean) {
        AppDB.getInstance(mContext).opBook.update(bean);
    }

    @Override
    protected List<Book> queryAllDB() {
        return AppDB.getInstance(mContext).opBook.queryAll();
    }
}
