package com.sky.beautiful.Rrefreshlayout.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.sky.beautiful.R;

import java.lang.reflect.Method;

/**
 * @Time : 2017/12/21 no 下午1:30
 * @USER : vvguoliang
 * @File : BGASwipeItemLayout.java
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

public class BGASwipeItemLayout extends FrameLayout {

    private static final String TAG = BGASwipeItemLayout.class.getSimpleName();
    private static final String INSTANCE_STATUS = "instance_status";
    private static final String STATUS_OPEN_CLOSE = "status_open_close";
    private static final int VEL_THRESHOLD = 400;
    private ViewDragHelper mDragHelper;
    private View mTopView;
    private View mBottomView;
    private int mSpringDistance;
    private int mDragRange;
    private BGASwipeItemLayout.SwipeDirection mSwipeDirection;
    private BGASwipeItemLayout.BottomModel mBottomModel;
    private BGASwipeItemLayout.Status mCurrentStatus;
    private BGASwipeItemLayout.Status mPreStatus;
    private int mTopLeft;
    private MarginLayoutParams mTopLp;
    private MarginLayoutParams mBottomLp;
    private float mDragRatio;
    private BGASwipeItemLayout.BGASwipeItemLayoutDelegate mDelegate;
    private GestureDetectorCompat mGestureDetectorCompat;
    private OnLongClickListener mOnLongClickListener;
    private OnClickListener mOnClickListener;
    private boolean mSwipeable;
    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener;
    private Runnable mCancelPressedTask;
    private ViewDragHelper.Callback mDragHelperCallback;

    public BGASwipeItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BGASwipeItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mSpringDistance = 0;
        this.mSwipeDirection = BGASwipeItemLayout.SwipeDirection.Left;
        this.mBottomModel = BGASwipeItemLayout.BottomModel.PullOut;
        this.mCurrentStatus = BGASwipeItemLayout.Status.Closed;
        this.mPreStatus = this.mCurrentStatus;
        this.mSwipeable = true;
        this.mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if(Math.abs(distanceX) > Math.abs(distanceY)) {
                    BGASwipeItemLayout.this.requestParentDisallowInterceptTouchEvent();
                    return true;
                } else {
                    return false;
                }
            }

            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if(Math.abs(velocityX) > Math.abs(velocityY)) {
                    BGASwipeItemLayout.this.requestParentDisallowInterceptTouchEvent();
                    return true;
                } else {
                    return false;
                }
            }

            public boolean onSingleTapConfirmed(MotionEvent e) {
                BGASwipeItemLayout.this.setPressed(false);
                return BGASwipeItemLayout.this.isClosed() && BGASwipeItemLayout.this.performClick();
            }

            public boolean onSingleTapUp(MotionEvent e) {
                if(BGASwipeItemLayout.this.isClosed()) {
                    BGASwipeItemLayout.this.setPressed(true);
                    return true;
                } else {
                    return false;
                }
            }

            public void onLongPress(MotionEvent e) {
                if(BGASwipeItemLayout.this.isClosed()) {
                    BGASwipeItemLayout.this.setPressed(true);
                    BGASwipeItemLayout.this.postDelayed(BGASwipeItemLayout.this.mCancelPressedTask, 300L);
                    BGASwipeItemLayout.this.performLongClick();
                }

            }

            public boolean onDoubleTap(MotionEvent e) {
                if(BGASwipeItemLayout.this.isClosed()) {
                    BGASwipeItemLayout.this.setPressed(true);
                    return true;
                } else {
                    return false;
                }
            }

            public boolean onDoubleTapEvent(MotionEvent e) {
                if(BGASwipeItemLayout.this.isClosed()) {
                    BGASwipeItemLayout.this.setPressed(false);
                    return true;
                } else {
                    return false;
                }
            }
        };
        this.mCancelPressedTask = new Runnable() {
            public void run() {
                BGASwipeItemLayout.this.setPressed(false);
            }
        };
        this.mDragHelperCallback = new ViewDragHelper.Callback() {
            public boolean tryCaptureView(View child, int pointerId) {
                return BGASwipeItemLayout.this.mSwipeable && child == BGASwipeItemLayout.this.mTopView;
            }

            public int getViewVerticalDragRange(View child) {
                return 0;
            }

            public int clampViewPositionVertical(View child, int top, int dy) {
                return BGASwipeItemLayout.this.getPaddingTop() + BGASwipeItemLayout.this.mTopLp.topMargin;
            }

            public int getViewHorizontalDragRange(View child) {
                return BGASwipeItemLayout.this.mDragRange + BGASwipeItemLayout.this.mSpringDistance;
            }

            public int clampViewPositionHorizontal(View child, int left, int dx) {
                int minTopLeft;
                int maxTopLeft;
                if(BGASwipeItemLayout.this.mSwipeDirection == BGASwipeItemLayout.SwipeDirection.Left) {
                    minTopLeft = BGASwipeItemLayout.this.getPaddingLeft() + BGASwipeItemLayout.this.mTopLp.leftMargin - (BGASwipeItemLayout.this.mDragRange + BGASwipeItemLayout.this.mSpringDistance);
                    maxTopLeft = BGASwipeItemLayout.this.getPaddingLeft() + BGASwipeItemLayout.this.mTopLp.leftMargin;
                } else {
                    minTopLeft = BGASwipeItemLayout.this.getPaddingLeft() + BGASwipeItemLayout.this.mTopLp.leftMargin;
                    maxTopLeft = BGASwipeItemLayout.this.getPaddingLeft() + BGASwipeItemLayout.this.mTopLp.leftMargin + BGASwipeItemLayout.this.mDragRange + BGASwipeItemLayout.this.mSpringDistance;
                }

                return Math.min(Math.max(minTopLeft, left), maxTopLeft);
            }

            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                BGASwipeItemLayout.this.mTopLeft = left;
                int topViewHorizontalOffset = Math.abs(BGASwipeItemLayout.this.mTopLeft - (BGASwipeItemLayout.this.getPaddingLeft() + BGASwipeItemLayout.this.mTopLp.leftMargin));
                if(topViewHorizontalOffset > BGASwipeItemLayout.this.mDragRange) {
                    BGASwipeItemLayout.this.mDragRatio = 1.0F;
                } else {
                    BGASwipeItemLayout.this.mDragRatio = 1.0F * (float)topViewHorizontalOffset / (float)BGASwipeItemLayout.this.mDragRange;
                }

                float alpha = 0.1F + 0.9F * BGASwipeItemLayout.this.mDragRatio;
                ViewCompat.setAlpha(BGASwipeItemLayout.this.mBottomView, alpha);
                BGASwipeItemLayout.this.dispatchSwipeEvent();
                BGASwipeItemLayout.this.invalidate();
                BGASwipeItemLayout.this.requestLayout();
            }

            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                int finalLeft = BGASwipeItemLayout.this.getPaddingLeft() + BGASwipeItemLayout.this.mTopLp.leftMargin;
                if(BGASwipeItemLayout.this.mSwipeDirection == BGASwipeItemLayout.SwipeDirection.Left) {
                    if(xvel < -400.0F || BGASwipeItemLayout.this.mPreStatus == BGASwipeItemLayout.Status.Closed && xvel < 400.0F && BGASwipeItemLayout.this.mDragRatio >= 0.3F || BGASwipeItemLayout.this.mPreStatus == BGASwipeItemLayout.Status.Opened && xvel < 400.0F && BGASwipeItemLayout.this.mDragRatio >= 0.7F) {
                        finalLeft -= BGASwipeItemLayout.this.mDragRange;
                    }
                } else if(xvel > 400.0F || BGASwipeItemLayout.this.mPreStatus == BGASwipeItemLayout.Status.Closed && xvel > -400.0F && BGASwipeItemLayout.this.mDragRatio >= 0.3F || BGASwipeItemLayout.this.mPreStatus == BGASwipeItemLayout.Status.Opened && xvel > -400.0F && BGASwipeItemLayout.this.mDragRatio >= 0.7F) {
                    finalLeft += BGASwipeItemLayout.this.mDragRange;
                }

                BGASwipeItemLayout.this.mDragHelper.settleCapturedViewAt(finalLeft, BGASwipeItemLayout.this.getPaddingTop() + BGASwipeItemLayout.this.mTopLp.topMargin);
                ViewCompat.postInvalidateOnAnimation(BGASwipeItemLayout.this);
            }
        };
        this.initAttrs(context, attrs);
        this.initProperty();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BGASwipeItemLayout);
        int N = typedArray.getIndexCount();

        for(int i = 0; i < N; ++i) {
            this.initAttr(typedArray.getIndex(i), typedArray);
        }

        typedArray.recycle();
    }

    private void initAttr(int attr, TypedArray typedArray) {
        int pullOutBottomMode;
        if(attr == R.styleable.BGASwipeItemLayout_bga_sil_swipeDirection) {
            pullOutBottomMode = typedArray.getInt(attr, this.mSwipeDirection.ordinal());
            if(pullOutBottomMode == BGASwipeItemLayout.SwipeDirection.Right.ordinal()) {
                this.mSwipeDirection = BGASwipeItemLayout.SwipeDirection.Right;
            }
        } else if(attr == R.styleable.BGASwipeItemLayout_bga_sil_bottomMode) {
            pullOutBottomMode = typedArray.getInt(attr, this.mBottomModel.ordinal());
            if(pullOutBottomMode == BGASwipeItemLayout.BottomModel.LayDown.ordinal()) {
                this.mBottomModel = BGASwipeItemLayout.BottomModel.LayDown;
            }
        } else if(attr == R.styleable.BGASwipeItemLayout_bga_sil_springDistance) {
            this.mSpringDistance = typedArray.getDimensionPixelSize(attr, this.mSpringDistance);
            if(this.mSpringDistance < 0) {
                throw new IllegalStateException("bga_sil_springDistance不能小于0");
            }
        } else if(attr == R.styleable.BGASwipeItemLayout_bga_sil_swipeAble) {
            this.mSwipeable = typedArray.getBoolean(attr, this.mSwipeable);
        }

    }

    private void initProperty() {
        this.mDragHelper = ViewDragHelper.create(this, this.mDragHelperCallback);
        this.mDragHelper.setEdgeTrackingEnabled(1);
        this.mGestureDetectorCompat = new GestureDetectorCompat(this.getContext(), this.mSimpleOnGestureListener);
    }

    public void setDelegate(BGASwipeItemLayout.BGASwipeItemLayoutDelegate delegate) {
        this.mDelegate = delegate;
    }

    public void setSwipeAble(boolean swipeAble) {
        this.mSwipeable = swipeAble;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if(this.getChildCount() != 2) {
            throw new IllegalStateException(BGASwipeItemLayout.class.getSimpleName() + "必须有且只有两个子控件");
        } else {
            this.mTopView = this.getChildAt(1);
            this.mBottomView = this.getChildAt(0);
            this.mBottomView.setVisibility(INVISIBLE);
            this.mTopLp = (MarginLayoutParams)this.mTopView.getLayoutParams();
            this.mBottomLp = (MarginLayoutParams)this.mBottomView.getLayoutParams();
            this.mTopLeft = this.getPaddingLeft() + this.mTopLp.leftMargin;
        }
    }

    public void computeScroll() {
        if(this.mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getAction() == 3 || ev.getAction() == 1) {
            this.mDragHelper.cancel();
        }

        return this.mDragHelper.shouldInterceptTouchEvent(ev) && this.mGestureDetectorCompat.onTouchEvent(ev);
    }

    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        this.mOnClickListener = l;
    }

    public void setOnLongClickListener(OnLongClickListener l) {
        super.setOnLongClickListener(l);
        this.mOnLongClickListener = l;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(this.insideAdapterView()) {
            if(this.mOnClickListener == null) {
                this.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        BGASwipeItemLayout.this.performAdapterViewItemClick();
                    }
                });
            }

            if(this.mOnLongClickListener == null) {
                this.setOnLongClickListener(new OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        BGASwipeItemLayout.this.performAdapterViewItemLongClick();
                        return true;
                    }
                });
            }
        }

    }

    private void performAdapterViewItemClick() {
        ViewParent t = this.getParent();
        if(t instanceof AdapterView) {
            AdapterView view = (AdapterView)t;
            int p = view.getPositionForView(this);
            if(p != -1) {
                view.performItemClick(view.getChildAt(p - view.getFirstVisiblePosition()), p, view.getAdapter().getItemId(p));
            }
        }

    }

    private boolean performAdapterViewItemLongClick() {
        ViewParent t = this.getParent();
        if(!(t instanceof AdapterView)) {
            return false;
        } else {
            AdapterView view = (AdapterView)t;
            int p = view.getPositionForView(this);
            if(p == -1) {
                return false;
            } else {
                long vId = view.getItemIdAtPosition(p);
                boolean handled = false;

                try {
                    @SuppressLint("PrivateApi") Method m = AbsListView.class.getDeclaredMethod("performLongPress", View.class, Integer.TYPE, Long.TYPE);
                    m.setAccessible(true);
                    handled = (Boolean) m.invoke(view, this, Integer.valueOf(p), Long.valueOf(vId));
                } catch (Exception var8) {
                    var8.printStackTrace();
                    if(view.getOnItemLongClickListener() != null) {
                        handled = view.getOnItemLongClickListener().onItemLongClick(view, this, p, vId);
                    }

                    if(handled) {
                        view.performHapticFeedback(0);
                    }
                }

                return handled;
            }
        }
    }

    private boolean insideAdapterView() {
        return this.getAdapterView() != null;
    }

    private AdapterView getAdapterView() {
        ViewParent t = this.getParent();
        return t instanceof AdapterView?(AdapterView)t:null;
    }

    private void requestParentDisallowInterceptTouchEvent() {
        ViewParent parent = this.getParent();
        if(parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }

    }

    public boolean onTouchEvent(MotionEvent event) {
        this.mDragHelper.processTouchEvent(event);
        this.mGestureDetectorCompat.onTouchEvent(event);
        return true;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.mDragRange = this.mBottomView.getMeasuredWidth() + this.mBottomLp.leftMargin + this.mBottomLp.rightMargin;
        int topTop = this.getPaddingTop() + this.mTopLp.topMargin;
        int topBottom = this.getMeasuredHeight() - this.getPaddingBottom() - this.mTopLp.bottomMargin;
        int topRight = this.mTopLeft + this.mTopView.getMeasuredWidth();
        int bottomTop = this.getPaddingTop() + this.mBottomLp.topMargin;
        int bottomBottom = this.getMeasuredHeight() - this.getPaddingBottom() - this.mBottomLp.bottomMargin;
        int bottomLeft;
        int bottomRight;
        int minBottomLeft;
        if(this.mSwipeDirection == BGASwipeItemLayout.SwipeDirection.Left) {
            if(this.mBottomModel == BGASwipeItemLayout.BottomModel.LayDown) {
                bottomRight = this.getMeasuredWidth() - this.getPaddingRight() - this.mBottomLp.rightMargin;
                bottomLeft = bottomRight - this.mBottomView.getMeasuredWidth();
            } else {
                bottomLeft = this.mTopLeft + this.mTopView.getMeasuredWidth() + this.mTopLp.rightMargin + this.mBottomLp.leftMargin;
                minBottomLeft = this.getMeasuredWidth() - this.getPaddingRight() - this.mBottomView.getMeasuredWidth() - this.mBottomLp.rightMargin;
                bottomLeft = Math.max(bottomLeft, minBottomLeft);
                bottomRight = bottomLeft + this.mBottomView.getMeasuredWidth();
            }
        } else if(this.mBottomModel == BGASwipeItemLayout.BottomModel.LayDown) {
            bottomLeft = this.getPaddingLeft() + this.mBottomLp.leftMargin;
            bottomRight = bottomLeft + this.mBottomView.getMeasuredWidth();
        } else {
            bottomLeft = this.mTopLeft - this.mDragRange;
            minBottomLeft = this.getPaddingLeft() + this.mBottomLp.leftMargin;
            bottomLeft = Math.min(minBottomLeft, bottomLeft);
            bottomRight = bottomLeft + this.mBottomView.getMeasuredWidth();
        }

        this.mBottomView.layout(bottomLeft, bottomTop, bottomRight, bottomBottom);
        this.mTopView.layout(this.mTopLeft, topTop, topRight, topBottom);
    }

    public void openWithAnim() {
        this.mPreStatus = BGASwipeItemLayout.Status.Moving;
        this.smoothSlideTo(1);
    }

    public void closeWithAnim() {
        this.mPreStatus = BGASwipeItemLayout.Status.Moving;
        this.smoothSlideTo(0);
    }

    public void open() {
        this.mPreStatus = BGASwipeItemLayout.Status.Moving;
        this.slideTo(1);
    }

    public void close() {
        this.mPreStatus = BGASwipeItemLayout.Status.Moving;
        this.slideTo(0);
    }

    public boolean isOpened() {
        return this.mCurrentStatus == BGASwipeItemLayout.Status.Opened || this.mCurrentStatus == BGASwipeItemLayout.Status.Moving && this.mPreStatus == BGASwipeItemLayout.Status.Opened;
    }

    public boolean isClosed() {
        return this.mCurrentStatus == BGASwipeItemLayout.Status.Closed || this.mCurrentStatus == BGASwipeItemLayout.Status.Moving && this.mPreStatus == BGASwipeItemLayout.Status.Closed;
    }

    public View getTopView() {
        return this.mTopView;
    }

    public View getBottomView() {
        return this.mBottomView;
    }

    private void smoothSlideTo(int isOpen) {
        if(this.mDragHelper.smoothSlideViewTo(this.mTopView, this.getCloseOrOpenTopViewFinalLeft(isOpen), this.getPaddingTop() + this.mTopLp.topMargin)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }

    private void slideTo(int isOpen) {
        if(isOpen == 1) {
            this.mBottomView.setVisibility(VISIBLE);
            ViewCompat.setAlpha(this.mBottomView, 1.0F);
            this.mCurrentStatus = BGASwipeItemLayout.Status.Opened;
            if(this.mDelegate != null) {
                this.mDelegate.onBGASwipeItemLayoutOpened(this);
            }
        } else {
            this.mBottomView.setVisibility(INVISIBLE);
            this.mCurrentStatus = BGASwipeItemLayout.Status.Closed;
            if(this.mDelegate != null) {
                this.mDelegate.onBGASwipeItemLayoutClosed(this);
            }
        }

        this.mPreStatus = this.mCurrentStatus;
        this.mTopLeft = this.getCloseOrOpenTopViewFinalLeft(isOpen);
        this.requestLayout();
    }

    private int getCloseOrOpenTopViewFinalLeft(int isOpen) {
        int left = this.getPaddingLeft() + this.mTopLp.leftMargin;
        if(this.mSwipeDirection == BGASwipeItemLayout.SwipeDirection.Left) {
            left -= isOpen * this.mDragRange;
        } else {
            left += isOpen * this.mDragRange;
        }

        return left;
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instance_status", super.onSaveInstanceState());
        bundle.putInt("status_open_close", this.mCurrentStatus.ordinal());
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle) {
            Bundle bundle = (Bundle)state;
            if(bundle.getInt("status_open_close") == BGASwipeItemLayout.Status.Opened.ordinal()) {
                this.open();
            } else {
                this.close();
            }

            super.onRestoreInstanceState(bundle.getParcelable("instance_status"));
        } else {
            super.onRestoreInstanceState(state);
        }

    }

    private void dispatchSwipeEvent() {
        BGASwipeItemLayout.Status preStatus = this.mCurrentStatus;
        this.updateCurrentStatus();
        if(this.mCurrentStatus != preStatus) {
            if(this.mCurrentStatus == BGASwipeItemLayout.Status.Closed) {
                this.mBottomView.setVisibility(INVISIBLE);
                if(this.mDelegate != null && this.mPreStatus != this.mCurrentStatus) {
                    this.mDelegate.onBGASwipeItemLayoutClosed(this);
                }

                this.mPreStatus = BGASwipeItemLayout.Status.Closed;
            } else if(this.mCurrentStatus == BGASwipeItemLayout.Status.Opened) {
                if(this.mDelegate != null && this.mPreStatus != this.mCurrentStatus) {
                    this.mDelegate.onBGASwipeItemLayoutOpened(this);
                }

                this.mPreStatus = BGASwipeItemLayout.Status.Opened;
            } else if(this.mPreStatus == BGASwipeItemLayout.Status.Closed) {
                this.mBottomView.setVisibility(VISIBLE);
                if(this.mDelegate != null) {
                    this.mDelegate.onBGASwipeItemLayoutStartOpen(this);
                }
            }
        }

    }

    private void updateCurrentStatus() {
        if(this.mSwipeDirection == BGASwipeItemLayout.SwipeDirection.Left) {
            if(this.mTopLeft == this.getPaddingLeft() + this.mTopLp.leftMargin - this.mDragRange) {
                this.mCurrentStatus = BGASwipeItemLayout.Status.Opened;
            } else if(this.mTopLeft == this.getPaddingLeft() + this.mTopLp.leftMargin) {
                this.mCurrentStatus = BGASwipeItemLayout.Status.Closed;
            } else {
                this.mCurrentStatus = BGASwipeItemLayout.Status.Moving;
            }
        } else if(this.mTopLeft == this.getPaddingLeft() + this.mTopLp.leftMargin + this.mDragRange) {
            this.mCurrentStatus = BGASwipeItemLayout.Status.Opened;
        } else if(this.mTopLeft == this.getPaddingLeft() + this.mTopLp.leftMargin) {
            this.mCurrentStatus = BGASwipeItemLayout.Status.Closed;
        } else {
            this.mCurrentStatus = BGASwipeItemLayout.Status.Moving;
        }

    }

    public interface BGASwipeItemLayoutDelegate {
        void onBGASwipeItemLayoutOpened(BGASwipeItemLayout var1);

        void onBGASwipeItemLayoutClosed(BGASwipeItemLayout var1);

        void onBGASwipeItemLayoutStartOpen(BGASwipeItemLayout var1);
    }

    public enum Status {
        Opened,
        Closed,
        Moving;

        Status() {
        }
    }

    public enum BottomModel {
        PullOut,
        LayDown;

        BottomModel() {
        }
    }

    public enum SwipeDirection {
        Left,
        Right;

        SwipeDirection() {
        }
    }
}
