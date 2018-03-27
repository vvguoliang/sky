package com.sky.beautiful.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.beautiful.BaseActivity.BaseActivity;
import com.sky.beautiful.ImmersionBar.ImmersionBar;
import com.sky.beautiful.OKHttp.AggregateMap;
import com.sky.beautiful.OKHttp.HttpImplements;
import com.sky.beautiful.OKHttp.HttpRequest;
import com.sky.beautiful.R;
import com.sky.beautiful.Utils.AppUtil;
import com.sky.beautiful.Utils.ButtonDownTimerUtils;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.Utils.ToatUtils;
import com.sky.beautiful.Utils.UtilsInterface;
import com.sky.beautiful.Utils.UtilsXML;

/**
 * @Time : 2017/12/27 no 下午12:46
 * @USER : vvguoliang
 * @File : LogoIngActivity.java
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

public class LogoIngActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_relat, login_linear, alerady_image_linear;

    private EditText login_edit_phone, login_edit_password, login_edit_code;

    private Button login_button_code, login_button_login;

    private TextView login_text_login, title, login_text_login1;

    private ImageView logo_phone_editText_error1, logo_phone_editText_error2, logo_phone_editText_error3, title_search_image2, alerady_image;

    private boolean text_login, alerady = false;

    private ButtonDownTimerUtils buttonDownTimerUtils;

    private String userNo = "";


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_text_login:
                if (fastClick()) {
                    getText_login();
                }
                break;
            case R.id.login_button_login:
                if (fastClick()) {
                    if (!alerady) {
                        if (TextUtils.isEmpty(login_edit_phone.getText().toString())) {
                            ToatUtils.showShort1(this, getString(R.string.loan_phone_error_empty));
                        } else if (!AppUtil.getInstance().isChinaPhoneLegal(login_edit_phone.getText().toString())) {
                            ToatUtils.showShort1(this, getString(R.string.loan_phone_error_no_correct));
                        } else if (TextUtils.isEmpty(login_edit_password.getText().toString())) {
                            ToatUtils.showShort1(this, getString(R.string.loan_password_error_empty));
                        }
                        if (text_login) {//注册
                            if (TextUtils.isEmpty(login_edit_code.getText().toString())) {
                                ToatUtils.showShort1(this, getString(R.string.loan_code_error_empty));
                            } else {
                                HttpRequest.getInstance().setPublic(this,
                                        AggregateMap.getInstance().setRegister(
                                                login_edit_phone.getText().toString(),
                                                login_edit_password.getText().toString(),
                                                login_edit_code.getText().toString()),
                                        mHandler, HttpImplements.getInstance().getHttp(this, "register"), "register");
                            }
                        } else { // 登录
                            HttpRequest.getInstance().setPublic(this,
                                    AggregateMap.getInstance().setLogin(
                                            login_edit_phone.getText().toString(),
                                            login_edit_password.getText().toString()),
                                    mHandler, HttpImplements.getInstance().getHttp(this, "login"), "login");
                        }
                    } else {
                        ToatUtils.showShort1(this, "请先同意用户服务条款");
                    }
                }
                break;
            case R.id.login_text_login1:
                if (fastClick()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("bundle", 2);
                    startActivity(NoPasswordActivity.class, bundle);
                }
                break;
            case R.id.login_button_code:
                if (fastClick()) {
                    if (TextUtils.isEmpty(login_edit_phone.getText().toString())) {
                        ToatUtils.showShort1(this, getString(R.string.loan_phone_error_empty));
                    } else if (!AppUtil.getInstance().isChinaPhoneLegal(login_edit_phone.getText().toString())) {
                        ToatUtils.showShort1(this, getString(R.string.loan_phone_error_no_correct));
                    } else {
                        buttonDownTimerUtils = new ButtonDownTimerUtils(login_button_code, 60 * 1000, 1000);
                        buttonDownTimerUtils.start();
                        HttpRequest.getInstance().setPublic(this,
                                AggregateMap.getInstance().setDynamicCode(login_edit_phone.getText().toString()), mHandler,
                                HttpImplements.getInstance().getHttp(this, "dynamicCode"), "dynamicCode");
                    }
                }
                break;
            case R.id.logo_phone_editText_error1:
                login_edit_phone.setText("");
                break;
            case R.id.logo_phone_editText_error2:
                login_edit_password.setText("");
                break;
            case R.id.logo_phone_editText_error3:
                login_edit_code.setText("");
                break;
            case R.id.title_search_image2:
                UtilsXML.getInstance().closeKeyboard(LogoIngActivity.this);
                finish();
                break;
            case R.id.alerady_image_linear:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", "http://www.51jinyinhua.com/xieyi/index.html");
                startActivity(intent);
                break;
            case R.id.alerady_image:
                if (alerady) {
                    alerady_image.setImageResource(R.drawable.ic_selected);
                    alerady = false;
                } else {
                    alerady_image.setImageResource(R.drawable.ic_selected_prospect);
                    alerady = true;
                }
                break;
        }
    }

    private void getText_login() {
        if (text_login) {
            title.setText(getString(R.string.account_password_login));
            login_text_login.setText(getString(R.string.account_password_code_registration));
            login_button_code.setVisibility(View.GONE);
            login_linear.setVisibility(View.GONE);
            alerady_image_linear.setVisibility(View.GONE);
            login_button_login.setText(getString(R.string.login));
            text_login = false;
        } else {
            login_button_code.setVisibility(View.VISIBLE);
            login_linear.setVisibility(View.VISIBLE);
            alerady_image_linear.setVisibility(View.VISIBLE);
            title.setText(getString(R.string.account_password_code_registration));
            login_text_login.setText(getString(R.string.account_password_login));
            login_button_login.setText(getString(R.string.registration));
            text_login = true;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 205:

                    break;
                case 206:

                    break;
                case 1004:
                    userNo = (String) msg.obj;
                    SharedPreferencesUtils.put(LogoIngActivity.this, "phone", login_edit_phone.getText().toString());
                    SharedPreferencesUtils.put(LogoIngActivity.this, "userNo", userNo);
                    UtilsXML.getInstance().closeKeyboard(LogoIngActivity.this);
                    finish();
                    break;
                case 1005:
                    ToatUtils.showShort1(LogoIngActivity.this, getString(R.string.phone_sms));
                    break;
                case 1007:
                    String userNos = (String) msg.obj;
                    String[] users = userNos.split(",");
                    userNo = users[0];
                    SharedPreferencesUtils.put(LogoIngActivity.this, "phone", login_edit_phone.getText().toString());
                    SharedPreferencesUtils.put(LogoIngActivity.this, "userNo", userNo);
                    SharedPreferencesUtils.put(LogoIngActivity.this, "user_gubi", users[1]);
                    UtilsXML.getInstance().closeKeyboard(LogoIngActivity.this);
                    finish();
                    break;
            }
        }
    };

    @Override
    public void initData() {
        title_relat.setAlpha(0.7f);
        title.setText(getString(R.string.account_password_login));
        login_text_login.setText(getString(R.string.account_password_code_registration));
        login_text_login.setOnClickListener(this);
        login_button_login.setOnClickListener(this);
        login_text_login1.setOnClickListener(this);
        logo_phone_editText_error1.setOnClickListener(this);
        logo_phone_editText_error2.setOnClickListener(this);
        logo_phone_editText_error3.setOnClickListener(this);
        login_button_code.setOnClickListener(this);

        alerady_image_linear.setOnClickListener(this);
        alerady_image.setOnClickListener(this);

        findView(R.id.title_search_image2).setOnClickListener(this);
        findView(R.id.title_search_image2).setVisibility(View.VISIBLE);

        login_edit_phone.addTextChangedListener(UtilsInterface.getInstance().setTextWatcher(logo_phone_editText_error1));
        login_edit_password.addTextChangedListener(UtilsInterface.getInstance().setTextWatcher(logo_phone_editText_error2));
        login_edit_code.addTextChangedListener(UtilsInterface.getInstance().setTextWatcher(logo_phone_editText_error3));
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
//        container = findView(R.id.container);

        title = findView(R.id.title);
        login_linear = findView(R.id.login_linear);
        login_edit_phone = findView(R.id.login_edit_phone);
        login_edit_password = findView(R.id.login_edit_password);
        login_edit_code = findView(R.id.login_edit_code);
        login_button_code = findView(R.id.login_button_code);
        login_button_login = findView(R.id.login_button_login);
        login_text_login = findView(R.id.login_text_login);
        logo_phone_editText_error1 = findView(R.id.logo_phone_editText_error1);
        logo_phone_editText_error2 = findView(R.id.logo_phone_editText_error2);
        logo_phone_editText_error3 = findView(R.id.logo_phone_editText_error3);
        login_text_login1 = findView(R.id.login_text_login1);
        alerady_image_linear = findView(R.id.alerady_image_linear);
        alerady_image = findView(R.id.alerady_image);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.act_logo;
    }
}
