package com.sky.beautiful.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvguoliang on 2017/12/19.
 * <p>
 * 主的左右滑动 适配器
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();

    private FragmentManager fragmentManager;

    public MainViewPagerAdapter(FragmentManager manager) {
        super(manager);
        fragmentManager = manager;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public long getItemId(int position) {
        // 获取当前数据的hashCode
        return mFragmentList.get(position).hashCode();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        this.fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = mFragmentList.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(List<Fragment> mFragmentList) {
        this.mFragmentList = mFragmentList;
    }
}
