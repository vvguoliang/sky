package com.sky.beautiful.BaseFragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sky.beautiful.Activity.ContentActivity;
import com.sky.beautiful.Adapter.SwipeRecyclerViewAdapter;
import com.sky.beautiful.Model.GetPageShow;
import com.sky.beautiful.OKHttp.AggregateMap;
import com.sky.beautiful.OKHttp.HttpImplements;
import com.sky.beautiful.OKHttp.HttpRequest;
import com.sky.beautiful.R;
import com.sky.beautiful.Rrefreshlayout.BGAMoocStyleRefreshViewHolder;
import com.sky.beautiful.Rrefreshlayout.BGARefreshLayout;
import com.sky.beautiful.Rrefreshlayout.View.Divider;
import com.sky.beautiful.Rrefreshlayout.adapter.BGAOnItemChildClickListener;
import com.sky.beautiful.SykApplication;
import com.sky.beautiful.Utils.AppUtil;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.Utils.ToatUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2017/12/22 no 上午11:04
 * @USER : vvguoliang
 * @File : DynamicFragment.java
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

@SuppressLint("ValidFragment")
public class DynamicFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnItemChildClickListener, View.OnClickListener {

    protected Activity mActivity;

    private View view;

    private List<String> labelList;

    private RecyclerView rv_recyclerview_data;

    private BGARefreshLayout mRefreshLayout;

    private SwipeRecyclerViewAdapter swipeRecyclerViewAdapter;

    private int mNewPageNumber = 1;

    private String inti;

    private RelativeLayout confirm_delete_imageview;

    private boolean mMorePageNumber = false;

    private List<GetPageShow> getPageShowList1 = new ArrayList<>();

    private static int mSerial = 0;

    private int mTabPos = 0;

    private boolean isFirst = true;

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

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //这里执行加载数据的操作
            getPageShow();
        }
    };

    public DynamicFragment(int serial, List<String> labelList) {
        super();
        this.mSerial = serial;
        this.labelList = labelList;
    }

    public void sendMessage() {
        Message message = handler.obtainMessage();
        message.sendToTarget();
    }

    public void setTabPos(int mTabPos) {
        this.mTabPos = mTabPos;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.far_dynamic, null);
        rv_recyclerview_data = view.findViewById(R.id.rv_recyclerview_data);
        mRefreshLayout = view.findViewById(R.id.rl_recyclerview_refresh);
        confirm_delete_imageview = view.findViewById(R.id.confirm_delete_imageview);
        //设置页和当前页一致时加载，防止预加载
        if (isFirst && mTabPos == mSerial) {
            isFirst = false;
            sendMessage();
        }
        getData();
        return view;
    }

    private void getData() {
        if (swipeRecyclerViewAdapter != null) {
            swipeRecyclerViewAdapter.clear();
        }
        mRefreshLayout.setDelegate(this);
        swipeRecyclerViewAdapter = new SwipeRecyclerViewAdapter(rv_recyclerview_data);
        swipeRecyclerViewAdapter.setOnItemChildClickListener(this);
        swipeRecyclerViewAdapter.getPosi(0, AppUtil.getInstance().Dispay(mActivity));
        BGAMoocStyleRefreshViewHolder moocStyleRefreshViewHolder = new BGAMoocStyleRefreshViewHolder(mActivity, true);
        moocStyleRefreshViewHolder.setOriginalImage(R.mipmap.ic_icon);
        moocStyleRefreshViewHolder.setUltimateColor(R.color.commont_colorff3a94);
        mRefreshLayout.setRefreshViewHolder(moocStyleRefreshViewHolder);
        rv_recyclerview_data.addItemDecoration(new Divider(10, 0));
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_recyclerview_data.setLayoutManager(layoutManager);
        rv_recyclerview_data.setAdapter(swipeRecyclerViewAdapter.getHeaderAndFooterAdapter());
        mNewPageNumber = 1;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        Intent intent = new Intent(mActivity, ContentActivity.class);
        intent.putExtra("ViewAtlas", getPageShowList1.get(position).getAtlasCode());
        startActivity(intent);
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView1, View childView2, int position) {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (getPageShowList1 != null && getPageShowList1.size() > 0) {
            getPageShowList1.clear();
        }
        mNewPageNumber = 1;
        mMorePageNumber = true;
        getPageShow();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mNewPageNumber++;
        mMorePageNumber = false;
        getPageShow();
        return true;
    }

    private void getPageShow() {
        HttpRequest.getInstance().setPublic(mActivity, AggregateMap.getInstance().setGetPageShow(SharedPreferencesUtils.get(mActivity, "userNo", "0").toString(),
                labelList.get(mTabPos), 2, mNewPageNumber), mHandler, HttpImplements.getInstance().getHttp(mActivity, "getPageShow"), "getPageShow");
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
                    if (mNewPageNumber == 1) {
                        if (mMorePageNumber) {
                            mHandler.postDelayed(() -> {
                                swipeRecyclerViewAdapter.clear();
                                mRefreshLayout.endRefreshing();
                                swipeRecyclerViewAdapter.addNewData(getPageShowList1);
                            }, 3000);
                        }
                    }
                    confirm_delete_imageview.setVisibility(View.VISIBLE);
                    break;
                case 1001:
                    List<GetPageShow> getPageShowList = (List<GetPageShow>) msg.obj;
                    if (getPageShowList != null && getPageShowList.size() > 0) {
                        getPageShowList1.addAll(getPageShowList);
                        if (mNewPageNumber == 1) {
                            if (getPageShowList1.size() > 0) {
                                getPageShowList1.clear();
                            }
                            swipeRecyclerViewAdapter.clear();
                            getPageShowList1.addAll(getPageShowList);
                            if (mMorePageNumber) {
                                mHandler.postDelayed(() -> {
                                    mRefreshLayout.endRefreshing();
                                    swipeRecyclerViewAdapter.addNewData(getPageShowList1);
                                }, 3000);
                            } else {
                                swipeRecyclerViewAdapter.addNewData(getPageShowList1);
                            }
                        } else {
                            getPageShowList1.addAll(getPageShowList);
                            swipeRecyclerViewAdapter.clear();
                            mRefreshLayout.endLoadingMore();
                            swipeRecyclerViewAdapter.addMoreData(getPageShowList1);
                        }
                    } else {
                        if (mNewPageNumber == 0) {
                            mRefreshLayout.setBackgroundResource(R.drawable.screen_default);
                        }
                        if (swipeRecyclerViewAdapter != null) {
                            swipeRecyclerViewAdapter.clear();
                            swipeRecyclerViewAdapter.addMoreData(getPageShowList1);
                        }
                        mRefreshLayout.endLoadingMore();
                        ToatUtils.showShort1(mActivity, "暂无数据");
                    }
                    confirm_delete_imageview.animate()
                            .alpha(0f)
                            .setDuration(2 * 1000)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    confirm_delete_imageview.setVisibility(View.GONE);
                                }
                            });
                    break;
            }
        }
    };
}
