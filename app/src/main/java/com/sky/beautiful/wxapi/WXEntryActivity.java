package com.sky.beautiful.wxapi;

import android.os.Bundle;

import com.sky.beautiful.BaseActivity.BaseActivity;
import com.sky.beautiful.Utils.ToatUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wx5467ee4a65044d12", false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    protected int setLayoutId() {
        return 0;
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        api = WXAPIFactory.createWXAPI(this, "wx5467ee4a65044d12", false);
        api.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {
        switch (baseReq.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                ToatUtils.showShort1(WXEntryActivity.this, "===COMMAND_GETMESSAGE_FROM_WX===");
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                ToatUtils.showShort1(WXEntryActivity.this, "===COMMAND_SHOWMESSAGE_FROM_WX===");
                break;
            default:
                break;
        }
    }


    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                // if(api!=null)
                // wxresp.onSuccess();
                ToatUtils.showShort1(this, "发送成功");
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                // if(wxresp!=null)
                // wxresp.onFail();
                ToatUtils.showShort1(this, "分享取消");
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ToatUtils.showShort1(this, "分享被拒绝");
                finish();
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                ToatUtils.showShort1(this, "不支持格式");
                finish();
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                ToatUtils.showShort1(this, "发送失败");
                finish();
                break;
            default:
                ToatUtils.showShort1(this, "分享返回");
                finish();
                break;
        }
    }
}