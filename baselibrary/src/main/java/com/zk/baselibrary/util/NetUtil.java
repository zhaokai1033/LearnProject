package com.zk.baselibrary.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.net.NetworkInterface;
import java.net.SocketException;


/**
 * ================================================
 * <p/>
 * Created by zhaokai on 16/8/1.
 * Email zhaokai1033@126.com
 * <p/>
 * ================================================
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class NetUtil {
    private static final String TAG = "NetUtil";
    public static final String WIFI = "Wi-Fi";
    public static final String DEFAULT_WIFI_ADDRESS = "00-00-00-00-00-00";

    public static NetUtil instance;

    private NetUtil() {
    }

    public static NetUtil getInstance() {
        if (instance == null) {
            synchronized (TAG) {
                if (instance == null) {
                    instance = new NetUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 获取网络制式
     *
     * @return WiFi=1/2G=2/3G=3/4G=4
     */
    public static int getNetMode(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) return 1;
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                return getNetworkClass(context);
            }
        }
        return 0;
    }

    /**
     * 判断网络的具体类型
     */
    private static int getNetworkClass(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return 2;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return 3;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return 4;
            default:
                return 0;
        }
    }

    /**
     * 网络连接是否畅通
     */
    public static boolean isNetConnect(Context context) {
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conManager != null) {
            try {
                NetworkInfo e = conManager.getActiveNetworkInfo();
                if (e != null) {
                    return e.isAvailable() && e.isConnected();
                }
            } catch (Exception ignored) {
            }
        }

        return false;
    }

    /**
     * 获取手机的mac 地址
     */
    public static String getWifiAddress(Context context) {
        NetworkInterface networkInterface;
        try {
            //noinspection SpellCheckingInspection
            networkInterface = NetworkInterface.getByName("wlan0");
            if (networkInterface != null) {
                byte[] bytes = networkInterface.getHardwareAddress();
                StringBuilder builder = new StringBuilder();
                for (byte b : bytes) {
                    builder.append(String.format("%02X:", b));
                }
                if (builder.length() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                return builder.toString();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "00:00:00:00:00:00";
    }

    /**
     * 获取Ip 地址
     */
    public static String getWifiIpAddress(Context context) {
        if (context != null) {
            try {
                WifiManager e = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiinfo = e.getConnectionInfo();
                if (wifiinfo != null) {
                    return _convertIntToIp(wifiinfo.getIpAddress());
                }

                return null;
            } catch (Exception ignored) {
            }
        }

        return null;
    }

    private static String _convertIntToIp(int i) {
        return (i & 255) + "." + (i >> 8 & 255) + "." + (i >> 16 & 255) + "." + (i >> 24 & 255);
    }


    /**
     * 是否处于飞行模式
     */
    public static boolean isAirModeOn(Context context) {
        //noinspection deprecation
        return (Settings.System.getInt(context.getApplicationContext().getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) == 1);
    }

    /**
     * 是否是WIFI
     */
    public static boolean isWifi(Context context) {
        return NetUtil.getNetMode(context) == 1;
    }

    /**
     * 判断网络状态是不是2G
     *
     * @param context 上下文
     * @return 判断结果
     */
    public static boolean isUsingMobileNetwork(Context context) {
        return NetUtil.getNetMode(context) == 2;
    }

    /**
     * 显示网络设置对话框
     */
    public static void showNetSetDialog(final Activity context) {
        // 提示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        try {
            builder.setTitle("网络设置提示")
                    .setMessage("网络连接不可用,是否进行设置?")
                    .setPositiveButton("设置",
                            new DialogInterface.OnClickListener() {

                                @SuppressLint("ObsoleteSdkInt")
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent intent;
                                    // 判断手机系统的版本 即API大于10 就是3.0或以上版本
                                    if (android.os.Build.VERSION.SDK_INT > 10) {
                                        intent = new Intent(
                                                android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                                    } else {
                                        intent = new Intent();
                                        ComponentName component = new ComponentName(
                                                "com.android.settings",
                                                "com.android.settings.WirelessSettings");
                                        intent.setComponent(component);
                                        intent.setAction("android.intent.action.VIEW");
                                    }
                                    context.startActivity(intent);
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
