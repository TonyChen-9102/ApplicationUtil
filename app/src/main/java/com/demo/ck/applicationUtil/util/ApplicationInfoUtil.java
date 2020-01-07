package com.demo.ck.applicationUtil.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * 获取application的一些信息
 */
public class ApplicationInfoUtil {

    public static ApplicationInfo getApplicationInfo(@NonNull Context context) {
        return context.getApplicationInfo();
    }

    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(@NonNull Context context) {
        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            String appName = appInfo.loadLabel(context.getPackageManager()) + "";
            return appName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取应用图标 bitmap
     *
     * @param context
     */
    public static synchronized Bitmap getAppIcon(@NonNull Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext()
                    .getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(
                    context.getPackageName(), 0);
            Drawable d = packageManager.getApplicationIcon(applicationInfo); //xxx根据自己的情况获取drawable
            Bitmap bm = null;
            if (d instanceof BitmapDrawable) {
                bm = ((BitmapDrawable) d).getBitmap();
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (d instanceof AdaptiveIconDrawable) {
                    int j = d.getIntrinsicWidth();
                    int k = d.getIntrinsicHeight();
                    int i = j;
                    if (j <= 0) {
                        i = 1;
                    }
                    j = k;
                    if (k <= 0) {
                        j = 1;
                    }
                    bm = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bm);
                    d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    d.draw(canvas);
                }
            }

            return bm;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getProcessName(@NonNull Context context) {
        ApplicationInfo info = getApplicationInfo(context);
        if (info != null) {
            return info.processName;
        }

        return null;
    }

    public static int getDescreptionRes(@NonNull Context context) {
        ApplicationInfo info = getApplicationInfo(context);
        if (info != null) {
            return info.descriptionRes;
        }

        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int getMinSdkVersion(@NonNull Context context) {
        ApplicationInfo info = getApplicationInfo(context);
        if (info != null) {
            return info.minSdkVersion;
        }

        return 0;
    }

    public static int getTargetSdkVersion(@NonNull Context context) {
        ApplicationInfo info = getApplicationInfo(context);
        if (info != null) {
            return info.targetSdkVersion;
        }

        return 0;
    }

    /**
     * 获取application的一些状态
     *
     * @param context
     * @return
     */
    public static int getFlags(@NonNull Context context) {
        ApplicationInfo info = getApplicationInfo(context);
        if (info != null) {
            return info.flags;
        }

        return 0;
    }

    public static boolean isSystemApp(@NonNull Context context) {
        int flags = getFlags(context);
        return (flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM;
    }

}
