package com.sky.beautiful.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.sky.beautiful.BaseActivity.BaseActivity;
import com.sky.beautiful.BaseActivityManager;
import com.sky.beautiful.ImmersionBar.ImmersionBar;
import com.sky.beautiful.R;
import com.sky.beautiful.Utils.CacheUtil;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.Utils.ToatUtils;
import com.sky.beautiful.Utils.UtilsXML;

/**
 * @Time : 2017/12/27 no 下午6:29
 * @USER : vvguoliang
 * @File : SetUpActivity.java
 * @Software: Android Studio
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 * ***┏┓   ┏ ┓
 * **┏┛┻━━━┛ ┻┓
 * **┃   ☃   ┃
 * **┃ ┳┛  ┗┳ ┃
 * **┃    ┻   ┃
 * **┗━┓    ┏━┛
 * ****┃    ┗━━━┓
 * ****┃ 神兽保佑 ┣┓
 * ****┃ 永无BUG！┏┛
 * ****┗┓┓┏━┳┓┏┛┏┛
 * ******┃┫┫  ┃┫┫
 * ******┗┻┛  ┗┻┛
 */

public class SetUpActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private LinearLayout set_up_gesture, title_relat;

    private TextView set_up_clear_data_text;

    private Switch switch1;

    private boolean isChecked = false;

    private String mFirst = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search_image2:
                finish();
                break;
            case R.id.set_up_gesture:
                Bundle build = new Bundle();
                if (isChecked) {
                    build.putString("name", "绘制图案密码");
                    build.putInt("type", 2);
                    build.putBoolean("isVerify", true);
                } else {
                    build.putInt("type", 1);
                    build.putBoolean("isVerify", false);
                    build.putString("name", "设置图案密码");
                }
                startActivity(GestureActivity.class, build);
                break;
            case R.id.set_up_clear_data:
                CacheUtil.clearAllCache(this);
                ToatUtils.showShort1(this, "缓存已清理");
                try {
                    set_up_clear_data_text.setText(CacheUtil.getTotalCacheSize(this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.set_up_password:
                Bundle bundle = new Bundle();
                bundle.putInt("bundle", 1);
                startActivity(NoPasswordActivity.class, bundle);
                break;
            case R.id.set_up_about_us:
                startActivity(AboutUsActivity.class);
                break;
            case R.id.set_up_exit_logon:
                SharedPreferencesUtils.logoutSuccess(this);
                finish();
                break;
        }
    }

    @Override
    public void initData() {
        mFirst = SharedPreferencesUtils.get(this, "mFirstPassword", "").toString();
        if (!TextUtils.isEmpty(mFirst)) {
            switch1.setChecked(true);
        } else {
            switch1.setChecked(false);
        }
        if (TextUtils.isEmpty(SharedPreferencesUtils.get(this, "userNo", "").toString())) {
            switch1.setChecked(false);
        }
        try {
            set_up_clear_data_text.setText(CacheUtil.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        title_relat.setAlpha(0.7f);
        findView(R.id.title_search_image2).setOnClickListener(this);
        findView(R.id.title_search_image2).setVisibility(View.VISIBLE);
//        findView(R.id.set_up_gesture).setOnClickListener(this);
        findView(R.id.set_up_clear_data).setOnClickListener(this);
        findView(R.id.set_up_password).setOnClickListener(this);
        findView(R.id.set_up_about_us).setOnClickListener(this);
        findView(R.id.set_up_exit_logon).setOnClickListener(this);
        UtilsXML.getInstance().closeKeyboard(SetUpActivity.this);
    }

    @Override
    public void initView() {
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            mImmersionBar.statusBarDarkFont(true).init();
        } else {
            findViewById(R.id.tab).setBackgroundColor(Color.rgb(231, 231, 231));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            findViewById(R.id.tab).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.tab).setVisibility(View.GONE);
        }
        TextView title = findView(R.id.title);
        title_relat = findView(R.id.title_relat);
        switch1 = findView(R.id.switch1);
        set_up_clear_data_text = findView(R.id.set_up_clear_data_text);
        title.setText(getString(R.string.set_up));
    }

    @Override
    protected int setLayoutId() {
        return R.layout.act_set_up;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Bundle build = new Bundle();
            build.putString("name", "设置图案密码");
            build.putInt("type", 1);
            build.putBoolean("isVerify", false);
            startActivityForResult(GestureActivity.class, 100, build);
        } else {
            Bundle build = new Bundle();
            build.putString("name", "关闭图案密码");
            build.putInt("type", 0);
            build.putBoolean("isVerify", true);
            startActivityForResult(GestuerOneActivity.class, 101, build);
        }
        this.isChecked = isChecked;
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch1.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch1.setOnCheckedChangeListener(null);
        if (resultCode == 100) {
            switch1.setChecked(true);
        } else if (resultCode == 101) {
            switch1.setChecked(!isChecked);
        } else if (resultCode == 102) {
            switch1.setChecked(!isChecked);
        } else if (resultCode == 0) {
            switch1.setChecked(isChecked);
        }
        switch1.setOnCheckedChangeListener(this);
        BaseActivityManager.getActivityManager().popActivityOne(GestureActivity.class);
    }
}
