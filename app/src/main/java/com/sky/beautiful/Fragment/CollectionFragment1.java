package com.sky.beautiful.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sky.beautiful.Model.MyCollect;
import com.sky.beautiful.OKHttp.AggregateMap;
import com.sky.beautiful.OKHttp.HttpImplements;
import com.sky.beautiful.OKHttp.HttpRequest;
import com.sky.beautiful.R;
import com.sky.beautiful.SykApplication;
import com.sky.beautiful.Utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2018/1/24 no 下午4:31
 * @USER : vvguoliang
 * @File : CollectionFragment1.java
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
@SuppressLint("ResourceAsColor")
public class CollectionFragment1 extends Fragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    protected Context mActivity;

    private View view;

    private RadioGroup radioGroup;

    private RadioButton radioButton1, radioButton2;

    private TextView title_search_text;

    private ViewPager viewPager;

    private FragmentStatePagerAdapter mFrPagerAdapter;

    private List<Fragment> mFragments = new ArrayList<>();

    private boolean isVisibleToUser = false;

    private int mNewPageNumber = 1;

    private boolean isRadio = false;

    private boolean isVisib1 = false;

    private boolean isVisib2 = false;

    private boolean title_edit = false;

    private CollectionFragment_1 collectionFragment_1;

    private CollectionFragment_2 collectionFragment_2;
    private String userNo = "";

    /**
     * 获得全局的，防止使用getActivity()为空
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(mActivity).inflate(R.layout.far_collectionfragment, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.findViewById(R.id.tab).setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            } else {
                view.findViewById(R.id.tab).setBackgroundColor(Color.rgb(231, 231, 231));
            }
        } else {
            view.findViewById(R.id.tab).setVisibility(View.GONE);
        }
        findViewById();
        getData();
        return view;
    }

    private void findViewById() {
        userNo = SharedPreferencesUtils.get(mActivity, "userNo", "").toString();
        radioGroup = view.findViewById(R.id.radioGroup);
        radioButton1 = view.findViewById(R.id.recharge_button1);
        radioButton2 = view.findViewById(R.id.recharge_button2);
        viewPager = view.findViewById(R.id.viewPager);
        title_search_text = view.findViewById(R.id.title_search_text);

        TextView title = view.findViewById(R.id.title);
        title.setText(getString(R.string.title_collection));
        title_search_text.setVisibility(View.VISIBLE);
        title_search_text.setText(getString(R.string.title_edit));

        title_search_text.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.recharge_button1:
                isRadio = false;
                title_edit = false;
                if (isVisib1) {
                    title_search_text.setVisibility(View.VISIBLE);
                    getScr(1);
                } else {
                    title_search_text.setVisibility(View.GONE);
                    getScr(0);
                }
                radioButton1.setBackgroundColor(Color.rgb(240, 240, 240));
                radioButton2.setBackgroundColor(Color.WHITE);
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.recharge_button2:
                isRadio = true;
                title_edit = false;
                if (isVisib2) {
                    title_search_text.setVisibility(View.VISIBLE);
                    getScr(1);
                } else {
                    getScr(0);
                    title_search_text.setVisibility(View.GONE);
                }
                radioButton2.setBackgroundColor(Color.rgb(240, 240, 240));
                radioButton1.setBackgroundColor(Color.WHITE);
                viewPager.setCurrentItem(1, false);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void getData() {
        radioButton1.setBackgroundColor(Color.rgb(240, 240, 240));
        radioButton2.setBackgroundColor(Color.WHITE);
        collectionFragment_1 = new CollectionFragment_1();
        mFragments.add(collectionFragment_1);
        collectionFragment_2 = new CollectionFragment_2();
        mFragments.add(collectionFragment_2);
        mFrPagerAdapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {

            private FragmentManager fragmentManager;

            @Override
            public int getCount() {
                fragmentManager = getFragmentManager();
                return mFragments.size();
            }

            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Fragment fragment = (Fragment) super.instantiateItem(container, position);
                this.fragmentManager.beginTransaction().show(fragment).commit();
                return fragment;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                Fragment fragment = mFragments.get(position);
                fragmentManager.beginTransaction().hide(fragment).commit();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };
        viewPager.setOffscreenPageLimit(mFragments.size());
        //ViewPager设置适配器
        viewPager.setAdapter(mFrPagerAdapter);
        //ViewPager显示第一个Fragment
        viewPager.setCurrentItem(0);
        //ViewPager页面切换监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        radioGroup.check(R.id.recharge_button1);
                        break;
                    case 1:
                        radioGroup.check(R.id.recharge_button2);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        collectionFragment_1.setOnClickListener(new CollectionFragment_1.OnClickLiner() {
            @Override
            public void onClick(boolean onclick, int collectType) {
                if (!isRadio) {
                    if (onclick) {
                        radioButton1.setText("美图(" + collectType + ")");
                        getScr(collectType);
                    }
                }
            }

            @Override
            public void setCollectType(int collectType) {
                if (collectType == 0) {
                    isVisib1 = false;
                } else {
                    isVisib1 = true;
                }
                getScr(collectType);
                radioButton1.setText("美图(" + collectType + ")");
            }
        });

        collectionFragment_2.setOnClickListener(new CollectionFragment_2.OnClickLiner() {
            @Override
            public void onClick(boolean onclick, int collectType) {
                if (isRadio) {
                    if (onclick) {
                        radioButton2.setText("专辑(" + collectType + ")");
                        getScr(collectType);
                    }
                }
            }

            @Override
            public void setCollectType(int collectType) {
                if (collectType == 0) {
                    isVisib2 = false;
                } else {
                    isVisib2 = true;
                }
//                getScr(collectType);
                radioButton2.setText("专辑(" + collectType + ")");
            }
        });
    }

    private void getScr(int collectType) {
        if (collectType != 0) {
            getScrollCilck();
        } else {
            getScrollCilck1();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mActivity == null) {
            mActivity = SykApplication.getContextObject();
        }
        if (isVisibleToUser) {
            this.isVisibleToUser = isVisibleToUser;
//            getMyCollect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.isVisibleToUser) {
            radioButton1.setText("美图(0)");
            radioButton2.setText("专辑(0)");
            viewPager.setCurrentItem(0);
            mFrPagerAdapter.notifyDataSetChanged();
//            getMyCollect();
        }
    }

    private void getMyCollect() {
        HttpRequest.getInstance().setPublic(mActivity,
                AggregateMap.getInstance().setMyCollect(SharedPreferencesUtils.get(mActivity, "userNo", "0").toString(), mNewPageNumber, 0),
                mHandler, HttpImplements.getInstance().getHttp(mActivity, "myCollect"), "myCollect");
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 205:
                    radioButton1.setText("美图(0)");
                    radioButton2.setText("专辑(0)");
                    title_search_text.setVisibility(View.GONE);
                    break;
                case 206:
                    radioButton1.setText("美图(0)");
                    radioButton2.setText("专辑(0)");
                    title_search_text.setVisibility(View.GONE);
                    break;
                case 1009:
                    List<MyCollect> myCollects = (List<MyCollect>) msg.obj;
                    int collectType1 = 0;
                    int collectType2 = 0;
                    for (int i = 0; myCollects.size() > i; i++) {
                        if (myCollects.get(i).getCollectType() == 1) {
                            collectType1++;
                        } else if (myCollects.get(i).getCollectType() == 2) {
                            collectType2++;
                        }
                    }
                    radioButton1.setText("美图(" + collectType1 + ")");
                    radioButton2.setText("专辑(" + collectType2 + ")");

                    if (collectType2 == 0) {
                        isVisib2 = false;
                    } else {
                        isVisib2 = true;
                    }
                    title_search_text.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search_text:
                title_edit = !title_edit;
                if (title_edit) {
                    title_search_text.setText(getString(R.string.title_cancel));
                    if (collectionFragment_1 != null && collectionFragment_2 != null) {
                        if (isRadio) {  // 专辑
                            collectionFragment_1.getVisibility(false);
                            collectionFragment_2.getVisibility(true);
                        } else {   //单图
                            collectionFragment_1.getVisibility(true);
                            collectionFragment_2.getVisibility(false);
                        }
                    }
                } else {
                    getScrollCilck();
                }
                break;
        }
    }

    private void getScrollCilck() {
        title_search_text.setText(getString(R.string.title_edit));
        title_search_text.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(userNo)) {
            if (collectionFragment_1 != null && collectionFragment_2 != null) {
                collectionFragment_1.getVisibility(false);
                collectionFragment_2.getVisibility(false);
            }
        }
    }

    private void getScrollCilck1() {
        title_search_text.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(userNo)) {
            if (collectionFragment_1 != null && collectionFragment_2 != null) {
                collectionFragment_1.getVisibility(false);
                collectionFragment_2.getVisibility(false);
            }
        }
    }
}
