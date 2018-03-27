package com.sky.beautiful.View;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.sky.beautiful.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2018/1/8 no 上午5:16
 * @USER : vvguoliang
 * @File : CarouselViewFlipper.java
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

public class CarouselViewFlipper extends ViewFlipper {


    /**
     * 视图切换监听
     */
    public interface CarouselChanged {
        void onChanged(int oldIndex, int newIndex, View view);
    }

    public interface OnClickListener {
        void onclick(StringBuffer stringBuffer);
    }

    private List<View> views;
    private CarouselChanged carouselChanged;
    private OnClickListener onClickListener;
    private GestureDetector gestureDetector;
    private GestureDetector.OnGestureListener gestureListener;
    //是否滑动中，该标志用来避免gesture的滑动和滑动View中item的点击冲突
    private boolean fling = false;

    private StringBuffer stringBuffer;

    public CarouselViewFlipper(Context context) {
        super(context);
        init(context);
    }

    public CarouselViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(final Context ctx) {
        stringBuffer = new StringBuffer();
        gestureListener = new GestureDetector.OnGestureListener() {
            long lastClick = 0;

            @Override
            public boolean onDown(MotionEvent e) {
                fling = false;
                if (System.currentTimeMillis() - lastClick <= 500) {
                    stringBuffer.append("1");
                    return true;
                }
                lastClick = System.currentTimeMillis();
                stringBuffer.append("2");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (System.currentTimeMillis() - lastClick <= 500) {
                    stringBuffer.append("3");
                    if (onClickListener != null) {
                        onClickListener.onclick(stringBuffer);
                    }
                    for (int i = 0; stringBuffer.length() > i; i++) {
                        stringBuffer.delete(i, stringBuffer.length());
                    }
                    return true;
                }
                lastClick = System.currentTimeMillis();
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                fling = true;
                for (int i = 0; stringBuffer.length() > i; i++) {
                    stringBuffer.delete(i, stringBuffer.length());
                }
//                if (e1.getX() - e2.getX() > 100) {
//                    setInAnimation(rightInAnim);
//                    setOutAnimation(leftOutAnim);
//                    int old = getDisplayedChild();
//                    showNext();
//                    int newindex = getDisplayedChild();
//                    triggerChanged(old,newindex);
//                    return true;
//                } else if (e1.getX() - e2.getX() < -100) {
//                    setInAnimation(leftInAnim);
//                    setOutAnimation(rightOutAnim);
//                    int old = getDisplayedChild();
//                    showPrevious();
//                    int newindex = getDisplayedChild();
//                    triggerChanged(old,newindex);
//                    return true;
//                }
                final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200;
                // Fling left  从右向左划
                if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                    CarouselViewFlipper.this.setOutAnimation(AnimationUtils.loadAnimation(ctx,
                            R.anim.push_left_out));
                    CarouselViewFlipper.this.setInAnimation(AnimationUtils.loadAnimation(ctx,
                            R.anim.push_left_in));
                    int old = getDisplayedChild();
                    CarouselViewFlipper.this.showNext();//显示下一个视图
                    int newindex = getDisplayedChild();
                    triggerChanged(old, newindex);
                    return true;
                } else if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                    // Fling right 从左向右划
                    CarouselViewFlipper.this.setOutAnimation(AnimationUtils.loadAnimation(ctx, R.anim.push_right_out));
                    CarouselViewFlipper.this.setInAnimation(AnimationUtils.loadAnimation(ctx, R.anim.push_right_in));
                    int old = getDisplayedChild();
                    CarouselViewFlipper.this.showPrevious();//显示上一个视图
                    int newindex = getDisplayedChild();
                    triggerChanged(old, newindex);
                    return true;
                } else if (e1.getY() - e2.getY() > FLING_MIN_DISTANCE && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
                    //从下向上划
                    CarouselViewFlipper.this.setOutAnimation(AnimationUtils.loadAnimation(ctx,
                            R.anim.push_up_down));
                    CarouselViewFlipper.this.setInAnimation(AnimationUtils.loadAnimation(ctx,
                            R.anim.push_up_in));

                    int old = getDisplayedChild();
                    CarouselViewFlipper.this.showNext();//显示下一个视图
                    int newindex = getDisplayedChild();
                    triggerChanged(old, newindex);
                    return true;
                } else if (e2.getY() - e1.getY() > FLING_MIN_DISTANCE && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
                    //从上向下划
                    CarouselViewFlipper.this.setOutAnimation(AnimationUtils.loadAnimation(ctx,
                            R.anim.push_down_out));
                    CarouselViewFlipper.this.setInAnimation(AnimationUtils.loadAnimation(ctx,
                            R.anim.push_down_in));
                    int old = getDisplayedChild();
                    CarouselViewFlipper.this.showPrevious();//显示上一个视图
                    int newindex = getDisplayedChild();
                    triggerChanged(old, newindex);
                    return true;
                }
//                CarouselViewFlipper.this.setEnabled( true );
                return false;
            }
        };
        gestureDetector = new GestureDetector(gestureListener);
        views = new ArrayList<>();
//        // 图片从右侧滑入
//        rightInAnim = new TranslateAnimation( Animation.RELATIVE_TO_PARENT, 1.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f );
//        rightInAnim.setDuration( duration );
//
//        // 图片从左侧滑出
//        leftOutAnim = new TranslateAnimation( Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, -1.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f );
//        leftOutAnim.setDuration( duration );
//
//        // 图片从右侧滑出
//        rightOutAnim = new TranslateAnimation( Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 1.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f );
//        rightOutAnim.setDuration( duration );
//
//        // 图片从左侧滑入
//        leftInAnim = new TranslateAnimation( Animation.RELATIVE_TO_PARENT, -1.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f );
//        leftInAnim.setDuration( duration );
    }

    private void triggerChanged(int oldIndex, int newIndex) {
        if (views.size() <= 0)
            return;
        if (carouselChanged != null) {
            carouselChanged.onChanged(oldIndex, newIndex, views.get(newIndex));
        }
    }

    public CarouselChanged getCarouselChanged() {
        return carouselChanged;
    }

    public void setCarouselChanged(CarouselChanged carouselChanged) {
        this.carouselChanged = carouselChanged;
    }

    public OnClickListener getOnClickListen() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    /**
     * 设置要轮播的视图列表
     *
     * @param views
     */
    public void setViews(List<View> views) {
        if (views != null && views.size() > 0) {
            this.views.clear();
            this.removeAllViews();
        }
        this.views.addAll(views);
        for (View v : views) {
            this.addView(v);
        }
    }

    /**
     * 拦截触屏，当按下时，fling=false，允许点击子视图中的item，当手势抬起时，为避免和滑动发生冲突，判断fling状态，
     * 如果处于滑动中，并且是手势抬起，返回true，不再向下传递，使item点击事件失效
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN)
            fling = false;
        else if (fling && ev.getAction() == MotionEvent.ACTION_UP) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 分发触屏，交给gesture
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}
