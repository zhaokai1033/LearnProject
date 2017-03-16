package com.zk.baselibrary.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * ================================================
 * Created by zhaokai on 2017/3/15.
 *
 * @Email zhaokai1033@126.com
 * @Describe :
 * ================================================
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class DateUtils {

    private static final String TAG = "DateUtils";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    private static final long ONE_MINUTE = 60;
    private static final long FIVE_MIN = 300;
    private static final long HALF_HOUR = 1800;
    private static final long ONE_HOUR = 3600;
    private static final long ONE_DAY = 86400;
    private static final long YESTERDAY = 172800;
    private static final long ONE_MONTH = 2592000;
    private static final long ONE_YEAR = 31104000;

    private static Locale locale = Locale.SIMPLIFIED_CHINESE;

    /**
     * 获取当前时间戳
     * 单位 秒
     */
    public static int getCurrentMill() {
//        System.out.println((int) (System.currentTimeMillis() / 1000));
        return (int) (System.currentTimeMillis() / 1000);//1814711747
        //1449218718
    }

    /**
     * 时间转日期 格式  yyyy-MM-dd HH:mm
     * 当输入 0时 返回当前日期
     *
     * @param date 秒数
     */
    public static String mill2time(long date) {
        return mill2time(date, YYYY_MM_DD);
    }

    /**
     * 时间转日期 格式  默认格式 yyyy-MM-dd
     * 当输入 0时 返回当前日期
     *
     * @param date 秒数
     * @return 指定格式的日期
     */
    public static String mill2time(long date, String timeFormat) {
        if (TextUtils.isEmpty(timeFormat)) {
            timeFormat = YYYY_MM_DD;
        }
        SimpleDateFormat format = new SimpleDateFormat(timeFormat, locale);//yyyy-MM-dd HH:mm:ss
        if (date == 0) {
            return format.format(new Date());
        }

        return format.format(new Date(date * 1000));
    }

    /**
     * 返回日期的秒数
     *
     * @param time 默认格式字符串格式 yyyy-MM-dd
     */
    public static long time2mill(String time) {
        return time2mill(time, null);
    }

    /**
     * 返回日期的秒数
     *
     * @param time          默认格式字符串格式 yyyy-MM-dd
     * @param defaultFormat 字符串格式
     */
    public static long time2mill(String time, String defaultFormat) {
        if (TextUtils.isEmpty(defaultFormat)) {
            defaultFormat = YYYY_MM_DD;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(defaultFormat, locale);
        long date = 0;
        try {
            date = (sdf.parse(time).getTime()) / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 获取 某个日期上个月的最大天数
     *
     * @param time 字符串格式 yyyy-MM-dd
     */
    public static int getLastDayByDate(String time) {
        //获取Calendar对象
        Calendar cal = Calendar.getInstance();
        //设置日期
        cal.setTime(new Date(time2mill(time, null) * 1000));
        cal.add(Calendar.MONTH, -1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取两个日期字符串相差的天数
     *
     * @param first  基准
     * @param second 测试的日期
     *               字符串格式 yyyy-MM-dd
     */
    public static int getDiffDays(String first, String second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time2mill(first, null) * 1000));
        long time1 = cal.getTimeInMillis();
        cal.setTime(new Date(time2mill(second, null) * 1000));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 距离今天多久
     *
     * @param date 日期
     * @return 距离今天多久
     */
    public static String timeParse(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        long time = date.getTime() / 1000;
        long now = new Date().getTime() / 1000;
        boolean isToday = isToday(time);
        long ago = now - time;
        if (ago <= ONE_MINUTE) {
            return "刚刚";
        } else if (ago <= ONE_HOUR) {
            return ago / ONE_MINUTE + "分前";
        } else if (ago < ONE_DAY) {
            return ago / ONE_HOUR + "小时前";
        } else if (isToday && ago <= ONE_DAY) {
            SimpleDateFormat simple = new SimpleDateFormat("HH : mm", locale);
            return simple.format(calendar.getTime());
        } else if (time > (time2mill(mill2time(0)) - ONE_DAY)) {
            return "昨天";
        } else if (time > (time2mill(mill2time(0)) - ONE_DAY * 2)) {
            return "前天";
        } else if (ago <= ONE_YEAR) {
            SimpleDateFormat simple = new SimpleDateFormat("MM - dd", locale);
            return simple.format(calendar.getTime());
        } else {
            SimpleDateFormat simple = new SimpleDateFormat("yy - MM", locale);
            return simple.format(calendar.getTime());
        }
    }

    /**
     * 是否是今天
     *
     * @param time 日期 秒
     */
    private static boolean isToday(long time) {
        return time > time2mill(mill2time(0));
    }
}
