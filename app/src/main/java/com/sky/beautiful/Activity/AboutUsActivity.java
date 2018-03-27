package com.sky.beautiful.Activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.beautiful.BaseActivity.BaseActivity;
import com.sky.beautiful.ImmersionBar.ImmersionBar;
import com.sky.beautiful.R;
import com.sky.beautiful.Utils.AppUtil;

/**
 * @Time : 2017/12/29 no 下午3:26
 * @USER : vvguoliang
 * @File : AboutUsActivity.java
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

public class AboutUsActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_relat;

    private TextView title, about_us_text;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search_image2:
                finish();
                break;
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        title_relat.setAlpha(0.7f);
        title.setText(getString(R.string.about_us));
        findView(R.id.title_search_image2).setOnClickListener(this);
        findView(R.id.title_search_image2).setVisibility(View.VISIBLE);
        about_us_text.setText("v" + AppUtil.getInstance().getVersionName(1, this));
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
        title_relat = findView(R.id.title_relat);
        title = findView(R.id.title);
        about_us_text = findView(R.id.about_us_text);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.act_about_us;
    }
}
