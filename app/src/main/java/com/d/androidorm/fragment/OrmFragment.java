package com.d.androidorm.fragment;

import android.view.View;

import com.d.androidorm.R;
import com.d.androidorm.presenter.OrmPresenter;
import com.d.androidorm.view.IOrmView;
import com.d.androidorm.widget.OperateView;
import com.d.lib.common.module.loader.AbsFragment;
import com.d.lib.common.utils.ViewHelper;
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

    protected String mTitle;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int resId = v.getId();
        if (resId == R.id.iv_title_left) {
            getActivity().finish();
        }
    }

    protected abstract String getTitle();

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_orm;
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);
        ViewHelper.setOnClick(rootView, this, R.id.iv_title_left);
    }

    @Override
    protected void init() {
        mTitle = getTitle();
        super.init();
    }

    @Override
    protected void initList() {
        super.initList();
        xrvList.setCanRefresh(false);
        xrvList.setCanLoadMore(false);
    }
}
