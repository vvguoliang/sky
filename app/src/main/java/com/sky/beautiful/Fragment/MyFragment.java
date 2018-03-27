package com.sky.beautiful.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.beautiful.Activity.CouponActivity;
import com.sky.beautiful.Activity.LogoIngActivity;
import com.sky.beautiful.Activity.MySetUPActivity;
import com.sky.beautiful.Activity.SetUpActivity;
import com.sky.beautiful.Activity.WebViewActivity;
import com.sky.beautiful.Model.MyUserMsg;
import com.sky.beautiful.OKHttp.AggregateMap;
import com.sky.beautiful.OKHttp.HttpImplements;
import com.sky.beautiful.OKHttp.HttpRequest;
import com.sky.beautiful.R;
import com.sky.beautiful.SykApplication;
import com.sky.beautiful.Utils.AppUtil;
import com.sky.beautiful.Utils.MarketTools;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.Utils.TakePictureManager;
import com.sky.beautiful.Utils.ToatUtils;
import com.sky.beautiful.Utils.UtilsInterface;
import com.sky.beautiful.View.CircleImageView;
import com.sky.beautiful.View.Dialog.BottomDialog;

import java.io.File;
import java.util.List;

/**
 * Created by vvguoliang on 2017/12/19.
 * <p>
 * 我的
 */

@SuppressWarnings("ConstantConditions")
@SuppressLint("SetTextI18n")
public class MyFragment extends Fragment implements View.OnClickListener {

    protected Context mActivity;

    private CircleImageView my_circle_image;

    private TextView title, my_v_name, my_v_melon, my_text_integral, my_text_coupon, my_text_melon;

    private View view;

    private TakePictureManager takePictureManager;

    private LinearLayout title_relat, my_v_linear;

    private String userNo = "";

    private ImageView my_v_image, my_activity_image;

    private MyUserMsg myUserMsg;

    private Intent intent;

    private boolean isVisibleToUser = false;

    private boolean isNoex = false;

