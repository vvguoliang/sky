package com.sky.beautiful.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.beautiful.Activity.LogoIngActivity;
import com.sky.beautiful.Model.GetPageShow;
import com.sky.beautiful.Model.RefreshModel;
import com.sky.beautiful.OKHttp.AggregateMap;
import com.sky.beautiful.OKHttp.HttpImplements;
import com.sky.beautiful.OKHttp.HttpRequest;
import com.sky.beautiful.R;
import com.sky.beautiful.Rrefreshlayout.adapter.BGARecyclerViewAdapter;
import com.sky.beautiful.Rrefreshlayout.adapter.BGAViewHolderHelper;
import com.sky.beautiful.Rrefreshlayout.adapter.BaseRecyclerAdapter;
import com.sky.beautiful.Utils.DisplayUtils;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.Utils.UtilsInterface;

import org.json.JSONArray;

/**
 * @Time : 2017/12/21 no 下午8:48
 * @USER : vvguoliang
 * @File : NormalRecyclerViewAdapter.java
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

@SuppressWarnings("UnusedAssignment")
public class NormalRecyclerViewAdapter extends BaseRecyclerAdapter<GetPageShow> {

    private int posi = 0;
    private int[] posis;
    private boolean bool = false;
    private Context mContext;

    private boolean isLike = false;

    public NormalRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    public void getNormalAdapter(boolean bool) {
        this.bool = bool;
    }

    public void getPosi(int posi, int[] posis) {
        this.posi = posi;
        this.posis = posis;
    }

//    @Override
//    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper, int viewType) {
//        viewHolderHelper.setItemChildClickListener(R.id.path_image);
//    }
//
//    @Override
//    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, GetPageShow getPageShow) {
//        viewHolderHelper
//                .setImageGilde(R.id.path_image, posi, posis, getPageShow.getImageHeight(), getPageShow.getImageWidth())
//                .setImageGilde(R.id.path_image, getPageShow.getFirstImage())
//                .setText(R.id.tv_item_swipe_detail, getPageShow.getAtlasName())
//                .setImage(R.id.home_adapter_image1, bool)
//                .setImage(R.id.home_adapter_image2, bool);
//    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_normalrecyclerview_item, parent, false);
        return new MyHolder(layout);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, GetPageShow data) {
        if (viewHolder instanceof MyHolder) {
            ((MyHolder) viewHolder).path_image.setScaleType(ImageView.ScaleType.FIT_XY);
            if (posi == 0) {
                ((MyHolder) viewHolder).path_image.setLayoutParams(new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, ((posis[0] * data.getImageHeight()) / data.getImageWidth()) / DisplayUtils.dip2px(mContext, 0.5f)));
            } else {
                ((MyHolder) viewHolder).path_image.setLayoutParams(new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, ((posis[0] * data.getImageHeight()) / data.getImageWidth()) / DisplayUtils.dip2px(mContext, 0.3f)));
            }
            UtilsInterface.getInstance().UtilGlideAngle(mContext, data.getFirstImage(), ((MyHolder) viewHolder).path_image);
            if (TextUtils.isEmpty(data.getAtlasName())) {
                ((MyHolder) viewHolder).tv_item_swipe_detail.setText("");
            } else {
                ((MyHolder) viewHolder).tv_item_swipe_detail.setText(data.getAtlasName());
            }
            if (TextUtils.isEmpty(data.getAtlasName())) {
                ((MyHolder) viewHolder).tv_item_swipe_detail1.setText("");
            } else {
                ((MyHolder) viewHolder).tv_item_swipe_detail1.setText(data.getAtlasName());
            }
            ((MyHolder) viewHolder).home_look.setText(data.getViewTimes() + "");

            ((MyHolder) viewHolder).home_look1.setText(data.getNum() + "");

            if (data.getIsClose() == 0) {
                isLike = false;
                ((MyHolder) viewHolder).home_like.setImageResource(R.mipmap.ic_home_dislike);
            } else {
                isLike = true;
                ((MyHolder) viewHolder).home_like.setImageResource(R.mipmap.ic_home_like);
            }

            if (bool) {
                ((MyHolder) viewHolder).home_adapter_image.setVisibility(View.VISIBLE);
                ((MyHolder) viewHolder).home_look_relative.setVisibility(View.GONE);
                ((MyHolder) viewHolder).tv_item_swipe_detail.setMaxEms(6);
                ((MyHolder) viewHolder).home_adapter_image1.setVisibility(View.VISIBLE);
                ((MyHolder) viewHolder).home_adapter_image2.setVisibility(View.VISIBLE);
            } else {
                ((MyHolder) viewHolder).home_look_relative.setVisibility(View.VISIBLE);
                ((MyHolder) viewHolder).home_adapter_image.setVisibility(View.GONE);
                ((MyHolder) viewHolder).tv_item_swipe_detail1.setMaxEms(15);
                ((MyHolder) viewHolder).home_adapter_image1.setVisibility(View.GONE);
                ((MyHolder) viewHolder).home_adapter_image2.setVisibility(View.GONE);
            }

            ((MyHolder) viewHolder).home_like.setOnClickListener(v -> {
                if (TextUtils.isEmpty(SharedPreferencesUtils.get(mContext, "userNo", "").toString())) {
                    mContext.startActivity(new Intent(mContext, LogoIngActivity.class));
                } else {
                    isLike = !isLike;
                    if (isLike) {
                        getcontent_bool2(viewHolder, data.getID(), data, data.getNum(), 1);
                    } else {
                        getcontent_bool2(viewHolder, data.getID(), data, data.getNum(), 0);
                    }
                }
            });
        }
    }

    private void getcontent_bool2(RecyclerView.ViewHolder viewHolder, String ID, GetPageShow data, int num, int type) {
        HttpRequest.getInstance().setPublic(mContext, AggregateMap.getInstance().setIsCollect(SharedPreferencesUtils.get(mContext, "userNo", "0").toString(),
                2, ID, type, 1, new JSONArray()),
                getmHandler(viewHolder, data, num), HttpImplements.getInstance().getHttp(mContext, "isCollect"), "isCollect");
    }

    @SuppressLint("SetTextI18n")
    private Handler getmHandler(RecyclerView.ViewHolder viewHolder, GetPageShow data, int num) {
        final boolean[] num_bool = {false};
        @SuppressLint("HandlerLeak")
        Handler mHandler = new Handler() {
            @SuppressLint("ResourceType")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 205:
                        num_bool[0] = true;
                        break;
                    case 206:
                        num_bool[0] = true;
                        break;
                    case 1006:
                        num_bool[0] = false;
                        break;
                }
            }
        };
        if (!num_bool[0]) {
            if (isLike) {
                data.setNum(num++);
                ((MyHolder) viewHolder).home_look1.setText(num++ + "");
                ((MyHolder) viewHolder).home_like.setImageResource(R.mipmap.ic_home_like);
            } else {
                if (num-- > 0) {
                    data.setNum(num--);
                    ((MyHolder) viewHolder).home_look1.setText(num-- + "");
                } else {
                    data.setNum(0);
                    ((MyHolder) viewHolder).home_look1.setText("0");
                }
                ((MyHolder) viewHolder).home_like.setImageResource(R.mipmap.ic_home_dislike);
            }
        }
        return mHandler;
    }


    class MyHolder extends BaseRecyclerAdapter.Holder {
        ImageView path_image;
        ImageView home_adapter_image1;
        ImageView home_adapter_image2;
        TextView tv_item_swipe_detail;

        RelativeLayout home_adapter_image;

        RelativeLayout home_look_relative;
        TextView home_look;
        TextView tv_item_swipe_detail1;
        TextView home_look1;
        ImageView home_like;


        public MyHolder(View itemView) {
            super(itemView);
            path_image = itemView.findViewById(R.id.path_image);
            home_adapter_image1 = itemView.findViewById(R.id.home_adapter_image1);
            home_adapter_image2 = itemView.findViewById(R.id.home_adapter_image2);
            tv_item_swipe_detail = itemView.findViewById(R.id.tv_item_swipe_detail);

            home_adapter_image = itemView.findViewById(R.id.home_adapter_image);
            home_look_relative = itemView.findViewById(R.id.home_look_relative);
            home_look = itemView.findViewById(R.id.home_look);
            tv_item_swipe_detail1 = itemView.findViewById(R.id.tv_item_swipe_detail1);
            home_look1 = itemView.findViewById(R.id.home_look1);
            home_like = itemView.findViewById(R.id.home_like);
        }
    }
}
