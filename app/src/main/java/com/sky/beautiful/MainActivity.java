package com.sky.beautiful;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.sky.beautiful.Adapter.MainViewPagerAdapter;
import com.sky.beautiful.BaseActivity.BaseActivity;
import com.sky.beautiful.Fragment.CollectionFragment;
import com.sky.beautiful.Fragment.CollectionFragment1;
import com.sky.beautiful.Fragment.HomeFragment;
import com.sky.beautiful.Fragment.MyFragment;
import com.sky.beautiful.Fragment.PrivatePhotosFragment;
import com.sky.beautiful.ImmersionBar.ImmersionBar;
import com.sky.beautiful.Utils.ToatUtils;
import com.sky.beautiful.Utils.UtilsInterface;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("deprecation")
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnTouchListener {

    private BottomNavigationView navigation;
    private ViewPager viewPager;
    private MenuItem menuItem;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_view:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initData() {
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            mImmersionBar.statusBarDarkFont(true).init();
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        UtilsInterface.getInstance().setDisableShiftMode(navigation);
        setScreenRoate(false);  // 屏幕是否旋转
        viewPager.setOnTouchListener(this);
        viewPager.setOnPageChangeListener(this);
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colors = new int[]{getResources().getColor(R.color.commont_color666666),
                getResources().getColor(R.color.colorAccent)
        };
        ColorStateList csl = new ColorStateList(states, colors);
        navigation.setItemTextColor(csl);
        navigation.setItemIconTintList(csl);
    }

    @Override
    public void initView() {
        navigation = findView(R.id.navigation);
        viewPager = findView(R.id.viewpager);
        setupViewPager(viewPager);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            navigation.getMenu().getItem(0).setChecked(false);
        }
        menuItem = navigation.getMenu().getItem(position);
        menuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    private void setupViewPager(ViewPager viewPager) {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        List<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new PrivatePhotosFragment());
        mFragmentList.add(new CollectionFragment1());
        mFragmentList.add(new MyFragment());
        adapter.addFragment(mFragmentList);
        viewPager.setAdapter(adapter);
    }

    /**
     * 连续按两次返回键就退出
     */
    private int keyBackClickCount = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (keyBackClickCount++) {
                case 0:
                    ToatUtils.showShort1(this, "再按一次退出" + getString(R.string.app_name));
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            keyBackClickCount = 0;
                        }
                    }, 3000);
                    break;
                case 1:
                    BaseActivityManager.getActivityManager().finishAll();
                    finish();
                    break;
                default:
                    break;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
