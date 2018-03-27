package com.sky.beautiful.View.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Time : 2017/12/26 no 下午6:32
 * @USER : vvguoliang
 * @File : BottomDialog.java
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

public class BottomDialog extends Dialog implements View.OnClickListener {

    protected View view;
    protected Map<Integer, View.OnClickListener> map = new HashMap<>();
    protected int width;
    protected int height;

    public BottomDialog(Context context, int layout, int customDialog) {
        super(context, customDialog);//R.style.customDialog
        view = LayoutInflater.from(context).inflate(layout, null);
    }

    @Override
    public View findViewById(int id) {
        return view.findViewById(id);
    }

    public void setWidthHeight(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        if (width <= 0) {
        } else {
            view.getLayoutParams().width = width;
        }
        if (height <= 0) {
        } else {
            view.getLayoutParams().height = height;
        }
        view.setLayoutParams(view.getLayoutParams());
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
