package com.sky.beautiful.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sky.beautiful.BaseActivity.BaseActivity;
import com.sky.beautiful.GaussianBlur.GlideBlurformation;
import com.sky.beautiful.ImmersionBar.BarHide;
import com.sky.beautiful.Model.ViewAtlas;
import com.sky.beautiful.OKHttp.AggregateMap;
import com.sky.beautiful.OKHttp.HttpImplements;
import com.sky.beautiful.OKHttp.HttpRequest;
import com.sky.beautiful.R;
import com.sky.beautiful.Utils.AppUtil;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.Utils.ToatUtils;
import com.sky.beautiful.Utils.UtilsInterface;
import com.sky.beautiful.View.CarouselViewFlipper;
import com.sky.beautiful.View.Dialog.SXSDialog;
import com.sky.beautiful.View.MyZoomImageView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @Time : 2017/12/22 no 下午1:35
 * @USER : vvguoliang
 * @File : ContentActivity.java
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
@SuppressLint("SetTextI18n")
public class ContentActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout title_relat, confirm_delete_imageview;

    private LinearLayout content_liner;

    private boolean bool_image = false;

    private TextView title_tab_tab_text, content_liner1_text, content_liner2_text;

    private String atlasNo = "";

    private ArrayList<String> images;

    private boolean content_bool1 = false;

    private boolean content_bool2 = false;

    private int islike = 0;

    private int setIsCollect = 0;

    private List<ViewAtlas> list1 = new ArrayList<>();

    private int position = 0;

    private int page = 0;

    private List<String> islisk = new ArrayList<>();

    private List<String> isCollect = new ArrayList<>();

    private int px = 0;

    private int type = 0;

    private CarouselViewFlipper content_flipper;

    private List<View> viewList = new ArrayList<>();

    private MyZoomImageView image;

    private int mShortAnimationDuration;

    private int type1 = 0;//单张 或者 专辑

    private ImageView content_zooimage;

    private String imageUrl = "";

    private String atlasName = "";

    private boolean isBoo_bug = false;

    @Override
    public void initData() {
        if (type1 == 2 || type1 == 0) {
            images = new ArrayList<>();
            geBoot();
            content_flipper.setCarouselChanged((oldIndex, newIndex, view) -> {
                if (newIndex == 0) {
                    type = 1;
                    position = 0;
                } else {
                    type = list1.size() + 1 - newIndex;
                    position = list1.size() + 1 - type;
                }
                title_tab_tab_text.setText(type + "/" + list1.size());
                getLiner(position);
                if (!list1.get(position).getIsSee().equals("2")) {
                    if (TextUtils.isEmpty(SharedPreferencesUtils.get(ContentActivity.this, "userNo", "").toString())) {
                        ToatUtils.showShort1(ContentActivity.this, "请先登录");
                    } else {
                        getSXSDialog(atlasName, list1.get(position).getAtlasPrice());
                    }
                }
            });
            content_flipper.setOnClickListener((CarouselViewFlipper.OnClickListener) stringBuffer -> {
                if (stringBuffer.toString().equals("23")) {
                    bool_image = !bool_image;
                    geBoot();
                    getLiner(position);
                }
            });
            getViewAtlas();
            content_liner.setVisibility(View.VISIBLE);
            content_flipper.setVisibility(View.VISIBLE);
            content_zooimage.setVisibility(View.GONE);
        } else if (type1 == 1) {
            content_liner.setVisibility(View.GONE);
            content_flipper.setVisibility(View.GONE);
            content_zooimage.setVisibility(View.VISIBLE);
            confirm_delete_imageview.animate()
                    .alpha(0f)
                    .setDuration(mShortAnimationDuration * 10)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            confirm_delete_imageview.setVisibility(View.GONE);
                        }
                    });
            Glide.with(this).load(imageUrl).into(content_zooimage);
        }
    }

    @Override
    public void initView() {
        atlasNo = getIntent().getStringExtra("ViewAtlas");
        px = getIntent().getIntExtra("px", 0);
        type1 = getIntent().getIntExtra("type", 0);
        imageUrl = getIntent().getStringExtra("imageUrl");
        atlasName = getIntent().getStringExtra("AtlasName");// 图集名字
        mImmersionBar.hideBar(BarHide.FLAG_HIDE_BAR).init();
        mImmersionBar.statusBarDarkFont(false).init();
        title_relat = findView(R.id.title_relat);
        content_liner = findView(R.id.content_liner);
        content_liner1_text = findView(R.id.content_liner1_text);
        content_liner2_text = findView(R.id.content_liner2_text);
        content_flipper = findView(R.id.content_flipper);
        content_zooimage = findView(R.id.content_zooimage);
        confirm_delete_imageview = findView(R.id.confirm_delete_imageview);

        findView(R.id.title_search_image).setOnClickListener(this);
        findView(R.id.content_liner1).setOnClickListener(this);
        findView(R.id.content_liner2).setOnClickListener(this);
        title_relat.setAlpha(0.5f);
        content_liner.setAlpha(0.5f);
        title_tab_tab_text = findView(R.id.title_tab_tab_text);
        page = 0;

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.act_content;
    }


    private void geBoot() {
        if (bool_image) {
            title_relat.setVisibility(View.VISIBLE);
            if (list1 != null && list1.size() > 0) {
                if (list1.get(position).getIsSee().equals("2")) {
                    content_liner.setVisibility(View.VISIBLE);
                } else {
                    content_liner.setVisibility(View.GONE);
                }
            } else {
                content_liner.setVisibility(View.VISIBLE);
            }
        } else {
            title_relat.setVisibility(View.GONE);
            content_liner.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search_image:
                finish();
                break;
            case R.id.content_liner1:
                if (TextUtils.isEmpty(SharedPreferencesUtils.get(this, "userNo", "").toString())) {
                    ToatUtils.showShort1(this, "请先登录");
                } else {
                    if (fastClick()) {
                        if (content_bool1) {
                            islike = 0;
                            getcontent_bool1();
                            content_bool1 = false;
                        } else {
                            islike = 1;
                            getcontent_bool1();
                            content_bool1 = true;
                        }
                    }
                }
                break;
            case R.id.content_liner2:
                if (TextUtils.isEmpty(SharedPreferencesUtils.get(this, "userNo", "").toString())) {
                    ToatUtils.showShort1(this, "请先登录");
                } else {
                    if (fastClick()) {
                        if (content_bool2) {
                            setIsCollect = 0;
                            getcontent_bool2();
                            content_bool2 = false;
                        } else {
                            setIsCollect = 1;
                            getcontent_bool2();
                            content_bool2 = true;
                        }
                    }
                }
                break;
            case R.id.dialog_payment_determine:
                if (TextUtils.isEmpty(SharedPreferencesUtils.get(ContentActivity.this, "userNo", "").toString())) {
                    ToatUtils.showShort1(ContentActivity.this, "请先登录");
                } else {
                    getBuy();
                }
                break;
        }
    }

    private void getBuy() {
        HttpRequest.getInstance().setPublic(this,
                AggregateMap.getInstance().setBug(SharedPreferencesUtils.get(this, "userNo", "0").toString(), atlasNo, atlasName),
                mHandler, HttpImplements.getInstance().getHttp(this, "buy"), "buy");
    }

    private void getViewAtlas() {
        HttpRequest.getInstance().setPublic(this, AggregateMap.getInstance().setGetViewAtlas(atlasNo, page, SharedPreferencesUtils.get(this, "userNo", "0").toString()),
                mHandler, HttpImplements.getInstance().getHttp(this, "getViewAtlas"), "getViewAtlas");
    }

    private void getcontent_bool1() {
        HttpRequest.getInstance().setPublic(this, AggregateMap.getInstance().setIsLike(SharedPreferencesUtils.get(this, "userNo", "0").toString(),
                list1.get(position).getImgID(), islike),
                mHandler, HttpImplements.getInstance().getHttp(this, "isLike"), "isLike");
    }

    private void getcontent_bool2() {
        HttpRequest.getInstance().setPublic(this, AggregateMap.getInstance().setIsCollect(SharedPreferencesUtils.get(this, "userNo", "0").toString(),
                1, list1.get(position).getImgID(), setIsCollect, 1, new JSONArray()),
                mHandler, HttpImplements.getInstance().getHttp(this, "isCollect"), "isCollect");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(type1 != 1){
            getViewAtlas();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 205:
                    title_relat.setVisibility(View.VISIBLE);
                    confirm_delete_imageview.setVisibility(View.VISIBLE);
                    break;
                case 206:
                    title_relat.setVisibility(View.VISIBLE);
                    confirm_delete_imageview.setVisibility(View.VISIBLE);
                    break;
                case 1003:
                    List<ViewAtlas> list = (List<ViewAtlas>) msg.obj;
                    list1.clear();
                    list1.addAll(list);
                    if (images != null) {
                        images.clear();
                    }
                    viewList.clear();
                    if (isBoo_bug) {  // 判断支付
                        px = type;
                        content_flipper.invalidate();
                    } else {
                        confirm_delete_imageview.animate()
                                .alpha(0f)
                                .setDuration(mShortAnimationDuration * 10)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                        confirm_delete_imageview.setVisibility(View.GONE);
                                    }
                                });
                    }
                    for (int i = 0; list1.size() > i; i++) {
                        images.add(list1.get(i).getUrl());
                        islisk.add(list1.get(i).getIsLike());
                        isCollect.add(list1.get(i).getIsCollect());
                        viewList.add(getImageView(i, list1.get(i).getUrl()));
                    }
                    content_flipper.setViews(viewList);
                    if (px != 0 && isBoo_bug) {
                        type = list1.size() + 1 - px;
                        content_flipper.setDisplayedChild(type);
                        title_tab_tab_text.setText(px + "/" + list1.size());
                    } else if (px != 0) {
                        type = list1.size() + 1 - px;
                        content_flipper.setDisplayedChild(px);
                        title_tab_tab_text.setText(type + "/" + list1.size());
                    } else {
                        title_tab_tab_text.setText((type + 1) + "/" + list1.size());
                    }
                    if (bool_image) {
                        if (list1.get(type).getIsSee().equals("2")) {
                            UtilsInterface.getInstance().UtilGlideAngle(ContentActivity.this, images.get(type), image);
                        } else {
                            UtilsInterface.getInstance().UtilBlur(ContentActivity.this, "", images.get(type), image);
                        }
                    }
                    getLiner(type);
                    break;
                case 1012:
                    if (content_bool1) {
                        islisk.set(position, "1");
                        content_liner1_text.setText("已点赞");
                        ToatUtils.showShort1(ContentActivity.this, "点赞成功");
                    } else {
                        islisk.set(position, "0");
                        content_liner1_text.setText("未点赞");
                        ToatUtils.showShort1(ContentActivity.this, "取消点赞");
                    }
                    break;
                case 1006:
                    if (content_bool2) {
                        isCollect.set(position, "1");
                        content_liner2_text.setText("已收藏");
                        ToatUtils.showShort1(ContentActivity.this, "收藏成功");
                    } else {
                        isCollect.set(position, "0");
                        content_liner2_text.setText("未收藏");
                        ToatUtils.showShort1(ContentActivity.this, "取消收藏");
                    }
                    break;
                case 1020:
                    String bug = msg.obj.toString();
                    if (bug.equals("1")) {
                        Intent intent = new Intent(ContentActivity.this, WebViewActivity.class);
                        intent.putExtra("url", HttpImplements.getInstance().Center + "userNo=" +
                                SharedPreferencesUtils.get(ContentActivity.this, "userNo", "").toString() + "&version=" +
                                AppUtil.getInstance().getVersionName(1, ContentActivity.this) + "&currentBalance=" +
                                SharedPreferencesUtils.get(ContentActivity.this, "user_gubi", "0").toString());
                        startActivity(intent);
                    } else {
                        isBoo_bug = true;
                        getViewAtlas();
                    }
                    break;
            }
        }
    };

    private void getLiner(int newIndex) {
        if (list1.get(newIndex).getIsSee().equals("2")) {
            content_liner.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(islisk.get(newIndex)) || islisk.get(newIndex).equals("0")) {
                content_liner1_text.setText("未点赞");
                content_bool1 = false;
            } else {
                content_liner1_text.setText("已点赞");
                content_bool1 = true;
            }
            if (TextUtils.isEmpty(isCollect.get(newIndex)) || isCollect.get(newIndex).equals("0")) {
                content_liner2_text.setText("未收藏");
                content_bool2 = false;
            } else {
                content_bool2 = true;
                content_liner2_text.setText("已收藏");
            }
        } else {
            content_liner.setVisibility(View.GONE);
        }
        title_relat.setVisibility(View.VISIBLE);
    }

    private ImageView getImageView(int oldIndex, String resId) {
        image = new MyZoomImageView(this);
        if (list1.get(oldIndex).getIsSee().equals("2")) {
            Glide.with(this).load(resId).transition(withCrossFade()).into(image);
        } else {
//            UtilsInterface.getInstance().UtilBlur(this, "", resId, image);
            Glide.with(this).load(resId).transition(withCrossFade()).apply(RequestOptions.bitmapTransform(new GlideBlurformation(this))).into(image);
        }
        return image;
    }

    private SXSDialog mSXSDialog;

    private void getSXSDialog(String tuji_name, String guabi_name) {

        if (mSXSDialog == null) {
            mSXSDialog = new SXSDialog(this, R.layout.view_dialog_payment, R.style.customDialognoback);
        }

        mSXSDialog.getWindow().setWindowAnimations(R.style.AnimBottom);
        mSXSDialog.getWindow().setGravity(Gravity.CENTER);
        mSXSDialog.setCanceledOnTouchOutside(false);
        mSXSDialog.setCancelable(false);
        mSXSDialog.setWidthHeight(AppUtil.getScreenDispaly(this)[0], 0);
        mSXSDialog.setOnClick(R.id.dialog_payment_determine, this);
        mSXSDialog.setOnClick(R.id.dialog_payment_clean, null);
        TextView dialog_payment_tuji_name = (TextView) mSXSDialog.findViewById(R.id.dialog_payment_tuji_name);
        TextView dialog_payment_guabi_name = (TextView) mSXSDialog.findViewById(R.id.dialog_payment_guabi_name);
        if (TextUtils.isEmpty(tuji_name)) {
            dialog_payment_tuji_name.setText("图集名称");
        } else {
            dialog_payment_tuji_name.setText(tuji_name);
        }
        if (TextUtils.isEmpty(guabi_name)) {
            dialog_payment_guabi_name.setText("瓜币金额");
        } else {
            dialog_payment_guabi_name.setText("需要支付瓜币:" + guabi_name);
        }

        if (!this.isFinishing()) {
            mSXSDialog.show();
        }
    }
}
