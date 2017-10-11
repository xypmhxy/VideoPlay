package com.video.ren.videoplay.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.video.ren.videoplay.BuildConfig;

/**
 * Created by Ren on 2017/10/11.
 * TODO
 */

public class BrandUtils {

    public static final String MEIZU = "Meizu";
    public static final String XIAOMI = "Xiaomi";
    public static final String HUAWEI = "Huawei";
    public static final String OPPO = "OPPO";

    public static String getPhoneBrand() {
        return Build.BRAND;
    }

    public static Intent getBrandAppIntent(Context context) {
        String brand = Build.BRAND;
        Intent intent = new Intent();
        switch (brand) {
            case MEIZU:
                intent.setAction("com.meizu.safe.security.SHOW_APPSEC");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
                return intent;
            case XIAOMI:
                intent.setAction("miui.intent.action.APP_PERM_EDITOR");
                ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                intent.setComponent(componentName);
                intent.putExtra("extra_pkgname", BuildConfig.APPLICATION_ID);
                return intent;
            case HUAWEI:
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
                ComponentName compHuawei = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
                intent.setComponent(compHuawei);
                return intent;
            case OPPO:
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
                ComponentName compOppo = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
                intent.setComponent(compOppo);
                return intent;
            default :
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                return intent;
        }
    }
}
