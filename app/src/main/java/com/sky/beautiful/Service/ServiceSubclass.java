package com.sky.beautiful.Service;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.WindowManager;
import android.app.AlertDialog.Builder;

import com.sky.beautiful.SykApplication;
import com.sky.beautiful.Utils.AppUtil;
import com.sky.beautiful.Utils.SharedPreferencesUtils;
import com.sky.beautiful.View.Dialog.VersionUpdateDialog;

/**
 * @Time : 2018/1/4 no 下午9:13
 * @USER : vvguoliang
 * @File : ServiceSubclass.java
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

public class ServiceSubclass extends Service {

    private String msg = "";

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onCreate() {
        System.out.println("---> Service onCreate()");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        System.out.println("---> Service onStart()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        msg = intent.getStringExtra("msg");
        VersionUpdateDialog.Builder versionUpdateDialog = new VersionUpdateDialog.Builder(this);
        long GradeNo = 0;
        if (Long.parseLong(SharedPreferencesUtils.get(this, "GradeNo", "1").toString()) - 1 == 0) {
            GradeNo = 0;
        } else if (Long.parseLong(SharedPreferencesUtils.get(this, "GradeNo", "1").toString()) - 1 == 1) {
            GradeNo = 1;
        } else if (Long.parseLong(SharedPreferencesUtils.get(this, "GradeNo", "1").toString()) - 1 == 2) {
            GradeNo = 2;
        }
        versionUpdateDialog.setContext("尊敬的v" + GradeNo + "会员,诚邀您参加与香瓜社区v" + AppUtil.getInstance().getChannel(SykApplication.getContextObject(), 0) + "版本的优先体验");
        versionUpdateDialog.setTitle(msg);
        versionUpdateDialog.setItemsCancel("稍后再说", (dialog, which) -> dialog.dismiss());
        versionUpdateDialog.setItemsConfirm("立即下载", (dialog, which) -> dialog.dismiss());
        versionUpdateDialog.create().show();
        VersionUpdateDialog alertDialog = versionUpdateDialog.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("---> Service onDestroy()");
    }
}
