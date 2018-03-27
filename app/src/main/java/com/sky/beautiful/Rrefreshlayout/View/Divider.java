package com.sky.beautiful.Rrefreshlayout.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @Time : 2017/12/21 no 下午4:06
 * @USER : vvguoliang
 * @File : Divider.java
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

@SuppressWarnings("deprecation")
public class Divider extends RecyclerView.ItemDecoration {
    private int space;
    private int type;

    public Divider(int space, int type) {
        this.space = space;
        this.type = type;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        //设置左右的间隔如果想设置的话自行设置，我这用不到就注释掉了
//        outRect.left = space;
//        outRect.right = space;


        //改成使用上面的间隔来设置
//        if (parent.getChildPosition(view) != 0)
//            outRect.top = space;
        if (type == 0) {
            outRect.left = space;
            outRect.right = space;
            if (parent.getChildPosition(view) != 0)
                outRect.top = space;
        } else {
            outRect.left = 0;
            outRect.right = 0;
            if (parent.getChildPosition(view) != 0)
                outRect.top = 0;
        }

        if (parent.getChildPosition(view) != 0)
            outRect.bottom = space;
    }
}
