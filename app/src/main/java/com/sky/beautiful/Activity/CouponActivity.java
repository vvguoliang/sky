package com.sky.beautiful.Activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.beautiful.BaseActivity.BaseActivity;
import com.sky.beautiful.BaseFragment.CouponFragment;
import com.sky.beautiful.BaseFragment.DynamicFragment;
import com.sky.beautiful.ImmersionBar.ImmersionBar;
import com.sky.beautiful.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2017/12/27 no 下午4:04
 * @USER : vvguoliang
 * @File : CouponActivity.java
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

@SuppressWarnings("deprecation")
@SuppressLint("SetTextI18n")
public class CouponActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private TextView title;

    private TabLayout collection_tablayout;

    private ViewPager collection_viewpage;

    private List<String> listTitles;

    private List<Fragment> fragments;

    private List<TextView> listTextViews;

    private CouponFragment couponFragment;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search_image2:
                finish();
                break;
        }

    }

    @Override
    public void initData() {
        findView(R.id.title_search_image2).setVisibility(View.VISIBLE);
        findView(R.id.title_search_image2).setOnClickListener(this);
        title.setText("我的" + getString(R.string.coupon));

        listTitles = new ArrayList<>();
        fragments = new ArrayList<>();
        listTextViews = new ArrayList<>();

        listTitles.add("未使用(0)");
        listTitles.add("已使用(0)");
        listTitles.add("已过期(0)");
        for (int i = 0; i < listTitles.size(); i++) {
            couponFragment = CouponFragment.newInstance(listTitles.get(i), i);
            fragments.add(couponFragment);
            collection_tablayout.addTab(collection_tablayout.newTab().setText(listTitles.get(i)));//添加tab选项
        }
        collection_tablayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式

        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(this.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
            @Override
            public CharSequence getPageTitle(int position) {
                return listTitles.get(position);
            }
        };
        collection_viewpage.setAdapter(mAdapter);
        collection_viewpage.setOnPageChangeListener(this);
        collection_tablayout.setupWithViewPager(collection_viewpage);//将TabLayout和ViewPager关联起来。
        collection_tablayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器

        collection_viewpage.setCurrentItem(0);
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
        title = findView(R.id.title);

        collection_tablayout = findView(R.id.collection_tablayout);
        collection_viewpage = findView(R.id.collection_viewpage);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.act_coupon;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        CouponFragment.newInstance(listTitles.get(position), position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
