package com.video.ren.videoplay.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import static android.R.attr.format;

/**
 * Created by Administrator on 2017/9/11
 */

public class DateUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);

    public static String formatDate(long time){
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));//设置后不算时区
        return sdf.format(time);
    }

    public static long parseDate(String time){
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));//设置后不算时区
        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
