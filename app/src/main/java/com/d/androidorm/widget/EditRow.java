package com.d.androidorm.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.d.androidorm.R;

/**
 * EditRow
 * Created by D on 2018/5/17.
 */
public class EditRow extends FrameLayout {
    private TextView tvNo;
    private EditText etName, etAuthor, etPrice, etDate;
    private String[] values = new String[]{"", "", "", "", ""};
    private OnTextChangedListener listener;

    public EditRow(@NonNull Context context) {
        super(context);
        init(context);
    }

    public EditRow(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EditRow(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.layout_edit_row, this);
        tvNo = (TextView) root.findViewById(R.id.tv_row_edit_no);
        etName = (EditText) root.findViewById(R.id.et_row_edit_name);
        etAuthor = (EditText) root.findViewById(R.id.et_row_edit_author);
        etPrice = (EditText) root.findViewById(R.id.et_row_edit_price);
        etDate = (EditText) root.findViewById(R.id.et_row_edit_date);

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                values[1] = s.toString();
                if (listener != null) {
                    listener.afterTextChanged(values);
                }
            }
        });

        etAuthor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                values[2] = s.toString();
                if (listener != null) {
                    listener.afterTextChanged(values);
                }
            }
        });


        etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                values[3] = s.toString();
                if (listener != null) {
                    listener.afterTextChanged(values);
                }
            }
        });


        etDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                values[4] = s.toString();
                if (listener != null) {
                    listener.afterTextChanged(values);
                }
            }
        });
    }

    public void setValues(String... values) {
        this.values = values;
    }

    public void setNo(CharSequence text) {
        tvNo.setText(text);
    }

    public CharSequence getNo() {
        return tvNo.getText();
    }

    public void setName(CharSequence text) {
        etName.setText(text);
    }

    public CharSequence getName() {
        return etName.getText();
    }

    public void setAuthor(CharSequence text) {
        etAuthor.setText(text);
    }

    public CharSequence getAuthor() {
        return etAuthor.getText();
    }

    public void setPrice(CharSequence text) {
        etPrice.setText(text);
    }

    public CharSequence getPrice() {
        return etPrice.getText();
    }

    public void setDate(CharSequence text) {
        etDate.setText(text);
    }

    public CharSequence getDate() {
        return etDate.getText();
    }

    public interface OnTextChangedListener {
        void afterTextChanged(String... s);
    }

    public void setOnTextChangedListener(OnTextChangedListener l) {
        this.listener = l;
    }
}
