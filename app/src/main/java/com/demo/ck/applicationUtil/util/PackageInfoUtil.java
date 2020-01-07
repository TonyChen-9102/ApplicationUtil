package com.demo.ck.applicationUtil.util;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.List;

/**
 * 获取pacakge的一些信息
 */
public class PackageInfoUtil {

    public static PackageManager getPackageManager(@NonNull Context context) {
        return context.getPackageManager();
    }

    public static PackageInfo getPackageInfo(@NonNull Context context, int flag) {
        try {
            return getPackageManager(context).getPackageInfo(context.getPackageName(), flag);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取包名
     */
    public static synchronized String getPackageName(@NonNull Context context) {
        return context.getPackageName();
    }

    public static Long getFirstInstallTime(@NonNull Context context) {
        PackageInfo packageInfo = getPackageInfo(context, 0);
        if (packageInfo != null) {
            return packageInfo.firstInstallTime;
        } else {
            return null;
        }
    }

    public static Long getLastUpdateTime(@NonNull Context context) {
        PackageInfo packageInfo = getPackageInfo(context, 0);
        if (packageInfo != null) {
            return packageInfo.lastUpdateTime;
        } else {
            return null;
        }
    }

    /**
     * 获取自定义权限 permission
     *
     * @param context
     * @return
     */
    public static PermissionInfo[] getPermissions(@NonNull Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_PERMISSIONS);
        if (packageInfo != null) {
            return packageInfo.permissions;
        } else {
            return null;
        }
    }

    /**
     * 获取uses-permission 列表
     *
     * @param context
     * @return
     */
    public static String[] getRequestedPermissions(@NonNull Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_PERMISSIONS);
        if (packageInfo != null) {
            return packageInfo.requestedPermissions;
        } else {
            return null;
        }
    }

    /**
     * 获取 uses-permission 权限是否获得状态
     *
     * @param context
     * @return
     */
    public static int[] getRequestedPermissionsFlags(@NonNull Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_PERMISSIONS);
        if (packageInfo != null) {
            return packageInfo.requestedPermissionsFlags;
        } else {
            return null;
        }
    }

    /**
     * @param context
     * @return
     */
    public static Long getVersionCode(@NonNull Context context) {
        PackageInfo packageInfo = getPackageInfo(context, 0);
        if (packageInfo != null) {
            return packageInfo.getLongVersionCode();
        } else {
            return null;
        }
    }

    public static String getVersionName(@NonNull Context context) {
        PackageInfo packageInfo = getPackageInfo(context, 0);
        if (packageInfo != null) {
            return packageInfo.versionName;
        } else {
            return null;
        }
    }

    /**
     * 获取签名信息，Android 9.0 以前使用
     *
     * @param context
     * @return
     */
    public static Signature[] getSignatures1(@NonNull Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo != null) {
            return packageInfo.signatures;
        }
        return null;
    }

    /**
     * 获取签名信息，Android 9.0 以后使用
     *
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public static SigningInfo getSignatures2(@NonNull Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNING_CERTIFICATES);
        if (packageInfo != null) {
            return packageInfo.signingInfo;
        }
        return null;
    }

    public static ActivityInfo[] getActivities(@NonNull Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_ACTIVITIES);
        if (packageInfo != null) {
            return packageInfo.activities;
        } else {
            return null;
        }
    }

    public static ServiceInfo[] getServices(@NonNull Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SERVICES);
        if (packageInfo != null) {
            return packageInfo.services;
        } else {
            return null;
        }
    }

    public static ActivityInfo[] getReceivers(@NonNull Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_RECEIVERS);
        if (packageInfo != null) {
            return packageInfo.receivers;
        } else {
            return null;
        }
    }

    public static ProviderInfo[] getProviders(@NonNull Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_PROVIDERS);
        if (packageInfo != null) {
            return packageInfo.providers;
        } else {
            return null;
        }
    }

    /**
     * 获取已安装的 app列表
     * @param context
     * @return
     */
    public static List<PackageInfo> getInstlledPackages(@NonNull Context context) {
        int uninstalled = android.os.Build.VERSION.SDK_INT >= 24 ?
                PackageManager.MATCH_UNINSTALLED_PACKAGES : PackageManager.GET_UNINSTALLED_PACKAGES;
        return getPackageManager(context).getInstalledPackages(uninstalled);
    }
}
