package com.sky.beautiful.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.share.sdk.openapi.APAPIFactory;
import com.alipay.share.sdk.openapi.APMediaMessage;
import com.alipay.share.sdk.openapi.APWebPageObject;
import com.alipay.share.sdk.openapi.SendMessageToZFB;
import com.sky.beautiful.BaseActivity.BaseActivity;
import com.sky.beautiful.ImmersionBar.ImmersionBar;
import com.sky.beautiful.OKHttp.AggregateMap;
import com.sky.beautiful.OKHttp.HttpImplements;
import com.sky.beautiful.OKHttp.HttpRequest;
import com.sky.beautiful.R;
import com.sky.beautiful.Share.ShareWxapTencent;
import com.sky.beautiful.Utils.AppUtil;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.Utils.TakePictureManager;
import com.sky.beautiful.Utils.ToatUtils;
import com.sky.beautiful.Utils.UtilsInterface;
import com.sky.beautiful.View.CircleImageView;
import com.sky.beautiful.View.Dialog.BottomDialog;

import java.io.File;
import java.util.List;

/**
 * @Time : 2017/12/29 no 下午12:50
 * @USER : vvguoliang
 * @File : MySetUPActivity.java
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

@SuppressWarnings("ConstantConditions")
public class MySetUPActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_relat;

    private TextView title, my_set_up_text;

    private CircleImageView my_circle_image;

    private EditText my_set_up_edite, my_set_up_age_edite;

    private Button my_set_up_button;

    private ImageView my_set_up_text_image;

    private String name, sex, age, HeadUrl;

    private TakePictureManager takePictureManager;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search_image2:
                finish();
                break;
            case R.id.my_set_up_text:
                showDialogBottom();
                break;
            case R.id.my_set_up_button:
                myUserMsg();
                break;
            case R.id.my_circle_image:
                showDialogBottom1();
                break;
        }
    }

    // 提示对话框方法
    private void showDialogBottom() {
        final BottomDialog bottomDialog = new BottomDialog(this, R.layout.dialog_buttom, R.style.customDialog);
        bottomDialog.getWindow().setWindowAnimations(R.style.AnimBottom);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);

        Window window = bottomDialog.getWindow();
        window.setGravity(Gravity.BOTTOM); //可设置dialog的位置
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        Button buttom_camera = (Button) bottomDialog.findViewById(R.id.buttom_camera);
        Button buttom_album = (Button) bottomDialog.findViewById(R.id.buttom_album);
        Button buttom_clean = (Button) bottomDialog.findViewById(R.id.buttom_clean);
        ImageView ic_sex_man = (ImageView) bottomDialog.findViewById(R.id.ic_sex_man);
        ImageView ic_sex_woman = (ImageView) bottomDialog.findViewById(R.id.ic_sex_woman);
        ic_sex_man.setVisibility(View.VISIBLE);
        ic_sex_woman.setVisibility(View.VISIBLE);
        buttom_camera.setText("男");
        buttom_camera.setOnClickListener(v -> {
            my_set_up_text.setText("男");
            my_set_up_text_image.setImageResource(R.mipmap.ic_sex_man);
            bottomDialog.dismiss();
        });
        buttom_album.setText("女");
        buttom_album.setOnClickListener(v -> {
            my_set_up_text.setText("女");
            my_set_up_text_image.setImageResource(R.mipmap.ic_sex_woman);
            bottomDialog.dismiss();
        });
        buttom_clean.setText(getString(R.string.clean));
        buttom_clean.setOnClickListener(v -> bottomDialog.dismiss());
        bottomDialog.show();
    }

    private void myUserMsg() {
        HttpRequest.getInstance().setPublic(this,
                AggregateMap.getInstance().setUserMsgInput(my_set_up_edite.getText().toString(), my_set_up_text.getText().toString()
                        , Long.parseLong(my_set_up_age_edite.getText().toString())
                        , SharedPreferencesUtils.get(this, "userNo", "").toString()),
                mHandler, HttpImplements.getInstance().getHttp(this, "userMsgInput"), "userMsgInput");
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
                case 1010:
                    finish();
                    break;
                case 1013:
                    SharedPreferencesUtils.put(MySetUPActivity.this, "Bottom", true);
                    break;
            }
        }
    };


    @Override
    public void initData() {
        title_relat.setAlpha(0.7f);
        title.setText(getText(R.string.title_my));
        findView(R.id.title_search_image2).setVisibility(View.VISIBLE);
        findView(R.id.title_search_image2).setOnClickListener(this);
        my_set_up_text.setOnClickListener(this);
        my_set_up_button.setOnClickListener(this);
        my_circle_image.setOnClickListener(this);
        my_set_up_text_image.setImageResource(R.mipmap.ic_sex_man);
        my_set_up_edite.setText(name);
        my_set_up_text.setText(sex);
        my_set_up_age_edite.setText(age);
        if (!TextUtils.isEmpty(sex)) {
            if (sex.equals("男")) {
                my_set_up_text_image.setImageResource(R.mipmap.ic_sex_man);
            } else {
                my_set_up_text_image.setImageResource(R.mipmap.ic_sex_woman);
            }
        }
        if (TextUtils.isEmpty(HeadUrl)) {
            my_circle_image.setImageResource(R.mipmap.ic_no_touxiang);
        } else {
            UtilsInterface.getInstance().UtilGlideAngle(this, HeadUrl, my_circle_image);
        }
    }

    @Override
    public void initView() {
        name = getIntent().getStringExtra("name");
        sex = getIntent().getStringExtra("sex");
        age = getIntent().getStringExtra("age");
        HeadUrl = getIntent().getStringExtra("HeadUrl");
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
        title = findView(R.id.title);
        my_set_up_text = findView(R.id.my_set_up_text);
        my_circle_image = findView(R.id.my_circle_image);
        my_set_up_edite = findView(R.id.my_set_up_edite);
        my_set_up_button = findView(R.id.my_set_up_button);
        my_set_up_text_image = findView(R.id.my_set_up_text_image);
        my_set_up_age_edite = findView(R.id.my_set_up_age_edite);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.act_my_set_up;
    }

    // 提示对话框方法
    private void showDialogBottom1() {
        final BottomDialog bottomDialog = new BottomDialog(this, R.layout.dialog_buttom, R.style.customDialog);
        bottomDialog.getWindow().setWindowAnimations(R.style.AnimBottom);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);

        SharedPreferencesUtils.put(this, "Bottom", false);

        Window window = bottomDialog.getWindow();
        window.setGravity(Gravity.BOTTOM); //可设置dialog的位置
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        Button buttom_camera = (Button) bottomDialog.findViewById(R.id.buttom_camera);
        Button buttom_album = (Button) bottomDialog.findViewById(R.id.buttom_album);
        Button buttom_clean = (Button) bottomDialog.findViewById(R.id.buttom_clean);
        buttom_camera.setText(getString(R.string.camera));
        buttom_camera.setOnClickListener(v -> {
            takePictureManager = new TakePictureManager(this);
            takePictureManager.setTailor(1, 1, 350, 350);
            takePictureManager.startTakeWayByCarema();
            takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
                @Override
                public void successful(boolean isTailor, File outFile, Uri filePath) {
                    UtilsInterface.getInstance().UtilGlideAngle(MySetUPActivity.this, outFile, my_circle_image);
                    UploadUserHead(outFile);
                }

                @Override
                public void failed(int errorCode, List<String> deniedPermissions) {

                }
            });
            bottomDialog.dismiss();
        });
        buttom_album.setText(getString(R.string.album));
        buttom_album.setOnClickListener(v -> {
            takePictureManager = new TakePictureManager(this);
            takePictureManager.setTailor(1, 1, 350, 350);
            takePictureManager.startTakeWayByAlbum();
            takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
                @Override
                public void successful(boolean isTailor, File outFile, Uri filePath) {
                    UtilsInterface.getInstance().UtilGlideAngle(MySetUPActivity.this, outFile, my_circle_image);
                    UploadUserHead(outFile);
                }

                @Override
                public void failed(int errorCode, List<String> deniedPermissions) {

                }

            });
            bottomDialog.dismiss();
        });
        buttom_clean.setText(getString(R.string.clean));
        buttom_clean.setOnClickListener(v -> bottomDialog.dismiss());
        bottomDialog.show();
    }

    private void UploadUserHead(File outFile) {
        HttpRequest.getInstance().setPublic(this, AggregateMap.getInstance().setUploadUserHead(SharedPreferencesUtils.get(this, "userNo", "").toString(),
                AppUtil.getInstance().encodeBase64File(outFile)), mHandler, HttpImplements.getInstance().getHttp(this, "uploadUserHead"), "uploadUserHead");
    }

    //把本地的onActivityResult()方法回调绑定到对象
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
    }

    //onRequestPermissionsResult()方法权限回调绑定到对象
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        takePictureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
