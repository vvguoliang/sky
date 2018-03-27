package com.sky.beautiful.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.beautiful.Activity.ContentActivity;
import com.sky.beautiful.Activity.SearchActivity;
import com.sky.beautiful.Adapter.NormalRecyclerViewAdapter;
import com.sky.beautiful.Model.GetHomeBanner;
import com.sky.beautiful.Model.GetPageShow;
import com.sky.beautiful.OKHttp.AggregateMap;
import com.sky.beautiful.OKHttp.HttpImplements;
import com.sky.beautiful.OKHttp.HttpRequest;
import com.sky.beautiful.R;
import com.sky.beautiful.RollingGraph.CustomBanner;
import com.sky.beautiful.Rrefreshlayout.BGAMoocStyleRefreshViewHolder;
import com.sky.beautiful.Rrefreshlayout.BGARefreshLayout;
import com.sky.beautiful.Rrefreshlayout.View.Divider;
import com.sky.beautiful.Rrefreshlayout.adapter.BGAOnItemChildClickListener;
import com.sky.beautiful.SykApplication;
import com.sky.beautiful.Utils.AppUtil;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.Utils.ToatUtils;
import com.sky.beautiful.Utils.UtilsInterface;
import com.sky.beautiful.Utils.UtilsXML;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by vvguoliang on 2017/12/19.
 * <p>
 * 首页
 */

@SuppressWarnings("deprecation")
public class HomeFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnItemChildClickListener, View.OnClickListener {

    protected Context mActivity;

    private RecyclerView rv_recyclerview_data;

    private ImageView home_image;

    private TextView title;

    private ImageView home_relat_image;

    private ImageView title_search_image1;

    private BGARefreshLayout rl_recyclerview_refresh;

    private NormalRecyclerViewAdapter mAdapter;

    private RelativeLayout confirm_delete_imageview;

    private View view, view1;

    private int mNewPageNumber = 1;

    private boolean mMorePageNumber = false;

    private boolean bool_image = false;

    private boolean isVisibleToUser = false;

    private List<GetHomeBanner> getHomeBannerList;

    private ArrayList<GetPageShow> getPageShowList1 = new ArrayList<>();

