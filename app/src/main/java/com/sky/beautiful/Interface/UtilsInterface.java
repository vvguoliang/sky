package com.sky.beautiful.Interface;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.BottomNavigationView;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.sky.beautiful.Model.ViewAtlas;
import com.sky.beautiful.RollingGraph.CustomBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2017/12/21 no 上午10:13
 * @USER : vvguoliang
 * @File : UtilsInterface.java
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

public interface UtilsInterface {

    /**
     * 显示高斯模糊效果
     *
     * @param pattern
     * @param url
     * @param view
     */
    void UtilBlur(Context context,String pattern, String url, ImageView view);

    /**
     * 显示高斯模糊效果
     *
     * @param pattern
     * @param url
     * @param view
     */
    void UtilBlur1(Context context,String pattern, String url, ImageView view);

    /**
     * 图片显示效果  Glide  图片圆角
     *
     * @param context
     * @param url
     * @param view
     */
    void UtilGlide(Context context, Object url, View view);
    /**
     * 图片显示效果  Glide  图片圆角
     *
     * @param context
     * @param url
     * @param view
     */
    void UtilGlideUtilGlideTransformations(Context context, Object url, View view);

    /**
     * 图片显示效果  Glide 图片直角
     *
     * @param context
     * @param url
     * @param view
     */
    void UtilGlideAngle(Context context, Object url, View view);
    /**
     * 图片显示效果  Glide 图片直角
     *
     * @param context
     * @param url
     * @param view
     */
    void UtilGlideAngle(Context context, Object url, ImageView view);


    /**
     * 轮播图
     *
     * @param context
     * @param customBanner
     * @param beans
     */
    void setBanner(Context context, CustomBanner customBanner, ArrayList beans);

    /**
     * 轮播图
     *
     * @param context
     * @param customBanner
     * @param beans
     */
    void setBannerAngle(Context context, CustomBanner customBanner, ArrayList beans);

    /**
     * 轮播图 放大缩小
     *
     * @param context
     * @param customBanner
     * @param beans
     */
    void setBannerActivity(Context context, CustomBanner customBanner, ArrayList beans, List<ViewAtlas> viewAtlases);

    /**
     * 从控件的底部移动到控件所在位置
     *
     * @return
     */
    TranslateAnimation setMoveToViewLocation(int time_int);

    /**
     * 添加底部显示情况
     *
     * @param navigationView
     */
    void setDisableShiftMode(BottomNavigationView navigationView);

    /**
     * 编辑框显示或者隐藏
     *
     * @param view
     * @return
     */
    TextWatcher setTextWatcher(View view);

    /**
     * 改变图片颜色
     *
     * @param bitmap
     * @return
     */
    Bitmap setImageMatrix(Context context, int bitmap);
}
