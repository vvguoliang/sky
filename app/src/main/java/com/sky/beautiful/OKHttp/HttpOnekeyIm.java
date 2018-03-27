package com.sky.beautiful.OKHttp;

import android.content.Context;
import android.os.Handler;

import java.util.Map;

/**
 * Created by vvguoliang on 2017/9/21.
 */

public interface HttpOnekeyIm {
    /**
     * 加密过程中的 JSON 拼接
     *
     * @param context
     * @param map1
     * @return
     */
    String setAesJSONEncry(Context context, Map<String, Object> map1);

    Map<String, Object> setMap(Context context, Map<String, Object> map);

    String setMapString(Context context, Map<String, Object> map);

    /**
     * 判断code 返回值时候成立
     *
     * @param context
     * @param code
     * @param msg
     * @return
     */
    Boolean setBoolen(Context context, String code, String msg, Handler mHandler, long version_type);

}
