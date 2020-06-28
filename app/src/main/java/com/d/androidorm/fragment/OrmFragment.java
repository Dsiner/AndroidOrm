package com.d.androidorm.fragment;

import android.view.View;

import com.d.androidorm.R;
import com.d.androidorm.presenter.OrmPresenter;
import com.d.androidorm.view.IOrmView;
import com.d.androidorm.widget.OperateView;
import com.d.lib.common.component.loader.v4.AbsFragment;
import com.d.lib.common.util.ViewHelper;
import com.d.lib.common.view.TitleLayout;

/**
 * OrmFragment
 * Created by D on 2018/5/15.
 */
public abstract class OrmFragment<T> extends AbsFragment<T, OrmPresenter<T>> implements IOrmView<T> {
    TitleLayout tl_title;
    OperateView v_operate;

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
        tl_title = ViewHelper.findView(rootView, R.id.tl_title);
        v_operate = (OperateView) ((View) ViewHelper.findView(tl_title,
                R.id.llyt_operate_right)).getParent();

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
        mXrvList.setCanRefresh(false);
        mXrvList.setCanLoadMore(false);
    }
}
