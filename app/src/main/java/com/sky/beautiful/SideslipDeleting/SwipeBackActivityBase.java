package com.sky.beautiful.SideslipDeleting;

/**
 * Created by vvguoliang on 2017/12/19.
 *
 * 定义侧滑接口
 */

public interface SwipeBackActivityBase {

    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    public abstract void scrollToFinishActivity();
}
