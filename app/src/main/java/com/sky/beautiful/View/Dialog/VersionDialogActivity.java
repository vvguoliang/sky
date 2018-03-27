package com.sky.beautiful.View.Dialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.sky.beautiful.BaseActivity.BaseActivity;
import com.sky.beautiful.BaseActivityManager;
import com.sky.beautiful.R;
import com.sky.beautiful.Service.UpdataService;
import com.sky.beautiful.Utils.AppUtil;
import com.sky.beautiful.Utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Time : 2018/1/5 no 上午11:05
 * @USER : vvguoliang
 * @File : VersionDialogActivity.java
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

public class VersionDialogActivity extends BaseActivity implements View.OnClickListener {

    private TextView version_conteont, version_clear, version_button, version_conteont1;

    private int GradeNo, type = 0;

    private String msg, updateURL, updateContent, newVersion, code;

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        GradeNo = getIntent().getIntExtra("GradeNo", 0);
        msg = getIntent().getStringExtra("msg");
        code = getIntent().getStringExtra("code");

        if (TextUtils.isEmpty(msg)) {
            version_conteont.setText("尊敬的v" + GradeNo + "会员,诚邀您参加与香瓜社区v" + AppUtil.getInstance().getChannel(this, 0) + "版本的优先体验");
        } else {
            try {
                JSONObject object = new JSONObject(msg);
                updateURL = object.optString("updateURL");
                updateContent = object.optString("updateContent");
                newVersion = object.optString("newVersion");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        switch (code) {
            case "202":
                type = 1;
                version_clear.setVisibility(View.GONE);
                version_button.setVisibility(View.VISIBLE);
                version_conteont1.setVisibility(View.GONE);
                version_conteont.setVisibility(View.VISIBLE);
                version_button.setText("公告通知");
                break;
            case "203":
                type = 2;
                version_clear.setVisibility(View.GONE);
                version_button.setVisibility(View.VISIBLE);
                version_conteont.setVisibility(View.VISIBLE);
                version_button.setText("立即下载");
                break;
            case "204":
                type = 3;
                version_clear.setVisibility(View.VISIBLE);
                version_button.setVisibility(View.VISIBLE);
                version_conteont.setVisibility(View.VISIBLE);
                version_button.setText("立即下载");
                break;

        }
        version_clear.setOnClickListener(this);
        version_button.setOnClickListener(this);
        version_conteont.setText(updateContent);
        version_conteont1.setText("更新版本v" + newVersion);

        version_clear.setText("稍后再说");
    }

    @Override
    public void initView() {
        setFinishOnTouchOutside(false);
        version_conteont = findView(R.id.version_conteont);
        version_clear = findView(R.id.version_clear);
        version_button = findView(R.id.version_button);
        version_conteont1 = findView(R.id.version_conteont1);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_version_update_view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.version_clear:
                SharedPreferencesUtils.put(this, "version_type", "0");
                SharedPreferencesUtils.put(this, "startup_type", true);
                SharedPreferencesUtils.put(this, "updata", true);
                finish();
                break;
            case R.id.version_button:
                switch (type) {
                    case 1:
                        BaseActivityManager.getActivityManager().finishAll();
                        break;
                    case 2:
                        SharedPreferencesUtils.put(this, "version_type", "0");
                        Intent intent = new Intent(this, UpdataService.class);
                        intent.putExtra("url", updateURL);
                        startService(intent);
                        BaseActivityManager.getActivityManager().finishAll();
                        break;
                    case 3:
                        SharedPreferencesUtils.put(this, "version_type", "0");
                        SharedPreferencesUtils.put(this, "startup_type", true);
                        intent = new Intent(this, UpdataService.class);
                        intent.putExtra("url", updateURL);
                        startService(intent);
                        finish();
                        break;
                }
                break;
        }
    }
}
