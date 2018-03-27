package com.sky.beautiful.Rrefreshlayout.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * @Time : 2017/12/21 no 下午3:19
 * @USER : vvguoliang
 * @File : BGAOnItemChildClickListener.java
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
 * <p>
 * AdapterView和RecyclerView的item中子控件点击事件监听器
 */

public interface BGAOnItemChildClickListener {

    void onItemChildClick(ViewGroup parent, View childView, int position);

    void onItemChildClick(ViewGroup parent, View childView1, View childView2, int position);
}
