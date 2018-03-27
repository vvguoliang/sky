package com.sky.beautiful.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.beautiful.Activity.SearchActivity;
import com.sky.beautiful.BaseFragment.DynamicFragment;
import com.sky.beautiful.Model.GetLabel;
import com.sky.beautiful.OKHttp.AggregateMap;
import com.sky.beautiful.OKHttp.HttpImplements;
import com.sky.beautiful.OKHttp.HttpRequest;
import com.sky.beautiful.R;
import com.sky.beautiful.SykApplication;
import com.sky.beautiful.Utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvguoliang on 2017/12/19.
 * <p>
 * 私照
 */

@SuppressWarnings("deprecation")
public class PrivatePhotosFragment extends Fragment implements TabLayout.OnTabSelectedListener, View.OnClickListener {

    protected Activity mActivity;

    private View view;

    private TabLayout collection_tablayout;

    private ViewPager collection_viewpage;

    private ImageView title_search_image;

    private TextView title;

    private RelativeLayout confirm_delete_imageview;

    private List<GetLabel> getLabelList;

    private List<String> listTitles;

    private List<String> list;

    private List<Fragment> fragments;

    private DynamicFragment fragment;

    private FragmentPagerAdapter mAdapter;

    private int curTab = 0;

    /**
     * 获得全局的，防止使用getActivity()为空
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
        if (mActivity == null) {
            mActivity = (Activity) SykApplication.getContextObject();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(mActivity).inflate(R.layout.far_private_photos, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.findViewById(R.id.tab).setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            } else {
                view.findViewById(R.id.tab).setBackgroundColor(Color.rgb(231, 231, 231));
            }
        } else {
            view.findViewById(R.id.tab).setVisibility(View.GONE);
        }
        collection_tablayout = view.findViewById(R.id.collection_tablayout);
        collection_viewpage = view.findViewById(R.id.collection_viewpage);
        title_search_image = view.findViewById(R.id.title_search_image);
        confirm_delete_imageview = view.findViewById(R.id.confirm_delete_imageview);
        title = view.findViewById(R.id.title);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setText(getString(R.string.title_private_photos));
        title_search_image.setVisibility(View.VISIBLE);
        title_search_image.setOnClickListener(this);

        final TabLayout.TabLayoutOnPageChangeListener listener = new TabLayout.TabLayoutOnPageChangeListener(collection_tablayout);
        collection_viewpage.addOnPageChangeListener(listener);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLabel();
    }

    private void initData() {
        fragments = new ArrayList<>();
        list = new ArrayList<>();
        for (int i = 0; i < getLabelList.size(); i++) {
            list.add(getLabelList.get(i).getTagCode());
        }

        for (int i = 0; i < listTitles.size(); i++) {
            fragment = new DynamicFragment(curTab, list);
            fragment.setTabPos(i);
            fragments.add(fragment);
            if (collection_tablayout.getTabCount() <= 0) {
                collection_tablayout.addTab(collection_tablayout.newTab().setText(listTitles.get(i)));//添加tab选项
            } else {
                TabLayout.Tab tab = collection_tablayout.getTabAt(i);
                if (tab != null && !tab.getText().toString().equals(listTitles.get(i))) {
                    collection_tablayout.addTab(collection_tablayout.newTab().setText(listTitles.get(i)));//添加tab选项
                }
            }
        }
        //1.MODE_SCROLLABLE模式
        collection_tablayout.setTabMode(TabLayout.MODE_FIXED);
        //mTabLayout.setTabMode(TabLayout.SCROLL_AXIS_HORIZONTAL);//设置tab模式，当前为系统默认模式
        collection_tablayout.setOnTabSelectedListener(this);
        collection_tablayout.setSmoothScrollingEnabled(true);
        collection_viewpage.setCurrentItem(0);

        mAdapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

            @Override
            public long getItemId(int position) {
                // 获取当前数据的hashCode
                return fragments.get(position).hashCode();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                return super.instantiateItem(container, position);
            }

            //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
            @Override
            public CharSequence getPageTitle(int position) {
                return listTitles.get(position);
            }
        };
        if (collection_viewpage.getAdapter() == null) {
            collection_viewpage.setAdapter(mAdapter);
            collection_tablayout.setupWithViewPager(collection_viewpage);//将TabLayout和ViewPager关联起来。
            collection_tablayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search_image:
                Intent intent = new Intent(mActivity, SearchActivity.class);
                intent.putExtra("type", 2);
                mActivity.startActivity(intent);
                break;
        }
    }

    private void getLabel() {
        HttpRequest.getInstance().setPublic(mActivity, AggregateMap.getInstance().setGetLabel(SharedPreferencesUtils.get(mActivity, "userNo", "0").toString(),
                ""), mHandler, HttpImplements.getInstance().getHttp(mActivity, "getLabel"), "getLabel");
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 205:
                    confirm_delete_imageview.setVisibility(View.VISIBLE);
                    break;
                case 206:
                    confirm_delete_imageview.setVisibility(View.VISIBLE);
                    break;
                case 1002:
                    confirm_delete_imageview.setVisibility(View.GONE);
                    if (getLabelList != null && getLabelList.size() > 0) {
                        getLabelList.clear();
                    }
                    getLabelList = (List<GetLabel>) msg.obj;
                    listTitles = new ArrayList<>();
                    for (int i = 0; getLabelList.size() > i; i++) {
                        listTitles.add(getLabelList.get(i).getTagName());
                    }
                    initData();
                    break;
            }
        }
    };

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //滑动监听加载数据，一次只加载一个标签页
        ((DynamicFragment) mAdapter.getItem(tab.getPosition())).sendMessage();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
