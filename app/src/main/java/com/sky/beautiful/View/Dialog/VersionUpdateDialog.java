package com.sky.beautiful.View.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.beautiful.R;
import com.sky.beautiful.Utils.DisplayUtils;

/**
 * @Time : 2018/1/4 no 下午5:58
 * @USER : vvguoliang
 * @File : VersionUpdateDialog.java
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

@SuppressWarnings("ConstantConditions")
public class VersionUpdateDialog extends Dialog {

    public VersionUpdateDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String comtext;
        private String Confirm;
        private String cancel;
        private OnClickListener itemseButtonClickListenerConfirm;
        private OnClickListener itemseButtonClickListenercancel;

        public Builder(Context context) {
            this.context = context.getApplicationContext();
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public VersionUpdateDialog.Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public VersionUpdateDialog.Builder setContext(String comtext) {
            this.comtext = comtext;
            return this;
        }

        public void setItemsConfirm(String Confirm, OnClickListener listener) {
            this.itemseButtonClickListenerConfirm = listener;
            this.Confirm = Confirm;
        }

        public void setItemsCancel(String cancel, OnClickListener listener) {
            this.itemseButtonClickListenercancel = listener;
            this.cancel = cancel;
        }

        public VersionUpdateDialog create() {
            LayoutInflater inflater = LayoutInflater.from(context);
            final VersionUpdateDialog dialog = new VersionUpdateDialog(context, R.style.Dialog);//customDialog
            @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.dialog_version_update_view, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

             /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置,
         * 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
//            Window dialogWindow = dialog.getWindow();
//            WindowManager.LayoutParams lp;
//            if (dialogWindow != null) {
//                lp = dialogWindow.getAttributes();
//                dialogWindow.setGravity(Gravity.CENTER);
//                lp.width = DisplayUtils.dip2px(context, 300); // 宽度
//                dialogWindow.setAttributes(lp);
//            }

            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);

            TextView version_conteont = dialog.findViewById(R.id.version_conteont);
            TextView version_clear = dialog.findViewById(R.id.version_clear);
            TextView version_button = dialog.findViewById(R.id.version_button);

            version_conteont.setText(comtext);
            version_button.setText(Confirm);
            version_clear.setText(cancel);

            if (null != itemseButtonClickListenerConfirm) {
                version_button.setOnClickListener(view -> itemseButtonClickListenerConfirm.onClick(dialog, DialogInterface.BUTTON_POSITIVE));
            }
            if (null != itemseButtonClickListenercancel) {
                version_clear.setOnClickListener(v -> itemseButtonClickListenercancel.onClick(dialog, DialogInterface.BUTTON_POSITIVE));
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }
}
