package com.zk.baselibrary.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

@SuppressWarnings("unused")
public class SplashView extends FrameLayout {

    ImageView splashImageView;
    ProgressTextView skipButton;

    private static String IMG_PATH = null;
    private static final String IMG_URL = "splash_img_url";
    private static final String SP_NAME = "splash";
    private static final int skipButtonSizeInDip = 36;
    private static final int skipButtonMarginInDip = 16;
    private Integer duration = 6;
    private static final int delayTime = 1000;   // 每隔1000 毫秒执行一次

    private boolean isActionBarShowing = true;

    private Context mActivity = null;

    private OnSplashViewActionListener mOnSplashViewActionListener = null;

    private Handler handler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (0 == duration) {
                dismissSplashView(false);
                return;
            } else {
                setDuration(--duration);
            }
            handler.postDelayed(timerRunnable, delayTime);
        }
    };
    private Integer maxDuration = 1;

    private void setImage(Bitmap image) {
        splashImageView.setImageBitmap(image);
    }

    public SplashView(Context context) {
        super(context);
        mActivity = context;
        initComponents();
    }

    public SplashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = context;
        initComponents();
    }

    public SplashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mActivity = context;
        initComponents();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SplashView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mActivity = context;
        initComponents();
    }

    @SuppressWarnings("deprecation")
    void initComponents() {

        splashImageView = new ImageView(mActivity);
        splashImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        splashImageView.setBackgroundColor(mActivity.getResources().getColor(android.R.color.white));
        LayoutParams imageViewLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(splashImageView, imageViewLayoutParams);
        splashImageView.setClickable(true);

        skipButton = new ProgressTextView(mActivity);
        int skipButtonSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, skipButtonSizeInDip, mActivity.getResources().getDisplayMetrics());
        LayoutParams skipButtonLayoutParams = new LayoutParams(skipButtonSize, skipButtonSize);
        skipButtonLayoutParams.gravity = Gravity.TOP | Gravity.END;
        int skipButtonMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, skipButtonMarginInDip, mActivity.getResources().getDisplayMetrics());
        skipButtonLayoutParams.setMargins(0, skipButtonMargin, skipButtonMargin, 0);
        skipButton.setGravity(Gravity.CENTER);
        skipButton.setTextColor(mActivity.getResources().getColor(android.R.color.white));
        skipButton.setProgressColor(Color.RED);
        skipButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
        this.addView(skipButton, skipButtonLayoutParams);
        skipButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissSplashView(true);
            }
        });
    }

    private void setDuration(Integer duration) {
        this.duration = duration;
        skipButton.setText(String.format(Locale.CHINA, "跳过\n%d s", duration));
//        skipButton.setMax(maxDuration);
//        skipButton.setProgress(duration);
    }

    private void setOnSplashImageClickListener(@Nullable final OnSplashViewActionListener listener) {
        if (null == listener) return;
        mOnSplashViewActionListener = listener;
        splashImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSplashImageClick();
            }
        });
    }

    /**
     * static method, show splashView on above of the activity
     * you should called after setContentView()
     *
     * @param activity         activity instance
     * @param durationTime     time to countDown
     * @param defaultBitmapRes if there's no cached bitmap, show this default bitmap;
     *                         if null == defaultBitmapRes, then will not show the splashView
     * @param listener         splash view listener contains onImageClick and onDismiss
     */
    public static void showSplashView(@NonNull Activity activity,
                                      @Nullable Integer durationTime,
                                      @Nullable Integer defaultBitmapRes,
                                      @Nullable OnSplashViewActionListener listener) {

        ViewGroup contentView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (null == contentView || 0 == contentView.getChildCount()) {
            throw new IllegalStateException("You should call showSplashView() after setContentView() in Activity instance");
        }
        IMG_PATH = activity.getFilesDir().getAbsolutePath() + "/splash_img.jpg";
        SplashView splashView = new SplashView(activity);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        splashView.setOnSplashImageClickListener(listener);
        if (null != durationTime) {
            splashView.setMaxDuration(durationTime);
            splashView.setDuration(durationTime);
        }
        Bitmap bitmapToShow = null;

        if (isExistsLocalSplashData(activity)) {
            bitmapToShow = BitmapFactory.decodeFile(IMG_PATH);
            SharedPreferences sp = activity.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        } else if (null != defaultBitmapRes) {
            bitmapToShow = BitmapFactory.decodeResource(activity.getResources(), defaultBitmapRes);
        }

        if (null == bitmapToShow) return;
        splashView.setImage(bitmapToShow);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (activity instanceof AppCompatActivity) {
            ActionBar supportActionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (null != supportActionBar) {
                //noinspection RestrictedApi
                supportActionBar.setShowHideAnimationEnabled(false);
                splashView.isActionBarShowing = supportActionBar.isShowing();
                supportActionBar.hide();
            }
        } else {
            android.app.ActionBar actionBar = activity.getActionBar();
            if (null != actionBar) {
                splashView.isActionBarShowing = actionBar.isShowing();
                actionBar.hide();
            }
        }
        contentView.addView(splashView, param);
        splashView.setDuration(durationTime);
        splashView.start();
    }

    private void start() {
        skipButton.startAuto(maxDuration, 10, null);
        handler.postDelayed(timerRunnable, delayTime);
    }

    /**
     * simple way to show splash view, set all non-able param as non
     */
    public static void simpleShowSplashView(@NonNull Activity activity) {
        showSplashView(activity, null, null, null);
    }

    private void dismissSplashView(boolean initiativeDismiss) {
        if (null != mOnSplashViewActionListener)
            mOnSplashViewActionListener.onSplashViewDismiss(initiativeDismiss);


        handler.removeCallbacks(timerRunnable);
        final ViewGroup parent = (ViewGroup) this.getParent();
        if (null != parent) {

            ValueAnimator animator = ValueAnimator.ofFloat(0.0f, .05f);
            animator.start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float cVal = (Float) animation.getAnimatedValue();
                    SplashView.this.setAlpha(1.0f - 2.0f * cVal);
                    SplashView.this.setScaleX(1.0f + cVal);
                    SplashView.this.setScaleY(1.0f + cVal);
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    parent.removeView(SplashView.this);
                    showSystemUi();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    parent.removeView(SplashView.this);
                    showSystemUi();
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }

    private void showSystemUi() {
        ((Activity) mActivity).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (mActivity instanceof AppCompatActivity) {
            ActionBar supportActionBar = ((AppCompatActivity) mActivity).getSupportActionBar();
            if (null != supportActionBar) {
                if (isActionBarShowing) supportActionBar.show();
            }
        } else if (mActivity instanceof Activity) {
            android.app.ActionBar actionBar = ((Activity) mActivity).getActionBar();
            if (null != actionBar) {
                if (isActionBarShowing) actionBar.show();
            }
        }
    }

    private static boolean isExistsLocalSplashData(Activity activity) {
        SharedPreferences sp = activity.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String imgUrl = sp.getString(IMG_URL, null);
        return !TextUtils.isEmpty(imgUrl) && isFileExist(IMG_PATH);
    }

    /**
     * static method, update splash view data
     *
     * @param imgUrl - url of image which you want to set as splash image
     */
    public static void updateSplashData(@NonNull Activity activity, @NonNull String imgUrl) {
        IMG_PATH = activity.getFilesDir().getAbsolutePath() + "/splash_img.jpg";

        SharedPreferences.Editor editor = activity.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(IMG_URL, imgUrl);
        editor.apply();

        getAndSaveNetWorkBitmap(imgUrl);
    }

    public void setMaxDuration(Integer maxDuration) {
        this.maxDuration = maxDuration;
    }

    public interface OnSplashViewActionListener {
        void onSplashImageClick();

        void onSplashViewDismiss(boolean initiativeDismiss);
    }

    private static void getAndSaveNetWorkBitmap(final String urlString) {
        Runnable getAndSaveImageRunnable = new Runnable() {
            @Override
            public void run() {
                URL imgUrl;
                Bitmap bitmap;
                try {
                    imgUrl = new URL(urlString);
                    HttpURLConnection urlConn = (HttpURLConnection) imgUrl.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.connect();
                    InputStream is = urlConn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    saveBitmapFile(bitmap, IMG_PATH);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(getAndSaveImageRunnable).start();
    }

    private static void saveBitmapFile(Bitmap bm, String filePath) throws IOException {
        File myCaptureFile = new File(filePath);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }

    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        } else {
            File file = new File(filePath);
            return file.exists() && file.isFile();
        }
    }
}
