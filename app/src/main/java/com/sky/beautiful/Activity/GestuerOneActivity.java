package com.sky.beautiful.Activity;

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

import com.sky.beautiful.BaseActivity.BaseActivity;
import com.sky.beautiful.ImmersionBar.ImmersionBar;
import com.sky.beautiful.R;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.Utils.UtilsInterface;
import com.sky.beautiful.View.CircleImageView;
import com.sky.beautiful.View.Gesture.GestureContentView;
import com.sky.beautiful.View.Gesture.GestureDrawline;
import com.sky.beautiful.View.Gesture.LockIndicator;

/**
 * @Time : 2018/1/2 no 下午8:54
 * @USER : vvguoliang
 * @File : GestuerOneActivity.java
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

public class GestuerOneActivity extends BaseActivity implements View.OnClickListener {

    private TextView title, text_tip2, text_tip1;

    private String name;

    private GestureContentView mGestureContentView;

    private String mFirstPassword;

    private FrameLayout gesture_container;

    private boolean isVerify = false;

    private String passWord, HeadUrl;

    private CircleImageView my_circle_image;

    private int type = 0;

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
        if (!TextUtils.isEmpty(HeadUrl)) {
            UtilsInterface.getInstance().UtilGlideAngle(this, HeadUrl, my_circle_image);
        }
        if (TextUtils.isEmpty(SharedPreferencesUtils.get(this, "name", "").toString())) {
            text_tip1.setText(SharedPreferencesUtils.get(this, "phone", "").toString());
        } else {
            text_tip1.setText(SharedPreferencesUtils.get(this, "name", "").toString());
        }
        findView(R.id.title_search_image2).setOnClickListener(this);
        if (type == 0) {
            findView(R.id.title_search_image2).setVisibility(View.VISIBLE);
        } else {
            findView(R.id.title_search_image2).setVisibility(View.GONE);
        }
        getGesture2();
    }

    private void getGesture2() {
        mGestureContentView = new GestureContentView(this, isVerify, passWord, inputCode -> mFirstPassword = inputCode, new GestureDrawline.GestureSuccessCallBack() {
            @Override
            public void checkedSuccess() {
                if (type == 0) {
                    SharedPreferencesUtils.put(GestuerOneActivity.this, "mFirstPassword", "");
                } else {
                    setResult(101);
                }
                SharedPreferencesUtils.put(GestuerOneActivity.this, "isVerify1", false);
                GestuerOneActivity.this.finish();
            }

            @Override
            public void checkedFail() {
                text_tip2.setText(Html.fromHtml("<font color='#c70c1e'>与上一次绘制不一致，请重新绘制</font>"));
                // 左右移动动画
                mGestureContentView.clearDrawlineState(1000L);
                // 保持绘制的线，1.5秒后清除
                Animation shakeAnimation = AnimationUtils.loadAnimation(GestuerOneActivity.this, R.anim.shake);
                text_tip2.startAnimation(shakeAnimation);
            }
        });
        // 设置手势解锁显示到哪个布局里面
        mGestureContentView.setParentView(gesture_container);
    }


    @Override
    public void initView() {
        name = getIntent().getStringExtra("name");
        HeadUrl = SharedPreferencesUtils.get(this, "HeadUrl", "").toString();
        isVerify = getIntent().getBooleanExtra("isVerify", false);
        type = getIntent().getIntExtra("type", 0);
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
        text_tip1 = findView(R.id.text_tip1);
        gesture_container = findView(R.id.gesture_container);
        text_tip2 = findView(R.id.text_tip2);
        my_circle_image = findView(R.id.my_circle_image);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.act_gestuer_one;
    }
}
