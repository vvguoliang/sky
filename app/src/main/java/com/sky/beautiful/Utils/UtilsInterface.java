package com.sky.beautiful.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.sky.beautiful.GaussianBlur.FastBlurUtil;
import com.sky.beautiful.Model.ViewAtlas;
import com.sky.beautiful.R;
import com.sky.beautiful.RollingGraph.CustomBanner;
import com.sky.beautiful.SykApplication;
import com.sky.beautiful.View.GlideRoundTransform;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @Time : 2017/12/21 no 上午10:14
 * @USER : vvguoliang
 * @File : UtilsInterface.java
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

public class UtilsInterface implements com.sky.beautiful.Interface.UtilsInterface {

    @SuppressLint("NewApi")
    @Override
    public void UtilBlur(Context context, String pattern, String url, ImageView view) {
        new Thread(() -> {
            int scaleRatio = 0;
            if (TextUtils.isEmpty(pattern)) {
                scaleRatio = 0;
            } else if (scaleRatio < 0) {
                scaleRatio = 0;
            } else {
                scaleRatio = Integer.parseInt(pattern);
            }
            //                        下面的这个方法必须在子线程中执行
            final Bitmap blurBitmap2 = FastBlurUtil.GetUrlBitmap(url, scaleRatio);

            //                        刷新ui必须在主线程中执行
            SykApplication.runOnUIThread(() -> {
                view.setImageBitmap(blurBitmap2);
//                UtilsInterface.getInstance().UtilGlideAngle(context, blurBitmap2, view);
            });
        }).start();
    }

    @SuppressLint("NewApi")
    @Override
    public void UtilBlur1(Context context, String pattern, String url, ImageView view) {
        new Thread(() -> {
            int scaleRatio = 0;
            if (TextUtils.isEmpty(pattern)) {
                scaleRatio = 0;
            } else if (scaleRatio < 0) {
                scaleRatio = 0;
            } else {
                scaleRatio = Integer.parseInt(pattern);
            }
            //                        下面的这个方法必须在子线程中执行
            final Bitmap blurBitmap2 = FastBlurUtil.GetUrlBitmap(url, scaleRatio);

            //                        刷新ui必须在主线程中执行
            SykApplication.runOnUIThread(() -> {
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                view.setImageBitmap(blurBitmap2);
//                UtilsInterface.getInstance().UtilGlideAngle(context, blurBitmap2, view);
            });
        }).start();
    }

