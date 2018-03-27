package com.sky.beautiful.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.beautiful.BaseActivity.BaseActivity;
import com.sky.beautiful.ImmersionBar.ImmersionBar;
import com.sky.beautiful.R;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.Utils.ToatUtils;
import com.sky.beautiful.View.CircleImageView;
import com.sky.beautiful.View.Gesture.GestureContentView;
import com.sky.beautiful.View.Gesture.GestureDrawline;
import com.sky.beautiful.View.Gesture.LockIndicator;

/**
 * @Time : 2017/12/27 no 下午10:18
 * @USER : vvguoliang
 * @File : GestureActivity.java
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

public class GestureActivity extends BaseActivity implements View.OnClickListener {

    private TextView title, text_tip;

    private String name;

    private GestureContentView mGestureContentView;

    private boolean mIsFirstInput = true;

    private String mFirstPassword;

    private LockIndicator lock_indicator;

    private FrameLayout gesture_container;

    private int type = 0;

    private boolean isVerify = false;

    private String passWord = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search_image2:
                setResult(102);
                finish();
                break;
        }

    }

    @Override
    public void initData() {
        title.setText(name);
        findView(R.id.title_search_image2).setOnClickListener(this);
        if (isVerify && type == 2) {
            findView(R.id.title_search_image2).setVisibility(View.GONE);
        } else {
            findView(R.id.title_search_image2).setVisibility(View.VISIBLE);
        }
        getGesture1();
    }

    private void getGesture1() {
        mGestureContentView = new GestureContentView(this, isVerify, passWord, inputCode -> {
            if (!isInputPassValidate(inputCode)) {
                text_tip.setText(Html.fromHtml("<font color='#c70c1e'>最少链接4个点, 请重新输入</font>"));
                mGestureContentView.clearDrawlineState(0L);
                return;
            }
            if (mIsFirstInput) {
                mFirstPassword = inputCode;
                updateCodeList(inputCode);
                mGestureContentView.clearDrawlineState(0L);
            } else {
                if (inputCode.equals(mFirstPassword)) {
                    SharedPreferencesUtils.put(this, "mFirstPassword", mFirstPassword);
                    SharedPreferencesUtils.put(GestureActivity.this, "isVerify1", true);
                    ToatUtils.showShort1(GestureActivity.this, "设置成功");
                    mGestureContentView.clearDrawlineState(0L);
                    setResult(100);
                    GestureActivity.this.finish();
                } else {
                    text_tip.setText(Html.fromHtml("<font color='#c70c1e'>与上一次绘制不一致，请重新绘制</font>"));
                    // 左右移动动画
                    Animation shakeAnimation = AnimationUtils.loadAnimation(GestureActivity.this, R.anim.shake);
                    text_tip.startAnimation(shakeAnimation);
                    // 保持绘制的线，1.5秒后清除
                    mGestureContentView.clearDrawlineState(1000L);
                }
            }
            mIsFirstInput = false;
        }, null);
        // 设置手势解锁显示到哪个布局里面
        mGestureContentView.setParentView(gesture_container);
        updateCodeList("");
    }

    private boolean isInputPassValidate(String inputPassword) {
        if (TextUtils.isEmpty(inputPassword) || inputPassword.length() < 4) {
            return false;
        }
        return true;
    }

    private void updateCodeList(String inputCode) {
        // 更新选择的图案
        lock_indicator.setPath(inputCode);
    }

    @Override
    public void initView() {
        name = getIntent().getStringExtra("name");
        type = getIntent().getIntExtra("type", 0);
        isVerify = getIntent().getBooleanExtra("isVerify", false);
        passWord = SharedPreferencesUtils.get(this, "mFirstPassword", "").toString();
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
        title = findView(R.id.title);
        text_tip = findView(R.id.text_tip);
        lock_indicator = findView(R.id.lock_indicator);
        gesture_container = findView(R.id.gesture_container);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(102);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.act_gesture;
    }
}
