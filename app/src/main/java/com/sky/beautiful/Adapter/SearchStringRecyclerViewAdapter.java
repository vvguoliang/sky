package com.sky.beautiful.Adapter;

import android.support.v7.widget.RecyclerView;

import com.sky.beautiful.Model.GetLabel;
import com.sky.beautiful.Model.GetPageShow;
import com.sky.beautiful.R;
import com.sky.beautiful.Rrefreshlayout.adapter.BGARecyclerViewAdapter;
import com.sky.beautiful.Rrefreshlayout.adapter.BGAViewHolderHelper;

/**
 * @Time : 2018/1/3 no 下午7:33
 * @USER : vvguoliang
 * @File : SearchStringRecyclerViewAdapter.java
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

public class SearchStringRecyclerViewAdapter extends BGARecyclerViewAdapter<GetLabel> {

    public SearchStringRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.view_searchstring_recyclerviewadapter_item);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.search_text, 0);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, GetLabel model) {
        helper.setText(R.id.search_text, model.getTagName());

    }
}
