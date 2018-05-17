package com.d.lib.common.common.preference;

import android.content.Context;

import com.d.lib.common.common.preference.operation.OpSub;

/**
 * Preferences
 * Created by D on 2017/4/29.
 */
public class Preferences extends AbstractPreference {
    private static Preferences instance;
    private OpSub opSub;

    private Preferences(Context context) {
        super(context);
        initOps();
    }

    /**
     * 初始化操作句柄
     */
    private void initOps() {
        opSub = new OpSub(settings, editor);
    }

    public static Preferences getInstance(Context context) {
        if (instance == null) {
            synchronized (Preferences.class) {
                if (instance == null) {
                    instance = new Preferences(context);
                }
            }
        }
        return instance;
    }

    private void save() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    public void clearUserData(String account) {
        boolean isFirst = getIsFirst();
        clearAllData();
        putIsFirst(isFirst);
    }

    public void clearAllData() {
        editor.clear();
        save();
    }

    /****************************** Sub ******************************/
    public OpSub optSms() {
        return opSub;
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
