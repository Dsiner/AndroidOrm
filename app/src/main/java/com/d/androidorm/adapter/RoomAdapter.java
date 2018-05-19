package com.d.androidorm.adapter;

import android.content.Context;

import com.d.androidorm.R;
import com.d.androidorm.widget.EditRow;
import com.d.lib.orm.room.bean.Book;
import com.d.lib.xrv.adapter.CommonHolder;
import com.d.lib.xrv.adapter.MultiItemTypeSupport;

import java.util.List;

/**
 * GreenDaoAdapter
 * Created by D on 2018/5/17.
 */
public class RoomAdapter extends OrmAdapter<Book> {
    private EditRow etrRow;

    public RoomAdapter(Context context, List<Book> datas, MultiItemTypeSupport<Book> multiItemTypeSupport) {
        super(context, datas, multiItemTypeSupport);
    }

    public EditRow getEtrRow() {
        return etrRow;
    }

    @Override
    public void convert(int position, CommonHolder holder, final Book item) {
        super.convert(position, holder, item);
        switch (holder.mLayoutId) {
            case R.layout.adapter_orm:
                holder.setText(R.id.tv_row_no, "" + item.getIsbn());
                holder.setText(R.id.tv_row_name, item.getName());
                holder.setText(R.id.tv_row_author, item.getAuthor());
                holder.setText(R.id.tv_row_price, "" + item.getPrice());
                holder.setText(R.id.tv_row_date, "" + item.getDate());
                break;
            case R.layout.adapter_orm_edit:
                etrRow = holder.getView(R.id.etr_row);
                etrRow.setTag(item);
                etrRow.setNo(item.getIsbn() != null ? "" + item.getIsbn() : "-");
                etrRow.setName(item.getName());
                etrRow.setAuthor(item.getAuthor());
                etrRow.setPrice("" + item.getPrice());
                etrRow.setDate("" + item.getDate());
                etrRow.setValues("" + item.getIsbn(), item.getName(), item.getAuthor(), "" + item.getPrice(), "" + item.getDate());
                etrRow.setOnTextChangedListener(new EditRow.OnTextChangedListener() {
                    @Override
                    public void afterTextChanged(String... s) {
                        item.setName(s[1]);
                        item.setAuthor(s[2]);
                        item.setPrice(Double.valueOf(s[3]));
                        item.setDate(Long.valueOf(s[4]));
                    }
                });
                break;
        }
    }

    @Override
    void update(Book item) {
        if (etrRow != null && etrRow.getTag() != null) {
            Book book = (Book) etrRow.getTag();
            book.type = 0;
        }
        if (listener != null) {
            listener.update(item);
        }
    }

    @Override
    void delete(Book item) {
        if (listener != null) {
            listener.delete(item);
        }
    }
}
