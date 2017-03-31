package com.zk.baselibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Px;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

@SuppressWarnings("unused")
public class ProgressTextView extends AppCompatTextView {
    private static final String TAG = "ProgressTextView";

    private int mOutLineColor = 0xFF888888;
    private int mOutLineWidth = 4;

    private int mCircleColor = 0x66333333;
    private int mCircleRadius;

    private int mTextColor = Color.WHITE;

    private int mProgressLineColor = Color.RED;
    private int mProgressLineWidth = 4;
    private int mProgress = 0;

    private int mMax = 100;   //默认值

    private Paint mPaint;
    private Rect mBounds;
    private RectF mArcRectF;

    private String mText = "跳过";
    private boolean isAuto;

    public ProgressTextView(Context context) {
        this(context, null);
    }

    public ProgressTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mPaint = new Paint();
        mBounds = new Rect();
        mArcRectF = new RectF();
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (width > height) {
            height = width;
        } else {
            width = height;
        }
        mCircleRadius = width / 2;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        getDrawingRect(mBounds); //找到view的边界

        int mCenterX = mBounds.centerX();
        int mCenterY = mBounds.centerY();

        //画大圆
        mPaint.setAntiAlias(true);  //防锯齿
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mCircleColor);
        canvas.drawCircle(mBounds.centerX(), mBounds.centerY(), mCircleRadius, mPaint);

        //画外边框
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mOutLineWidth);
        mPaint.setColor(mOutLineColor);
        canvas.drawCircle(mBounds.centerX(), mBounds.centerY(), mCircleRadius - mOutLineWidth, mPaint);

        //画字
        Paint paint = getPaint();
        paint.setColor(mTextColor);
        paint.setAntiAlias(true);  //防锯齿
        paint.setTextAlign(Paint.Align.CENTER);
        float textY = mCenterY - (paint.descent() + paint.ascent()) / 2;
        canvas.drawText(mText, mCenterX, textY, paint);

        //画进度条
        mPaint.setStrokeWidth(mProgressLineWidth);
        mPaint.setColor(mProgressLineColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcRectF.set(mBounds.left + mProgressLineWidth, mBounds.top + mProgressLineWidth,
                mBounds.right - mProgressLineWidth, mBounds.bottom - mProgressLineWidth);
        canvas.drawArc(mArcRectF, -90, 360 * mProgress / mMax, false, mPaint);
        super.onDraw(canvas);
    }

    public void setText(String text) {
        mText = text;
        this.postInvalidate();
    }

    public void setMax(int max) {
        mMax = max;
        this.postInvalidate();
    }

    public int getTime() {
        return mMax;
    }

    public void setProgress(int mProgress) {
        isAuto = false;
        this.mProgress = mProgress;
        this.postInvalidate();
    }


    public void setProgressColor(int color) {
        mProgressLineColor = color;
        this.postInvalidate();
    }

    public void setCircleBackgroundColor(int color) {
        mCircleColor = color;
        this.postInvalidate();
    }

    public void setTextColor(int color) {
        mTextColor = color;
        this.postInvalidate();
    }

    public void setOutLineColor(int mOutLineColor) {
        this.mOutLineColor = mOutLineColor;
    }

    public void setOutLineWidth(@Px int mOutLineWidth) {
        this.mOutLineWidth = mOutLineWidth;
    }

    public void setProgressWidth(@Px int mProgressLineWidth) {
        this.mProgressLineWidth = mProgressLineWidth;
    }

    private int interval = 10;
    private long startTime = 0;

    /**
     * @param time     second
     * @param interval mill
     */
    public void startAuto(int time, int interval, FinishCallBack finishCallBack) {
        if (time <= 0 || interval <= 0) {
            throw new RuntimeException("Time and interval cannot be zero");
        }
        this.finishCallBack = finishCallBack;
        this.interval = interval;
        this.startTime = System.currentTimeMillis() + (time * 1000) - 100;
        setMax(time * 1000);
        setProgress(time * 1000);
        isAuto = true;
        post(refresh);
    }

    private Runnable refresh = new Runnable() {
        @Override
        public void run() {
            mProgress = ((int) (startTime - System.currentTimeMillis()));
            if (mProgress >= 0) {
                invalidate();
                if (isAuto)
                    postDelayed(refresh, interval);
            } else {
                mProgress = 0;
                invalidate();
                if (finishCallBack != null) {
                    finishCallBack.onFinish();
                }
            }
        }
    };

    private FinishCallBack finishCallBack;

    interface FinishCallBack {
        void onFinish();
    }

}