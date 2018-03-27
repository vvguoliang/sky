package com.sky.beautiful.BaseFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sky.beautiful.Activity.ContentActivity;
import com.sky.beautiful.Adapter.SwipeRecyclerViewAdapter;
import com.sky.beautiful.R;
import com.sky.beautiful.Rrefreshlayout.BGAMoocStyleRefreshViewHolder;
import com.sky.beautiful.Rrefreshlayout.BGARefreshLayout;
import com.sky.beautiful.Rrefreshlayout.View.Divider;
import com.sky.beautiful.Rrefreshlayout.adapter.BGAOnItemChildClickListener;
import com.sky.beautiful.SykApplication;

/**
 * @Time : 2017/12/27 no 下午4:49
 * @USER : vvguoliang
 * @File : CouponFragment.java
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

public class CouponFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnItemChildClickListener, View.OnClickListener {

    protected Activity mActivity;

    private View view;

    private static String KEY = "title";

    private static int POSITION = 0;

    private RecyclerView rv_recyclerview_data;

    private BGARefreshLayout mRefreshLayout;

    private SwipeRecyclerViewAdapter swipeRecyclerViewAdapter;

    private int mNewPageNumber = 0;

    private int mMorePageNumber = 0;

    private LinearLayout coupon_linear;

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

    /**
     * fragment静态传值
     */
    public static CouponFragment newInstance(String str, int position) {
        KEY = str;
        POSITION = position;
        CouponFragment fragment = new CouponFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.far_coupon, null);

        rv_recyclerview_data = view.findViewById(R.id.rv_recyclerview_data);
        mRefreshLayout = view.findViewById(R.id.rl_recyclerview_refresh);
        coupon_linear = view.findViewById(R.id.coupon_linear);
        getData();
        return view;
    }

    private void getData() {
        coupon_linear.setVisibility(View.VISIBLE);
        mRefreshLayout.setVisibility(View.GONE);
        mRefreshLayout.setDelegate(this);
        swipeRecyclerViewAdapter = new SwipeRecyclerViewAdapter(rv_recyclerview_data);
        swipeRecyclerViewAdapter.setOnItemChildClickListener(this);

        BGAMoocStyleRefreshViewHolder moocStyleRefreshViewHolder = new BGAMoocStyleRefreshViewHolder(mActivity, true);
        moocStyleRefreshViewHolder.setOriginalImage(R.mipmap.ic_icon);
        moocStyleRefreshViewHolder.setUltimateColor(R.color.commont_colorff3a94);
        mRefreshLayout.setRefreshViewHolder(moocStyleRefreshViewHolder);

        rv_recyclerview_data.addItemDecoration(new Divider(10, 0));
        rv_recyclerview_data.setLayoutManager(new GridLayoutManager(mActivity, 2));
        rv_recyclerview_data.setAdapter(swipeRecyclerViewAdapter.getHeaderAndFooterAdapter());

        mNewPageNumber = 0;
        mMorePageNumber = 0;
//        swipeRecyclerViewAdapter.addNewData(getRefreshModel());
        swipeRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        startActivity(new Intent(mActivity, ContentActivity.class));
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView1, View childView2, int position) {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mNewPageNumber++;
        if (mNewPageNumber > 4) {
            mRefreshLayout.endRefreshing();
            return;
        }
        new Handler().postDelayed(() -> {
            mRefreshLayout.endRefreshing();
//            swipeRecyclerViewAdapter.addNewData(getRefreshModel());
            rv_recyclerview_data.smoothScrollToPosition(0);
        }, 3000);

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mMorePageNumber++;
        if (mMorePageNumber > 4) {
            mRefreshLayout.endLoadingMore();
            return false;
        }
        new Handler().postDelayed(() -> {
            mRefreshLayout.endLoadingMore();
//            swipeRecyclerViewAdapter.addMoreData(getRefreshModel());
        }, 3000);
        return true;
    }
}
