package com.d.lib.orm.greendao.db;

import android.content.Context;

import com.d.lib.orm.greendao.dao.BookDao;
import com.d.lib.orm.greendao.dao.DaoMaster;
import com.d.lib.orm.greendao.dao.DaoSession;

import de.greenrobot.dao.identityscope.IdentityScopeType;

/**
 * AppDB
 * Created by D on 2017/7/25.
 */
public abstract class AppDB extends AbstractDB<DaoMaster, DaoSession> {
    protected BookDao bookDao;

    protected AppDB(Context context) {
        super(context);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context.getApplicationContext(), "greendao_v2.db", null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
        //GreenDao的Session会将第一次query的结果缓存起来，后面如果调用相同的查询语句则会直接显示缓存的对象
        //两种解决方式：
        //type1:每次查询更新的表之前调用一下<daoSession.clear();>清除缓存
        //type2:初始化时使用无缓存模式
        daoSession = daoMaster.newSession(IdentityScopeType.None);//无缓存模式
        initDaos();
    }

    private void initDaos() {
        bookDao = daoSession.getBookDao();
    }
}
