package com.sky.beautiful.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sky.beautiful.Adapter.SearchRecyclerViewAdapter;
import com.sky.beautiful.Adapter.SearchStringRecyclerViewAdapter;
import com.sky.beautiful.BaseActivity.BaseActivity;
import com.sky.beautiful.ImmersionBar.ImmersionBar;
import com.sky.beautiful.Model.GetLabel;
import com.sky.beautiful.Model.GetPageShow;
import com.sky.beautiful.OKHttp.AggregateMap;
import com.sky.beautiful.OKHttp.HttpImplements;
import com.sky.beautiful.OKHttp.HttpRequest;
import com.sky.beautiful.R;
import com.sky.beautiful.Rrefreshlayout.BGAMoocStyleRefreshViewHolder;
import com.sky.beautiful.Rrefreshlayout.BGARefreshLayout;
import com.sky.beautiful.Rrefreshlayout.View.Divider;
import com.sky.beautiful.Rrefreshlayout.adapter.BGAOnItemChildClickListener;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.Utils.ToatUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2018/1/2 no 下午2:16
 * @USER : vvguoliang
 * @File : SearchActivity.java
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

public class SearchActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnItemChildClickListener, View.OnClickListener {

    private TextView title, title_search_text;

    private int type = 0;

    private RecyclerView rv_recyclerview_data, rv_recyclerview_data1;

    private BGARefreshLayout rl_recyclerview_refresh, rl_recyclerview_refresh1;

    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;

    private SearchStringRecyclerViewAdapter searchStringRecyclerViewAdapter;

    private int mNewPageNumber = 1;

    private String inti;

    private boolean mMorePageNumber = false;

    private List<GetPageShow> getPageShowList1 = new ArrayList<>();

    private List<GetLabel> getLabelList;

