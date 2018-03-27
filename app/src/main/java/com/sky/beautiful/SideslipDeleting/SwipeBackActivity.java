package com.sky.beautiful.SideslipDeleting;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.sky.beautiful.BaseActivityManager;
import com.sky.beautiful.ImmersionBar.ImmersionBar;

/**
 * Created by vvguoliang on 2017/12/19.
 *
 * 基类 侧滑删除这个页面
 */

public class SwipeBackActivity extends FragmentActivity implements SwipeBackActivityBase{

    private SwipeBackActivityHelper mHelper;

    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        BaseActivityManager.getActivityManager().pushActivity(this);
        if (isImmersionBarEnabled())
            initImmersionBar();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
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
}
