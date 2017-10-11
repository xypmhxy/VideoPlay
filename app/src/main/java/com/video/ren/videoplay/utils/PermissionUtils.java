package com.video.ren.videoplay.utils;


import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/9/5
 */

public class PermissionUtils {

    public static final int REQUESTCODE = 1;

    public static boolean checkReadSDcardPermission(Activity activity) {
        int permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission == PackageManager.PERMISSION_GRANTED)
            return true;
        else {
            requestPermission(activity);
            return false;
        }
    }

    public static boolean checkAlertWindowPermission(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Method method = manager.getClass().getDeclaredMethod("checkOp", int.class, int.class, String.class);
                int property = (Integer) method.invoke(manager, 24,
                        Binder.getCallingUid(), context.getPackageName());
                return AppOpsManager.MODE_ALLOWED == property;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e("rq", "Below API 19 cannot invoke!");
        }
        return false;
    }

    public static void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUESTCODE);
    }
}
