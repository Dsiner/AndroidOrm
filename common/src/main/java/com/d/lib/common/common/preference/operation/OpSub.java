package com.d.lib.common.common.preference.operation;

import android.content.SharedPreferences;

/**
 * Sub Operation
 * Created by D on 2018/3/14.
 */
public class OpSub extends AbstractOp {

    public OpSub(SharedPreferences settings, SharedPreferences.Editor editor) {
        super(settings, editor);
    }

    /*************************** 第一次使用 ***************************/
    /**
     * 设置是否是第一次使用
     */
    public void putIsFirst(boolean isFirst) {
        editor.putBoolean("is_first", isFirst);
        save();
    }

    /**
     * 获取是否是第一次使用
     */
    public boolean getIsFirst() {
        return settings.getBoolean("is_first", true);
    }
}
