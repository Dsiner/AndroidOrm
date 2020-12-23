package com.d.androidorm.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d.androidorm.R;

/**
 * OperateView
 * Created by D on 2018/5/15.
 */
public class OperateView extends LinearLayout implements View.OnClickListener {
    public static final int STATE_DONE = 0;
    public static final int STATE_INSERTING = 1;
    public static final int STATE_UPDATING = 2;
    private TextView[] tvOps;
    private int state = STATE_DONE;
    private OnCallback listener;

    public OperateView(Context context) {
        super(context);
        init(context);
    }

    public OperateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public OperateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        View root = LayoutInflater.from(context).inflate(R.layout.layout_operate, this);
        tvOps = new TextView[]{(TextView) findViewById(R.id.tv_op0),
                (TextView) findViewById(R.id.tv_op1),
                (TextView) findViewById(R.id.tv_op2),
                (TextView) findViewById(R.id.tv_op3)};
        for (TextView op : tvOps) {
            op.setOnClickListener(this);
        }
        setState(STATE_DONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_op0:
                break;
            case R.id.tv_op1:
                if (listener != null) {
                    listener.add();
                }
                break;
            case R.id.tv_op2:
                if (listener != null) {
                    listener.cancel();
                }
                break;
            case R.id.tv_op3:
                if (listener != null) {
                    if (state == STATE_INSERTING) {
                        listener.insert();
                    } else if (state == STATE_UPDATING) {
                        listener.update();
                    }
                }
                break;
        }
    }

    public void setState(int state) {
        switch (state) {
            case STATE_DONE:
                this.state = state;
                tvOps[0].setVisibility(GONE);
                tvOps[1].setVisibility(VISIBLE);
                tvOps[1].setText("Insert");
                tvOps[2].setVisibility(GONE);
                tvOps[3].setVisibility(GONE);
                break;

            case STATE_INSERTING:
                this.state = state;
                tvOps[0].setVisibility(GONE);
                tvOps[1].setVisibility(GONE);
                tvOps[2].setVisibility(VISIBLE);
                tvOps[2].setText("Cancel");
                tvOps[3].setVisibility(VISIBLE);
                tvOps[3].setText("Done");
                break;

            case STATE_UPDATING:
                this.state = state;
                tvOps[0].setVisibility(GONE);
                tvOps[1].setVisibility(GONE);
                tvOps[2].setVisibility(VISIBLE);
                tvOps[2].setText("Cancel");
                tvOps[3].setVisibility(VISIBLE);
                tvOps[3].setText("Done");
                break;
        }
    }

    public void setOnCallback(OnCallback l) {
        this.listener = l;
    }

    public interface OnCallback {
        void add();

        void insert();

        void update();

        void cancel();
    }
}
