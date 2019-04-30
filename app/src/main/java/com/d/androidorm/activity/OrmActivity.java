package com.d.androidorm.activity;

import android.support.v4.app.Fragment;

import com.d.androidorm.R;
import com.d.androidorm.fragment.GreenDaoFragment;
import com.d.androidorm.fragment.LitepalFragment;
import com.d.androidorm.fragment.OrmliteFragment;
import com.d.androidorm.fragment.RealmFragment;
import com.d.androidorm.fragment.RoomFragment;
import com.d.androidorm.fragment.SqliteFragment;
import com.d.lib.common.component.mvp.app.v4.BaseFragmentActivity;

public class OrmActivity extends BaseFragmentActivity {

    @Override
    protected int getLayoutRes() {
        return R.layout.lib_pub_activity_abs_frg;
    }

    @Override
    protected void init() {
        Fragment fragment;
        int type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 1:
                fragment = new SqliteFragment();
                break;
            case 2:
                fragment = new RoomFragment();
                break;
            case 3:
                fragment = new OrmliteFragment();
                break;
            case 4:
                fragment = new LitepalFragment();
                break;
            case 5:
                fragment = new RealmFragment();
                break;
            case 0:
            default:
                fragment = new GreenDaoFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, fragment).commitAllowingStateLoss();
    }
}
