package com.video.ren.videoplay.utils;

import android.content.Context;

/**
 * Created by Administrator on 2017/09/13 0013
 */

public class PreferencesUtils {
    private static final String KEY_PREFERENCES_NAME = "video_setting";
    private static final String KEY_PLAY_NOW = "play_now";
    private static final String KEY_FLOAT_WINDOW = "float_window";
    private static final String KEY_SCAN_TIME = "scan_time";

    public static void savePlayNow(Context context, boolean playNow) {
        context.getSharedPreferences(KEY_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(KEY_PLAY_NOW, playNow)
                .apply();
    }

    public static boolean getPlayNow(Context context) {
        return context.getSharedPreferences(KEY_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getBoolean(KEY_PLAY_NOW, false);
    }

    public static void saveFloatWindow(Context context, boolean isPlay) {
        context.getSharedPreferences(KEY_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(KEY_FLOAT_WINDOW, isPlay)
                .apply();
    }

    public static boolean getFloatWindow(Context context) {
        return context.getSharedPreferences(KEY_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getBoolean(KEY_FLOAT_WINDOW, false);
    }

    public static void saveScanTime(Context context, boolean isTrue) {
        context.getSharedPreferences(KEY_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(KEY_SCAN_TIME, isTrue)
                .apply();
    }

    public static boolean getScanTime(Context context) {
        return context.getSharedPreferences(KEY_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getBoolean(KEY_SCAN_TIME, false);
    }
}
