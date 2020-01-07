package com.demo.ck.applicationUtil.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * 获取设备基本信息
 */
public class DeviceUtil {
    private static final String TAG = "DeviceUtil";

    /**
     * 获取手机型号
     * <p>
     * android.os.Build提供以下信息：
     * String  BOARD   The name of the underlying board, like "goldfish".
     * String  BRAND   The brand (e.g., carrier) the software is customized for, if any.
     * String  DEVICE  The name of the industrial design.
     * String  FINGERPRINT     A string that uniquely identifies this build.
     * String  HOST
     * String  ID  Either a changelist number, or a label like "M4-rc20".
     * String  MODEL   The end-user-visible name for the end product.
     * String  PRODUCT     The name of the overall product.
     * String  TAGS    Comma-separated tags describing the build, like "unsigned,debug".
     * long    TIME
     * String  TYPE    The type of build, like "user" or "eng".
     * String  USER
     */
    public String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 整体产品
     *
     * @return
     */
    public String getProduct() {
        return android.os.Build.PRODUCT;
    }

    /**
     * 厂商
     *
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * Android 版本
     *
     * @return
     */
    public static String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }


    /**
     * 获取移动用户标志，IMSI
     * <p>
     * 用到的权限：
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     */
    @SuppressLint("HardwareIds")
    public static String getIMSI(@NonNull Context ctx) {
        String strResult = null;
        TelephonyManager telephonyManager = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "DeviceUtil;getSubscriberId;permission deny");
                return null;
            }
            strResult = telephonyManager.getSubscriberId();
        }
        return strResult;
    }

    /**
     * 获取设备ID\IMEI
     * <p>
     * 用到的权限：
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     */
    @SuppressLint("HardwareIds")
    public static String getIMEI(@NonNull Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "DeviceUtil;getImei;permission deny");
            return null;
        }
        TelephonyManager telephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        return telephonyMgr != null ? telephonyMgr.getDeviceId() : null;
    }

    /**
     * 获取SIM卡号
     * <p>
     * 用到的权限：
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     */
    @SuppressLint("HardwareIds")
    public static String getSIM(@NonNull Context ctx) {
        String strResult = null;
        TelephonyManager telephonyManager = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "DeviceUtil;getSim;permission deny");
                return null;
            }
            strResult = telephonyManager.getSimSerialNumber();
        }
        return strResult;
    }

    /**
     * 获取移动用户标志，IMSI(卡2)
     * <p>
     * 用到的权限：
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     */
    public static String getIMSIBySoltId(@NonNull Context context, int soltId) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "DeviceUtil;getIMSIById;permission deny");
            return null;
        }

        Object object = getPhoneInfo(context, getSubIdBySoltId(context, soltId), "getSubscriberId");

        if (object != null) {
            return String.valueOf(object);
        }

        return null;
    }

    /**
     * 获取设备ID\IMEI(卡2)
     * <p>
     * 用到的权限：
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     */
    public static String getIMEIBySoltId(@NonNull Context context, int soltId) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "DeviceUtil;getIMEIById;permission deny");
            return null;
        }

        Object object = getPhoneInfo(context, getSubIdBySoltId(context, soltId), "getDeviceId");

        if (object != null) {
            return String.valueOf(object);
        }

        return null;
    }

    /**
     * 获取SIM卡号(卡2)
     * <p>
     * 用到的权限：
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     */
    public static String getSIMBySoltId(@NonNull Context context, int soltId) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "DeviceUtil;getSIMById;permission deny");
            return null;
        }

        Object object = getPhoneInfo(context, getSubIdBySoltId(context, soltId), "getSimSerialNumber");

        if (object != null) {
            return String.valueOf(object);
        }

        return null;
    }

    public static int getSubIdBySoltId(@NonNull Context context, int slotId) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "DeviceUtil;getSubId;permission deny");
            return -1;
        }
        SubscriptionManager mSubscriptionManager = (SubscriptionManager) context.getSystemService(
                Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        if (mSubscriptionManager != null) {
            SubscriptionInfo info = mSubscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(slotId);
            if (info != null) {
                return info.getSubscriptionId();
            }
        }

        return -1;
    }

    private static Class[] getMethodParamTypes(@NonNull String methodName) {
        Class[] params = null;
        try {
            Method[] methods = TelephonyManager.class.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                if (methodName.equals(methods[i].getName())) {
                    params = methods[i].getParameterTypes();
                    if (params.length >= 1) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    private static Object getPhoneInfo(@NonNull Context context, int subId, @NonNull String methodName) {
        Object value = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                Method method = tm.getClass().getMethod(methodName, getMethodParamTypes(methodName));
                if (subId >= 0) {
                    value = method.invoke(tm, subId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 获取基站信息
     * <p>
     * 用到的权限：
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
     */
    @SuppressLint("HardwareIds")
    public static SCell getCellInfo(@NonNull Context ctx) {
        SCell cell = new SCell();
        TelephonyManager tm = null;
        try {
            tm = (TelephonyManager) ctx
                    .getSystemService(Context.TELEPHONY_SERVICE);
        } catch (Exception e) {
            return null;
        }

        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        String IMSI = tm != null ? tm.getSubscriberId() : null;
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (IMSI != null) {
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                cell.NETWORK_TYPE = "CHINA MOBILE";

                GsmCellLocation location = (GsmCellLocation) tm
                        .getCellLocation();
                if (location == null) {
                    cell = null;
                } else {
                    String operator = tm.getNetworkOperator();
                    if (operator.length() > 4) {
                        int mcc = Integer.parseInt(operator.substring(0, 3));
                        int mnc = Integer.parseInt(operator.substring(3));
                        int cid = location.getCid();
                        int lac = location.getLac();

                        cell.MCC = mcc;
                        cell.MNC = mnc;
                        cell.LAC = lac;
                        cell.CID = cid;
                    } else {
                        cell = null;
                    }
                }
            } else if (IMSI.startsWith("46001")) {
                cell.NETWORK_TYPE = "CHINA UNICOM";

                GsmCellLocation location = (GsmCellLocation) tm
                        .getCellLocation();
                if (location == null) {
                    cell = null;
                } else {
                    String operator = tm.getNetworkOperator();
                    if (operator.length() > 4) {
                        int mcc = Integer.parseInt(operator.substring(0, 3));
                        int mnc = Integer.parseInt(operator.substring(3));
                        int cid = location.getCid();
                        int lac = location.getLac();

                        cell.MCC = mcc;
                        cell.MNC = mnc;
                        cell.LAC = lac;
                        cell.CID = cid;
                    } else {
                        cell = null;
                    }
                }
            } else if (IMSI.startsWith("46003")) {
                cell.NETWORK_TYPE = "CHINA TELECOM";

                CdmaCellLocation location = (CdmaCellLocation) tm
                        .getCellLocation();
                if (location == null) {
                    cell = null;
                } else {
                    String operator = tm.getNetworkOperator();
                    if (operator.length() > 4) {
                        int mcc = Integer.parseInt(operator.substring(0, 3));
                        int mnc = Integer.parseInt(operator.substring(3));
                        int cid = location.getBaseStationId();
                        int lac = location.getNetworkId();

                        cell.MCC = mcc;
                        cell.MNC = mnc;
                        cell.LAC = lac;
                        cell.CID = cid;
                    } else {
                        cell = null;
                    }
                }
            } else {
                cell = null;
            }
        } else {
            cell = null;
        }
        return cell;
    }

    /**
     * 基站信息
     */
    public static class SCell {

        public String NETWORK_TYPE;

        public int MCC;

        public int MNC;

        public int LAC;

        public int CID;

        public JSONObject toJSON() {
            JSONObject json = new JSONObject();
            try {
                json.put("network_type", NETWORK_TYPE);
                json.put("mcc", MCC);
                json.put("MNC", MNC);
                json.put("LAC", LAC);
                json.put("CID", CID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        public String toString() {
            return "SCell{" +
                    "NETWORK_TYPE='" + NETWORK_TYPE + '\'' +
                    ", MCC=" + MCC +
                    ", MNC=" + MNC +
                    ", LAC=" + LAC +
                    ", CID=" + CID +
                    '}';
        }
    }


    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getRealScreenWidth(@NonNull Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            wm.getDefaultDisplay().getRealMetrics(outMetrics);
        }
        return outMetrics.widthPixels;
    }

    /**
     * 获取实际屏幕高度
     *
     * @param context
     * @return
     */
    public static int getRealScreenHeight(@NonNull Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            wm.getDefaultDisplay().getRealMetrics(outMetrics);
        }
        return outMetrics.heightPixels;
    }

    /**
     * 获取密度
     *
     * @param context
     * @return
     */
    public static float getScreenDensity(@NonNull Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            wm.getDefaultDisplay().getRealMetrics(outMetrics);
        }
        return outMetrics.density;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(@NonNull Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
