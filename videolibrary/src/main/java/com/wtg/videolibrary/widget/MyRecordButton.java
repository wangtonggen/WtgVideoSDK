package com.wtg.videolibrary.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.wtg.videolibrary.utils.ScreenUtils;

/**
 * author: wtg  2019/10/29 0029
 * desc: 录制视频按钮
 */
public class MyRecordButton extends View {
    private Context mContext;

    /**
     * 绘制中间的画笔
     */
    private Paint mRectPaint;

    /**
     * 圆环画笔
     */
    private Paint mCirclePaint;

    /**
     * 矩形圆角(corner的值等于矩形宽度的一半时，矩形就是圆形了)
     */
    private float corner;

    /**
     * 圆环半径
     */
    private float circleRadius;

    /**
     * 圆环宽度
     */
    private float circleStrokeWidth;

    /**
     * 矩形宽
     */
    private float rectWidth;

    /**
     * 圆环内半径
     */
    private float mMinCircleRadius;

    /**
     * 最大圆环半径
     */
    private float mMaxCircleRadius;

    /**
     * 最小矩形宽
     */
    private float mMinRectWidth;

    /**
     * 最大矩形宽
     */
    private float mMaxRectWidth;

    /**
     * 最小圆角
     */
    private float mMinCorner;

    /**
     * 最大圆角
     */
    private float mMaxCorner;

    /**
     * 最小圆环宽度
     */
    private float mMinCircleStrokeWidth;

    /**
     * 最大圆环宽度
     */
    private float mMaxCircleStrokeWidth;

    /**
     * 矩形
     */
    private RectF mRectF = new RectF();

    /**
     * 录像时间 s为单位 只有长按会用到此时间
     */
    private int recordTime = 15;

    /**
     * 自定义枚举类，标明录制几种状态，默认ORIGIN
     */
    private RecordMode mRecordMode = RecordMode.ORIGIN;

//    private RecordMode mRecordMode = RecordMode.LONG_CLICK;

    /**
     * 开始动画集合
     */
    private AnimatorSet mBeginAnimatorSet = new AnimatorSet();

    /**
     * 结束动画集合
     */
    private AnimatorSet mEndAnimatorSet = new AnimatorSet();

    /**
     * {@link (PorterDuff.Mode)}
     */
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);


    private Handler mHandler = new Handler();

    //长按
    private ClickRunnable mClickRunnable = new ClickRunnable();
    //单次点击
    private SingleClickRunnable singleClickRunnable = new SingleClickRunnable();

    /**
     * 自定义录制按钮监听接口
     */
    private OnRecordStateChangedListener mOnRecordStateChangedListener;

    private float mInitX;

    private float mInitY;

    /**
     * Down X
     */
    private float mDownRawX;

    /**
     * Down Y
     */
    private float mDownRawY;

    /**
     * 滑动比例，抖音根据滑动 调整焦距
     */
    private float mInfectionPoint;

