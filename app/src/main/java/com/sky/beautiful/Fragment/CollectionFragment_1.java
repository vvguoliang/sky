package com.sky.beautiful.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
 * @Time : 2018/1/24 no 下午5:03
 * @USER : vvguoliang
 * @File : CollectionFragment_1.java
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

@SuppressLint("SetTextI18n")
public class CollectionFragment_1 extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnItemChildClickListener, View.OnClickListener {

    protected Context mActivity;

    private View view;

    private RelativeLayout confirm_delete_imageview;

    private BGARefreshLayout rl_recyclerview_refresh;

    private RecyclerView rv_recyclerview_data;

    private CollectionRecyclerViewAdapter collectionRecyclerViewAdapter;

    private TextView title_path;

    private Button confirm_delete_button;

    private int mNewPageNumber = 1;

    private boolean mMorePageNumber = false;

    private boolean isVisibleToUser = false;

    private boolean isBoolean = false;

    private boolean intboole = false;

    private boolean bool_image_image = false;

    private List<MyCollect> myCollects1 = new ArrayList<>();

    private List<Map<String, Object>> integerList = new ArrayList<>();

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
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        view = inflater.inflate(R.layout.far_collection_fragment, container, false);
        findViewById();
        getData();
        return view;
    }

    @SuppressLint("WrongViewCast")
    private void findViewById() {
        confirm_delete_imageview = view.findViewById(R.id.confirm_delete_imageview);
        rl_recyclerview_refresh = view.findViewById(R.id.rl_recyclerview_refresh);
        rv_recyclerview_data = view.findViewById(R.id.rv_recyclerview_data);

        title_path = view.findViewById(R.id.title_path);
        confirm_delete_button = view.findViewById(R.id.confirm_delete_button);
        confirm_delete_button.setOnClickListener(this);

    }

    private void getData() {
        rl_recyclerview_refresh.setDelegate(this);
        collectionRecyclerViewAdapter = new CollectionRecyclerViewAdapter(rv_recyclerview_data);
        collectionRecyclerViewAdapter.setOnItemChildClickListener(this);

        getRecycler();
    }

    private void getRecycler() {
        BGAMoocStyleRefreshViewHolder moocStyleRefreshViewHolder = new BGAMoocStyleRefreshViewHolder(mActivity, true);
        moocStyleRefreshViewHolder.setOriginalImage(R.mipmap.ic_icon);
        moocStyleRefreshViewHolder.setUltimateColor(R.color.commont_colorff3a94);
        rl_recyclerview_refresh.setRefreshViewHolder(moocStyleRefreshViewHolder);
        rv_recyclerview_data.addItemDecoration(new Divider(10, 0));

        rv_recyclerview_data.setLayoutManager(new GridLayoutManager(mActivity, 3));
        rv_recyclerview_data.setAdapter(collectionRecyclerViewAdapter.getHeaderAndFooterAdapter());
        mNewPageNumber = 1;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mActivity == null) {
            mActivity = SykApplication.getContextObject();
        }
        this.isVisibleToUser = isVisibleToUser;
        userNo = SharedPreferencesUtils.get(mActivity, "userNo", "").toString();
        if (isVisibleToUser) {
            mNewPageNumber = 1;
            if (!TextUtils.isEmpty(userNo)) {
                getMyCollect(mNewPageNumber);
            }else{
                if(collectionRecyclerViewAdapter != null){
                    collectionRecyclerViewAdapter.clear();
                }
                onClickListener.setCollectType(0);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mNewPageNumber = 1;
        if (isVisibleToUser && !TextUtils.isEmpty(userNo)) {
            getMyCollect(mNewPageNumber);
        }else{
            if(collectionRecyclerViewAdapter != null){
                collectionRecyclerViewAdapter.clear();
            }
            onClickListener.setCollectType(0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_delete_button:
                bool_image_image = false;
                confirm_delete_button.setVisibility(View.GONE);
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
            intent.putExtra("type", 1);
            intent.putExtra("imageUrl", myCollects1.get(position).getImageUrl());
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
        getMyCollect(mNewPageNumber);

        mHandler.postDelayed(() -> {
            rl_recyclerview_refresh.endRefreshing();
        }, 5000);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isVisibleToUser) {
            mNewPageNumber++;
        }
        mMorePageNumber = false;
        getMyCollect(mNewPageNumber);
        mHandler.postDelayed(() -> {
            rl_recyclerview_refresh.endLoadingMore();
        }, 5000);
        return true;
    }

    private void getMyCollect(int mNewPageNumber) {
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

        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 205:
                    if (myCollects1.size() <= 0) {
                        confirm_delete_imageview.setVisibility(View.VISIBLE);
                    }
                    break;
                case 206:
                    if (myCollects1.size() <= 0) {
                        confirm_delete_imageview.setVisibility(View.VISIBLE);
                    }
                    break;
                case 1009:
                    confirm_delete_imageview.setVisibility(View.GONE);
                    List<MyCollect> myCollects = (List<MyCollect>) msg.obj;
                    if (myCollects != null && myCollects.size() > 0) {
                        getMyCollet(myCollects);
                        if (mNewPageNumber == 1) {
                            if (myCollects1.size() > 0) {
                                myCollects1.clear();
                            }
                            getMyCollet(myCollects);
                            if (mMorePageNumber) {
                                mHandler.postDelayed(() -> {
                                    collectionRecyclerViewAdapter.clear();
                                    rl_recyclerview_refresh.endRefreshing();
                                    collectionRecyclerViewAdapter.addNewData(myCollects1);
                                    rv_recyclerview_data.smoothScrollToPosition(0);
                                }, 3000);
                            } else {
                                if (myCollects1.size() <= 0) {
                                    confirm_delete_imageview.setVisibility(View.VISIBLE);
                                } else {
                                    collectionRecyclerViewAdapter.clear();
                                    collectionRecyclerViewAdapter.addNewData(myCollects1);
                                    collectionRecyclerViewAdapter.notifyDataSetChangedWrapper();
                                }
                            }
                        } else {
                            getMyCollet(myCollects);
                            collectionRecyclerViewAdapter.clear();
                            rl_recyclerview_refresh.endLoadingMore();
                            collectionRecyclerViewAdapter.addMoreData(myCollects1);
                        }
                    } else {
                        getMyCollet(myCollects);
                        collectionRecyclerViewAdapter.clear();
                        rl_recyclerview_refresh.endLoadingMore();
                        collectionRecyclerViewAdapter.addMoreData(myCollects1);
                        ToatUtils.showShort1(mActivity, "暂无数据");
                    }
                    integerList.clear();
                    if (myCollects1.size() > 0) {
//                        title_path.setVisibility(View.VISIBLE);
//                        title_path.setText("共 " + myCollects1.size() + "张照片");
                        onClickListener.setCollectType(myCollects1.size());
                    } else {
                        title_path.setVisibility(View.GONE);
                    }
                    break;
                case 1006:
                    getWhile();
                    onClickListener.onClick(true, myCollects1.size());
                    collectionRecyclerViewAdapter.clear();
                    collectionRecyclerViewAdapter.addNewData(myCollects1);
                    rv_recyclerview_data.setAdapter(collectionRecyclerViewAdapter.getHeaderAndFooterAdapter());
                    collectionRecyclerViewAdapter.notifyDataSetChangedWrapper();
                    integerList.clear();
                    if (myCollects1.size() <= 0) {
//                        title_path.setVisibility(View.VISIBLE);
//                        title_path.setText("共 " + myCollects1.size() + "张照片");
                        confirm_delete_imageview.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };

    private void getMyCollet(List<MyCollect> myCollects) {
        if (myCollects != null && myCollects.size() > 0) {
            for (int i = 0; myCollects.size() > i; i++) {
                if (myCollects.get(i).getCollectType() == 1) {
                    MyCollect myCollect = new MyCollect(
                            myCollects.get(i).getImgID(),
                            myCollects.get(i).getImageUrl(),
                            myCollects.get(i).getImageAtlasCode(),
                            myCollects.get(i).getPx(),
                            myCollects.get(i).getCollectType(), myCollects.get(i).getAtlasName(), myCollects.get(i).getFirstImage());
                    myCollects1.add(myCollect);
                }
            }
            if (myCollects1.size() <= 0) {
                confirm_delete_imageview.setVisibility(View.VISIBLE);
            } else {
                confirm_delete_imageview.setVisibility(View.GONE);
            }
        } else {
            if (myCollects1.size() <= 0) {
                confirm_delete_imageview.setVisibility(View.VISIBLE);
            } else {
                confirm_delete_imageview.setVisibility(View.GONE);
            }
        }
    }

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

    private OnClickLiner onClickListener;

    public void getVisibility(boolean Visibility) {
        if (Visibility) {
            confirm_delete_button.setVisibility(View.VISIBLE);
            bool_image_image = true;
        } else {
            confirm_delete_button.setVisibility(View.GONE);
            bool_image_image = false;
            if (integerList != null && integerList.size() > 0) {
                integerList.clear();
            }
        }
        collectionRecyclerViewAdapter.getNormalAdapter(Visibility);
        confirm_delete_button.setText("确定删除" + "(" + 0 + ")");
        rv_recyclerview_data.setLayoutManager(new GridLayoutManager(mActivity, 3));
        rv_recyclerview_data.setAdapter(collectionRecyclerViewAdapter.getHeaderAndFooterAdapter());
    }

    public interface OnClickLiner {
        void onClick(boolean onclick, int collectType);

        void setCollectType(int collectType);
    }

    public void setOnClickListener(OnClickLiner onClickListener) {
        this.onClickListener = onClickListener;
    }
}
