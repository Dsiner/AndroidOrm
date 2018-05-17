package com.d.lib.common.common.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * AbstractPreference
 * Created by D on 2017/4/29.
 */
public class AbstractPreference {
    protected SharedPreferences settings;
    protected SharedPreferences.Editor editor;

    public AbstractPreference(Context context) {
        settings = context.getApplicationContext().getSharedPreferences("android_orm_" + getClass().getSimpleName(), 0);
        editor = settings.edit();
    }

    public void registerOnSharePreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        settings.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharePreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        settings.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
