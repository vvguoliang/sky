package com.sky.beautiful.OKHttp;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.sky.beautiful.Model.GetHomeBanner;
import com.sky.beautiful.Model.GetLabel;
import com.sky.beautiful.Model.GetPageShow;
import com.sky.beautiful.Model.MyCollect;
import com.sky.beautiful.Model.MyUserMsg;
import com.sky.beautiful.Model.ViewAtlas;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.Utils.ToatUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/9/19.
 * <p>
 * 请求数据封装
 */

@SuppressWarnings("JavaDoc")
public class HttpRequest implements HttpRequestIn {

    @Override
    public void setPublic(Context context, Map<String, Object> map, Handler mHnadler, String url, String name) {
        OkHttpManager.postAsync(url, name, HttpOnekey.getInstance().setMap(context, map),
                new DataCallBack() {
                    @Override
                    public void requestFailure(Request request, String name, IOException e) {
                        ToatUtils.showShort1(context, "亲!服务器故障,请稍后再试");
                        mHnadler.sendEmptyMessage(206);
                    }

                    @Override
                    public void requestSuccess(String result, String name) throws Exception {
                        if (SharedPreferencesUtils.get(context, "updata", false).toString().equals("false") && (name.equals("userMsgInput")
                                || name.equals("uploadUserHead")
                                || name.equals("getPageShow")
                                || name.equals("getViewAtlas")
                                || name.equals("myCollect")
                                || name.equals("getLabel")
                                || name.equals("getHomeBanner"))) {
                            HttpRequest.getInstance().setPublic(context, null, mHnadler, HttpImplements.getInstance().getHttp(context, "updata"), "updata");
                        }
                        object = new JSONObject(result);
                        Long version_type = 0L;
                        if (context != null) {
                            version_type = Long.parseLong(SharedPreferencesUtils.get(context, "version_type", "0").toString());
                        }
                        if (HttpOnekey.getInstance().setBoolen(context, object.optString("code"), object.optString("msg"), mHnadler, version_type)) {
                            switch (name) {
                                case "register":
                                    register(result, mHnadler);
                                    break;
                                case "modifyPassword":
                                    modifyPassword(result, mHnadler);
                                    break;
                                case "login":
                                    login(result, mHnadler);
                                    break;
                                case "exitLogin":
                                    break;
                                case "userMsgInput":
                                    userMsgInput(result, mHnadler);
                                    break;
                                case "uploadUserHead":
                                    uploadUserHead(result, mHnadler);
                                    break;
                                case "isCollect":
                                    isCollect(result, mHnadler);
                                    break;
                                case "myCollect":
                                    myCollect(result, mHnadler);
                                    break;
                                case "getHomeBanner":
                                    getHomeBanner(result, mHnadler);
                                    break;
                                case "getPageShow":
                                    getPageShow(result, mHnadler);
                                    break;
                                case "getViewAtlas":
                                    getViewAtlas(result, mHnadler);
                                    break;
                                case "getLabel":
                                    getLabel(result, mHnadler);
                                    break;
                                case "myUserMsg":
                                    myUserMsg(result, mHnadler);
                                    break;
                                case "dynamicCode":
                                    dynamicCode(result, mHnadler);
                                    break;
                                case "isLike":
                                    isLike(result, mHnadler);
                                    break;
                                case "updata":
                                    updata(context, mHnadler);
                                    break;
                                case "buy":
                                    isBuy(result, mHnadler);
                                    break;
                            }
                        } else {
                            message = new Message();
                            message.what = 205;
                            message.obj = object.optString("msg");
                            mHnadler.sendMessage(message);
                        }
                    }
                });
    }

