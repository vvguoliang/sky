package com.sky.beautiful.OKHttp;

import android.content.Context;

/**
 * @Time : 2017/12/22 no 下午6:35
 * @USER : vvguoliang
 * @File : HttpImplements.java
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

public class HttpImplements {

    /**
     * 单例对象实例
     */
    private static class HttpImplementsHolder {
        static final HttpImplements INSTANCE = new HttpImplements();
    }

    public static HttpImplements getInstance() {
        return HttpImplements.HttpImplementsHolder.INSTANCE;
    }

    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private HttpImplements() {
    }

    public String ExternalIP = "http://pv.sohu.com/cityjson?ie=utf-8";

    public String Center = "http://www.51jinyinhua.com/xgPay/XGRechargeCenter/index.html?";

//    private String Okhttp = "http://39.106.48.135:8088/XgUserCenter_consumer";
//    private String Okhttp = "http://39.106.138.4:8088/XgUserCenter_consumer";
    private String Okhttp = "https://api.51jinyinhua.com/XgUserCenter_consumer";

    /**
     * 用户注册
     */
    private String register = "/uz/register.do";

    /**
     * 用户修改密码
     */
    private String modifyPassword = "/uz/modifyPassword.do";

    /**
     * 用户登录
     */
    private String login = "/uz/login.do";

    /**
     * 退出登录
     */
    private String exitLogin = "/uz/exitLogin.do";

    /**
     * 用户个人信息录入
     */
    private String userMsgInput = "/user/userMsgInput.do";

    /**
     * 用户头像上传
     */
    private String uploadUserHead = "/user/uploadUserHead.do";

    /**
     * 收藏、取消收藏
     */
    private String isCollect = "/collect/isCollect.do";

    /**
     * 我的收藏
     */
    private String myCollect = "/collect/myCollect.do";

    /**
     * 首页轮播图接口
     */
    private String getHomeBanner = "/hb/getHomeBanner.do";

    /**
     * 点赞、取消点赞
     */
    private String isLike = "/like/isLike.do";

    /**
     * 获取首页照片瀑布流、获取私照照片瀑布流、搜索
     */
    private String getPageShow = "/ps/getPageShow.do";

    /**
     * 查看图集
     */
    private String getViewAtlas = "/va/getViewAtlas.do";

    /**
     * 查看标签
     */
    private String getLabel = "/va/getLabel.do";

    /**
     * 获取我的页面展示信息
     */
    private String myUserMsg = "/user/myUserMsg.do";

    /**
     * 获取短信
     */
    private String dynamicCode = "/uz/dynamicCode.do";
    /**
     * 升级
     */
    private String updata = "/apk/updata.do";
    /**
     * 升级
     */
    private String buy = "/order/buy.do";


    public final String getHttp(Context context, String publicString) {
        String publicS = "";
        switch (publicString) {
            case "register":
                publicS = register;
                break;
            case "modifyPassword":
                publicS = modifyPassword;
                break;
            case "login":
                publicS = login;
                break;
            case "exitLogin":
                publicS = exitLogin;
                break;
            case "userMsgInput":
                publicS = userMsgInput;
                break;
            case "uploadUserHead":
                publicS = uploadUserHead;
                break;
            case "isCollect":
                publicS = isCollect;
                break;
            case "myCollect":
                publicS = myCollect;
                break;
            case "getHomeBanner":
                publicS = getHomeBanner;
                break;
            case "getPageShow":
                publicS = getPageShow;
                break;
            case "getViewAtlas":
                publicS = getViewAtlas;
                break;
            case "getLabel":
                publicS = getLabel;
                break;
            case "myUserMsg":
                publicS = myUserMsg;
                break;
            case "dynamicCode":
                publicS = dynamicCode;
                break;
            case "isLike":
                publicS = isLike;
                break;
            case "updata":
                publicS = updata;
                break;
            case "buy":
                publicS = buy;
                break;
        }
        return Okhttp + publicS;
    }
}
