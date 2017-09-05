package com.video.ren.videoplay.utils;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Administrator on 2017/9/5
 */

public class PermissionUtils {

    public static final int REQUESTCODE=1;

    public static boolean checkPermission(Activity activity){
        int permission=ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission==PackageManager.PERMISSION_GRANTED)
            return true ;
        else{
            requestPermission(activity);
            return false ;
        }
    }

    public static void requestPermission(Activity activity){
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUESTCODE);
    }
}
