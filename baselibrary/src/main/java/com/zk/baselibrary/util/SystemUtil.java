package com.zk.baselibrary.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * ================================================
 * Describe：
 * Created by zhaokai on 2017/3/7.
 * Email zhaokai1033@126.com
 * ================================================
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class SystemUtil {


    /**
     * 系统自带的文字分享
     *
     * @param ctx  上下文
     * @param text 文本
     */
    public static void shareText(Context ctx, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        ctx.startActivity(Intent.createChooser(sendIntent, "分享至"));
    }

    /**
     * 系统自带的图片分享
     *
     * @param ctx 上下文
     * @param uri 图片本地地址
     */

    public static void shareImage(Context ctx, Uri uri) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType("image/jpeg");
        ctx.startActivity(Intent.createChooser(sendIntent, "分享至"));
    }

    /**
     * 系统自带的图片分享
     *
     * @param ctx  上下文
     * @param uris 图片本地地址
     */
    public static void shareImageList(Context ctx, ArrayList<Uri> uris) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uris);
        sendIntent.setType("image/*");
        ctx.startActivity(Intent.createChooser(sendIntent, "分享至"));
    }

    /**
     * 调起系统发短信功能
     *
     * @param phoneNumber 收件人/手机号
     * @param message     发送信息
     */
    @RequiresPermission(Manifest.permission.SEND_SMS)
    public static void sendMSG(Context ctx, String phoneNumber, String message) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            intent.putExtra("sms_body", message);
            ctx.startActivity(intent);
        }
    }

    /**
     * 获取设备的制造商
     *
     * @return 设备制造商
     */
    public static String getDeviceManufacture() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 获取设备名称
     *
     * @return 设备名称
     */
    public static String getDeviceName() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取设备号
     * Requires Permission:
     * {@link android.Manifest.permission#READ_PHONE_STATE READ_PHONE_STATE}
     */
    @SuppressLint("HardwareIds")
    public static String getDeviceIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null || TextUtils.isEmpty(telephonyManager.getDeviceId())) {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            return telephonyManager.getDeviceId();
        }
    }

    /**
     * 获取应用的版本号
     */
    public static String getAppVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取剪切板的内容
     */
    public static CharSequence getText(Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);

        StringBuilder sb = new StringBuilder();
        if (!cmb.hasPrimaryClip()) {
            return sb.toString();
        } else {
            ClipData clipData = cmb.getPrimaryClip();
            int count = clipData.getItemCount();

            for (int i = 0; i < count; ++i) {

                ClipData.Item item = clipData.getItemAt(i);
                CharSequence str = item.coerceToText(context);
                sb.append(str);
            }
        }

        return sb.toString();
    }

    /**
     * 为剪切板设置内容
     *
     * @param text 文本框
     */
    public static void copyText(TextView text) {
        copyText(text.getContext(), text.getText().toString().trim(), null);
    }

    /**
     * 为剪切板设置内容
     *
     * @param text  文本
     * @param label 数据标签
     */
    public static void copyText(Context context, String text, String label) {
        ClipboardManager cmb = (ClipboardManager) context.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(label, text));

        ClipData clipData = cmb.getPrimaryClip();
        int count = clipData.getItemCount();

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < count; ++i) {
            ClipData.Item item = clipData.getItemAt(i);
            CharSequence str = item.coerceToText(context.getApplicationContext());
            builder.append(str);
        }
        LogUtil.e("UIUtil", builder.toString());
    }

    /**
     * 调用震动器
     *
     * @param context      调用该方法的Context
     * @param milliseconds 震动的时长，单位是毫秒
     */
    public static void vibrate(final Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    /**
     * 调用震动器
     *
     * @param context  调用该方法的Context
     * @param pattern  自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * @param isRepeat 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public static void vibrate(final Context context, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }

    /**
     * 设置页面全屏
     *
     * @param activity 当前窗口
     * @param isFull   是否全屏
     */
    public static void setFullScreen(Activity activity, boolean isFull) {
        if (isFull) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().setAttributes(attrs);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public static boolean isFullScreen(Activity activity) {
        // 全屏 66816 - 非全屏 65792
        final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        return attrs.flags == 66816;//非全屏
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void setTranslucentStatus(Activity activity, boolean isTranslucent) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (isTranslucent) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
//        translucentSystemBar(activity, isTranslucent, false);
    }

    public static Boolean translucentSystemBar(Activity mActivity, boolean translucent_status_bar, boolean translucent_navigation_bar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = null;
            if (mActivity != null)
                window = mActivity.getWindow();            // Translucent status bar
            if (translucent_status_bar) {
                if (window != null)
                    window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }            // Translucent navigation bar
            if (translucent_navigation_bar) {
                if (window != null)
                    window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
            return true;
        }
        return false;
    }

}
