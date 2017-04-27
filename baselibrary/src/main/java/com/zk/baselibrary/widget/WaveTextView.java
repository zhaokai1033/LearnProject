package com.zk.baselibrary.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import com.zk.baselibrary.R;

/**
 * ================================================
 * Created by zhaokai on 2017/4/26.
 * Email zhaokai1033@126.com
 * Describe : 动画文字
 * ================================================
 */
public class WaveTextView extends AppCompatTextView {

    public interface AnimationSetupCallback {
        public void onSetupAnimation(WaveTextView waveTextView);
    }

    // callback fired at first onSizeChanged
    private AnimationSetupCallback animationSetupCallback;
    // wave shader coordinates
    private float maskX, maskY;
    // if true, the shader will display the wave
    private boolean sinking;
    // true after the first onSizeChanged
    private boolean setUp;

    // shader containing a repeated wave
    private BitmapShader shader;
    // shader matrix
    private Matrix shaderMatrix;
    // wave drawable
    private Drawable wave;
    // (getHeight() - waveHeight) / 2
    private float offsetY;

    public WaveTextView(Context context) {
        super(context);
        init();
    }

    public WaveTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        shaderMatrix = new Matrix();
    }

    public AnimationSetupCallback getAnimationSetupCallback() {
        return animationSetupCallback;
    }

    public void setAnimationSetupCallback(AnimationSetupCallback animationSetupCallback) {
        this.animationSetupCallback = animationSetupCallback;
    }

    public float getMaskX() {
        return maskX;
    }

    public void setMaskX(float maskX) {
        this.maskX = maskX;
        invalidate();
    }

    public float getMaskY() {
        return maskY;
    }

    public void setMaskY(float maskY) {
        this.maskY = maskY;
        invalidate();
    }

    public boolean isSinking() {
        return sinking;
    }

    public void setSinking(boolean sinking) {
        this.sinking = sinking;
    }

    public boolean isSetUp() {
        return setUp;
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        createShader();
    }

    @Override
    public void setTextColor(ColorStateList colors) {
        super.setTextColor(colors);
        createShader();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        createShader();

        if (!setUp) {
            setUp = true;
            if (animationSetupCallback != null) {
                animationSetupCallback.onSetupAnimation(WaveTextView.this);
            }
        }
    }

    /**
     * Create the shader
     * draw the wave with current color for a background
     * repeat the bitmap horizontally, and clamp colors vertically
     */
    private void createShader() {

        if (wave == null) {
            wave = getResources().getDrawable(R.drawable.wave);
        }

        int waveW = wave.getIntrinsicWidth();
        int waveH = wave.getIntrinsicHeight();

        Bitmap b = Bitmap.createBitmap(waveW, waveH, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        c.drawColor(getCurrentTextColor());

        wave.setBounds(0, 0, waveW, waveH);
        wave.draw(c);

        shader = new BitmapShader(b, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        getPaint().setShader(shader);

        offsetY = (getHeight() - waveH) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // modify text paint shader according to sinking state
        if (sinking && shader != null) {

            // first call after sinking, assign it to our paint
            if (getPaint().getShader() == null) {
                getPaint().setShader(shader);
            }

            // translate shader accordingly to maskX maskY positions
            // maskY is affected by the offset to vertically center the wave
            shaderMatrix.setTranslate(maskX, maskY + offsetY);

            // assign matrix to invalidate the shader
            shader.setLocalMatrix(shaderMatrix);
        } else {
            getPaint().setShader(null);
        }

        super.onDraw(canvas);
    }

    public Wave waveAnim = new Wave();

    /**
     * 开启动画
     */
    public void startWave() {
        waveAnim.start(this);
    }

    /**
     * 结束动画
     */
    public void stopWave() {
        waveAnim.cancel();
    }

    private class Wave {

        private AnimatorSet animatorSet;
        private Animator.AnimatorListener animatorListener;

        public Animator.AnimatorListener getAnimatorListener() {
            return animatorListener;
        }

        public void setAnimatorListener(Animator.AnimatorListener animatorListener) {
            this.animatorListener = animatorListener;
        }

        public void start(final WaveTextView textView) {

            final Runnable animate = new Runnable() {
                @Override
                public void run() {

                    textView.setSinking(true);

                    // horizontal animation. 200 = wave.png width
                    ObjectAnimator maskXAnimator = ObjectAnimator.ofFloat(textView, "maskX", 0, 200);
                    maskXAnimator.setRepeatCount(ValueAnimator.INFINITE);
                    maskXAnimator.setDuration(1000);
                    maskXAnimator.setStartDelay(0);

                    int h = textView.getHeight();

                    // vertical animation
                    // maskY = 0 -> wave vertically centered
                    // repeat mode REVERSE to go back and forth
                    ObjectAnimator maskYAnimator = ObjectAnimator.ofFloat(textView, "maskY", h / 2, -h / 2);
                    maskYAnimator.setRepeatCount(ValueAnimator.INFINITE);
                    maskYAnimator.setRepeatMode(ValueAnimator.REVERSE);
                    maskYAnimator.setDuration(10000);
                    maskYAnimator.setStartDelay(0);

                    // now play both animations together
                    animatorSet = new AnimatorSet();
                    animatorSet.playTogether(maskXAnimator, maskYAnimator);
                    animatorSet.setInterpolator(new LinearInterpolator());
                    animatorSet.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            textView.setSinking(false);

                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                textView.postInvalidate();
                            } else {
                                textView.postInvalidateOnAnimation();
                            }

                            animatorSet = null;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });


                    if (animatorListener != null) {
                        animatorSet.addListener(animatorListener);
                    }

                    animatorSet.start();
                }
            };

            if (!textView.isSetUp()) {
                textView.setAnimationSetupCallback(new WaveTextView.AnimationSetupCallback() {
                    @Override
                    public void onSetupAnimation(final WaveTextView target) {
                        animate.run();
                    }
                });
            } else {
                animate.run();
            }
        }

        public void cancel() {
            if (animatorSet != null) {
                animatorSet.cancel();
            }
        }
    }
}