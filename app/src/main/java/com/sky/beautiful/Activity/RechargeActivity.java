package com.sky.beautiful.Activity;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.beautiful.BaseActivity.BaseActivity;
import com.sky.beautiful.ImmersionBar.ImmersionBar;
import com.sky.beautiful.R;
import com.sky.beautiful.Utils.ToatUtils;

/**
 * @Time : 2018/1/2 no 上午9:40
 * @USER : vvguoliang
 * @File : RechargeActivity.java
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

public class RechargeActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_relat;

    private TextView recharge_current, recharge_minimum, recharge_other_balace_text1, recharge_other_balace_text2, recharge_other_balace_text3,
            recharge_other_balace_text4, recharge_other_balace_text5, title;

    private LinearLayout recharge_other_balace1, recharge_other_balace2, recharge_other_balace3,
            recharge_other_balace4, recharge_other_balace, recharge_checkbox_linear2, recharge_checkbox_linear1;

    private ImageView recharge_checkbox1, recharge_checkbox2;

    private Button recharge_button;

    private EditText recharge_other_balace_text6;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search_image2:
                finish();
                break;
            case R.id.recharge_other_balace1:
                getBack_resou(1);

                break;
            case R.id.recharge_other_balace2:
                getBack_resou(2);
                break;
            case R.id.recharge_other_balace3:
                getBack_resou(3);
                break;
            case R.id.recharge_other_balace4:
                getBack_resou(4);
                break;
            case R.id.recharge_other_balace:
                getBack_resou(5);
                break;
            case R.id.recharge_checkbox_linear2:
                getcheckbox(1);
                break;
            case R.id.recharge_checkbox_linear1:
                getcheckbox(2);
                break;
            case R.id.recharge_button:
                ToatUtils.showShort1(this, getString(R.string.coming_soon));
                break;
        }

    }

    private void getBack_resou(int resou) {
        if (resou == 5) {
            recharge_other_balace_text5.setVisibility(View.GONE);
            recharge_other_balace_text6.setVisibility(View.VISIBLE);
        } else {
            recharge_other_balace_text5.setVisibility(View.VISIBLE);
            recharge_other_balace_text6.setVisibility(View.GONE);
        }
        switch (resou) {
            case 0:
                recharge_other_balace1.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace2.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace3.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace4.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace.setBackgroundResource(R.drawable.shape_corner1);
                break;
            case 1:
                recharge_other_balace1.setBackgroundResource(R.mipmap.ic_recharge);
                recharge_other_balace2.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace3.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace4.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace.setBackgroundResource(R.drawable.shape_corner1);
                break;
            case 2:
                recharge_other_balace1.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace2.setBackgroundResource(R.mipmap.ic_recharge);
                recharge_other_balace3.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace4.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace.setBackgroundResource(R.drawable.shape_corner1);
                break;
            case 3:
                recharge_other_balace1.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace2.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace3.setBackgroundResource(R.mipmap.ic_recharge);
                recharge_other_balace4.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace.setBackgroundResource(R.drawable.shape_corner1);
                break;
            case 4:
                recharge_other_balace1.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace2.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace3.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace4.setBackgroundResource(R.mipmap.ic_recharge);
                recharge_other_balace.setBackgroundResource(R.drawable.shape_corner1);
                break;
            case 5:
                recharge_other_balace1.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace2.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace3.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace4.setBackgroundResource(R.drawable.shape_corner1);
                recharge_other_balace.setBackgroundResource(R.mipmap.ic_recharge);
                break;
        }
    }

    private void getcheckbox(int checkbox) {
        if (checkbox == 1) {
            recharge_checkbox2.setImageResource(R.mipmap.ic_chenck_prospect);
            recharge_checkbox1.setImageResource(R.mipmap.ic_check_bg);
        } else {
            recharge_checkbox1.setImageResource(R.mipmap.ic_chenck_prospect);
            recharge_checkbox2.setImageResource(R.mipmap.ic_check_bg);
        }
    }

    @Override
    public void initData() {
        title.setText(getString(R.string.recharge_core));
        findView(R.id.title_search_image2).setOnClickListener(this);
        findView(R.id.title_search_image2).setVisibility(View.VISIBLE);

        recharge_other_balace1.setOnClickListener(this);
        recharge_other_balace2.setOnClickListener(this);
        recharge_other_balace3.setOnClickListener(this);
        recharge_other_balace4.setOnClickListener(this);
        recharge_other_balace.setOnClickListener(this);
        recharge_checkbox_linear2.setOnClickListener(this);
        recharge_checkbox_linear1.setOnClickListener(this);
        recharge_button.setOnClickListener(this);

        getBack_resou(0);
    }

    @Override
    public void initView() {
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            mImmersionBar.statusBarDarkFont(true).init();
        } else {
            findViewById(R.id.tab).setBackgroundColor(Color.rgb(231, 231, 231));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            findViewById(R.id.tab).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.tab).setVisibility(View.GONE);
        }
        title_relat = findView(R.id.title_relat);
        recharge_current = findView(R.id.recharge_current);
        recharge_minimum = findView(R.id.recharge_minimum);
        recharge_other_balace1 = findView(R.id.recharge_other_balace1);
        recharge_other_balace2 = findView(R.id.recharge_other_balace2);
        recharge_other_balace3 = findView(R.id.recharge_other_balace3);
        recharge_other_balace4 = findView(R.id.recharge_other_balace4);
        recharge_other_balace = findView(R.id.recharge_other_balace);
        recharge_checkbox_linear2 = findView(R.id.recharge_checkbox_linear2);
        recharge_checkbox_linear1 = findView(R.id.recharge_checkbox_linear1);
        recharge_checkbox1 = findView(R.id.recharge_checkbox1);
        recharge_checkbox2 = findView(R.id.recharge_checkbox2);
        recharge_other_balace_text1 = findView(R.id.recharge_other_balace_text1);
        recharge_other_balace_text2 = findView(R.id.recharge_other_balace_text2);
        recharge_other_balace_text3 = findView(R.id.recharge_other_balace_text3);
        recharge_other_balace_text4 = findView(R.id.recharge_other_balace_text4);
        recharge_other_balace_text5 = findView(R.id.recharge_other_balace_text5);
        recharge_other_balace_text6 = findView(R.id.recharge_other_balace_text6);
        recharge_button = findView(R.id.recharge_button);
        title = findView(R.id.title);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.act_recharge;
    }
}
