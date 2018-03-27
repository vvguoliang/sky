package com.sky.beautiful.Share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.alipay.share.sdk.openapi.APAPIFactory;
import com.alipay.share.sdk.openapi.APImageObject;
import com.alipay.share.sdk.openapi.APMediaMessage;
import com.alipay.share.sdk.openapi.APWebPageObject;
import com.alipay.share.sdk.openapi.BaseReq;
import com.alipay.share.sdk.openapi.BaseResp;
import com.alipay.share.sdk.openapi.IAPAPIEventHandler;
import com.alipay.share.sdk.openapi.IAPApi;
import com.alipay.share.sdk.openapi.SendMessageToZFB;
import com.sky.beautiful.R;
import com.sky.beautiful.Utils.AppUtil;
import com.sky.beautiful.Utils.ToatUtils;
import com.sky.beautiful.View.Dialog.SXSDialog;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.tencent.wxop.stat.StatConfig;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by vvguoliang on 2017/2/13.
 */

public class ShareWxapTencent implements View.OnClickListener, IAPAPIEventHandler {

    public ShareWxapTencent(Context context, String url, String mQqTitle, String mQqSummary, String mWxwebtitle, String mWxwebdescription) {
        super();
        this.mActivity = (Activity) context;
        if (!TextUtils.isEmpty(url) || !url.equals("null")) {
            this.url = url;
        } else {
            ToatUtils.showShort1(mActivity, "操作失败，请重试");
            return;
        }
        if (!TextUtils.isEmpty(mQqTitle) && !mQqTitle.equals("null")) {
            this.mQqTitle = mQqTitle;
        }
        if (!TextUtils.isEmpty(mQqSummary) && !mQqSummary.equals("null")) {
            this.mQqSummary = mQqSummary;
        }
        if (!TextUtils.isEmpty(mWxwebtitle) && !mWxwebtitle.equals("null")) {
            this.mWxwebtitle = mWxwebtitle;
        }
        if (!TextUtils.isEmpty(mWxwebdescription) && !mWxwebdescription.equals("null")) {
            this.mWxwebdescription = mWxwebdescription;
        }
        getShare();
    }

    private Activity mActivity;

    private String url = "";

    /**
     * 腾讯分享
     */
    public static Tencent mTencent;
    /**
     * 微信分享
     */
    private IWXAPI api;
    /**
     * 分享支付宝
     */
    private IAPApi zfbapi;

    private String mQqTitle = "[沙小僧]送你888元启动资金,跟小沙一起取金吧!";

    private String mQqSummary = "最高14.4%年利率，历史项目完美兑付！沙小僧，靠谱儿！";

    private String mWxwebtitle = "[沙小僧]送你888元启动资金,跟小沙一起取金吧!";

    private String mWxwebdescription = "我在沙小僧取金，安全可靠福利多，新手注册即送888元，最高14.4%年利率，快来一起赚~";

    private SXSDialog mSXSDialog;

    private int sxs_icon = R.mipmap.ic_launcher;

    private Bitmap thumb = null;

    private String pathstring = "";