//    private ScrollDirection mScrollDirection;


    /**
     * Cancel Flag
     */
    private boolean mHasCancel = false;

    public MyRecordButton(Context context) {
        this(context, null);
    }

    public MyRecordButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecordButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        setLayerType(LAYER_TYPE_HARDWARE, null);
        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setStyle(Paint.Style.FILL);
        mRectPaint.setColor(Color.parseColor("#F91069")); //内部录制按钮颜色

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.parseColor("#66F6A623"));
//        mCirclePaint.setColor(Color.parseColor("#F91069"));

        mMinCircleStrokeWidth = ScreenUtils.dip2px(mContext, 3);
        mMaxCircleStrokeWidth = ScreenUtils.dip2px(mContext, 12);
        circleStrokeWidth = mMinCircleStrokeWidth;
        mCirclePaint.setStrokeWidth(circleStrokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int centerX = width / 2;
        int centerY = height / 2;

        mMaxRectWidth = width / 3;
        mMinRectWidth = mMaxRectWidth * 0.6f;

        mMinCircleRadius = mMaxRectWidth / 2 + mMinCircleStrokeWidth + ScreenUtils.dip2px(mContext, 5);
        mMaxCircleRadius = (width / 2.0f) - mMaxCircleStrokeWidth;

        mMinCorner = ScreenUtils.dip2px(mContext, 5);
        mMaxCorner = mMaxRectWidth / 2;

        if (rectWidth == 0) {
            rectWidth = mMaxRectWidth;
        }
        if (circleRadius == 0) {
            circleRadius = mMinCircleRadius;
        }
        if (corner == 0) {
            corner = rectWidth / 2;
        }

        //初始圆环外边界
        mCirclePaint.setColor(Color.parseColor("#F6A623"));
        canvas.drawCircle(centerX, centerY, circleRadius, mCirclePaint);
        mCirclePaint.setXfermode(mXfermode);
        //初始圆环内边界
        mCirclePaint.setColor(Color.parseColor("#9900FF"));
        canvas.drawCircle(centerX, centerY, circleRadius - circleStrokeWidth, mCirclePaint);
        mCirclePaint.setXfermode(null);

        //初始画内圆
        mRectF.left = centerX - rectWidth / 2;
        mRectF.right = centerX + rectWidth / 2;
        mRectF.top = centerY - rectWidth / 2;
        mRectF.bottom = centerY + rectWidth / 2;
        canvas.drawRoundRect(mRectF, corner, corner, mRectPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.e("haha",mHasCancel+"");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("click", mRecordMode + "--");
                switch (mRecordMode) {
                    case ORIGIN://初始化
                    case LONG_CLICK://长按
                        if (inBeginRange(event)) {
                            mDownRawX = event.getRawX();
                            mDownRawY = event.getRawY();
                            mHandler.postDelayed(mClickRunnable, 200);
                        }
                        break;
                    case SINGLE_CLICK://单次点击
                        break;
                }

//                if (mRecordMode == RecordMode.LONG_CLICK && inBeginRange(event)) {
//                    mDownRawX = event.getRawX();
//                    mDownRawY = event.getRawY();
//                    mHandler.postDelayed(mClickRunnable, 200);
//                }
                break;
//            case MotionEvent.ACTION_MOVE:
//                if (!mHasCancel) {
//                    if (mRecordMode == RecordMode.LONG_CLICK) {
//                        float zoomPercentage = (mInfectionPoint - getY()) / mInitY;
//                        if (mOnRecordStateChangedListener != null) {
//                            mOnRecordStateChangedListener.onZoom(zoomPercentage);
//                        }
//                    }
//                }
//                break;
            case MotionEvent.ACTION_UP:
                switch (mRecordMode) {
                    case ORIGIN://初始化
                    case LONG_CLICK://长按 时间设置
                        if (!mHasCancel) {
                            if (mOnRecordStateChangedListener != null) {
                                mOnRecordStateChangedListener.onRecordStop();
                            }
                            resetLongClick();
                        } else {
                            mHasCancel = false;
                        }
                        break;
                    case SINGLE_CLICK://单次点击
                        if (inEndRange(event)) {
                            mDownRawX = event.getRawX();
                            mDownRawY = event.getRawY();
                            mHandler.postDelayed(singleClickRunnable, 200);
                            if (mOnRecordStateChangedListener != null) {
                                mOnRecordStateChangedListener.onRecordStop();
                            }
                        }
                        break;
                }
                break;
            default:
                break;
        }
        return true;
    }

    private boolean inBeginRange(MotionEvent event) {
        int centerX = getMeasuredWidth() / 2;
        int centerY = getMeasuredHeight() / 2;
        int minX = (int) (centerX - mMinCircleRadius);
        int maxX = (int) (centerX + mMinCircleRadius);
        int minY = (int) (centerY - mMinCircleRadius);
        int maxY = (int) (centerY + mMinCircleRadius);
        boolean isXInRange = event.getX() >= minX && event.getX() <= maxX;
        boolean isYInRange = event.getY() >= minY && event.getY() <= maxY;
        return isXInRange && isYInRange;
    }

    private boolean inEndRange(MotionEvent event) {
        int minX = 0;
        int maxX = getMeasuredWidth();
        int minY = 0;
        int maxY = getMeasuredHeight();
        boolean isXInRange = event.getX() >= minX && event.getX() <= maxX;
        boolean isYInRange = event.getY() >= minY && event.getY() <= maxY;
        return isXInRange && isYInRange;
    }

    private void resetLongClick() {
        mRecordMode = RecordMode.LONG_CLICK;
        mBeginAnimatorSet.cancel();
        startEndAnimation();
        setX(mInitX);
        setY(mInitY);
    }

    private void resetSingleClick() {
        mRecordMode = RecordMode.ORIGIN;
        mBeginAnimatorSet.cancel();
        startEndAnimation();
    }

    public RecordMode getmRecordMode() {
        return mRecordMode;
    }

    public void setmRecordMode(RecordMode mRecordMode) {
        this.mRecordMode = mRecordMode;
    }

    public void setRecordBurronConfig(RecordButtonConfig recordButtonConfig) {
        this.mRecordMode = recordButtonConfig.getButton_recode_mode();
        requestLayout();
        invalidate();
    }

    public void reset() {
        if (mRecordMode == RecordMode.LONG_CLICK) {
            resetLongClick();
            mRecordMode = RecordMode.ORIGIN;
        } else if (mRecordMode == RecordMode.SINGLE_CLICK) {
            resetSingleClick();
            mRecordMode = RecordMode.ORIGIN;
        } else if (mRecordMode == RecordMode.ORIGIN) {
            if (mBeginAnimatorSet.isRunning()) {
                mHasCancel = true;
                mBeginAnimatorSet.cancel();
                startEndAnimation();
                mHandler.removeCallbacks(mClickRunnable);
                mRecordMode = RecordMode.ORIGIN;
            }
        }

    }

    public void startClockRecord() {
        if (mRecordMode == RecordMode.ORIGIN) {
            startBeginAnimation();
            mRecordMode = RecordMode.SINGLE_CLICK;
        }
    }

    public void startBeginAnimation() {
        AnimatorSet startAnimatorSet = new AnimatorSet();
        //矩形动画
        ObjectAnimator cornerAnimator = ObjectAnimator.ofFloat(this, "corner",
                mMaxCorner, mMinCorner)
                .setDuration(500);
        ObjectAnimator rectSizeAnimator = ObjectAnimator.ofFloat(this, "rectWidth",
                mMaxRectWidth, mMinRectWidth)
                .setDuration(500);

        //外圈圆环
        ObjectAnimator radiusAnimator = ObjectAnimator.ofFloat(this, "circleRadius",
                mMinCircleRadius, mMaxCircleRadius)
                .setDuration(500);
        startAnimatorSet.playTogether(cornerAnimator, rectSizeAnimator, radiusAnimator);

        //圆环抖动 动画
        ObjectAnimator circleWidthAnimator = ObjectAnimator.ofFloat(this, "circleStrokeWidth",
                mMinCircleStrokeWidth, mMaxCircleStrokeWidth, mMinCircleStrokeWidth)
                .setDuration(1500);
        circleWidthAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        mBeginAnimatorSet.playSequentially(startAnimatorSet, circleWidthAnimator);
        mBeginAnimatorSet.start();
    }

    private void startEndAnimation() {
        ObjectAnimator cornerAnimator = ObjectAnimator.ofFloat(this, "corner",
                mMinCorner, mMaxCorner)
                .setDuration(500);
        ObjectAnimator rectSizeAnimator = ObjectAnimator.ofFloat(this, "rectWidth",
                mMinRectWidth, mMaxRectWidth)
                .setDuration(500);
        ObjectAnimator radiusAnimator = ObjectAnimator.ofFloat(this, "circleRadius",
                mMaxCircleRadius, mMinCircleRadius)
                .setDuration(500);
        ObjectAnimator circleWidthAnimator = ObjectAnimator.ofFloat(this, "circleStrokeWidth",
                mMaxCircleStrokeWidth, mMinCircleStrokeWidth)
                .setDuration(1500);
        mEndAnimatorSet.playTogether(cornerAnimator, rectSizeAnimator, radiusAnimator, circleWidthAnimator);
        mEndAnimatorSet.start();
    }

    /**
     * 单次点击动画
     */
    public void startSingleBeginAnimation() {
        AnimatorSet startAnimatorSet = new AnimatorSet();
        //矩形动画
        ObjectAnimator cornerAnimator = ObjectAnimator.ofFloat(this, "corner",
                mMaxCorner, mMinCorner)
                .setDuration(500);
        ObjectAnimator rectSizeAnimator = ObjectAnimator.ofFloat(this, "rectWidth",
                mMaxRectWidth, mMinRectWidth)
                .setDuration(500);

        //外圈圆环
        ObjectAnimator radiusAnimator = ObjectAnimator.ofFloat(this, "circleRadius",
                mMinCircleRadius, mMaxCircleRadius)
                .setDuration(500);
        startAnimatorSet.playTogether(cornerAnimator, rectSizeAnimator, radiusAnimator);
        //圆环抖动 动画
//        ObjectAnimator circleWidthAnimator = ObjectAnimator.ofFloat(this, "circleStrokeWidth",
//                mMinCircleStrokeWidth, mMaxCircleStrokeWidth, mMinCircleStrokeWidth)
//                .setDuration(1500);
//        circleWidthAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        mBeginAnimatorSet.playSequentially(startAnimatorSet);
        mBeginAnimatorSet.start();

        mBeginAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startSingleEndAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void startSingleEndAnimation() {
        ObjectAnimator cornerAnimator = ObjectAnimator.ofFloat(this, "corner",
                mMinCorner, mMaxCorner)
                .setDuration(500);
        ObjectAnimator rectSizeAnimator = ObjectAnimator.ofFloat(this, "rectWidth",
                mMinRectWidth, mMaxRectWidth)
                .setDuration(500);
        ObjectAnimator radiusAnimator = ObjectAnimator.ofFloat(this, "circleRadius",
                mMaxCircleRadius, mMinCircleRadius)
                .setDuration(500);
        ObjectAnimator circleWidthAnimator = ObjectAnimator.ofFloat(this, "circleStrokeWidth",
                mMaxCircleStrokeWidth, mMinCircleStrokeWidth)
                .setDuration(500);

        mEndAnimatorSet.playTogether(cornerAnimator, rectSizeAnimator, radiusAnimator, circleWidthAnimator);
        mEndAnimatorSet.start();
    }

    public void setCorner(float corner) {
        this.corner = corner;
        invalidate();
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }

    public void setCircleStrokeWidth(float circleStrokeWidth) {
        this.circleStrokeWidth = circleStrokeWidth;
        invalidate();
    }

    public void setRectWidth(float rectWidth) {
        this.rectWidth = rectWidth;
    }

    class ClickRunnable implements Runnable {
        @Override
        public void run() {
            if (!mHasCancel) {
                mRecordMode = RecordMode.LONG_CLICK;
//                mRecordMode = RecordMode.ORIGIN;
                mInitX = getX();
                mInitY = getY();
                mInfectionPoint = mInitY;
                if (mOnRecordStateChangedListener != null) {
                    mOnRecordStateChangedListener.onLongPressRecordStart();
                    startBeginAnimation();
                }
//                mScrollDirection = ScrollDirection.UP;
            }
        }
    }

    class SingleClickRunnable implements Runnable {
        @Override
        public void run() {
            Log.e("eee", "单次点击");
            startSingleBeginAnimation();
//                mRecordMode = RecordMode.LONG_CLICK;
//            mRecordMode = RecordMode.ORIGIN;
//            mInitX = getX();
//            mInitY = getY();
//            mInfectionPoint = mInitY;
//            if (mOnRecordStateChangedListener != null) {
//                mOnRecordStateChangedListener.onLongPressRecordStart();
//                startBeginAnimation();
////                mScrollDirection = ScrollDirection.UP;
//            }
        }
    }

    public void setOnRecordStateChangedListener(OnRecordStateChangedListener listener) {
        this.mOnRecordStateChangedListener = listener;
    }

    public interface OnRecordStateChangedListener {

        /**
         * 开始录制
         */
        void onRecordStart();

        /**
         * 开始长按录制
         */
        void onLongPressRecordStart();

        /**
         * 结束录制
         */
        void onRecordStop();

        /**
         * 缩放百分比
         *
         * @param percentage 百分比值 0%~100% 对应缩放支持的最小和最大值 默认最小1.0
         */
        void onZoom(float percentage);
    }

    public enum RecordMode {
        /**
         * 单击录制模式
         */
        SINGLE_CLICK,
        /**
         * 长按录制模式
         */
        LONG_CLICK,
        /**
         * 初始化
         */
        ORIGIN;

        RecordMode() {

        }
    }

    private enum ScrollDirection {
        /**
         * 滑动方向 上
         */
        UP,
        /**
         * 滑动方向 下
         */
        DOWN;

        ScrollDirection() {

        }
    }
}
