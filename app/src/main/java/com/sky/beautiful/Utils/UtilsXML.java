package com.sky.beautiful.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.sky.beautiful.Activity.ContentActivity;
import com.sky.beautiful.Interface.UtilsXml;
import com.sky.beautiful.Model.GetHomeBanner;
import com.sky.beautiful.R;
import com.sky.beautiful.RollingGraph.CustomBanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Time : 2017/12/21 no 下午1:50
 * @USER : vvguoliang
 * @File : UtilsXML.java
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

public class UtilsXML implements UtilsXml {

    @Override
    public View CustomHeaderBrannerView(Context context, List<GetHomeBanner> list) {
        View headerView = View.inflate(context, R.layout.view_custom_banner, null);
        CustomBanner mBanner = headerView.findViewById(R.id.banner);
        ArrayList<String> images = new ArrayList<>();
        for (int i = 0; list.size() > i; i++) {
            images.add(list.get(i).getUrl());
        }
        //设置普通指示器
        UtilsInterface.getInstance().setBanner(context, mBanner, images);
        mBanner.setOnPageClickListener((position, o) -> {
            Intent intent = new Intent(context, ContentActivity.class);
            intent.putExtra("ViewAtlas", list.get(position).getAtlasNo());
            context.startActivity(intent);
        });
        return headerView;
    }

    @Override
    public void closeKeyboard(Activity context) {
        View view = context.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 单例对象实例
     */
    private static class UtilsXMLHolder {
        static final UtilsXML INSTANCE = new UtilsXML();
    }

    public static UtilsXML getInstance() {
        return UtilsXML.UtilsXMLHolder.INSTANCE;
    }

    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private UtilsXML() {
    }
}