    private BGAMoocStyleRefreshViewHolder moocStyleRefreshViewHolder, moocStyleRefreshViewHolder1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search_image2:
                finish();
                break;
            case R.id.title_search_text:
                title_search_text.setVisibility(View.GONE);
                rv_recyclerview_data.setVisibility(View.GONE);
                rv_recyclerview_data1.setVisibility(View.VISIBLE);
                rl_recyclerview_refresh.setVisibility(View.GONE);
                rl_recyclerview_refresh1.setVisibility(View.VISIBLE);
                if (getLabelList != null && getLabelList.size() > 0) {
                    getLabelList.clear();
                    searchStringRecyclerViewAdapter.clear();
                }
                getLabel();
                break;
        }

    }

    @Override
    public void initData() {
        findView(R.id.title_search_image2).setOnClickListener(this);
        findView(R.id.title_search_image2).setVisibility(View.VISIBLE);
        title_search_text.setOnClickListener(this);
        title.setText("搜索");
        rv_recyclerview_data.setVisibility(View.GONE);
        rl_recyclerview_refresh.setVisibility(View.GONE);
        rl_recyclerview_refresh1.setVisibility(View.VISIBLE);
        rv_recyclerview_data1.setVisibility(View.VISIBLE);

        getData();
        getData1();
        getLabel();
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type", 0);
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
        title_search_text = findView(R.id.title_search_text);
        rv_recyclerview_data = findViewById(R.id.rv_recyclerview_data);
        rv_recyclerview_data1 = findViewById(R.id.rv_recyclerview_data1);
        rl_recyclerview_refresh = findViewById(R.id.rl_recyclerview_refresh);
        rl_recyclerview_refresh1 = findViewById(R.id.rl_recyclerview_refresh1);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.act_search;
    }

    private void getHttp() {
        HttpRequest.getInstance().setPublic(this, AggregateMap.getInstance().setGetPageShow(SharedPreferencesUtils.get(this, "userNo", "0").toString(),
                inti, type, mNewPageNumber), mHandler, HttpImplements.getInstance().getHttp(this, "getPageShow"), "getPageShow");
    }

    private void getLabel() {
        HttpRequest.getInstance().setPublic(this, AggregateMap.getInstance().setGetLabel(SharedPreferencesUtils.get(this, "userNo", "0").toString(),
                ""), mHandler, HttpImplements.getInstance().getHttp(this, "getLabel"), "getLabel");
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 205:
                    break;
                case 206:
                    break;
                case 1001:
                    List<GetPageShow> getPageShowList = (List<GetPageShow>) msg.obj;
                    if (getPageShowList != null && getPageShowList.size() > 0) {
                        if (mNewPageNumber == 1) {
                            if (getPageShowList1.size() > 0) {
                                getPageShowList1.clear();
                            }
                            getPageShowList1.addAll(getPageShowList);
                            if (mMorePageNumber) {
                                mHandler.postDelayed(() -> {
                                    searchRecyclerViewAdapter.clear();
                                    rl_recyclerview_refresh.endRefreshing();
                                    searchRecyclerViewAdapter.addNewData(getPageShowList1);
                                    rv_recyclerview_data.smoothScrollToPosition(0);
                                }, 3000);
                            } else {
                                searchRecyclerViewAdapter.clear();
                                searchRecyclerViewAdapter.addNewData(getPageShowList1);
                            }
                        } else {
                            getPageShowList1.addAll(getPageShowList);
                            searchRecyclerViewAdapter.clear();
                            rl_recyclerview_refresh.endLoadingMore();
                            searchRecyclerViewAdapter.addMoreData(getPageShowList1);
                        }
                    } else {
                        if (searchRecyclerViewAdapter != null) {
                            searchRecyclerViewAdapter.clear();
                            searchRecyclerViewAdapter.addMoreData(getPageShowList1);
                        }
                        rl_recyclerview_refresh.endLoadingMore();
                        ToatUtils.showShort1(SearchActivity.this, "暂无数据");
                    }
                    break;
                case 1002:
                    getLabelList = (List<GetLabel>) msg.obj;
                    searchStringRecyclerViewAdapter.addNewData(getLabelList);
                    break;

            }
        }
    };

    private void getData() {
        rl_recyclerview_refresh.setDelegate(this);
        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(rv_recyclerview_data);
        searchRecyclerViewAdapter.setOnItemChildClickListener(this);

        moocStyleRefreshViewHolder = new BGAMoocStyleRefreshViewHolder(this, true);
        moocStyleRefreshViewHolder.setOriginalImage(R.mipmap.ic_icon);
        moocStyleRefreshViewHolder.setUltimateColor(R.color.commont_colorff3a94);
        rl_recyclerview_refresh.setRefreshViewHolder(moocStyleRefreshViewHolder);
//        if (type == 1) {
//            searchRecyclerViewAdapter.getString("首页");
//        } else {
        searchRecyclerViewAdapter.getString("私照");
//        }
        rv_recyclerview_data.addItemDecoration(new Divider(10, 0));
        rv_recyclerview_data.setLayoutManager(new LinearLayoutManager(this));
        rv_recyclerview_data.setAdapter(searchRecyclerViewAdapter.getHeaderAndFooterAdapter());
        rl_recyclerview_refresh.setIsShowLoadingMoreView(true);

        mNewPageNumber = 1;
    }

    private void getData1() {
        searchStringRecyclerViewAdapter = new SearchStringRecyclerViewAdapter(rv_recyclerview_data1);
        searchStringRecyclerViewAdapter.setOnItemChildClickListener(this);

        moocStyleRefreshViewHolder1 = new BGAMoocStyleRefreshViewHolder(this, true);
        moocStyleRefreshViewHolder1.setOriginalImage(R.mipmap.ic_icon);
        moocStyleRefreshViewHolder1.setUltimateColor(R.color.commont_colorff3a94);
        rl_recyclerview_refresh1.setRefreshViewHolder(moocStyleRefreshViewHolder1);
        rv_recyclerview_data1.addItemDecoration(new Divider(5, 0));
        rv_recyclerview_data1.setLayoutManager(new GridLayoutManager(this, 4));
        rv_recyclerview_data1.setAdapter(searchStringRecyclerViewAdapter.getHeaderAndFooterAdapter());
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (getPageShowList1 != null && getPageShowList1.size() > 0) {
            getPageShowList1.clear();
        }
        mNewPageNumber = 1;
        mMorePageNumber = true;
        getHttp();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mNewPageNumber++;
        mMorePageNumber = false;
        getHttp();
        return true;
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        Intent intent = new Intent(this, ContentActivity.class);
        intent.putExtra("ViewAtlas", getPageShowList1.get(position).getAtlasCode());
        startActivity(intent);
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView1, View childView2, int position) {
        inti = getLabelList.get(position).getTagCode();
        title_search_text.setVisibility(View.VISIBLE);
        rv_recyclerview_data.setVisibility(View.VISIBLE);
        rv_recyclerview_data1.setVisibility(View.GONE);
        rl_recyclerview_refresh.setVisibility(View.VISIBLE);
        rl_recyclerview_refresh1.setVisibility(View.GONE);
        title_search_text.setText(getString(R.string.title_cancel));
        if (searchStringRecyclerViewAdapter != null) {
            searchStringRecyclerViewAdapter.clear();
        }
        getHttp();
    }
}
