package com.sky.beautiful.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.sky.beautiful.SykApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by vvguoliang on 2017/6/24.
 * <p>
 * APP 操作
 */
@SuppressWarnings("JavaDoc")
@SuppressLint("HardwareIds")
public class AppUtil {

    /**
     * 单例对象实例
     */
    private static class AppUtilHolder {
        static final AppUtil INSTANCE = new AppUtil();
    }

    public static AppUtil getInstance() {
        return AppUtilHolder.INSTANCE;
    }

    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private AppUtil() {
    }

    /**
     * 屏幕分辨率
     *
     * @param context
     * @return
     */
    public int[] Dispay(Activity context) {
        WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();
        return new int[]{screenWidth, screenHeight};
    }

    //设置EditText可输入和不可输入状态
    public void editTextable(EditText editText, boolean editable) {
        if (!editable) { // disable editing password
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            editText.setClickable(false); // user navigates with wheel and selects widget
        } else { // enable editing of password
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.setClickable(true);
        }
    }

    public void getList(List<Map<String, Object>> list, String data, TextView textView) {
        if (list != null && list.size() > 0) {
            for (int i = 0; list.size() > i; i++) {
                if (data.equals(list.get(i).get("listNo") + "")) {
                    textView.setText(list.get(i).get("name").toString());//需要查询 学历
                }
            }
        } else {
            textView.setText("您的信息出错,请退出这个页面");//需要查询 学历
        }
    }

    public void getList(List<Map<String, Object>> list, String data, EditText textView) {
        for (int i = 0; list.size() > i; i++) {
            if (data.equals(list.get(i).get("listNo") + "")) {
                textView.setText(list.get(i).get("name").toString());//需要查询 学历
            }
        }
    }

