package com.d.androidorm.presenter;

import android.content.Context;

import com.d.lib.orm.litepal.bean.Book;
import com.d.lib.orm.litepal.utils.AppDBUtil;

import java.util.List;

/**
 * GreenDaoPresenter
 * Created by D on 2018/5/14.
 */
public class LitepalPresenter extends OrmPresenter<Book> {

    public LitepalPresenter(Context context) {
        super(context);
    }

    @Override
    protected void insertDB(Book bean) {
        AppDBUtil.getInstance(mContext).optBook().insertOrReplace(bean);
    }

    @Override
    protected void deleteDB(Book bean) {
        AppDBUtil.getInstance(mContext).optBook().delete(bean);
    }

    @Override
    protected void updateDB(Book bean) {
        AppDBUtil.getInstance(mContext).optBook().insertOrReplace(bean);
    }

    @Override
    protected List<Book> queryAllDB() {
        return AppDBUtil.getInstance(mContext).optBook().queryAll();
    }
}
