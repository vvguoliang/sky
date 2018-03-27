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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.beautiful.Activity.ContentActivity;
import com.sky.beautiful.Adapter.CollectionRecyclerViewAdapter;
import com.sky.beautiful.Model.MyCollect;
import com.sky.beautiful.OKHttp.AggregateMap;
import com.sky.beautiful.OKHttp.HttpImplements;
import com.sky.beautiful.OKHttp.HttpRequest;
import com.sky.beautiful.R;
import com.sky.beautiful.Rrefreshlayout.BGAMoocStyleRefreshViewHolder;
import com.sky.beautiful.Rrefreshlayout.BGARefreshLayout;
import com.sky.beautiful.Rrefreshlayout.View.Divider;
import com.sky.beautiful.Rrefreshlayout.adapter.BGAOnItemChildClickListener;
import com.sky.beautiful.SykApplication;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.Utils.ToatUtils;
import com.sky.beautiful.Utils.UtilsInterface;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vvguoliang on 2017/12/19.
 * 收藏
 */
@SuppressLint("SetTextI18n")
public class CollectionFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnItemChildClickListener, View.OnClickListener {

    protected Activity mActivity;

    private View view;

    private CollectionRecyclerViewAdapter collectionRecyclerViewAdapter;

    private BGARefreshLayout mRefreshLayout;

    private RecyclerView rv_recyclerview_data;

    private int mNewPageNumber = 1;

    private boolean mMorePageNumber = false;

    private TextView title_search_text;

    private boolean bool_image = true;

    private boolean bool_image_image = false;

    private TextView title_path, title;

    private Button confirm_delete_button;

    private LinearLayout confirm_delete_linear;

    private RelativeLayout confirm_delete_imageview;

    private List<Map<String, Object>> integerList = new ArrayList<>();

    private List<MyCollect> myCollects1 = new ArrayList<>();