    private void getShare() {

        api = WXAPIFactory.createWXAPI(mActivity, mActivity.getString(R.string.WXAPI), false);
        api.registerApp(mActivity.getString(R.string.WXAPI));

        mTencent = Tencent.createInstance(mActivity.getString(R.string.qq), mActivity);
        StatConfig.setAppKey(mActivity, mActivity.getString(R.string.qq_key));
        thumb = BitmapFactory.decodeResource(mActivity.getResources(), sxs_icon);
        BitmapUtils.saveBitmap(thumb);

        if (url == null || url.equals("")) {
            ToatUtils.showShort1(mActivity, "操作失败，请重试");
            return;
        }
        if (mSXSDialog == null) {
            mSXSDialog = new SXSDialog(mActivity, R.layout.my_friends_botton_popupwindow, R.style.customDialognoback);
        }
        mSXSDialog.getWindow().setWindowAnimations(R.style.AnimBottom);
        mSXSDialog.getWindow().setGravity(Gravity.BOTTOM);
        mSXSDialog.setWidthHeight(AppUtil.getScreenDispaly(mActivity)[0], 0);
        mSXSDialog.setOnClick(R.id.tab_rb_1, this);
        mSXSDialog.setOnClick(R.id.tab_rb_2, this);
        mSXSDialog.setOnClick(R.id.tab_rb_3, this);
        mSXSDialog.setOnClick(R.id.tab_rb_4, this);
        mSXSDialog.setOnClick(R.id.tab_rb_6, this);
        mSXSDialog.setOnClick(R.id.tab_rb_7, this);
        mSXSDialog.setOnClick(R.id.my_pop_cancel_button, null);
        if (!mActivity.isFinishing()) {
            mSXSDialog.show();
        }
        pathstring = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cache/pics/sxs/share/xgsq_icon_share.png";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_rb_1:// 微信
                shareToWXshare(0);
                break;
            case R.id.tab_rb_2:// 朋友圈
                shareToWXshare(1);
                break;
            case R.id.tab_rb_3:// QQ空间
                shareToQQzone();
                break;
            case R.id.tab_rb_4:// QQ
                onClickShare();
                break;
            case R.id.tab_rb_6://生活圈
                sharezfu(1);
                break;
            case R.id.tab_rb_7://生活圈
                Intent share_intent = new Intent();
                share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
                share_intent.setType("text/plain");//设置分享内容的类型
                share_intent.putExtra(Intent.EXTRA_SUBJECT, mQqTitle);//添加分享内容标题
                share_intent.putExtra(Intent.EXTRA_TEXT, mQqSummary);//添加分享内容
                //创建分享的Dialog
                share_intent = Intent.createChooser(share_intent, mQqTitle);
                mActivity.startActivity(share_intent);
                break;
            default:
                break;
        }
    }

    private void onClickShare() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, mQqTitle);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, mQqSummary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, pathstring);
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "");SHARE_TO_QQ_IMAGE_LOCAL_URL
//        params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
        if (mTencent != null)
            mTencent.shareToQQ(mActivity, params, new BaseUIListener(mActivity));
    }

    private void shareToQQzone() {
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, mQqTitle);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, mQqSummary);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);
        ArrayList<String> imageUrls = new ArrayList<>();
        imageUrls.add(pathstring);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        if (mTencent != null)
            mTencent.shareToQzone(mActivity, params, new BaseUIListener(mActivity));
    }

    private void shareToWXshare(final int WX_FRIENDS) {
        /**
         * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码）
         *
         * @param flag
         *            (0:分享到微信好友，1：分享到微信朋友圈)
         */
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = mWxwebtitle;
        msg.description = mWxwebdescription;
        // 这里替换一张自己工程里的图片资源
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = WX_FRIENDS == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        if (api != null)
            api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    private void sharezfu(final int WX_FRIENDS) {
        //创建工具对象实例，此处的APPID为上文提到的，申请应用生效后，在应用详情页中可以查到的支付宝应用唯一标识
        zfbapi = APAPIFactory.createZFBApi(mActivity.getApplicationContext(), mActivity.getString(R.string.zfb), false);

        if (zfbapi.isZFBAppInstalled()) {//是否安装支付宝
            String path = pathstring;
            File file = new File(path);
            if (!file.exists()) {
                ToatUtils.showShort1(mActivity, "选择的文件不存在");
                return;
            }
            APWebPageObject webPageObject = new APWebPageObject();
            webPageObject.webpageUrl = url;
            APMediaMessage webMessage = new APMediaMessage();
            webMessage.title = mWxwebtitle;
            webMessage.description = mWxwebdescription;
            webMessage.mediaObject = webPageObject;
            webMessage.thumbUrl = pathstring;
            SendMessageToZFB.Req webReq = new SendMessageToZFB.Req();
            webReq.message = webMessage;
            webReq.transaction = buildTransaction("webpage");

            zfbapi.sendReq(webReq);
            //通过调用工具实例提供的handleIntent方法，绑定消息处理对象实例，
            zfbapi.handleIntent(mActivity.getIntent(), this);
        } else {
            ToatUtils.showShort1(mActivity, "亲！确定安装支付宝了吗？");
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        int result;

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                result = R.string.errcode_send_fail;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }
        ToatUtils.showShort1(mActivity, mActivity.getString(result));
    }
}
