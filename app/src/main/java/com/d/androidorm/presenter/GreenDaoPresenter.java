package com.d.androidorm.presenter;

import android.content.Context;

import com.d.lib.orm.greendao.bean.Book;
import com.d.lib.orm.greendao.utils.AppDBUtil;

import java.util.List;

/**
 * GreenDaoPresenter
 * Created by D on 2018/5/14.
 */
public class GreenDaoPresenter extends OrmPresenter<Book> {

    public GreenDaoPresenter(Context context) {
        super(context);
    }

    @Override
    protected void insertDB(Book bean) {
        AppDBUtil.getInstance(mContext).optBook().insertBook(bean);
    }

    @Override
    protected void deleteDB(Book bean) {
        AppDBUtil.getInstance(mContext).optBook().deleteBook(bean);
    }

    @Override
    protected void updateDB(Book bean) {
        AppDBUtil.getInstance(mContext).optBook().insertBook(bean);
    }

    @Override
    protected List<Book> queryAllDB() {
        return AppDBUtil.getInstance(mContext).optBook().queryAll();
    }
}
