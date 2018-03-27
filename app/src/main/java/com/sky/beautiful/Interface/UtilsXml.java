package com.sky.beautiful.Interface;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.sky.beautiful.Model.GetHomeBanner;

import java.util.List;
import java.util.Map;

/**
 * @Time : 2017/12/21 no 下午1:49
 * @USER : vvguoliang
 * @File : UtilsXml.java
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

public interface UtilsXml {

    /**
     * 定义轮播图
     *
     * @param context
     * @return
     */
    View CustomHeaderBrannerView(Context context, List<GetHomeBanner> list);

    /**
     * 关闭筽键盘
     */
    void closeKeyboard(Activity context);
}
