package com.sky.beautiful.BaseActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.sky.beautiful.Activity.GestuerOneActivity;
import com.sky.beautiful.BaseActivityManager;
import com.sky.beautiful.ImmersionBar.ImmersionBar;
import com.sky.beautiful.R;
import com.sky.beautiful.SykApplication;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.View.Dialog.SweetAlertDialog;

import java.util.List;

/**
 * Created by vvguoliang on 2017/12/18.
 * <p>
 * 基类 BaseActivity
 */

public abstract class BaseActivity extends FragmentActivity {

    protected ImmersionBar mImmersionBar;

    private String mFirst = "";

    private boolean isVerify1 = false;

    //用来控制应用前后台切换的逻辑
    private boolean isCurrentRunningForeground = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        BaseActivityManager.getActivityManager().pushActivity(this);
        if (isImmersionBarEnabled())
            initImmersionBar();
        initView();
        initData();
    }

    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    public abstract void initData();

    public abstract void initView();

    protected abstract int setLayoutId();

    /**
     * 是否禁止旋转屏幕
     **/
    public void setScreenRoate(boolean isAllowScreenRoate) {
        if (isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void TLog(String msg) {
        if (SykApplication.isDebug) {
            Log.d(SykApplication.APP_NAME, msg);
        }
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    /**
     * 点击按钮 疯狂
     *
     * @return
     */
    protected boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * 跳转
     *
     * @param aClass
     */
    public void startActivity(Class<?> aClass) {
        this.startActivity(new Intent(this, aClass));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 携带数据的页面跳转 返回值
     *
     * @param clz
     * @param bundle
     */
    public void startActivityForResult(Class<?> clz, int requestCode, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferencesUtils.put(this, "isVerify1", true);
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //在BaseActivity里销毁
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isCurrentRunningForeground) {
            mFirst = SharedPreferencesUtils.get(this, "mFirstPassword", "").toString();
            boolean Bottom = (boolean) SharedPreferencesUtils.get(this,"Bottom",false);
            isVerify1 = (boolean) SharedPreferencesUtils.get(this, "isVerify1", false);
            if (!TextUtils.isEmpty(mFirst) && isVerify1 && Bottom && !TextUtils.isEmpty(SharedPreferencesUtils.get(this, "userNo", "").toString())) {
                Bundle build = new Bundle();
                build.putString("name", "绘制图案密码");
                build.putInt("type", 2);
                build.putBoolean("isVerify", true);
                startActivity(GestuerOneActivity.class, build);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isCurrentRunningForeground = isRunningForeground();
        if (!isCurrentRunningForeground) {
        }
    }

    public boolean isRunningForeground() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        // 枚举进程
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(this.getApplicationInfo().processName)) {
                    Log.d("", "EntryActivity isRunningForeGround");
                    return true;
                }
            }
        }
        Log.d("", "EntryActivity isRunningBackGround");
        return false;
    }

    SweetAlertDialog mLoadingDialog;

    public void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            mLoadingDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setTitleText("数据加载中...");
        }
        mLoadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}
