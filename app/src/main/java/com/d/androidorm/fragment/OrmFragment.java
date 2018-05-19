package com.d.androidorm.fragment;

import com.d.androidorm.R;
import com.d.androidorm.presenter.OrmPresenter;
import com.d.androidorm.view.IOrmView;
import com.d.androidorm.widget.OperateView;
import com.d.lib.common.module.loader.AbsFragment;
import com.d.lib.common.view.TitleLayout;

import butterknife.BindView;

/**
 * OrmFragment
 * Created by D on 2018/5/15.
 */
public abstract class OrmFragment<T> extends AbsFragment<T, OrmPresenter<T>> implements IOrmView<T> {
    @BindView(R.id.tl_title)
    TitleLayout tlTitle;
    @BindView(R.id.ov_operate)
    OperateView ovOperate;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_orm;
    }

    @Override
    protected void initList() {
        super.initList();
        xrvList.setCanRefresh(false);
        xrvList.setCanLoadMore(false);
    }
}
