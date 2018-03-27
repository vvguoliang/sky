package com.sky.beautiful.Activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
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
import com.sky.beautiful.Utils.ToatUtils;
import com.sky.beautiful.Utils.UtilsXML;

/**
 * @Time : 2017/12/27 no 下午5:59
 * @USER : vvguoliang
 * @File : NoPasswordActivity.java
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

public class NoPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText login_edit_phone, login_edit_code, login_edit_password, login_edit_password1;

    private ImageView logo_phone_editText_error1, logo_phone_editText_error3, logo_phone_editText_error2, logo_phone_editText_error4, title_search_image2;

    private Button login_button_code, login_button_login;

    private ButtonDownTimerUtils buttonDownTimerUtils;

    private LinearLayout title_relat, login_linear_code;

    private boolean code_boo = false;

    private int bundleint = 0;

    private TextView title;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logo_phone_editText_error1:
                login_edit_phone.setText("");
                break;
            case R.id.logo_phone_editText_error2:
                login_edit_code.setText("");
                break;
            case R.id.logo_phone_editText_error3:
                login_edit_password.setText("");
                break;
            case R.id.logo_phone_editText_error4:
                login_edit_password1.setText("");
                break;
            case R.id.login_button_code:
                if (TextUtils.isEmpty(login_edit_phone.getText().toString())) {
                    ToatUtils.showShort1(this, getString(R.string.loan_phone_error_empty));
                } else if (!AppUtil.getInstance().isChinaPhoneLegal(login_edit_phone.getText().toString())) {
                    ToatUtils.showShort1(this, getString(R.string.loan_phone_error_no_correct));
                } else {
                    buttonDownTimerUtils = new ButtonDownTimerUtils(login_button_code, 60 * 1000, 1000);
                    buttonDownTimerUtils.start();
                    code_boo = true;
                    HttpRequest.getInstance().setPublic(this,
                            AggregateMap.getInstance().setDynamicCode(login_edit_phone.getText().toString()), mHandler,
                            HttpImplements.getInstance().getHttp(this, "dynamicCode"), "dynamicCode");
                }
                break;
            case R.id.login_button_login:
                if (TextUtils.isEmpty(login_edit_phone.getText().toString())) {
                    ToatUtils.showShort1(this, getString(R.string.loan_phone_error_empty));
                } else if (!AppUtil.getInstance().isChinaPhoneLegal(login_edit_phone.getText().toString())) {
                    ToatUtils.showShort1(this, getString(R.string.loan_phone_error_no_correct));
                } else {
                    if (bundleint == 1) {
                        if (TextUtils.isEmpty(login_edit_password.getText().toString())) {
                            ToatUtils.showShort1(this, getString(R.string.loan_password_error_empty3));
                            return;
                        } else if (TextUtils.isEmpty(login_edit_password1.getText().toString())) {
                            ToatUtils.showShort1(this, getString(R.string.loan_password_error_empty4));
                            return;
                        }
                    } else {
                        if (TextUtils.isEmpty(login_edit_code.getText().toString())) {
                            ToatUtils.showShort1(this, getString(R.string.loan_code_error_empty));
                            return;
                        } else if (TextUtils.isEmpty(login_edit_password.getText().toString())) {
                            ToatUtils.showShort1(this, getString(R.string.loan_password_error_empty));
                            return;
                        } else if (TextUtils.isEmpty(login_edit_password1.getText().toString())) {
                            ToatUtils.showShort1(this, getString(R.string.loan_password_error_empty1));
                            return;
                        } else if (!login_edit_password.getText().toString().equals(login_edit_password1.getText().toString())) {
                            ToatUtils.showShort1(this, getString(R.string.loan_password_error_empty2));
                            return;
                        }
                    }
                    HttpRequest.getInstance().setPublic(this,
                            AggregateMap.getInstance().setModifyPassword(
                                    login_edit_phone.getText().toString(),
                                    login_edit_password1.getText().toString(),
                                    login_edit_password.getText().toString(), login_edit_code.getText().toString(), bundleint),
                            mHandler, HttpImplements.getInstance().getHttp(this, "modifyPassword"), "modifyPassword");
                }
                break;
            case R.id.title_search_image2:
                UtilsXML.getInstance().closeKeyboard(NoPasswordActivity.this);
                finish();
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 205:
                    if (code_boo) {
                        buttonDownTimerUtils.onFinish();
                    }
                    break;
                case 206:
                    if (code_boo) {
                        buttonDownTimerUtils.onFinish();
                    }
                    break;
                case 1005:
                    ToatUtils.showShort1(NoPasswordActivity.this, getString(R.string.phone_sms));
                    break;
                case 1011:
                    UtilsXML.getInstance().closeKeyboard(NoPasswordActivity.this);
                    finish();
                    break;
            }
        }
    };

    @Override
    public void initData() {
        if (bundleint == 1) {
            title.setText(getString(R.string.set_up_password));
            login_edit_password.setHint(R.string.hint_password_eidttext2);
            login_edit_password1.setHint(R.string.hint_password_eidttext3);
            login_linear_code.setVisibility(View.GONE);
            login_button_code.setVisibility(View.GONE);
        } else {
            title.setText(getString(R.string.account_no_password));
            login_button_code.setVisibility(View.VISIBLE);
        }
        logo_phone_editText_error1.setOnClickListener(this);
        logo_phone_editText_error2.setOnClickListener(this);
        logo_phone_editText_error3.setOnClickListener(this);
        logo_phone_editText_error4.setOnClickListener(this);
        login_button_code.setOnClickListener(this);
        login_button_login.setOnClickListener(this);
        title_search_image2.setOnClickListener(this);
        title_search_image2.setVisibility(View.VISIBLE);
        title_relat.setAlpha(0.7f);
    }

    @Override
    public void initView() {
        bundleint = getIntent().getIntExtra("bundle", 2);
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
        title = findView(R.id.title);
        login_edit_phone = findView(R.id.login_edit_phone);
        login_edit_code = findView(R.id.login_edit_code);
        login_edit_password = findView(R.id.login_edit_password);
        login_edit_password1 = findView(R.id.login_edit_password1);
        logo_phone_editText_error1 = findView(R.id.logo_phone_editText_error1);
        logo_phone_editText_error3 = findView(R.id.logo_phone_editText_error3);
        logo_phone_editText_error2 = findView(R.id.logo_phone_editText_error2);
        logo_phone_editText_error4 = findView(R.id.logo_phone_editText_error4);
        login_button_code = findView(R.id.login_button_code);
        login_button_login = findView(R.id.login_button_login);
        title_search_image2 = findView(R.id.title_search_image2);
        title_relat = findView(R.id.title_relat);
        login_linear_code = findView(R.id.login_linear_code);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.act_no_password;
    }
}
