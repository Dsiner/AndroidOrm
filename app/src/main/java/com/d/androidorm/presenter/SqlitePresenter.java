package com.d.androidorm.presenter;

import android.content.Context;

import com.d.lib.orm.sqlite.bean.Book;
import com.d.lib.orm.sqlite.utils.AppDBUtil;

import java.util.List;

/**
 * GreenDaoPresenter
 * Created by D on 2018/5/14.
 */
public class SqlitePresenter extends OrmPresenter<Book> {

    public SqlitePresenter(Context context) {
        super(context);
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
