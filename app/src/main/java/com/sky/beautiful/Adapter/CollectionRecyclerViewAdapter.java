package com.sky.beautiful.Adapter;

import android.support.v7.widget.RecyclerView;

import com.sky.beautiful.Model.MyCollect;
import com.sky.beautiful.Model.RefreshModel;
import com.sky.beautiful.R;
import com.sky.beautiful.Rrefreshlayout.adapter.BGARecyclerViewAdapter;
import com.sky.beautiful.Rrefreshlayout.adapter.BGAViewHolderHelper;

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

public class CollectionRecyclerViewAdapter extends BGARecyclerViewAdapter<MyCollect> {

    private int posi = 0;
    private boolean bool = false;

    public CollectionRecyclerViewAdapter(RecyclerView recyclerView) {
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
                .setImageGilde(R.id.path_image, model.getImageUrl(), posi)
                .setImage(R.id.selected_image, bool);
    }
}
