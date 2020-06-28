package com.d.androidorm.adapter;

import android.content.Context;

import com.d.androidorm.R;
import com.d.androidorm.widget.EditRow;
import com.d.lib.orm.sqlite.bean.Book;
import com.d.lib.xrv.adapter.CommonHolder;
import com.d.lib.xrv.adapter.MultiItemTypeSupport;

import java.util.List;

/**
 * GreenDaoAdapter
 * Created by D on 2018/5/17.
 */
public class qliteAdapter extends OrmAdapter<Book> {
    private EditRow etrRow;

    public qliteAdapter(Context context, List<Book> datas, MultiItemTypeSupport<Book> multiItemTypeSupport) {
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
                holder.setText(R.id.tv_row_no, "" + item.isbn);
                holder.setText(R.id.tv_row_name, item.name);
                holder.setText(R.id.tv_row_author, item.author);
                holder.setText(R.id.tv_row_price, "" + item.price);
                holder.setText(R.id.tv_row_date, "" + item.date);
                break;
            case R.layout.adapter_orm_edit:
                etrRow = holder.getView(R.id.etr_row);
                etrRow.setTag(item);
                etrRow.setNo(item.isbn != null ? "" + item.isbn : "-");
                etrRow.setName(item.name);
                etrRow.setAuthor(item.author);
                etrRow.setPrice("" + item.price);
                etrRow.setDate("" + item.date);
                etrRow.setValues("" + item.isbn, item.name,
                        item.author, "" + item.price, "" + item.date);
                etrRow.setOnTextChangedListener(new EditRow.OnTextChangedListener() {
                    @Override
                    public void afterTextChanged(String... s) {
                        item.name = s[1];
                        item.author = s[2];
                        item.price = Double.valueOf(s[3]);
                        item.date = Long.valueOf(s[4]);
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
