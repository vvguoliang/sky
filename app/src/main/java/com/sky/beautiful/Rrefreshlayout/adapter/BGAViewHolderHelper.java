package com.sky.beautiful.Rrefreshlayout.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.beautiful.Utils.DisplayUtils;
import com.sky.beautiful.Utils.UtilsInterface;
import com.sky.beautiful.View.Random.RandomColor;

import java.util.ArrayList;
import java.util.Random;

/**
 * @Time : 2017/12/21 no 下午2:59
 * @USER : vvguoliang
 * @File : BGAViewHolderHelper.java
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

public class BGAViewHolderHelper implements View.OnLongClickListener, CompoundButton.OnCheckedChangeListener, View.OnTouchListener {

    protected final SparseArrayCompat<View> mViews;
    protected BGAOnItemChildClickListener mOnItemChildClickListener;
    protected BGAOnItemChildLongClickListener mOnItemChildLongClickListener;
    protected BGAOnItemChildCheckedChangeListener mOnItemChildCheckedChangeListener;
    protected BGAOnRVItemChildTouchListener mOnRVItemChildTouchListener;
    protected View mConvertView;
    protected Context mContext;
    protected int mPosition;
    protected BGARecyclerViewHolder mRecyclerViewHolder;
    protected RecyclerView mRecyclerView;

    protected AdapterView mAdapterView;
    /**
     * 留着以后作为扩充对象
     */
    protected Object mObj;

    public BGAViewHolderHelper(ViewGroup adapterView, View convertView) {
        mViews = new SparseArrayCompat<>();
        mAdapterView = (AdapterView) adapterView;
        mConvertView = convertView;
        mContext = convertView.getContext();
    }

    public BGAViewHolderHelper(RecyclerView recyclerView, BGARecyclerViewHolder recyclerViewHolder) {
        mViews = new SparseArrayCompat<>();
        mRecyclerView = recyclerView;
        mRecyclerViewHolder = recyclerViewHolder;
        mConvertView = mRecyclerViewHolder.itemView;
        mContext = mConvertView.getContext();
    }

    public BGARecyclerViewHolder getRecyclerViewHolder() {
        return mRecyclerViewHolder;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public int getPosition() {
        if (mRecyclerViewHolder != null) {
            return mRecyclerViewHolder.getAdapterPositionWrapper();
        }
        return mPosition;
    }

    /**
     * 设置item子控件点击事件监听器
     *
     * @param onItemChildClickListener
     */
    public void setOnItemChildClickListener(BGAOnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    /**
     * 为id为viewId的item子控件设置点击事件监听器
     *
     * @param viewId
     */
    public void setItemChildClickListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(new BGAOnNoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (mOnItemChildClickListener != null) {
                        if (mRecyclerView != null) {
                            mOnItemChildClickListener.onItemChildClick(mRecyclerView, v, getPosition());
                        } else if (mAdapterView != null) {
                            mOnItemChildClickListener.onItemChildClick(mAdapterView, v, getPosition());
                        }
                    }
                }
            });
        }
    }

    /**
     * 为id为viewId的item子控件设置点击事件监听器
     *
     * @param viewId1
     * @param viewId2
     */
    public void setItemChildClickListener(@IdRes int viewId1, @IdRes int viewId2) {
        View view1 = getView(viewId1);
        View view2 = getView(viewId2);
        if (view1 != null) {
            view1.setOnClickListener(new BGAOnNoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (mOnItemChildClickListener != null) {
                        if (mRecyclerView != null) {
                            mOnItemChildClickListener.onItemChildClick(mRecyclerView, v, view2, getPosition());
                        } else if (mAdapterView != null) {
                            mOnItemChildClickListener.onItemChildClick(mAdapterView, v, view2, getPosition());
                        }
                    }
                }
            });
        }
    }

    /**
     * 设置item子控件长按事件监听器
     *
     * @param onItemChildLongClickListener
     */
    public void setOnItemChildLongClickListener(BGAOnItemChildLongClickListener onItemChildLongClickListener) {
        mOnItemChildLongClickListener = onItemChildLongClickListener;
    }

    /**
     * 为id为viewId的item子控件设置长按事件监听器
     *
     * @param viewId
     */
    public void setItemChildLongClickListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnLongClickListener(this);
        }
    }

    /**
     * 设置 RecyclerView 中的 item 子控件触摸事件监听器
     *
     * @param onRVItemChildTouchListener
     */
    public void setOnRVItemChildTouchListener(BGAOnRVItemChildTouchListener onRVItemChildTouchListener) {
        mOnRVItemChildTouchListener = onRVItemChildTouchListener;
    }

    /**
     * 为 id 为 viewId 的 RecyclerView 的 item 子控件设置触摸事件监听器
     *
     * @param viewId
     */
    public void setRVItemChildTouchListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnTouchListener(this);
        }
    }

    /**
     * 设置item子控件选中状态变化事件监听器
     *
     * @param onItemChildCheckedChangeListener
     */
    public void setOnItemChildCheckedChangeListener(BGAOnItemChildCheckedChangeListener onItemChildCheckedChangeListener) {
        mOnItemChildCheckedChangeListener = onItemChildCheckedChangeListener;
    }

    /**
     * 为id为viewId的item子控件设置选中状态变化事件监听器
     *
     * @param viewId
     */
    public void setItemChildCheckedChangeListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null && view instanceof CompoundButton) {
            ((CompoundButton) view).setOnCheckedChangeListener(this);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (mOnRVItemChildTouchListener != null && mRecyclerView != null) {
            return mOnRVItemChildTouchListener.onRvItemChildTouch(mRecyclerViewHolder, view, motionEvent);
        }
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemChildLongClickListener != null) {
            if (mRecyclerView != null) {
                return mOnItemChildLongClickListener.onItemChildLongClick(mRecyclerView, v, getPosition());
            } else if (mAdapterView != null) {
                return mOnItemChildLongClickListener.onItemChildLongClick(mAdapterView, v, getPosition());
            }
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mOnItemChildCheckedChangeListener != null) {
            if (mRecyclerView != null) {
                BGARecyclerViewAdapter recyclerViewAdapter;

                RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
                if (adapter instanceof BGAHeaderAndFooterAdapter) {
                    recyclerViewAdapter = (BGARecyclerViewAdapter) ((BGAHeaderAndFooterAdapter) adapter).getInnerAdapter();
                } else {
                    recyclerViewAdapter = (BGARecyclerViewAdapter) adapter;
                }
                if (!recyclerViewAdapter.isIgnoreCheckedChanged()) {
                    mOnItemChildCheckedChangeListener.onItemChildCheckedChanged(mRecyclerView, buttonView, getPosition(), isChecked);
                }
            } else if (mAdapterView != null) {
                if (!((BGAAdapterViewAdapter) mAdapterView.getAdapter()).isIgnoreCheckedChanged()) {
                    mOnItemChildCheckedChangeListener.onItemChildCheckedChanged(mAdapterView, buttonView, getPosition(), isChecked);
                }
            }
        }
    }

    /**
     * 通过控件的Id获取对应的控件，如果没有则加入mViews，则从item根控件中查找并保存到mViews中
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 通过ImageView的Id获取ImageView
     *
     * @param viewId
     * @return
     */
    public ImageView getImageView(@IdRes int viewId) {
        return getView(viewId);
    }

    /**
     * 通过TextView的Id获取TextView
     *
     * @param viewId
     * @return
     */
    public TextView getTextView(@IdRes int viewId) {
        return getView(viewId);
    }

    /**
     * 获取item的根控件
     *
     * @return
     */
    public View getConvertView() {
        return mConvertView;
    }

    public void setObj(Object obj) {
        mObj = obj;
    }

    public Object getObj() {
        return mObj;
    }

    /**
     * 设置对应id的控件的文本内容
     *
     * @param viewId
     * @param text
     * @return
     */
    public BGAViewHolderHelper setText(@IdRes int viewId, CharSequence text) {
        if (text == null) {
            text = "";
        }
        getTextView(viewId).setText(text);
        return this;
    }

    /**
     * 设置对应id的控件的文本内容
     *
     * @param viewId
     * @param text
     * @return
     */
    public BGAViewHolderHelper setTextColor(@IdRes int viewId, CharSequence text, int mPosition) {
        if (text == null) {
            text = "";
        }
        getTextView(viewId).setText(text);
//        RandomColor randomColor = new RandomColor();
//        int[] colors = randomColor.randomColor(100);
        ArrayList<int[]> rgbs = getRGB();
        getTextView(viewId).setBackgroundColor(Color.rgb(rgbs.get(0)[0], rgbs.get(0)[1], rgbs.get(0)[2]));
        return this;
    }

    /**
     * 一个算法　－　判断是深颜色还是浅颜色的算法
     * <p>
     * $grayLevel = $R * 0.299 + $G * 0.587 + $B * 0.114;
     * if ($grayLevel >= 192) {
     * // add shadow
     * }
     */
    private static ArrayList<int[]> getRGB() {
        //1.　随机生成　rgb
        //2.　判断深颜色还是浅颜色
        //3.　继续随机生成　rgb
        //4.　判读是深颜色还是浅颜色　：和第一次相反，返回两个rgb值
        //5.  颜色深的作为背景，颜色浅的作为文字；
        //6.　第一个存放深颜色，第二个存放浅颜色
        ArrayList<int[]> colorList = new ArrayList<int[]>();
        int[] rgb = getRanRGB();
        while (true) {
            if (isShenRGB(rgb)) {
                colorList.add(rgb);
                break;
            } else {
                rgb = getRanRGB();
            }
        }
        int[] rgbQ = getRanRGB();
        while (true) {
            if (isShenRGB(rgbQ)) {
                rgbQ = getRanRGB();
            } else {
                colorList.add(rgbQ);
                break;
            }
        }
        return colorList;
    }

    /**
     * 获得随机颜色
     *
     * @return
     */
    private static int[] getRanRGB() {
        int[] colors = new int[3];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = (int) (Math.random() * 256);
        }
        return colors;
    }

    /**
     * 判断是不是深颜色
     *
     * @return
     */
    private static boolean isShenRGB(int[] colors) {
        int grayLevel = (int) (colors[0] * 0.299 + colors[1] * 0.587 + colors[2] * 0.114);
        if (grayLevel >= 100) {
            return true;
        }
        return false;
    }

    /**
     * 设置对应id的控件的文本内容
     *
     * @param viewId
     * @param stringResId 字符串资源id
     * @return
     */
    public BGAViewHolderHelper setText(@IdRes int viewId, @StringRes int stringResId) {
        getTextView(viewId).setText(stringResId);
        return this;
    }

    /**
     * 设置对应id的控件的文字大小，单位为 sp
     *
     * @param viewId
     * @param size   文字大小，单位为 sp
     * @return
     */
    public BGAViewHolderHelper setTextSizeSp(@IdRes int viewId, float size) {
        getTextView(viewId).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    /**
     * 设置对应id的控件的文字是否为粗体
     *
     * @param viewId
     * @param isBold 是否为粗体
     * @return
     */
    public BGAViewHolderHelper setIsBold(@IdRes int viewId, boolean isBold) {
        getTextView(viewId).getPaint().setTypeface(isBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        return this;
    }

    /**
     * 设置对应id的控件的html文本内容
     *
     * @param viewId
     * @param source html文本
     * @return
     */
    public BGAViewHolderHelper setHtml(@IdRes int viewId, String source) {
        if (source == null) {
            source = "";
        }
        getTextView(viewId).setText(Html.fromHtml(source));
        return this;
    }

    /**
     * 设置对应id的控件是否选中
     *
     * @param viewId
     * @param checked
     * @return
     */
    public BGAViewHolderHelper setChecked(@IdRes int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public BGAViewHolderHelper setTag(@IdRes int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public BGAViewHolderHelper setTag(@IdRes int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public BGAViewHolderHelper setVisibility(@IdRes int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public BGAViewHolderHelper setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public BGAViewHolderHelper setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public BGAViewHolderHelper setImageGilde(@IdRes int viewId, String getGidle) {
        ImageView view = getView(viewId);
        UtilsInterface.getInstance().UtilGlideAngle(mContext, getGidle, view);
        return this;
    }

    public BGAViewHolderHelper setImageGilde(@IdRes int viewId, String getGidle, int mPosition, int[] posis, int Height, int Width) {
        ImageView view = getView(viewId);
        UtilsInterface.getInstance().UtilGlideAngle(mContext, getGidle, view);
        return this;
    }
    public BGAViewHolderHelper setImageGilde(@IdRes int viewId, int mPosition, int[] posis, int Height, int Width) {
        ImageView view = getView(viewId);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        if (mPosition == 0) {
            view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ((posis[0] * Height) / Width) / DisplayUtils.dip2px(mContext, 0.5f)));
        } else {
            view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ((posis[0] * Height) / Width) / DisplayUtils.dip2px(mContext, 0.3f)));
        }
        return this;
    }

    public BGAViewHolderHelper setImageGilde(@IdRes int viewId, String getGidle, int mPosition) {
        ImageView view = getView(viewId);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        UtilsInterface.getInstance().UtilGlideAngle(mContext, getGidle, view);
        return this;
    }

    public BGAViewHolderHelper setImage(@IdRes int viewId1, boolean mPosition) {
        ImageView view = getView(viewId1);
        if (mPosition) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * @param viewId
     * @param textColorResId 颜色资源id
     * @return
     */
    public BGAViewHolderHelper setTextColorRes(@IdRes int viewId, @ColorRes int textColorResId) {
        getTextView(viewId).setTextColor(mContext.getResources().getColor(textColorResId));
        return this;
    }

    /**
     * @param viewId
     * @param textColor 颜色值
     * @return
     */
    public BGAViewHolderHelper setTextColor(@IdRes int viewId, int textColor) {
        getTextView(viewId).setTextColor(textColor);
        return this;
    }

    /**
     * @param viewId
     * @param backgroundResId 背景资源id
     * @return
     */
    public BGAViewHolderHelper setBackgroundRes(@IdRes int viewId, int backgroundResId) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundResId);
        return this;
    }

    /**
     * @param viewId
     * @param color  颜色值
     * @return
     */
    public BGAViewHolderHelper setBackgroundColor(@IdRes int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * @param viewId
     * @param colorResId 颜色值资源id
     * @return
     */
    public BGAViewHolderHelper setBackgroundColorRes(@IdRes int viewId, @ColorRes int colorResId) {
        View view = getView(viewId);
        view.setBackgroundColor(mContext.getResources().getColor(colorResId));
        return this;
    }

    /**
     * @param viewId
     * @param imageResId 图像资源id
     * @return
     */
    public BGAViewHolderHelper setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    /**
     * 设置字体是否为粗体
     *
     * @param viewId
     * @param isBold
     * @return
     */
    public BGAViewHolderHelper setBold(@IdRes int viewId, boolean isBold) {
        getTextView(viewId).getPaint().setTypeface(isBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        return this;
    }
}
