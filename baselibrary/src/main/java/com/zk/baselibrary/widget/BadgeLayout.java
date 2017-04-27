package com.zk.baselibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.zk.baselibrary.R;

/**
 * ================================================
 * Created by zhaokai on 2017/4/13.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

@SuppressWarnings("unused")
public class BadgeLayout extends FrameLayout {

    private BadgeDrawable mBadgeDrawable;

    public BadgeLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public BadgeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BadgeLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public BadgeLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BadgeButton);
        String badgeText = a.getString(R.styleable.BadgeButton_btnBadgeText);
        int badgeBackColor = a.getColor(R.styleable.BadgeButton_btnBadgeColorBack, 0xffFF4081);
        int badgeTextColor = a.getColor(R.styleable.BadgeButton_btnBadgeColorText, 0xffFFFFFF);
        int badgeHeight = a.getDimensionPixelSize(R.styleable.BadgeButton_btnBadgeHeight, (int) (getResources().getDisplayMetrics().density * 12));
        boolean badgeVisible = a.getBoolean(R.styleable.BadgeButton_btnBadgeVisible, false);
        a.recycle();

        mBadgeDrawable = new BadgeDrawable(badgeHeight, badgeBackColor);
        mBadgeDrawable.setVisible(badgeVisible);
        mBadgeDrawable.setText(badgeText);
        mBadgeDrawable.setTextColor(badgeTextColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBadgeDrawable.draw(canvas);
    }

    public BadgeLayout setBadgeTextBackground(int textBackground) {
        mBadgeDrawable.setColor(textBackground);
        return this;
    }

    public BadgeLayout setBadgeTextColor(int textColor) {
        mBadgeDrawable.setTextColor(textColor);
        return this;
    }

    public BadgeLayout setBadgeText(String text) {
        mBadgeDrawable.setText(text);
        return this;
    }

    public BadgeLayout setBadgeVisible(boolean visible) {
        mBadgeDrawable.setVisible(visible);
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        mBadgeDrawable.layout((width - mBadgeDrawable.getMinimumWidth()), getPaddingTop(), width - getPaddingRight());
    }

    private static class BadgeDrawable extends GradientDrawable {
        private String mText;
        private boolean mIsVisible;
        private TextPaint mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

        private int mHeight = 0;

        BadgeDrawable(int height, int color) {

            setColor(color);

            mPaint.setColor(0xffffffff);
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setTextSize(height * 0.8f);

            mHeight = height;
        }

        void layout(int x, int y, int max) {
            Rect rect = getBounds();
            rect.offsetTo(Math.min(x - rect.width() / 2, max - rect.width() - (int) (0.2f * mHeight)), Math.max(0, y - rect.height() / 2));
            setBounds(rect);
        }

        void resize(int w, int h) {
            Rect rect = getBounds();
            setBounds(rect.left, rect.top, rect.left + w, rect.top + h);
            invalidateSelf();
        }

        public void setText(String text) {
            mText = text;
            if (TextUtils.isEmpty(mText)) {
                int size = (int) (mHeight * 0.65);
                resize(size, size);
            } else {
                int width = (int) (mPaint.measureText(mText) + 0.4 * mHeight);
                resize(Math.max(width, mHeight), mHeight);
            }
        }

        public void setVisible(boolean visible) {
            if (mIsVisible != visible) {
                invalidateSelf();
            }
            mIsVisible = visible;
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            setCornerRadius(getBounds().height() / 2f);
        }

        @Override
        public void draw(Canvas canvas) {
            if (!mIsVisible) {
                return;
            }
            super.draw(canvas);
            if (TextUtils.isEmpty(mText)) {
                return;
            }
            canvas.drawText(mText, getBounds().exactCenterX(), getBounds().exactCenterY() - (mPaint.descent() + mPaint.ascent()) / 2, mPaint);
        }

        public void setTextColor(int textColor) {
            mPaint.setColor(textColor);
            invalidateSelf();
        }
    }
}
