package com.demo.ck.applicationUtil.util;

import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;

public class ExitUtil {

    private static long exitTime = 0;

    private static long clickTime = 0;
    private static int clickViewId;

    private static Handler handler = new Handler();

    /**
     * 退出应用
     *
     * @param activity
     */
    public static void ExitApp(Activity activity) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(activity.getApplication(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            activity.finish();
        }

    }


    /**
     * 防止view连续点击
     *
     * @param v
     * @return
     */
    public static synchronized boolean canClick(int v) {
        if (clickViewId != v) {
            clickViewId = v;
            return true;
        }
        if ((System.currentTimeMillis() - clickTime) > 500) {
            clickTime = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }

    }

    /**
     * 强制重启应用
     */
    public static void exitApp() {
        handler.removeCallbacks(exitRunnable);
        handler.postDelayed(exitRunnable, 100);
    }

    public static Runnable exitRunnable = new Runnable() {
        @Override
        public void run() {
            System.exit(0);//重启应用
        }
    };

}
