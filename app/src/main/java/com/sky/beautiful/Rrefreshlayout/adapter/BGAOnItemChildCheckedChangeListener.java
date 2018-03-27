package com.sky.beautiful.Rrefreshlayout.adapter;

import android.view.ViewGroup;
import android.widget.CompoundButton;

/**
 * @Time : 2017/12/21 no 下午3:19
 * @USER : vvguoliang
 * @File : BGAOnItemChildCheckedChangeListener.java
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
 * AdapterView和RecyclerView的item中子控件选中状态变化事件监听器
 */

public interface BGAOnItemChildCheckedChangeListener {

    void onItemChildCheckedChanged(ViewGroup parent, CompoundButton childView, int position, boolean isChecked);
}
