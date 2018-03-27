package com.sky.beautiful.Utils;

import android.content.Context;

/**
 * @Time : 2018/1/3 no 下午9:50
 * @USER : vvguoliang
 * @File : MarketToolsInterface.java
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

public interface MarketToolsInterface {

    void setGoToMarket(Context context, String url);

    // 魅族系统
    String SYS_FLYME = "sys_flyme";


    // 小米系统
    String SYS_MIUI = "sys_miui";
    String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";


    // 华为系统
    String SYS_EMUI = "sys_emui";
    String KEY_EMUI_VERSION = "ro.build.version.emui";
    String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    // oppo 系统
    String SYS_OPPO = "sys_oppo";
    String KEY_OPPO_VERSIN = "ro.build.version.opporom";

    // vivo 系统
    String SYS_VIVO = "sys_vivo";
    String KEY_VIVO_VERSIN = "ro.vivo.os.version";

    // 锤子 系统
    String SYS_SMARTISAN = "sys_smartisan";
    String KEY_SMARTISAN_VERSIN = "ro.smartisan.version";

    // 360 系统
    String SYS_360 = "sys_360";
    String SYS_QIKU_VERSIN = "QIKU";
    String SYS_360_VERSIN = "360";
    String SYS_PRODUCT_VERSIN = "ro.product.manufacturer";

    //中兴
    String SYS_ZTE = "sys_zte";

    //三星
    String SYS_SAMSUNG = "sys_samsung";

    //HTC
    String SYS_HTC = "sys_htc";

    //乐视
    String SYS_LETV = "sys_letv";
}