    /**
     * encodeBase64File:(将文件转成base64 字符串). <br/>
     *
     * @return
     * @throws Exception
     * @since JDK 1.6
     */
    public String encodeBase64File(File file) {
        FileInputStream inputFile;
        byte[] buffer = new byte[(int) file.length()];
        try {
            inputFile = new FileInputStream(file);
            inputFile.read(buffer);
            inputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    public List<Map<String, Object>> getList(String[] stringline) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (stringline != null && stringline.length > 0) {
            for (String anEducation : stringline) {
                Map<String, Object> map = new HashMap<>();
                map.put("boolean", "2");
                map.put("name", anEducation);
                mapList.add(map);
            }
            return mapList;
        } else {
            return null;
        }

    }

    public Long setNumber(Context context, String text, String namestring) {
        String stringa = SharedPreferencesUtils.get(context, namestring, "").toString();
        if (!TextUtils.isEmpty(stringa)) {
            List<Map<String, Object>> mapList = SharedPreferencesUtils.getInfo(context, stringa);
            for (int i = 0; mapList.size() > i; i++) {
                if (!TextUtils.isEmpty(text)) {
                    if (text.equals(mapList.get(i).get("name"))) {
                        return Long.parseLong(mapList.get(i).get("listNo").toString());
                    }
                }
            }
        }
        return 0L;
    }

    /**
     * 定位GPS
     */
    public final String LOCATION = "location";
    public final String LOCATION_ACTION = "locationAction";

    public final int MY_PERMISSIONS_REQUEST_CAMERA = 100;//拍照权限
    public final int MY_PERMISSIONS_REQUEST_READ_SD = 101;//读SD卡权限
    public final int MY_PERMISSIONS_REQUEST_WRITE_SK = 102;//写SD卡权限
    public final int MY_PERMISSIONS_REQUEST_READ_SD_PHOTOALBUM = 103;//写SD卡权限
    public final int MY_PERMISSIONS_REQUEST_WRITE_SK_PHOTOALBUM = 104;//写SD卡权限
    public final int CAPTURE_IMAGE_REQUEST = 104;//拍照后的返回值
    public final int LOAD_IMAGE_REQUEST = 105;//相册的返回值
    public final int CLIP_IMAGE_REQUEST = 106;//剪裁图片的返回值
    public final String IMAGE_TYPE = "image/*";
    public File mOutFile;//图片uri路径
    public File mImageFile = null;//图片file路径
    public final int MY_PERMISSIONS_READ_PHONE_STATE = 107;
    public final int REQUEST_PERMISSION_SETTING = 108;//跳进设置页面
    public final int MY_PERMISSIONS_INT = 109;//弹出框返回  108   109 公共权限设置
    public final int MY_PERMISSIONS_REQUEST_CONTACT = 110;
    public final int MY_PERMISSIONS_READ_CONTACTS = 111;
    public final int MY_PERMISSIONS_READ_SMS = 114;
    public final int PAGE_INTO_LIVENESS = 112;
    public final int INTO_IDCARDSCAN_PAGE = 113;

    public final int TIME = 2000;


    public Integer mBuildVersion = Build.VERSION.SDK_INT;//当前SDK版本


    public String PHONE_MOVE = "10086";

    public String PHONE_UNICOM = "10010";

    public String PHONE_TELECOM = "10000";


    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称 版本号
     */
    public String getVersionName(int code, Context context) {
        try {
            PackageManager packageManager = SykApplication.getContextObject().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(SykApplication.getContextObject().getPackageName(), 0);
            if (code == 1) {
                return packageInfo.versionName;
            } else {
                return packageInfo.versionCode + "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getManager(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 判断摄像头是否可用
     * 主要针对6.0 之前的版本，现在主要是依靠try...catch... 报错信息，感觉不太好，
     * 以后有更好的方法的话可适当替换
     *
     * @return
     */
    public boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            // setParameters 是针对魅族MX5 做的。MX5 通过Camera.open() 拿到的Camera
            // 对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
        }
        return canUse;
    }

    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    @SuppressLint("ObsoleteSdkInt")
    public Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }

    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = 0;// 手机屏幕的宽度
        int height = 0;
        if (windowManager != null) {
            width = windowManager.getDefaultDisplay().getWidth();
            height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        }
        int result[] = {width, height};
        return result;
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^1(2|3|4|5|6|7|8|9)\\d{9}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    private static final String CHANNEL_KEY = "UMENG_CHANNEL";

    /**
     * 返回市场。 如果获取失败返回defaultChannel
     *
     * @param context
     * @return
     */
    public String getChannel(Context context, int id_channle) {
        // 从apk中获取
        String mChannel = getAppMetaData(context, CHANNEL_KEY);
        return mChannle(id_channle, mChannel);
    }

    private String getChannelFromApk1(Context context, String channelKey) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(channelKey);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * 获取application中指定的meta-data。本例中，调用方法时key就是UMENG_CHANNEL
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    /**
     * 渠道id
     */
    private String mChannle(int id_channle, String channle) {
        if ("QD0111".equals(channle)) {
            if (id_channle == 1) {
                return "安卓应用市场" + "QD0111";
            } else {
                return "QD0111";
            }
        } else if ("QD0026".equals(channle)) {
            if (id_channle == 1) {
                return "360手机助手" + "QD0026";
            } else {
                return "QD0026";
            }
        } else if ("QD0029".equals(channle)) {
            if (id_channle == 1) {
                return "豌豆荚开发者中心" + "QD0029";
            } else {
                return "QD0029";
            }
        } else if ("QD0107".equals(channle)) {
            if (id_channle == 1) {
                return "木蚂蚁开发者中心" + "QD0107";
            } else {
                return "QD0107";
            }
        } else if ("QD0035".equals(channle)) {
            if (id_channle == 1) {
                return "小米应用商店" + "QD0035";
            } else {
                return "QD0035";
            }
        } else if ("QD0085".equals(channle)) {
            if (id_channle == 1) {
                return "华为应用商店" + "QD0085";
            } else {
                return "QD0085";
            }
        } else if ("QD0028".equals(channle)) {
            if (id_channle == 1) {
                return "PP助手开发者中心" + "QD0028";
            } else {
                return "QD0028";
            }
        } else if ("QD0030".equals(channle)) {
            if (id_channle == 1) {
                return "安智开发者联盟" + "QD0030";
            } else {
                return "QD0030";
            }
        } else if ("QD0021".equals(channle)) {
            if (id_channle == 1) {
                return "OPPO商店" + "QD0021";
            } else {
                return "QD0021";
            }
        } else if ("QD0025".equals(channle)) {
            if (id_channle == 1) {
                return "魅族商店" + "QD0025";
            } else {
                return "QD0025";
            }
        } else if ("QD0022".equals(channle)) {
            if (id_channle == 1) {
                return "VIVO商店" + "QD0022";
            } else {
                return "QD0022";
            }
        } else if ("QD0018".equals(channle)) {
            if (id_channle == 1) {
                return "联通沃商店" + "QD0018";
            } else {
                return "QD0018";
            }
        } else if ("QD0024".equals(channle)) {
            if (id_channle == 1) {
                return "搜狗手机助手" + "QD0024";
            } else {
                return "QD0024";
            }
        } else if ("QD0108".equals(channle)) {
            if (id_channle == 1) {
                return "应用汇" + "QD0108";
            } else {
                return "QD0108";
            }
        } else if ("QD0109".equals(channle)) {
            if (id_channle == 1) {
                return "酷派" + "QD0109";
            } else {
                return "QD0109";
            }
        } else if ("QD0034".equals(channle)) {
            if (id_channle == 1) {
                return "应用宝" + "QD0034";
            } else {
                return "QD0034";
            }
        } else if ("QD0031".equals(channle)) {
            if (id_channle == 1) {
                return "历趣市场" + "QD0031";
            } else {
                return "QD0031";
            }
        } else if ("QD0112".equals(channle)) {
            if (id_channle == 1) {
                return "机锋开发者平台" + "QD0112";
            } else {
                return "QD0112";
            }
        } else if ("QD0113".equals(channle)) {
            if (id_channle == 1) {
                return "自然" + "QD0113";
            } else {
                return "QD0113";
            }
        } else if ("QD0106".equals(channle)) {
            if (id_channle == 1) {
                return "三星" + "QD0106";
            } else {
                return "QD0106";
            }
        } else if ("QD0110".equals(channle)) {
            if (id_channle == 1) {
                return "神马" + "QD0110";
            } else {
                return "QD0110";
            }
        } else if ("QD0023".equals(channle)) {
            if (id_channle == 1) {
                return "百度手机助手" + "QD0023";
            } else {
                return "QD0023";
            }
        } else if ("QD0020".equals(channle)) {
            if (id_channle == 1) {
                return "sogou开发者" + "QD0020";
            } else {
                return "QD0020";
            }
        } else if ("QD0019".equals(channle)) {
            if (id_channle == 1) {
                return "优亿市场" + "QD0019";
            } else {
                return "QD0019";
            }
        } else if ("QD0016".equals(channle)) {
            if (id_channle == 1) {
                return "91手机商城发布中心" + "QD0016";
            } else {
                return "QD0016";
            }
        } else if ("QD0033".equals(channle)) {
            if (id_channle == 1) {
                return "联想乐商店" + "QD0033";
            } else {
                return "QD0033";
            }
        } else if ("QD0032".equals(channle)) {
            if (id_channle == 1) {
                return "锤子科技开发者" + "QD0032";
            } else {
                return "QD0032";
            }
        } else if ("QD0012".equals(channle)) {
            if (id_channle == 1) {
                return "乐视" + "QD0012";
            } else {
                return "QD0012";
            }
        } else if ("QD0115".equals(channle)) {
            if (id_channle == 1) {
                return "酷安" + "QD0115";
            } else {
                return "QD0115";
            }
        } else if ("QD0116".equals(channle)) {
            if (id_channle == 1) {
                return "阿里平台" + "QD0116";
            } else {
                return "QD0115";
            }
        } else if ("QD0007".equals(channle)) {
            if (id_channle == 1) {
                return "今日头条" + "QD0007";
            } else {
                return "QD0007";
            }
        } else if ("QD0081".equals(channle)) {
            if (id_channle == 1) {
                return "广点通-分包-3" + "QD0081";
            } else {
                return "QD0081";
            }
        } else if ("QD0080".equals(channle)) {
            if (id_channle == 1) {
                return "广点通-分包-2" + "QD0080";
            } else {
                return "QD0080";
            }
        } else if ("QD0079".equals(channle)) {
            if (id_channle == 1) {
                return "广点通-分包-3" + "QD0079";
            } else {
                return "QD0079";
            }
        } else if ("QD0073".equals(channle)) {
            if (id_channle == 1) {
                return "新浪A" + "QD0073";
            } else {
                return "QD0073";
            }
        } else if ("QD0074".equals(channle)) {
            if (id_channle == 1) {
                return "新浪B" + "QD0074";
            } else {
                return "QD0074";
            }
        } else if ("QD0075".equals(channle)) {
            if (id_channle == 1) {
                return "新浪C" + "QD0075";
            } else {
                return "QD0075";
            }
        } else if ("QD0054".equals(channle)) {
            if (id_channle == 1) {
                return "应用宝-推广" + "QD0054";
            } else {
                return "QD0054";
            }
        } else if ("QD0009".equals(channle)) {
            if (id_channle == 1) {
                return "今日头条" + "QD0009";
            } else {
                return "QD0009";
            }
        } else if ("QD0099".equals(channle)) {
            if (id_channle == 1) {
                return "乐推-E" + "QD0099";
            } else {
                return "QD0099";
            }
        } else if ("QD0098".equals(channle)) {
            if (id_channle == 1) {
                return "乐推-D" + "QD0098";
            } else {
                return "QD0098";
            }
        } else if ("QD0097".equals(channle)) {
            if (id_channle == 1) {
                return "乐推-C" + "QD0097";
            } else {
                return "QD0097";
            }
        } else if ("QD0096".equals(channle)) {
            if (id_channle == 1) {
                return "乐推-B" + "QD0096";
            } else {
                return "QD0096";
            }
        } else if ("QD0095".equals(channle)) {
            if (id_channle == 1) {
                return "乐推-A" + "QD0095";
            } else {
                return "QD0095";
            }
        } else if ("QD0057".equals(channle)) {
            if (id_channle == 1) {
                return "应用宝推广" + "QD0057";
            } else {
                return "QD0057";
            }
        } else if ("QD0056".equals(channle)) {
            if (id_channle == 1) {
                return "应用宝推广" + "QD0056";
            } else {
                return "QD0056";
            }
        } else {
            return "";
        }
    }

    /**
     * 判断是否有SD卡
     *
     * @return
     */
    public boolean existSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private long lastClickTime;

    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
