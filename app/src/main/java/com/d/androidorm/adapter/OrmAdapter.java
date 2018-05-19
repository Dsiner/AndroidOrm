package com.d.androidorm.adapter;

import android.content.Context;
import android.view.View;

import com.d.androidorm.R;
import com.d.lib.slidelayout.SlideLayout;
import com.d.lib.slidelayout.SlideManager;
import com.d.lib.xrv.adapter.CommonAdapter;
import com.d.lib.xrv.adapter.CommonHolder;
import com.d.lib.xrv.adapter.MultiItemTypeSupport;

import java.util.List;

/**
 * OrmAdapter
 * Created by D on 2018/5/14.
 */
public abstract class OrmAdapter<T> extends CommonAdapter<T> {
    protected SlideManager slideManager;
    protected OnOperateListener<T> listener;

    public OrmAdapter(Context context, List<T> datas, MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, datas, multiItemTypeSupport);
        slideManager = new SlideManager();
    }

    @Override
    public void convert(int position, CommonHolder holder, final T item) {
        switch (holder.mLayoutId) {
            case R.layout.adapter_orm:
                final SlideLayout slSlide = holder.getView(R.id.sl_slide);
                slSlide.setOnStateChangeListener(new SlideLayout.OnStateChangeListener() {
                    @Override
                    public void onChange(SlideLayout layout, boolean isOpen) {
                        slideManager.onChange(layout, isOpen);
                    }

                    @Override
                    public boolean closeAll(SlideLayout layout) {
                        return slideManager.closeAll(layout);
                    }
                });
                holder.setViewOnClickListener(R.id.tv_update, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        slSlide.setOpen(false, false);
                        update(item);
                    }
                });
                holder.setViewOnClickListener(R.id.tv_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        slSlide.setOpen(false, false);
                        delete(item);
                    }
                });
                break;
            case R.layout.adapter_orm_edit:
                break;
        }
    }

    abstract void update(T item);

    abstract void delete(T item);

    public interface OnOperateListener<T> {
        void update(T item);

        void delete(T item);
    }

    public void setOnOperateListener(OnOperateListener<T> l) {
        this.listener = l;
    }
}
