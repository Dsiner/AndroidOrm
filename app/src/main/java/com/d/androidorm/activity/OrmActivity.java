package com.d.androidorm.activity;

import com.d.androidorm.R;
import com.d.androidorm.fragment.LitepalFragment;
import com.d.lib.common.component.mvp.app.v4.BaseFragmentActivity;

public class OrmActivity extends BaseFragmentActivity {

    @Override
    protected int getLayoutRes() {
        return R.layout.lib_pub_activity_abs_frg;
    }

    @Override
    protected void init() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, new LitepalFragment()).commitAllowingStateLoss();
    }
}
