package com.sky.beautiful.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sky.beautiful.BaseActivity.BaseActivity;
import com.sky.beautiful.ImmersionBar.BarHide;
import com.sky.beautiful.MainActivity;
import com.sky.beautiful.OKHttp.HttpImplements;
import com.sky.beautiful.OKHttp.HttpRequest;
import com.sky.beautiful.R;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.Utils.TakePictureManager;
import com.sky.beautiful.Utils.UtilsInterface;

import java.io.File;
import java.util.List;

/**
 * @Time : 2017/12/19 no 下午5:14
 * @USER : vvguoliang
 * @File : StartUpActivity.java
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

public class StartUpActivity extends BaseActivity {

    private ImageView start_up_image;
    private ImageView start_up_image1;
    private ImageView start_up_image2;

    private int time_int = 1000;

    private int mShortAnimationDuration;

    private String mFirst = "";

    private boolean isVerify1 = false;

    private int isVerify2 = 0;

    private TakePictureManager takePictureManager;

    @Override
    public void initData() {
//        isVerify1 = (boolean) SharedPreferencesUtils.get(this, "isVerify1", false);
//        if (!TextUtils.isEmpty(mFirst) && isVerify1) {
//            Bundle build = new Bundle();
//            build.putString("name", "绘制图案密码");
//            build.putInt("type", 2);
//            build.putBoolean("isVerify", true);
//            startActivity(GestuerOneActivity.class, build);
//        } else {
//            mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
//            start_up_image.setVisibility(View.GONE);
//            start_up_image1.setVisibility(View.GONE);
//            start_up_image2.setVisibility(View.VISIBLE);
//            start_up_image2.animate()
//                    .alpha(0f)
//                    .setDuration(mShortAnimationDuration * 16)
//                    .setListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
////                        start_up_image.setVisibility(View.VISIBLE);
////                        start_up_image1.setVisibility(View.VISIBLE);
////                        start_up_image2.setVisibility(View.GONE);
////                        getStart_UP();
//                            startActivity(new Intent(StartUpActivity.this, MainActivity.class));
//                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                            StartUpActivity.this.finish();
//                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                        }
//                    });
//        }
    }

    private void getdata() {
        isVerify1 = (boolean) SharedPreferencesUtils.get(this, "isVerify1", false);
        if (!TextUtils.isEmpty(mFirst) && isVerify1 && !TextUtils.isEmpty(SharedPreferencesUtils.get(this, "userNo", "").toString())) {
            isVerify2++;
            Bundle build = new Bundle();
            build.putString("name", "绘制图案密码");
            build.putInt("type", 2);
            build.putBoolean("isVerify", true);
            startActivityForResult(GestuerOneActivity.class, 100, build);
        } else {
            mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
            start_up_image.setVisibility(View.GONE);
            start_up_image1.setVisibility(View.GONE);
            start_up_image2.setVisibility(View.VISIBLE);
            start_up_image2.animate()
                    .alpha(0f)
                    .setDuration(mShortAnimationDuration * 16)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
//                        start_up_image.setVisibility(View.VISIBLE);
//                        start_up_image1.setVisibility(View.VISIBLE);
//                        start_up_image2.setVisibility(View.GONE);
//                        getStart_UP();
                            startActivity(new Intent(StartUpActivity.this, MainActivity.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            StartUpActivity.this.finish();
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    });
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        isVerify1 = (boolean) SharedPreferencesUtils.get(this, "isVerify1", false);
//        if (!TextUtils.isEmpty(mFirst) && isVerify1) {
//            Bundle build = new Bundle();
//            build.putString("name", "绘制图案密码");
//            build.putInt("type", 2);
//            build.putBoolean("isVerify", true);
//            startActivity(GestuerOneActivity.class, build);
//        } else {
//            initView();
//            initData();
//            new Handler().postDelayed(() -> {
//                this.startActivity(MainActivity.class);
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                StartUpActivity.this.finish();
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//            }, time_int * 8);
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();
        if ((boolean) SharedPreferencesUtils.get(this, "startup_type", false)) {
            getdata();
        }
    }

    @Override
    public void initView() {
        SharedPreferencesUtils.put(this, "startup_type", false);
        SharedPreferencesUtils.put(this, "updata", false);
        mImmersionBar.hideBar(BarHide.FLAG_HIDE_BAR).init();
        mFirst = SharedPreferencesUtils.get(this, "mFirstPassword", "").toString();
        start_up_image = findView(R.id.start_up_image);
        start_up_image1 = findView(R.id.start_up_image1);
        start_up_image2 = findView(R.id.start_up_image2);

        takePictureManager = new TakePictureManager(this);
//        takePictureManager.setTailor(1, 1, 350, 350);
        if (!takePictureManager.startTakeWayByCarema1()) {
            getHomeBanner();
        }
        takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
            @Override
            public void successful(boolean isTailor, File outFile, Uri filePath) {
                getHomeBanner();
            }

            @Override
            public void failed(int errorCode, List<String> deniedPermissions) {
                getHomeBanner();
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.act_start_up;
    }

    private void getAnimationSet() {
        Animation mAnimation = AnimationUtils.loadAnimation(this, R.anim.balloonscale);
        mAnimation.setDuration(time_int * 2);
        start_up_image.setAnimation(mAnimation);
    }

    private void getStart_UP() {
        start_up_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(this)
                .load("")
                .transition(new DrawableTransitionOptions().crossFade(time_int * 2))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        start_up_image.setImageResource(R.mipmap.custom_mooc_icon);
                        getAnimationSet();
                        start_up_image1.setAnimation(UtilsInterface.getInstance().setMoveToViewLocation(time_int));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        start_up_image.setImageDrawable(resource);
                        getAnimationSet();
                        start_up_image1.setAnimation(UtilsInterface.getInstance().setMoveToViewLocation(time_int));
                        return false;
                    }
                }).into(start_up_image);
    }

    private void getHomeBanner() {
        SharedPreferencesUtils.put(this, "version_type", "0");
        HttpRequest.getInstance().setPublic(this, null, mHandler, HttpImplements.getInstance().getHttp(this, "updata"), "updata");
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1015:
                    if (isVerify2 == 0) {
                        getdata();
                    }
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101) {
            getdata();
        }
        takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        takePictureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
