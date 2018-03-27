package com.sky.beautiful.OKHttp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.sky.beautiful.R;
import com.sky.beautiful.Service.ServiceSubclass;
import com.sky.beautiful.SykApplication;
import com.sky.beautiful.Utils.AppUtil;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.Utils.TimeUtils;
import com.sky.beautiful.Utils.ToatUtils;
import com.sky.beautiful.View.Dialog.UtilsDialog;
import com.sky.beautiful.View.Dialog.VersionDialogActivity;
import com.sky.beautiful.View.Dialog.VersionUpdateDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/9/19.
 * <p>
 * 接口 加密揭秘和所有请求方式
 */

public class HttpOnekey implements HttpOnekeyIm {

    @Override
    public String setAesJSONEncry(Context context, Map<String, Object> map1) {
        return getMAPJSON(context, map1);
    }

    @Override
    public Map<String, Object> setMap(Context context, Map<String, Object> map) {
        return getMapProt(context, map);
    }

    @Override
    public String setMapString(Context context, Map<String, Object> map) {
        return getMapProtString(context, map);
    }

    @Override
    public Boolean setBoolen(Context context, String code, String msg, Handler mHandler, long version_type) {
        return getObject(context, code, msg, mHandler, version_type);
    }

    /**
     * 单例对象实例
     */
    private static class HttpOnekeyHolder {
        static final HttpOnekey INSTANCE = new HttpOnekey();
    }

    public static HttpOnekey getInstance() {
        return HttpOnekeyHolder.INSTANCE;
    }

    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private HttpOnekey() {
    }

    private String getMAPJSON(Context context, Map<String, Object> map1) {
        if (map1 != null) {
            return "{\"base\":" + getJSON(context) + ",\"data\":" + getMap(map1).toString() + "}".trim().replace("\\", "");
        } else {
            return "{\"base\":" + getJSON(context) + "}".trim().replace("\\", "");
        }
    }

    private JSONObject getMap(Map<String, Object> maps) {
        JSONObject jsonObject1 = new JSONObject();
        /*
         * 在这对添加的参数进行遍历，map遍历有四种方式，如果想要了解的可以网上查找
         */
        if (maps != null) {
            try {
                for (Map.Entry<String, Object> map : maps.entrySet()) {
                    String key = map.getKey();
                    String value;
            /*
             * 判断值是否是空的
             */
                    if (map.getValue() == null) {
                        value = "";
                    } else {
                        value = map.getValue().toString();
                    }
                    jsonObject1.put(key, value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject1;
    }

    private Map<String, Object> getMapProt(Context context, Map<String, Object> objectMap) {
        Map<String, Object> map = new HashMap<>();
        map.put("reqData", getMAPJSON(context, objectMap).trim().replace("\n", ""));
        return map;
    }

    private String getMapProtString(Context context, Map<String, Object> objectMap) {
        return getMAPJSON(context, objectMap).trim().replace("\n", "");
    }

    private String getJSON(Context context) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("version", AppUtil.getInstance().getVersionName(1, context));
            jsonObject.put("IPAddress", getExternalIP(context));
            jsonObject.put("userNo", SharedPreferencesUtils.get(context, "userNo", "0").toString());
            jsonObject.put("timestamp", TimeUtils.getTime(TimeUtils.getCurrentTimeInLong()));
            jsonObject.put("source", AppUtil.getInstance().getChannel(context, 0));
            jsonObject.put("model", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * 外网IP
     *
     * @param context
     * @return
     */
    private String getExternalIP(Context context) {
        final String[] cip = new String[1];
        OkHttpManager.postAsync(HttpImplements.getInstance().ExternalIP, "", null, new DataCallBack() {
            @Override
            public void requestFailure(Request request, String name, IOException e) {
                Log.e("", "");
            }

            @Override
            public void requestSuccess(String result, String name) throws Exception {
                String[] results = result.split("\\{");
                JSONObject object = new JSONObject("{" + results[1]);
                cip[0] = object.optString("cip");
                SharedPreferencesUtils.put(context, "ExternalIP", cip[0]);
            }
        });

        return cip[0];
    }


    private Boolean getObject(Context context, String code, String msg, Handler mHandler, long version_type) {
        Boolean boolea = false;
        switch (code) {
            case "200":
                boolea = true;
                break;
            case "202"://系统维护
                boolea = false;
                if (version_type == 0) {
                    SharedPreferencesUtils.put(context, "version_type", "1");
                    Intent intent = new Intent(context, VersionDialogActivity.class);
                    intent.putExtra("GradeNo", 3);
                    intent.putExtra("msg", msg);
                    intent.putExtra("code", "202");
                    context.startActivity(intent);
                }
                break;
            case "203"://强制升级
                boolea = false;
                long GradeNo = 0;
                if (Long.parseLong(SharedPreferencesUtils.get(context, "GradeNo", "1").toString()) - 1 == 0) {
                    GradeNo = 0;
                } else if (Long.parseLong(SharedPreferencesUtils.get(context, "GradeNo", "1").toString()) - 1 == 1) {
                    GradeNo = 1;
                } else if (Long.parseLong(SharedPreferencesUtils.get(context, "GradeNo", "1").toString()) - 1 == 2) {
                    GradeNo = 2;
                }
                if (version_type == 0) {
                    SharedPreferencesUtils.put(context, "version_type", "1");
                    Intent intent = new Intent(context, VersionDialogActivity.class);
                    intent.putExtra("GradeNo", GradeNo);
                    intent.putExtra("msg", msg);
                    intent.putExtra("code", "203");
                    context.startActivity(intent);
                }
                break;
            case "204"://建议升级
                boolea = false;
                GradeNo = 0;
                if (Long.parseLong(SharedPreferencesUtils.get(context, "GradeNo", "1").toString()) - 1 == 0) {
                    GradeNo = 0;
                } else if (Long.parseLong(SharedPreferencesUtils.get(context, "GradeNo", "1").toString()) - 1 == 1) {
                    GradeNo = 1;
                } else if (Long.parseLong(SharedPreferencesUtils.get(context, "GradeNo", "1").toString()) - 1 == 2) {
                    GradeNo = 2;
                }
                if (version_type == 0) {
                    SharedPreferencesUtils.put(context, "version_type", "1");
                    Intent intent = new Intent(context, VersionDialogActivity.class);
                    intent.putExtra("GradeNo", GradeNo);
                    intent.putExtra("msg", msg);
                    intent.putExtra("code", "204");
                    context.startActivity(intent);
                }
                break;
            case "205"://错误描述
                if (!msg.contains("用户未登录") && !msg.contains("查询获取")) {
                    ToatUtils.showShort1(context, msg);
                }
                break;
        }
        return boolea;
    }
}
