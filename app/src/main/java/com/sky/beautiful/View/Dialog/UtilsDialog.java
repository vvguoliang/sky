package com.sky.beautiful.View.Dialog;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sky.beautiful.R;
import com.sky.beautiful.Utils.AppUtil;
import com.sky.beautiful.Utils.TakePictureManager;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @Time : 2017/12/26 no 下午6:35
 * @USER : vvguoliang
 * @File : UtilsDialog.java
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

public class UtilsDialog {
    /**
     * 单例对象实例
     */
    private static class UtilsDialogHolder {
        static final UtilsDialog INSTANCE = new UtilsDialog();
    }

    public static UtilsDialog getInstance() {
        return UtilsDialog.UtilsDialogHolder.INSTANCE;
    }

    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private UtilsDialog() {
    }

    public void getVersion(Context context, long GradeNo, String msg) {
        VersionUpdateDialog.Builder versionUpdateDialog = new VersionUpdateDialog.Builder(context);
        versionUpdateDialog.setContext("尊敬的v" + GradeNo + "会员,诚邀您参加与香瓜社区v" + AppUtil.getInstance().getChannel(context, 0) + "版本的优先体验");
        versionUpdateDialog.setTitle(msg);
        versionUpdateDialog.setItemsCancel("稍后再说", (dialog, which) -> dialog.dismiss());
        versionUpdateDialog.setItemsConfirm("立即下载", (dialog, which) -> dialog.dismiss());
        versionUpdateDialog.create().show();
    }

}
