package com.zk.baselibrary.util;

import android.graphics.Color;
import android.support.annotation.ColorInt;

import java.util.regex.Pattern;

/**
 * ================================================
 * <p>
 * Created by zhaokai on 16/10/10.
 * Email zhaokai1033@126.com
 * <p>
 * ================================================
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class ColorUtil {


    /**
     * 计算从startColor过度到endColor过程中百分比为per时的颜色值
     *
     * @param startColor 起始颜色 int类型
     * @param endColor   结束颜色 int类型
     * @param per        百分比
     * @return 返回int格式的color
     */
    @ColorInt
    public static int transitionColor(int startColor, int endColor, float per) {
        int colorInt = 0;
        String strStartColor = "#" + Integer.toHexString(startColor);
        String strEndColor = "#" + Integer.toHexString(endColor);
        String color = transitionColor(strStartColor, strEndColor, per);
        try {
            colorInt = Color.parseColor(color);
        } catch (Exception e) {
            LogUtil.e("ColorUtil", "color " + color + " percent" + per + " strStartColor " + strStartColor + " strEndColor" + strEndColor + " e" + e.getMessage());
        }
        return colorInt;
    }

    /**
     * 计算从startColor过度到endColor过程中百分比为percent时的颜色值
     *
     * @param startColor 起始颜色 （格式#FFFFFFFF）
     * @param endColor   结束颜色 （格式#FFFFFFFF）
     * @param percent    百分比0.5
     * @return 返回String格式的color（格式#FFFFFFFF）
     */
    public static String transitionColor(String startColor, String endColor, float percent) {

        int startAlpha = Integer.parseInt(startColor.substring(1, 3), 16);
        int startRed = Integer.parseInt(startColor.substring(3, 5), 16);
        int startGreen = Integer.parseInt(startColor.substring(5, 7), 16);
        int startBlue = Integer.parseInt(startColor.substring(7), 16);

        int endAlpha = Integer.parseInt(endColor.substring(1, 3), 16);
        int endRed = Integer.parseInt(endColor.substring(3, 5), 16);
        int endGreen = Integer.parseInt(endColor.substring(5, 7), 16);
        int endBlue = Integer.parseInt(endColor.substring(7), 16);

        int currentAlpha = (int) ((endAlpha - startAlpha) * percent + startAlpha);
        int currentRed = (int) ((endRed - startRed) * percent + startRed);
        int currentGreen = (int) ((endGreen - startGreen) * percent + startGreen);
        int currentBlue = (int) ((endBlue - startBlue) * percent + startBlue);

        return "#" + getHexString(currentAlpha) + getHexString(currentRed)
                + getHexString(currentGreen) + getHexString(currentBlue);

    }

    /**
     * 将10进制颜色值转换成16进制。
     */
    public static String getHexString(int value) {
        String hexString = Integer.toHexString(value);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }

    /**
     * 将十六进制 颜色代码 转换为 int
     */
    @SuppressWarnings("SpellCheckingInspection")
    public static int hexToColor(String color) {

        // #ff00CCFF
        String reg = "#[a-f0-9A-F]{8}";
        if (!Pattern.matches(reg, color)) {
            color = "#00ffffff";
        }

        return Color.parseColor(color);
    }

    /**
     * 修改颜色透明度
     *
     * @param color colorInt
     * @param alpha 透明度
     */
    @ColorInt
    public static int changeAlpha(@ColorInt int color, int alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        return Color.argb(alpha, red, green, blue);
    }

}
