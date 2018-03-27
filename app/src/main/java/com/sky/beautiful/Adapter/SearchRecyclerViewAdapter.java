package com.sky.beautiful.Adapter;

import android.support.v7.widget.RecyclerView;

import com.sky.beautiful.Model.GetPageShow;
import com.sky.beautiful.R;
import com.sky.beautiful.Rrefreshlayout.adapter.BGARecyclerViewAdapter;
import com.sky.beautiful.Rrefreshlayout.adapter.BGAViewHolderHelper;

/**
 * @Time : 2018/1/2 no 下午2:59
 * @USER : vvguoliang
 * @File : SearchRecyclerViewAdapter.java
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

public class SearchRecyclerViewAdapter extends BGARecyclerViewAdapter<GetPageShow> {

    private String name = "";

    public SearchRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.view_search_recycler_view_adapter_item);
    }

    public void getString(String name) {
        this.name = name;
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.search_text_linear);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, GetPageShow model) {
        helper.setImageGilde(R.id.my_circle_image, model.getFirstImage(), 0)
                .setText(R.id.search_text, model.getAtlasName())
                .setText(R.id.search_text1, name);
    }
}
