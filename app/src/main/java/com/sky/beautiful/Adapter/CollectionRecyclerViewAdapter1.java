package com.sky.beautiful.Adapter;

import android.support.v7.widget.RecyclerView;

import com.sky.beautiful.Model.MyCollect;
import com.sky.beautiful.R;
import com.sky.beautiful.Rrefreshlayout.adapter.BGARecyclerViewAdapter;
import com.sky.beautiful.Rrefreshlayout.adapter.BGAViewHolderHelper;

/**
 * @Time : 2018/1/25 no 下午1:07
 * @USER : vvguoliang
 * @File : CollectionRecyclerViewAdapter1.java
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

public class CollectionRecyclerViewAdapter1 extends BGARecyclerViewAdapter<MyCollect> {

    private int posi = 0;
    private boolean bool = false;

    public CollectionRecyclerViewAdapter1(RecyclerView recyclerView) {
        super(recyclerView, R.layout.view_collectionrecycler_item);
        this.posi = posi;
    }

    public void getNormalAdapter(boolean bool) {
        this.bool = bool;
    }

    public int getPosi(int posi) {
        return posi;
    }

    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper, int viewType) {
        viewHolderHelper.setItemChildClickListener(R.id.path_image, R.id.selected_image);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, MyCollect model) {
        viewHolderHelper
                .setImageGilde(R.id.path_image, model.getFirstImage(), posi)
                .setImage(R.id.selected_image, bool);
    }
}