    private void login(String result, Handler mHnadler) {
        try {
            object = new JSONObject(result);
            object = new JSONObject(object.getString("reqData"));
            message = new Message();
            message.what = 1007;
            message.obj = object.getString("userNo") + "," + object.getString("melonMoney");
            mHnadler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void modifyPassword(String result, Handler mHnadler) {
        message = new Message();
        message.what = 1011;
        mHnadler.sendMessage(message);
    }

    private void isLike(String result, Handler mHnadler) {
        message = new Message();
        message.what = 1012;
        mHnadler.sendMessage(message);
    }

    private void isBuy(String result, Handler mHnadler) {
        try {
            object = new JSONObject(result);
            object = new JSONObject(object.getString("reqData"));
            message = new Message();
            message.what = 1020;
            message.obj = object.getString("state");
            mHnadler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updata(Context context, Handler mHnadler) {
        SharedPreferencesUtils.put(context, "updata", false);
        message = new Message();
        message.what = 1015;
        mHnadler.sendMessage(message);
    }

    private void uploadUserHead(String result, Handler mHnadler) {
        message = new Message();
        message.what = 1013;
        mHnadler.sendMessage(message);
    }

    private void userMsgInput(String result, Handler mHnadler) {
        message = new Message();
        message.what = 1010;
        message.obj = 0;
        mHnadler.sendMessage(message);
    }

    private void myUserMsg(String result, Handler mHnadler) {
        try {
            object = new JSONObject(result);
            object = new JSONObject(object.getString("reqData"));
            MyUserMsg myUserMsg = gson.fromJson(object.toString(), MyUserMsg.class);
            message = new Message();
            message.what = 1008;
            message.obj = myUserMsg;
            mHnadler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void register(String result, Handler mHnadler) {
        try {
            object = new JSONObject(result);
            object = new JSONObject(object.getString("reqData"));
            message = new Message();
            message.what = 1004;
            message.obj = object.getString("userNo");
            ;
            mHnadler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void dynamicCode(String result, Handler mHnadler) {
        message = new Message();
        message.what = 1005;
        message.obj = 0;
        mHnadler.sendMessage(message);
    }

    private void isCollect(String result, Handler mHnadler) {
        message = new Message();
        message.what = 1006;
        message.obj = 0;
        mHnadler.sendMessage(message);
    }

    private void getHomeBanner(String result, Handler mHnadler) {
        List<GetHomeBanner> getHomeBannerList = new ArrayList<>();
        JSONArray array = null;
        try {
            object = new JSONObject(result);
            array = new JSONArray(object.getString("reqData"));
            for (int i = 0; array.length() > i; i++) {
                JSONObject jsonObject1 = array.optJSONObject(i);
                getHomeBannerList.add(gson.fromJson(jsonObject1.toString(), GetHomeBanner.class));
            }
            message = new Message();
            message.what = 1000;
            message.obj = getHomeBannerList;
            mHnadler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void myCollect(String result, Handler mHnadler) {
        List<MyCollect> myCollects = new ArrayList<>();
        JSONArray array = null;
        try {
            object = new JSONObject(result);
            array = new JSONArray(object.getString("reqData"));
            for (int i = 0; array.length() > i; i++) {
                JSONObject jsonObject1 = array.optJSONObject(i);
                myCollects.add(gson.fromJson(jsonObject1.toString(), MyCollect.class));
            }
            message = new Message();
            message.what = 1009;
            message.obj = myCollects;
            mHnadler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getPageShow(String result, Handler mHnadler) {
        List<GetPageShow> getPageShowList = new ArrayList<>();
        JSONArray array = null;
        try {
            object = new JSONObject(result);
            array = new JSONArray(object.getString("reqData"));
            for (int i = 0; array.length() > i; i++) {
                JSONObject jsonObject1 = array.optJSONObject(i);
                getPageShowList.add(gson.fromJson(jsonObject1.toString(), GetPageShow.class));
            }
            message = new Message();
            message.what = 1001;
            message.obj = getPageShowList;
            mHnadler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getLabel(String result, Handler mHnadler) {
        List<GetLabel> getLabelList = new ArrayList<>();
        JSONArray array = null;
        try {
            object = new JSONObject(result);
            array = new JSONArray(object.getString("reqData"));
            for (int i = 0; array.length() > i; i++) {
                JSONObject jsonObject1 = array.optJSONObject(i);
                getLabelList.add(gson.fromJson(jsonObject1.toString(), GetLabel.class));
            }
            message = new Message();
            message.what = 1002;
            message.obj = getLabelList;
            mHnadler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getViewAtlas(String result, Handler mHnadler) {
        List<ViewAtlas> viewAtlasList = new ArrayList<>();
        JSONArray array = null;
        try {
            object = new JSONObject(result);
            array = new JSONArray(object.getString("reqData"));
            for (int i = 0; array.length() > i; i++) {
                JSONObject jsonObject1 = array.optJSONObject(i);
                viewAtlasList.add(gson.fromJson(jsonObject1.toString(), ViewAtlas.class));
            }
            message = new Message();
            message.what = 1003;
            message.obj = viewAtlasList;
            mHnadler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单例对象实例
     */
    private static class HttpRequestHolder {
        static final HttpRequest INSTANCE = new HttpRequest();
    }

    public static HttpRequest getInstance() {
        return HttpRequestHolder.INSTANCE;
    }

    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private HttpRequest() {
    }

    private Gson gson = new Gson();

    private Message message;

    private JSONObject object;

}