    @Override
    public void UtilGlide(Context context, Object url, View view) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.custom_mooc_icon)
                .error(R.mipmap.custom_mooc_icon)
                .priority(Priority.HIGH)
                .transform(new GlideRoundTransform(15));
        Glide.with(context)
                .load(url)
                .apply(options)
                .into((ImageView) view);
    }

    @Override
    public void UtilGlideUtilGlideTransformations(Context context, Object url, View view) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.custom_mooc_icon)
                .error(R.mipmap.custom_mooc_icon)
                .priority(Priority.HIGH)
                // 设置高斯模糊
                .bitmapTransform(new BlurTransformation(context, 14, 3))
                .transform(new GlideRoundTransform(15));
        Glide.with(context)
                .load(url)
                .apply(options)
                .into((ImageView) view);
    }

    @Override
    public void UtilGlideAngle(Context context, Object url, View view) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.custom_mooc_icon)
                .error(R.mipmap.custom_mooc_icon);
        Glide.with(context).load(url).apply(options).into((ImageView) view);
    }

    @Override
    public void UtilGlideAngle(Context context, Object url, ImageView view) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.custom_mooc_icon)
                .error(R.mipmap.custom_mooc_icon);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(url).apply(options).into(view);
    }

    @Override
    public void setBanner(Context context, CustomBanner customBanner, ArrayList beans) {
        if (beans != null && beans.size() > 1) {
            customBanner.setPages(new CustomBanner.ViewCreator<String>() {
                @Override
                public View createView(Context context, int position) {
                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    return imageView;
                }

                @Override
                public void updateUI(Context context, View view, int position, String entity) {
                    UtilsInterface.getInstance().UtilGlide(context, entity, view);
                }
            }, beans)
//                    //设置指示器为普通指示器
//                    .setIndicatorStyle(CustomBanner.IndicatorStyle.ORDINARY)
//                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                    .setIndicatorRes(R.drawable.shape_point_select, R.drawable.shape_point_unselect)
//                    //设置指示器的方向
//                    .setIndicatorGravity(CustomBanner.IndicatorGravity.CENTER)
//                    //设置指示器的指示点间隔
//                    .setIndicatorInterval(20)
                    //设置自动翻页
                    .startTurning(3000);
        } else {
            customBanner.setPages(new CustomBanner.ViewCreator() {
                @Override
                public View createView(Context context, int position) {
                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    return imageView;
                }

                @Override
                public void updateUI(Context context, View view, int position, Object o) {
                    UtilsInterface.getInstance().UtilGlideAngle(context, o, view);
                }
            }, beans).stopTurning();
        }
    }

    @Override
    public void setBannerAngle(Context context, CustomBanner customBanner, ArrayList beans) {
        if (beans != null && beans.size() > 1) {
            customBanner.setPages(new CustomBanner.ViewCreator<String>() {
                @Override
                public View createView(Context context, int position) {
                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    return imageView;
                }

                @Override
                public void updateUI(Context context, View view, int position, String entity) {
                    if (position == beans.size() - 1) {
                        UtilsInterface.getInstance().UtilBlur(context, "5", entity, (ImageView) view);
                    } else {
                        UtilsInterface.getInstance().UtilGlideAngle(context, entity, view);
                    }
                }
            }, beans).startTurning();
        } else {
            customBanner.setPages(new CustomBanner.ViewCreator() {
                @Override
                public View createView(Context context, int position) {
                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    return imageView;
                }

                @Override
                public void updateUI(Context context, View view, int position, Object o) {
                    UtilsInterface.getInstance().UtilGlideAngle(context, o, view);
                }
            }, beans).stopTurning();
        }
    }

    @Override
    public void setBannerActivity(Context context, CustomBanner customBanner, ArrayList beans, List<ViewAtlas> viewAtlases) {
        if (beans != null && beans.size() > 1) {
            customBanner.setPages(new CustomBanner.ViewCreator<String>() {
                @Override
                public View createView(Context context, int position) {
                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    return imageView;
                }

                @Override
                public void updateUI(Context context, View view, int position, String entity) {
                    if (viewAtlases.get(position).getIsSee().equals("1")) {
                        UtilsInterface.getInstance().UtilBlur(context, "5", entity, (ImageView) view);
                    } else {
                        UtilsInterface.getInstance().UtilGlideAngle(context, entity, view);
                    }
                }
            }, beans).startTurning();
        } else {
            customBanner.setPages(new CustomBanner.ViewCreator() {
                @Override
                public View createView(Context context, int position) {
                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    return imageView;
                }

                @Override
                public void updateUI(Context context, View view, int position, Object o) {
                    UtilsInterface.getInstance().UtilGlideAngle(context, o, view);
                }
            }, beans).stopTurning();
        }
    }

    @Override
    public TranslateAnimation setMoveToViewLocation(int time_int) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(time_int * 5);
        return mHiddenAction;
    }

    @SuppressLint({"NewApi", "RestrictedApi"})
    @Override
    public void setDisableShiftMode(BottomNavigationView navigationView) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(true);

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TextWatcher setTextWatcher(View view) {
        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
            }
        };
        return textWatcher;
    }

    @Override
    public Bitmap setImageMatrix(Context context, int bitmap) {
        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), bitmap);
        float[] mColorMatrix = new float[20];
//        reset();
        Bitmap bmp = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), Bitmap.Config.ARGB_8888);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(mColorMatrix);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap1, 0, 0, paint);
        return bmp;
    }

    /**
     * 单例对象实例
     */
    private static class UtilsInterfaceHolder {
        static final UtilsInterface INSTANCE = new UtilsInterface();
    }

    public static UtilsInterface getInstance() {
        return UtilsInterface.UtilsInterfaceHolder.INSTANCE;
    }

    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private UtilsInterface() {
    }
}
