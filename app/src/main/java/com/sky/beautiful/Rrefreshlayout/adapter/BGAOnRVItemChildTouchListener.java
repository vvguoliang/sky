package com.sky.beautiful.Rrefreshlayout.adapter;

import android.view.MotionEvent;
import android.view.View;

/**
 * @Time : 2017/12/21 no 下午3:21
 * @USER : vvguoliang
 * @File : BGAOnRVItemChildTouchListener.java
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
 *
 * RecyclerView的item中子控件触摸事件监听器
 */

public interface BGAOnRVItemChildTouchListener {
    boolean onRvItemChildTouch(BGARecyclerViewHolder holder, View childView, MotionEvent event);
}
