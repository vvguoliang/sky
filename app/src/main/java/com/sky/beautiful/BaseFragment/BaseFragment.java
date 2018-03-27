package com.sky.beautiful.BaseFragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky.beautiful.SykApplication;

/**
 * Created by vvguoliang on 2017/12/18.
 * <p>
 * 基类 Fragment
 */

public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;

    /**
     * 获得全局的，防止使用getActivity()为空
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState) {
        if (mActivity == null) {
            mActivity = (Activity) SykApplication.getContextObject();
        }
        View view = LayoutInflater.from(mActivity)
                .inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);

        return view;
    }

    /**
     * 该抽象方法就是 onCreateView中需要的layoutID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 该抽象方法就是 初始化view
     *
     * @param view
     * @param savedInstanceState
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 执行数据的加载
     */
    protected abstract void initData();


    public void TLog(String msg) {
        if (SykApplication.isDebug) {
            Log.d(SykApplication.APP_NAME, msg);
        }
    }

    /**
     * 点击按钮 疯狂
     *
     * @return
     */
    private boolean fastClick() {
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
        this.startActivity(aClass);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