    /**
     * 获得全局的，防止使用getActivity()为空
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        view = LayoutInflater.from(mActivity).inflate(R.layout.far_my, container, false);
        findViewById();
        getData();
        return view;
    }

    private void getData() {
        my_circle_image.setOnClickListener(this);
        title.setText(getString(R.string.title_my));
        title_relat.setBackgroundColor(Color.TRANSPARENT);
        view.findViewById(R.id.my_relative).setOnClickListener(this);
        view.findViewById(R.id.my_coupon_linear).setOnClickListener(this);
        view.findViewById(R.id.my_set_up).setOnClickListener(this);
        view.findViewById(R.id.my_coupon_membership_rights).setOnClickListener(this);
        view.findViewById(R.id.my_coupon_collection).setOnClickListener(this);
        view.findViewById(R.id.my_coupon_purchase).setOnClickListener(this);
        view.findViewById(R.id.my_coupon_praise).setOnClickListener(this);
        view.findViewById(R.id.my_coupon_linear1).setOnClickListener(this);
        view.findViewById(R.id.my_coupon_linear2).setOnClickListener(this);
    }

    private void findViewById() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.findViewById(R.id.tab).setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            } else {
                view.findViewById(R.id.tab).setBackgroundColor(Color.rgb(153, 153, 153));
            }
        } else {
            view.findViewById(R.id.tab).setVisibility(View.GONE);
        }
        my_circle_image = view.findViewById(R.id.my_circle_image);
        title = view.findViewById(R.id.title);
        my_v_linear = view.findViewById(R.id.my_v_linear);
        title_relat = view.findViewById(R.id.title_relat);
        my_v_image = view.findViewById(R.id.my_v_image);
        my_v_name = view.findViewById(R.id.my_v_name);
        my_v_melon = view.findViewById(R.id.my_v_melon);
        my_text_integral = view.findViewById(R.id.my_text_integral);
        my_text_coupon = view.findViewById(R.id.my_text_coupon);
        my_text_melon = view.findViewById(R.id.my_text_melon);
        my_activity_image = view.findViewById(R.id.my_activity_image);
    }


    @Override
    public void onClick(View v) {
        if (isNoex) {
            ToatUtils.showShort1(mActivity, "亲!查看一下网络");
        } else if (TextUtils.isEmpty(userNo)) {
            mActivity.startActivity(new Intent(mActivity, LogoIngActivity.class));
        } else {
            switch (v.getId()) {
                case R.id.my_circle_image:
                    showDialogBottom();
                    break;
                case R.id.my_relative:
                    intent = new Intent(mActivity, MySetUPActivity.class);
                    intent.putExtra("name", myUserMsg.getUserName());
                    intent.putExtra("sex", myUserMsg.getSex());
                    intent.putExtra("age", myUserMsg.getAge());
                    intent.putExtra("HeadUrl", myUserMsg.getHeadUrl());
                    mActivity.startActivity(intent);
                    break;
                case R.id.my_coupon_linear:
                    mActivity.startActivity(new Intent(mActivity, CouponActivity.class));
                    break;
                case R.id.my_set_up:
                    mActivity.startActivity(new Intent(mActivity, SetUpActivity.class));
                    break;
                case R.id.my_coupon_membership_rights:
                    if (myUserMsg != null && !TextUtils.isEmpty(myUserMsg.getMemberUrl())) {
                        intent = new Intent(mActivity, WebViewActivity.class);
                        intent.putExtra("url", myUserMsg.getMemberUrl());
                        startActivity(intent);
                    } else {
                        ToatUtils.showShort1(mActivity, getString(R.string.coming_soon));
                    }
                    break;
                case R.id.my_coupon_collection:
                    ToatUtils.showShort1(mActivity, getString(R.string.coming_soon));
                    break;
                case R.id.my_coupon_purchase:
                    ToatUtils.showShort1(mActivity, getString(R.string.coming_soon));
                    break;
                case R.id.my_coupon_praise:
                    MarketTools.getInstance().setGoToMarket(mActivity, MarketTools.getInstance().URL);
                    break;
                case R.id.my_coupon_linear1:
                    Intent intent = new Intent(mActivity, WebViewActivity.class);
                    intent.putExtra("url", HttpImplements.getInstance().Center + "userNo=" +
                            SharedPreferencesUtils.get(mActivity, "userNo", "").toString() + "&version=" +
                            AppUtil.getInstance().getVersionName(1, mActivity) + "&currentBalance=" +
                            SharedPreferencesUtils.get(mActivity, "user_gubi", "0").toString());
                    startActivity(intent);
                    break;
                case R.id.my_coupon_linear2:
                    ToatUtils.showShort1(mActivity, getString(R.string.coming_soon));
                    break;
            }
        }
    }


    // 提示对话框方法
    private void showDialogBottom() {
        final BottomDialog bottomDialog = new BottomDialog(mActivity, R.layout.dialog_buttom, R.style.customDialog);
        bottomDialog.getWindow().setWindowAnimations(R.style.AnimBottom);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);

        SharedPreferencesUtils.put(mActivity, "Bottom", false);

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
                    UtilsInterface.getInstance().UtilGlideAngle(mActivity, outFile, my_circle_image);
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
                    UtilsInterface.getInstance().UtilGlideAngle(mActivity, outFile, my_circle_image);
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
        HttpRequest.getInstance().setPublic(mActivity, AggregateMap.getInstance().setUploadUserHead(SharedPreferencesUtils.get(mActivity, "userNo", "").toString(),
                AppUtil.getInstance().encodeBase64File(outFile)), mHandler, HttpImplements.getInstance().getHttp(mActivity, "uploadUserHead"), "uploadUserHead");
    }

    @Override
    public void onResume() {
        super.onResume();
        userNo = SharedPreferencesUtils.get(mActivity, "userNo", "").toString();
        if (TextUtils.isEmpty(userNo)) {
            my_v_linear.setVisibility(View.GONE);
            my_v_image.setVisibility(View.GONE);
            my_v_name.setText(getString(R.string.login));
            my_v_melon.setText(getString(R.string.registration));
        } else {
            my_v_linear.setVisibility(View.VISIBLE);
            my_v_image.setVisibility(View.VISIBLE);
        }
        if (isVisibleToUser) {
            myUserMsg();
        } else {
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mActivity == null) {
            mActivity = SykApplication.getContextObject();
        }
        if (isVisibleToUser) {
            myUserMsg();
        }
        this.isVisibleToUser = isVisibleToUser;
    }


    private void myUserMsg() {
        HttpRequest.getInstance().setPublic(mActivity,
                AggregateMap.getInstance().setMyUserMsg(SharedPreferencesUtils.get(mActivity, "userNo", "").toString()),
                mHandler, HttpImplements.getInstance().getHttp(mActivity, "myUserMsg"), "myUserMsg");
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 205:
                    if (!msg.obj.toString().equals("用户未登录")) {
                        isNoex = true;
                    }
                    my_v_linear.setVisibility(View.GONE);
                    my_v_image.setVisibility(View.GONE);
                    my_v_name.setText(getString(R.string.login));
                    my_v_melon.setText(getString(R.string.registration));
                    my_circle_image.setImageResource(R.mipmap.ic_no_touxiang);
                    break;
                case 206:
                    isNoex = true;
                    my_v_linear.setVisibility(View.GONE);
                    my_v_image.setVisibility(View.GONE);
                    my_v_name.setText(getString(R.string.login));
                    my_v_melon.setText(getString(R.string.registration));
                    my_circle_image.setImageResource(R.mipmap.ic_no_touxiang);
                    break;
                case 1008:
                    isNoex = false;
                    myUserMsg = (MyUserMsg) msg.obj;
                    SharedPreferencesUtils.put(mActivity, "userNo", myUserMsg.getUserNo());
                    my_v_melon.setText("瓜号：" + myUserMsg.getUserNo());
                    if (TextUtils.isEmpty(myUserMsg.getGradeNo()) || myUserMsg.getGradeNo().equals("1")) {
                        my_v_image.setImageResource(R.mipmap.ic_user_v0);
                    } else if (myUserMsg.getGradeNo().equals("2")) {
                        my_v_image.setImageResource(R.mipmap.ic_user_v1);
                    } else if (myUserMsg.getGradeNo().equals("3")) {
                        my_v_image.setImageResource(R.mipmap.ic_user_v2);
                    }
                    SharedPreferencesUtils.put(mActivity, "GradeNo", myUserMsg.getGradeNo());
                    if (TextUtils.isEmpty(myUserMsg.getUserName())) {
                        my_v_name.setText(SharedPreferencesUtils.get(mActivity, "phone", "").toString());
                    } else {
                        SharedPreferencesUtils.put(mActivity, "name", myUserMsg.getUserName());
                        my_v_name.setText(myUserMsg.getUserName());
                    }
                    if (TextUtils.isEmpty(myUserMsg.getHeadUrl())) {
                        my_circle_image.setImageResource(R.mipmap.ic_no_touxiang);
                    } else {
                        UtilsInterface.getInstance().UtilGlide(mActivity, myUserMsg.getHeadUrl(), my_circle_image);
                    }
                    SharedPreferencesUtils.put(mActivity, "HeadUrl", myUserMsg.getHeadUrl());
                    if (TextUtils.isEmpty(myUserMsg.getScore())) {
                        my_text_integral.setText("0");
                    } else {
                        my_text_integral.setText(myUserMsg.getScore());
                    }
                    if (TextUtils.isEmpty(myUserMsg.getCoupon())) {
                        my_text_coupon.setText("0");
                    } else {
                        my_text_coupon.setText(myUserMsg.getCoupon());
                    }
                    if (TextUtils.isEmpty(myUserMsg.getMelonMoney())) {
                        my_text_melon.setText("0");
                    } else {
                        my_text_melon.setText(myUserMsg.getMelonMoney());
                    }
                    SharedPreferencesUtils.put(mActivity, "user_gubi", myUserMsg.getMelonMoney());
                    if (TextUtils.isEmpty(myUserMsg.getActivityUrl())) {
                        my_activity_image.setImageResource(R.drawable.screen_default);
                    } else {
                        UtilsInterface.getInstance().UtilGlideAngle(mActivity, myUserMsg.getActivityUrl(), my_activity_image);
                    }
                    my_v_linear.setVisibility(View.VISIBLE);
                    break;
                case 1013:
                    isNoex = false;
                    SharedPreferencesUtils.put(mActivity, "Bottom", true);
                    break;

            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        takePictureManager.attachToActivityForResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        takePictureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
