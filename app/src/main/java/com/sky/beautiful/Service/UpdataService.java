package com.sky.beautiful.Service;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;

import com.sky.beautiful.R;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.Utils.ToatUtils;

import java.io.File;
import java.util.Objects;

/**
 * @Time : 2018/1/5 no 下午1:24
 * @USER : vvguoliang
 * @File : UpdataService.java
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

public class UpdataService extends Service {

    /**
     * 安卓系统下载类
     **/
    private DownloadManager manager;
    /**
     * 接收下载完的广播
     **/
    private DownloadCompleteReceiver receiver;
    private String url;
    private String DOWNLOADPATH = "/apk/";//下载路径，如果不定义自己的路径，6.0的手机不自动安装

    /**
     * 初始化下载器
     **/
    private void initDownManager() {
        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        receiver = new DownloadCompleteReceiver();
        //设置下载地址
        DownloadManager.Request down = new DownloadManager.Request(Uri.parse(url));
        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                | DownloadManager.Request.NETWORK_WIFI);
        down.setAllowedOverRoaming(false);
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        down.setMimeType(mimeString);
        // 下载时，通知栏显示途中
        down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        // 显示下载界面
        down.setVisibleInDownloadsUi(true);
        // 设置下载后文件存放的位置
        down.setDestinationInExternalPublicDir(DOWNLOADPATH, "xgsq.apk");
        down.setTitle(this.getString(R.string.app_name));
        // 将下载请求放入队列
        manager.enqueue(down);
        //注册下载广播
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        url = intent.getStringExtra("url");
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + DOWNLOADPATH + "xgsq.apk";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {
            // 调用下载
            initDownManager();
        } catch (Exception e) {
            e.printStackTrace();
            ToatUtils.showShort1(getApplicationContext(), "下载失败");
            SharedPreferencesUtils.put(this, "startup_type", true);
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        if (receiver != null)
            // 注销下载广播
            unregisterReceiver(receiver);
        super.onDestroy();
    }

    // 接受下载完成后的intent
    class DownloadCompleteReceiver extends BroadcastReceiver {
        @SuppressLint("NewApi")
        @Override
        public void onReceive(Context context, Intent intent) {

            //判断是否下载完成的广播
            if (Objects.equals(intent.getAction(), DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                //获取下载的文件id
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (manager.getUriForDownloadedFile(downId) != null) {
                    //自动安装apk
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + DOWNLOADPATH + "xgsq.apk");
                    installApk(file, context);
                    //installAPK(context);
                } else {
                    ToatUtils.showShort1(context, "下载失败");
                    SharedPreferencesUtils.put(UpdataService.this, "updata", false);
                }
                //停止服务并关闭广播
                UpdataService.this.stopSelf();
            }
        }
    }

    //    @SuppressLint("ObsoleteSdkInt")
//    public void openFile(File file, Context context) {
//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.VIEW");
//        Uri data;
//        // 判断版本大于等于7.0
//        if (Build.VERSION.SDK_INT >= 24) {
//            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
//            data = FileProvider.getUriForFile(context, "com.sky.beautiful.fileProvider", file);
//            // 给目标应用一个临时授权
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        } else {
//            data = Uri.fromFile(file);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        intent.setDataAndType(data, "application/vnd.android.package-archive");
//        context.startActivity(intent);
//    }
    private static final String MIME_TYPE_APK = "application/vnd.android.package-archive";

    /**
     * 安装 apk 文件
     *
     * @param apkFile
     */
    public static void installApk(File apkFile, Context context) {
        Intent installApkIntent = new Intent();
        installApkIntent.setAction(Intent.ACTION_VIEW);
        installApkIntent.addCategory(Intent.CATEGORY_DEFAULT);
        installApkIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            installApkIntent.setDataAndType(FileProvider.getUriForFile(context, getFileProviderAuthority(context), apkFile), MIME_TYPE_APK);
            installApkIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            installApkIntent.setDataAndType(Uri.fromFile(apkFile), MIME_TYPE_APK);
        }

        if (context.getPackageManager().queryIntentActivities(installApkIntent, 0).size() > 0) {
            context.startActivity(installApkIntent);
        }
    }

    /**
     * 获取FileProvider的auth
     *
     * @return
     */
    private static String getFileProviderAuthority(Context context) {
        try {
            for (ProviderInfo provider : context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PROVIDERS).providers) {
                if (FileProvider.class.getName().equals(provider.name) && provider.authority.endsWith("FileProvider")) {
                    return provider.authority;
                }
            }
        } catch (PackageManager.NameNotFoundException ignore) {
        }
        return null;
    }

}
