package com.sky.beautiful.Utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @Time : 2018/1/3 no 下午9:49
 * @USER : vvguoliang
 * @File : MarketTools.java
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

public class MarketTools  implements MarketToolsInterface{

    /**
     * 单例对象实例
     */
    private static class MarketToolsHolder {
        static final MarketTools INSTANCE = new MarketTools();
    }

    public static MarketTools getInstance() {
        return MarketTools.MarketToolsHolder.INSTANCE;
    }

    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private MarketTools() {
    }

    public String URL  = "http://imtt.dd.qq.com/16891/7D91E3C6506B8AA818D4489E7D0E8C64.apk?fsname=com.sky.beautiful";

    @Override
    public void setGoToMarket(Context context, String url) {
        if (TextUtils.isEmpty( getSystem() )) {
            getGoToMarket( context, url, context.getPackageName() );
        } else {
            switch (getSystem()) {
                case SYS_MIUI:
                case SYS_EMUI:
                case SYS_FLYME:
                case SYS_OPPO:
                case SYS_VIVO:
                case SYS_SMARTISAN:
                case SYS_360:
                case SYS_ZTE:
                case SYS_HTC:
                    getGoToMarket( context, url, context.getPackageName() );
                    break;
                case SYS_SAMSUNG:
                    goToSamsungappsMarket( context, url, context.getPackageName() );
                    break;
                case SYS_LETV:
                    goToLeTVStoreDetail( context, url, context.getPackageName() );
                    break;
            }
        }
    }

    /**
     * 跳转到应用上当大众方法
     *
     * @param context
     */
    private void getGoToMarket(Context context, String url, String packageName) {
        try {
            String str = "market://details?id=" + packageName;
            Intent localIntent = new Intent( Intent.ACTION_VIEW );
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            localIntent.setData( Uri.parse( str ) );
            context.startActivity( localIntent );
        } catch (Exception e) {
            getACTION( context, url );
        }
    }

    /**
     * 跳转其他市场
     *
     * @param context
     */
    private void getACTION(Context context, String url) {
        try {
            Intent intent = new Intent( Intent.ACTION_MAIN );
            intent.addCategory( Intent.CATEGORY_APP_MARKET );
            intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity( intent );
        } catch (Exception e) {
            e.printStackTrace();
            ToatUtils.showShort1( context, "打开应用商店失败，自动跳转系统浏览器" );
            openLinkBySystem( context, url );
        }
    }

    /**
     * 调用系统浏览器打开网页
     *
     * @param url 地址
     */
    private void openLinkBySystem(Context context, String url) {
        Intent intent = new Intent( Intent.ACTION_VIEW );
        intent.setData( Uri.parse( url ) );
        context.startActivity( intent );
    }

    /**
     * 判断系统为 小米 华为 魅族
     *
     * @return
     */
    private String getSystem() {
        String SYS = "";
        try {
            Properties prop = new Properties();
            prop.load( new FileInputStream( new File( Environment.getRootDirectory(), "build.prop" ) ) );
            if (prop.getProperty( KEY_MIUI_VERSION_CODE, null ) != null
                    || prop.getProperty( KEY_MIUI_VERSION_NAME, null ) != null
                    || prop.getProperty( KEY_MIUI_INTERNAL_STORAGE, null ) != null) {
                SYS = SYS_MIUI;//小米
            } else if (prop.getProperty( KEY_EMUI_API_LEVEL, null ) != null
                    || prop.getProperty( KEY_EMUI_VERSION, null ) != null
                    || prop.getProperty( KEY_EMUI_CONFIG_HW_SYS_VERSION, null ) != null) {
                SYS = SYS_EMUI;//华为
            } else if (getMeizuFlymeOSFlag().toLowerCase().contains( "flyme" )) {
                SYS = SYS_FLYME;//魅族
            } else if (prop.getProperty( KEY_OPPO_VERSIN, null ) != null) {
                SYS = SYS_OPPO;// oppo
            } else if (prop.getProperty( KEY_VIVO_VERSIN, null ) != null) {
                SYS = SYS_VIVO;//vivo
            } else if (prop.getProperty( KEY_SMARTISAN_VERSIN, null ) != null) {
                SYS = SYS_SMARTISAN;//锤子
            } else if (prop.getProperty( SYS_QIKU_VERSIN, null ) != null
                    || prop.getProperty( SYS_360_VERSIN, null ) != null
                    || prop.getProperty( SYS_PRODUCT_VERSIN, null ) != null) {
                SYS = SYS_360;//奇虎360
            } else if (isZte()) {
                SYS = SYS_ZTE;//中兴
            } else if (isSamsung()) {
                SYS = SYS_SAMSUNG;//三星
            } else if (isHTC()) {
                SYS = SYS_HTC;//HTC
            } else if (isletv()) {
                SYS = SYS_LETV;//乐视
            }
        } catch (IOException e) {
            e.printStackTrace();
            return SYS;
        }
        return SYS;
    }

    /**
     * 魅族手机
     *
     * @return
     */
    private String getMeizuFlymeOSFlag() {
        return getSystemProperty( "ro.build.display.id", "" );
    }

    private String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName( "android.os.SystemProperties" );
            Method get = clz.getMethod( "get", String.class, String.class );
            return (String) get.invoke( clz, key, defaultValue );
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * 检测是否是中兴机器
     */
    private boolean isZte() {
        return getDeviceModel().toLowerCase().contains( "zte" );
    }

    /**
     * 判断是否是三星的手机
     */
    private boolean isSamsung() {
        return getManufacturer().toLowerCase().contains( "samsung" );
    }

    /**
     * 判断是否是三星的手机
     */
    private boolean isletv() {
        return getManufacturer().toLowerCase().contains( "letv" );
    }

    /**
     * 检测是否HTC手机
     */
    private boolean isHTC() {
        return getManufacturer().toLowerCase().contains( "htc" );
    }

    /**
     * 获得设备型号
     *
     * @return
     */
    private String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取厂商信息
     */
    private String getManufacturer() {
        return Build.MANUFACTURER;
    }


    /**
     * 三星
     *
     * @param context
     */
    private void goToSamsungappsMarket(Context context, String url, String PackageNam) {
        try {
            Uri uri = Uri.parse( "http://www.samsungapps.com/appquery/appDetail.as?appId=" + PackageNam );
            Intent goToMarket = new Intent();
            goToMarket.setClassName( "com.sec.android.app.samsungapps", "com.sec.android.app.samsungapps.Main" );
            goToMarket.setData( uri );
            context.startActivity( goToMarket );
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            getACTION( context, url );
        }
    }

    /**
     * 乐视
     *
     * @param context
     * @param packageName
     */
    private void goToLeTVStoreDetail(Context context, String url, String packageName) {
        try {
            Intent intent = new Intent();
            intent.setClassName( "com.letv.app.appstore", "com.letv.app.appstore.appmodule.details.DetailsActivity" );
            intent.setAction( "com.letv.app.appstore.appdetailactivity" );
            intent.putExtra( "packageName", packageName );
            context.startActivity( intent );
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            getACTION( context, url );
        }
    }
}