    private StaggeredGridLayoutManager layoutManager;

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
        view = LayoutInflater.from(mActivity).inflate(R.layout.far_home, container, false);
        findViewById();
        getRecycler();
        getHomeBanner();
        getPageShow();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mActivity == null) {
            mActivity =  SykApplication.getContextObject();
        }
        if (isVisibleToUser) {
            bool_image = false;
            this.isVisibleToUser = isVisibleToUser;
        }
    }

    private void findViewById() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.findViewById(R.id.tab).setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            } else {
                view.findViewById(R.id.tab).setBackgroundColor(Color.rgb(153, 153, 153));
            }
        } else {
            view.findViewById(R.id.tab).setVisibility(View.GONE);
        }
        rv_recyclerview_data = view.findViewById(R.id.rv_recyclerview_data);
        rl_recyclerview_refresh = view.findViewById(R.id.rl_recyclerview_refresh);
        home_image = view.findViewById(R.id.home_image);
        title_search_image1 = view.findViewById(R.id.title_search_image1);
        title = view.findViewById(R.id.title);
        confirm_delete_imageview = view.findViewById(R.id.confirm_delete_imageview);

        title_search_image1.setOnClickListener(this);
        view.findViewById(R.id.title_search_image).setOnClickListener(this);
        view.findViewById(R.id.title_search_image).setVisibility(View.VISIBLE);
        title_search_image1.setVisibility(View.VISIBLE);
        title.setText(getString(R.string.title_home));


        rl_recyclerview_refresh.setDelegate(this);
        mAdapter = new NormalRecyclerViewAdapter(mActivity);
        mAdapter.setOnItemClickListener((position, data) -> {
            Intent intent = new Intent(mActivity, ContentActivity.class);
            intent.putExtra("ViewAtlas", getPageShowList1.get(position).getAtlasCode());
            intent.putExtra("AtlasName", getPageShowList1.get(position).getAtlasName());
            startActivity(intent);
        });
    }

    private void setdata(ArrayList<String> images) {
        CustomBanner mBanner = view.findViewById(R.id.banner);
        //设置普通指示器
        UtilsInterface.getInstance().setBanner(mActivity, mBanner, images);
        mBanner.setOnPageClickListener((position, o) -> {
            Intent intent = new Intent(mActivity, ContentActivity.class);
            intent.putExtra("ViewAtlas", getHomeBannerList.get(position).getAtlasNo());
            startActivity(intent);
        });
    }

    private void getRecycler() {
        BGAMoocStyleRefreshViewHolder moocStyleRefreshViewHolder = new BGAMoocStyleRefreshViewHolder(mActivity, true);
        moocStyleRefreshViewHolder.setOriginalImage(R.mipmap.ic_icon);
        moocStyleRefreshViewHolder.setUltimateColor(R.color.commont_colorff3a94);
        rl_recyclerview_refresh.setRefreshViewHolder(moocStyleRefreshViewHolder);
        rv_recyclerview_data.addItemDecoration(new Divider(10, 0));

        geBoot();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (getPageShowList1 != null && getPageShowList1.size() > 0) {
            getPageShowList1.clear();
        }
        mNewPageNumber = 1;
        mMorePageNumber = true;
        getHomeBanner();
        getPageShow();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mNewPageNumber++;
        mMorePageNumber = false;
        getPageShow();
        return true;
    }

    @SuppressLint("ShowToast")
    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        Intent intent = new Intent(mActivity, ContentActivity.class);
        intent.putExtra("ViewAtlas", getPageShowList1.get(position).getAtlasCode());
        startActivity(intent);
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView1, View childView2, int position) {

    }

    @SuppressLint({"ResourceType", "NewApi"})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search_image1:
                if (bool_image) {
                    home_image.setImageResource(Color.rgb(0, 0, 0));
                    title_search_image1.setImageResource(R.mipmap.ic_list2);
                    home_relat_image.setImageResource(R.mipmap.ic_recommend_red);
                    bool_image = false;
                } else {
                    if (getHomeBannerList != null && getHomeBannerList.size() > 0) {
                        UtilsInterface.getInstance().UtilBlur1(mActivity, "", getHomeBannerList.get(0).getUrl(), home_image);
                    }
                    home_relat_image.setImageResource(R.mipmap.ic_recommend_while);
                    title_search_image1.setImageResource(R.mipmap.ic_list1);
                    bool_image = true;
                }
                mAdapter.getNormalAdapter(bool_image);
                geBoot();
                break;
            case R.id.title_search_image:
                Intent intent = new Intent(mActivity, SearchActivity.class);
                intent.putExtra("type", 1);
                mActivity.startActivity(intent);
                break;
        }
    }

    private void geBoot() {
        if (bool_image) {
            mAdapter.getPosi(0, AppUtil.getInstance().Dispay(getActivity()));
            layoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
            rv_recyclerview_data.setLayoutManager(layoutManager);
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
            mAdapter.getPosi(1, AppUtil.getInstance().Dispay(getActivity()));
            rv_recyclerview_data.setLayoutManager(linearLayoutManager);
        }
        rv_recyclerview_data.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    private void getHomeBanner() {
        HttpRequest.getInstance().setPublic(mActivity, null, mHandler, HttpImplements.getInstance().getHttp(mActivity, "getHomeBanner"), "getHomeBanner");
    }

    private void getPageShow() {
        HttpRequest.getInstance().setPublic(mActivity, AggregateMap.getInstance().setGetPageShow(SharedPreferencesUtils.get(mActivity, "userNo", "0").toString(),
                "", 1, mNewPageNumber), mHandler, HttpImplements.getInstance().getHttp(mActivity, "getPageShow"), "getPageShow");
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("ResourceType")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 205:
                    getVisib(1);
                    break;
                case 206:
                    if (mNewPageNumber == 1) {
                        if (mMorePageNumber) {
                            mHandler.postDelayed(() -> {
                                mAdapter.clear();
                                rl_recyclerview_refresh.endRefreshing();
                                mAdapter.addDatas(getPageShowList1);
                            }, 3000);
                        }
                    }
                    getVisib(0);
                    break;
                case 1000:
                    getHomeBannerList = (List<GetHomeBanner>) msg.obj;
                    Collections.sort(getHomeBannerList);
                    ArrayList<String> images = new ArrayList<>();
                    for (int i = 0; getHomeBannerList.size() > i; i++) {
                        images.add(getHomeBannerList.get(i).getUrl());
                    }
                    view1 = UtilsXML.getInstance().CustomHeaderBrannerView(mActivity, getHomeBannerList);
                    home_relat_image = view1.findViewById(R.id.home_relat_image);
                    mAdapter.setHeaderView(view1);
                    break;
                case 1001:
                    List<GetPageShow> getPageShowList = (List<GetPageShow>) msg.obj;
                    if (getPageShowList != null && getPageShowList.size() > 0) {
                        getPageShowList1.addAll(getPageShowList);
                        if (mNewPageNumber == 1) {
                            if (mMorePageNumber) {
                                mHandler.postDelayed(() -> {
                                    mAdapter.clear();
                                    rl_recyclerview_refresh.endRefreshing();
                                    mAdapter.addDatas(getPageShowList1);
                                }, 3000);
                            } else {
                                mNewPageNumber = 1;
                                mAdapter.clear();
                                mAdapter.addDatas(getPageShowList1);
                            }
                        } else {
                            rl_recyclerview_refresh.endLoadingMore();
                            mAdapter.addMoreData(getPageShowList1);
                        }
                    } else {
                        rl_recyclerview_refresh.endLoadingMore();
                        mAdapter.addDatas(getPageShowList1);
                        ToatUtils.showShort1(mActivity, "暂无数据");
                    }
                    if (bool_image) {
                        if (getHomeBannerList != null && getHomeBannerList.size() > 0) {
                            UtilsInterface.getInstance().UtilBlur(mActivity, "", getHomeBannerList.get(0).getUrl(), home_image);
                        }
                    } else {
                        home_image.setImageResource(Color.rgb(0, 0, 0));
                    }
                    getVisib(1);
                    break;
            }
        }
    };

    private void getVisib(int Visib) {
        if (Visib == 0) {
            confirm_delete_imageview.setVisibility(View.VISIBLE);
            title_search_image1.setVisibility(View.GONE);
        } else {
            confirm_delete_imageview.animate()
                    .alpha(0f)
                    .setDuration(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            confirm_delete_imageview.setVisibility(View.GONE);
                        }
                    });
            title_search_image1.setVisibility(View.VISIBLE);
        }
    }
}
