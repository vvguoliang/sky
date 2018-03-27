package com.sky.beautiful.Adapter;

import android.support.v7.widget.RecyclerView;

import com.sky.beautiful.Model.GetPageShow;
import com.sky.beautiful.Model.RefreshModel;
import com.sky.beautiful.R;
import com.sky.beautiful.Rrefreshlayout.View.BGASwipeItemLayout;
import com.sky.beautiful.Rrefreshlayout.adapter.BGAViewHolderHelper;
import com.sky.beautiful.Rrefreshlayout.adapter.BGARecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * // * @Time : 2017/12/21 no 下午1:27
 *
 * @USER : vvguoliang
 * @File : SwipeRecyclerViewAdapter.java
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

public class SwipeRecyclerViewAdapter extends BGARecyclerViewAdapter<GetPageShow> {

    private int posi = 0;
    private int[] posis;

    public SwipeRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_swipe);
    }

    public void getPosi(int posi, int[] posis) {
        this.posi = posi;
        this.posis = posis;
    }

    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper, int viewType) {
        viewHolderHelper.setItemChildClickListener(R.id.path_image);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, GetPageShow getPageShow) {
        viewHolderHelper
                .setImageGilde(R.id.path_image, posi, posis, getPageShow.getImageHeight(), getPageShow.getImageWidth())
                .setImageGilde(R.id.path_image, getPageShow.getFirstImage());
//                .setText(R.id.tv_item_swipe_detail, getPageShow.getAtlasName())
//                .setTextColor(R.id.tv_item_swipe_detail, getPageShow.getAtlasName(), position);
    }
}
