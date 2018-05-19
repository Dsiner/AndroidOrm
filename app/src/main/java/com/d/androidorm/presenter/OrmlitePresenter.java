package com.d.androidorm.presenter;

import android.content.Context;

import com.d.lib.orm.ormlite.bean.Book;
import com.d.lib.orm.ormlite.utils.AppDBUtil;

import java.util.List;

/**
 * GreenDaoPresenter
 * Created by D on 2018/5/14.
 */
public class OrmlitePresenter extends OrmPresenter<Book> {

    public OrmlitePresenter(Context context) {
        super(context);
    }

    @Override
    protected void insertDB(Book bean) {
        AppDBUtil.getInstance(mContext).optBook().insert(bean);
    }

    @Override
    protected void deleteDB(Book bean) {
        AppDBUtil.getInstance(mContext).optBook().deleteBook(bean);
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
