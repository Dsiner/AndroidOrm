package com.d.androidorm.fragment;

import com.d.androidorm.R;
import com.d.androidorm.adapter.GreenDaoAdapter;
import com.d.androidorm.adapter.OrmAdapter;
import com.d.androidorm.presenter.GreenDaoPresenter;
import com.d.androidorm.presenter.OrmPresenter;
import com.d.androidorm.view.IGreenDaoView;
import com.d.androidorm.widget.EditRow;
import com.d.androidorm.widget.OperateView;
import com.d.lib.common.component.mvp.MvpView;
import com.d.lib.common.utils.keyboard.KeyboardHelper;
import com.d.lib.orm.greendao.bean.Book;
import com.d.lib.xrv.adapter.CommonAdapter;
import com.d.lib.xrv.adapter.MultiItemTypeSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * GreenDaoFragment
 * Created by D on 2018/5/15.
 */
public class GreenDaoFragment extends OrmFragment<Book> implements IGreenDaoView {

    @Override
    protected CommonAdapter<Book> getAdapter() {
        final GreenDaoAdapter adapter = new GreenDaoAdapter(mContext, new ArrayList<Book>(), new MultiItemTypeSupport<Book>() {
            @Override
            public int getLayoutId(int viewType) {
                switch (viewType) {
                    case 1:
                        return R.layout.adapter_orm_edit;
                    case 0:
                    default:
                        return R.layout.adapter_orm;
                }
            }

            @Override
            public int getItemViewType(int position, Book book) {
                return book.type;
            }
        });
        adapter.setOnOperateListener(new OrmAdapter.OnOperateListener<Book>() {
            @Override
            public void update(Book item) {
                item.type = 1;
                adapter.notifyDataSetChanged();
                ovOperate.setState(OperateView.STATE_UPDATING);
            }

            @Override
            public void delete(Book item) {
                mPresenter.delete(item);
            }
        });
        return adapter;
    }

    @Override
    public OrmPresenter<Book> getPresenter() {
        return new GreenDaoPresenter(getActivity().getApplicationContext());
    }

    @Override
    protected MvpView getMvpView() {
        return this;
    }

    @Override
    protected String getTitle() {
        return "GreenDao";
    }

    @Override
    protected void init() {
        super.init();
        tlTitle.setText(R.id.tv_title_title, mTitle);
        ovOperate.setOnCallback(new OperateView.OnCallback() {
            @Override
            public void add() {
                Book book = new Book(null, "", "", 0L, 0D);
                book.type = 1;
                List<Book> datas = new ArrayList<>();
                datas.addAll(mCommonLoader.getDatas());
                datas.add(book);
                mCommonLoader.setData(datas);
                ovOperate.setState(OperateView.STATE_INSERTING);
                mXrvList.scrollToPosition(mCommonLoader.getDatas().size());
            }

            @Override
            public void insert() {
                GreenDaoAdapter greenDaoAdapter = (GreenDaoAdapter) mAdapter;
                EditRow etrRow = greenDaoAdapter.getEtrRow();
                Book book = (Book) etrRow.getTag();
                mPresenter.insert(book);
            }

            @Override
            public void update() {
                GreenDaoAdapter greenDaoAdapter = (GreenDaoAdapter) mAdapter;
                EditRow etrRow = greenDaoAdapter.getEtrRow();
                Book book = (Book) etrRow.getTag();
                mPresenter.update(book);
            }

            @Override
            public void cancel() {
                getData();
            }
        });
    }

    @Override
    protected void onLoad(int page) {
        mPresenter.queryAll();
    }

    @Override
    public void setData(List<Book> datas) {
        super.setData(datas);
        tlTitle.setText(R.id.tv_title_title, mTitle + (datas.size() > 0 ? "(" + datas.size() + ")" : ""));
        ovOperate.setState(OperateView.STATE_DONE);
        KeyboardHelper.hideKeyboard(mRootView);
    }
}
