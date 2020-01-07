package com.demo.ck.applicationUtil;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * 检查获取权限
 */
public class PermissionUtil {
    public static final int REQUEST_PERMISSIONS = 0x001;

    public static final int CONTEXT_ACTIVITY = 1;
    public static final int CONTEXT_FRAGMENT = 2;

    /**
     * 检查是否有权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean checkPermission(@NonNull Context context, @NonNull String[] permissions) {
        ArrayList<String> needQuestPer = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (context.getPackageManager().checkPermission(permissions[i]
                    , context.getPackageName())
                    != PackageManager.PERMISSION_GRANTED) {
                needQuestPer.add(permissions[i]);
            }
        }

        if (needQuestPer.size() == 0) {
            return true;
        }

        return false;
    }

    /**
     * 检查是否有权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean checkPermission2(@NonNull Context context, @NonNull String... permissions) {
        ArrayList<String> needQuestPer = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (context.getPackageManager().checkPermission(permissions[i]
                    , context.getPackageName())
                    != PackageManager.PERMISSION_GRANTED) {
                needQuestPer.add(permissions[i]);
            }
        }

        if (needQuestPer.size() == 0) {
            return true;
        }

        return false;
    }

    /**
     * 获取权限
     *
     * @param context
     * @param permissions
     * @param requestCode
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean requestPermission(@NonNull Object context, String[] permissions, int requestCode) {
        final int contextType;
        Context finalContext;

        if (context instanceof Activity) {
            contextType = CONTEXT_ACTIVITY;
            finalContext = (Activity) context;
        } else if (context instanceof Fragment) {
            contextType = CONTEXT_FRAGMENT;
            finalContext = ((Fragment) context).getActivity();
        } else {
            throw new IllegalArgumentException("context not found");
        }

        ArrayList<String> needQuestPer = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (finalContext.getPackageManager().checkPermission(permissions[i]
                    , finalContext.getPackageName())
                    != PackageManager.PERMISSION_GRANTED) {
                needQuestPer.add(permissions[i]);
            }
        }

        if (needQuestPer.size() != 0) {
            String[] Questper = new String[needQuestPer.size()];
            for (int i = 0; i < needQuestPer.size(); i++) {
                Questper[i] = needQuestPer.get(i);
            }

            if (contextType == CONTEXT_ACTIVITY) {
                ((Activity) context).requestPermissions(Questper, requestCode);
            } else if (contextType == CONTEXT_FRAGMENT) {
                ((Fragment) context).requestPermissions(Questper, requestCode);
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 权限是否都开通
     *
     * @param grantResults
     * @return
     */
    public static boolean checkSharePermission(int[] grantResults) {
        boolean check = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                check = false;
                break;
            }
        }

        return check;
    }
}