    private boolean isVisibleToUser = false;


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
        view = LayoutInflater.from(mActivity).inflate(R.layout.far_collection, container, false);
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
        getRecycler();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mActivity == null) {
            mActivity = (Activity) SykApplication.getContextObject();
        }
        if (isVisibleToUser) {
            mMorePageNumber = false;
            this.isVisibleToUser = isVisibleToUser;
            getMyCollect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.isVisibleToUser) {
            getMyCollect();
        }
    }

    private void getData() {
        title.setText(getString(R.string.title_collection));
        title_search_text.setVisibility(View.VISIBLE);
        title_search_text.setOnClickListener(this);
        confirm_delete_button.setOnClickListener(this);

        mRefreshLayout.setDelegate(this);
        collectionRecyclerViewAdapter = new CollectionRecyclerViewAdapter(rv_recyclerview_data);
        collectionRecyclerViewAdapter.setOnItemChildClickListener(this);
    }

    private void getRecycler() {
        BGAMoocStyleRefreshViewHolder moocStyleRefreshViewHolder = new BGAMoocStyleRefreshViewHolder(mActivity, true);
        moocStyleRefreshViewHolder.setOriginalImage(R.mipmap.ic_icon);
        moocStyleRefreshViewHolder.setUltimateColor(R.color.commont_colorff3a94);
        mRefreshLayout.setRefreshViewHolder(moocStyleRefreshViewHolder);
        rv_recyclerview_data.addItemDecoration(new Divider(10, 0));

        rv_recyclerview_data.setLayoutManager(new GridLayoutManager(mActivity, 3));
        rv_recyclerview_data.setAdapter(collectionRecyclerViewAdapter.getHeaderAndFooterAdapter());
        mNewPageNumber = 1;
    }

    private void findViewById() {
        title = view.findViewById(R.id.title);
        mRefreshLayout = view.findViewById(R.id.rl_recyclerview_refresh);
        rv_recyclerview_data = view.findViewById(R.id.rv_recyclerview_data);
        title_search_text = view.findViewById(R.id.title_search_text);
        title_path = view.findViewById(R.id.title_path);
        confirm_delete_button = view.findViewById(R.id.confirm_delete_button);
        confirm_delete_linear = view.findViewById(R.id.confirm_delete_linear);
        confirm_delete_imageview = view.findViewById(R.id.confirm_delete_imageview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search_text:
                collectionRecyclerViewAdapter.getNormalAdapter(bool_image);
                if (bool_image) {
                    bool_image_image = true;
                    title_search_text.setText(getString(R.string.title_cancel));
                    confirm_delete_button.setVisibility(View.VISIBLE);
                    bool_image = false;
                } else {
                    if (integerList != null && integerList.size() > 0) {
                        integerList.clear();
                    }
                    bool_image_image = false;
                    title_search_text.setText(getString(R.string.title_edit));
                    confirm_delete_button.setVisibility(View.GONE);
                    bool_image = true;
                }
                confirm_delete_button.setText("确定删除" + "(" + 0 + ")");
                rv_recyclerview_data.setLayoutManager(new GridLayoutManager(mActivity, 3));
                rv_recyclerview_data.setAdapter(collectionRecyclerViewAdapter.getHeaderAndFooterAdapter());
                break;
            case R.id.confirm_delete_button:
                title_search_text.setText(getString(R.string.title_edit));
                bool_image_image = false;
                confirm_delete_button.setVisibility(View.GONE);
                bool_image = true;
                collectionRecyclerViewAdapter.getNormalAdapter(bool_image_image);
                rv_recyclerview_data.setLayoutManager(new GridLayoutManager(mActivity, 3));
                rv_recyclerview_data.setAdapter(collectionRecyclerViewAdapter.getHeaderAndFooterAdapter());
                getcontent_bool2();
                break;
        }
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {

    }

    private boolean intboole = false;

    @Override
    public void onItemChildClick(ViewGroup parent, View childView1, View childView2, int position) {
        if (bool_image_image) {
            if (integerList.size() > 0) {
                for (int j = 0; integerList.size() > j; j++) {
                    if (myCollects1.get(position).getImgID().equals(integerList.get(j).get("imgID"))) {
                        UtilsInterface.getInstance().UtilGlideAngle(mActivity, myCollects1.get(position).getImageUrl(), childView1);
                        ((ImageView) childView2).setImageResource(R.drawable.ic_selected_prospect);
                        integerList.remove(j);
                        intboole = true;
                    }
                }
                if (!intboole) {
                    getImagId(position, childView1, childView2);
                }
            } else {
                getImagId(position, childView1, childView2);
            }
            confirm_delete_button.setText("确定删除" + "(" + (integerList.size()) + ")");
        } else {
            Intent intent = new Intent(mActivity, ContentActivity.class);
            intent.putExtra("ViewAtlas", myCollects1.get(position).getImageAtlasCode());
            intent.putExtra("px", myCollects1.get(position).getPx());
            startActivity(intent);
        }
    }

    private void getImagId(int position, View childView1, View childView2) {
        Map<String, Object> map = new HashMap<>();
        map.put("imgID", myCollects1.get(position).getImgID());
        integerList.add(map);
        intboole = false;
        ((ImageView) childView2).setImageResource(R.drawable.ic_selected);
        for (int i = 0; integerList.size() > i; i++) {
            if (myCollects1.get(position).getImgID().equals(integerList.get(i).get("imgID"))) {
                UtilsInterface.getInstance().UtilBlur(mActivity, "", myCollects1.get(position).getImageUrl(), (ImageView) childView1);
            }
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (myCollects1 != null && myCollects1.size() > 0) {
            myCollects1.clear();
        }
        mNewPageNumber = 1;
        mMorePageNumber = true;
        getMyCollect();

        mHandler.postDelayed(() -> {
            mRefreshLayout.endRefreshing();
        }, 5000);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mNewPageNumber++;
        mMorePageNumber = false;
        getMyCollect();
        mHandler.postDelayed(() -> {
            mRefreshLayout.endLoadingMore();
        }, 5000);
        return true;
    }

    private void getMyCollect() {
        HttpRequest.getInstance().setPublic(mActivity,
                AggregateMap.getInstance().setMyCollect(SharedPreferencesUtils.get(mActivity, "userNo", "0").toString(), mNewPageNumber, 1),
                mHandler, HttpImplements.getInstance().getHttp(mActivity, "myCollect"), "myCollect");
    }

    private void getcontent_bool2() {
        try {
            HttpRequest.getInstance().setPublic(mActivity, AggregateMap.getInstance().setIsCollect(SharedPreferencesUtils.get(mActivity, "userNo", "0").toString(),
                    1, "", 0, 2, new JSONArray(SharedPreferencesUtils.saveInfo(mActivity, integerList))),
                    mHandler, HttpImplements.getInstance().getHttp(mActivity, "isCollect"), "isCollect");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 205:
                    if (myCollects1.size() <= 0) {
                        getVisible(0);
                    }
                    break;
                case 206:
                    getVisible(0);
                    break;
                case 1009:
                    List<MyCollect> myCollects = (List<MyCollect>) msg.obj;
                    if (myCollects != null && myCollects.size() > 0) {
                        if (mNewPageNumber == 1) {
                            if (myCollects1.size() > 0) {
                                myCollects1.clear();
                            }
                            myCollects1.addAll(myCollects);
                            if (mMorePageNumber) {
                                mHandler.postDelayed(() -> {
                                    collectionRecyclerViewAdapter.clear();
                                    mRefreshLayout.endRefreshing();
                                    collectionRecyclerViewAdapter.addNewData(myCollects1);
                                    rv_recyclerview_data.smoothScrollToPosition(0);
                                }, 3000);
                            } else {
                                collectionRecyclerViewAdapter.clear();
                                collectionRecyclerViewAdapter.addNewData(myCollects1);
                                collectionRecyclerViewAdapter.notifyDataSetChangedWrapper();
                            }
                        } else {
                            myCollects1.addAll(myCollects);
                            collectionRecyclerViewAdapter.clear();
                            mRefreshLayout.endLoadingMore();
                            collectionRecyclerViewAdapter.addMoreData(myCollects1);
                        }
                    } else {
                        collectionRecyclerViewAdapter.clear();
                        mRefreshLayout.endLoadingMore();
                        collectionRecyclerViewAdapter.addMoreData(myCollects1);
                        ToatUtils.showShort1(mActivity, "暂无数据");
                    }
                    getVisible(1);
                    integerList.clear();
                    if (myCollects1.size() > 0) {
                        title_path.setVisibility(View.VISIBLE);
                        title_path.setText("共 " + myCollects1.size() + "张照片");
                    } else {
                        title_path.setVisibility(View.GONE);
                    }
                    break;
                case 1006:
                    getWhile();
                    collectionRecyclerViewAdapter.clear();
                    collectionRecyclerViewAdapter.addNewData(myCollects1);
                    rv_recyclerview_data.setAdapter(collectionRecyclerViewAdapter.getHeaderAndFooterAdapter());
                    collectionRecyclerViewAdapter.notifyDataSetChangedWrapper();
                    integerList.clear();
                    if (myCollects1.size() > 0) {
                        title_path.setVisibility(View.VISIBLE);
                        title_path.setText("共 " + myCollects1.size() + "张照片");
                    } else {
                        getVisible(0);
                    }
                    break;
            }
        }
    };

    private void getWhile() {
        int size = myCollects1.size();
        if (size == integerList.size()) {
            myCollects1.clear();
        } else {
            for (int j = 0; size > j; j++) {
                for (int i = 0; integerList.size() > i; i++) {
                    if (myCollects1.get(j).getImgID().equals(integerList.get(i).get("imgID"))) {
                        myCollects1.remove(j);
                        size--;
                    }
                }
            }
        }
    }

    private void getVisible(int visib) {
        if (visib == 0) {
            confirm_delete_linear.setVisibility(View.GONE);
            title_search_text.setVisibility(View.GONE);
            confirm_delete_imageview.setVisibility(View.VISIBLE);
        } else {
            confirm_delete_linear.setVisibility(View.VISIBLE);
            title_search_text.setVisibility(View.VISIBLE);
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
        }
    }
}
