package com.d.androidorm.presenter;

import android.content.Context;

import com.d.lib.orm.sqlite.DBManager;
import com.d.lib.orm.sqlite.bean.Book;

import java.util.List;

/**
 * SQLitePresenter
 * Created by D on 2018/5/14.
 */
public class SQLitePresenter extends OrmPresenter<Book> {

    public SQLitePresenter(Context context) {
        super(context);
    }

    @Override
    protected void insertDB(Book bean) {
        DBManager.getInstance(mContext).opBook.insert(bean);
    }

    @Override
    protected void deleteDB(Book bean) {
        DBManager.getInstance(mContext).opBook.delete(bean);
    }

    @Override
    protected void updateDB(Book bean) {
        DBManager.getInstance(mContext).opBook.update(bean);
    }

    @Override
    protected List<Book> queryAllDB() {
        return DBManager.getInstance(mContext).opBook.queryAll();
    }
}
