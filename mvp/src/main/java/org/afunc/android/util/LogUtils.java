package org.afunc.android.util;

import android.util.Log;

/**
 * Created by 紫紫 on 2017/8/7
 * Q157596462@outlook.com
 * 描述：日志封装
 */
public class LogUtils {
    private static boolean DEBUG = true;
    private static String mTAG = "紫紫";

    /**
     * @param tag 在application 中 可以设置统一的 mTAG
     */
    public static void init(boolean isDebug, String tag) {
        if (isDebug) {
            DEBUG = isDebug;
            mTAG = tag;
        }
    }

    /**
     * @param msg 错误提示
     */
    public static void i(String msg) {
        if (DEBUG)
            Log.i(mTAG, msg);
    }

    /**
     * @param msg 错误提示
     */
    public static void d(String msg) {
        if (DEBUG)
            Log.d(mTAG, msg);
    }

    /**
     * @param msg 错误提示
     */
    public static void e(String msg) {
        if (DEBUG)
            Log.e(mTAG, msg);
    }

    /**
     * @param msg 错误提示
     */
    public static void v(String msg) {
        if (DEBUG)
            Log.v(mTAG, msg);
    }

    /**
     * @param msg 错误提示
     * @param e   可抛异常
     */
    public static void e(String msg, Throwable e) {
        if (DEBUG)
            Log.e(mTAG, msg, e);
    }

    /**
     * @param TAG 标志位
     * @param msg 错误提示
     */
    public static void i(String TAG, String msg) {
        if (DEBUG)
            Log.i(TAG + TAG, msg);
    }

    /**
     * @param TAG 标志位
     * @param msg 错误提示
     */
    public static void d(String TAG, String msg) {
        if (DEBUG)
            Log.d(TAG + TAG, msg);
    }

    /**
     * @param TAG 标志位
     * @param msg 错误提示
     */
    public static void e(String TAG, String msg) {
        if (DEBUG)
            Log.e(TAG + TAG, msg);
    }

    /**
     * @param TAG 标志位
     * @param msg 错误提示
     */
    public static void v(String TAG, String msg) {
        if (DEBUG)
            Log.v(TAG + TAG, msg);
    }

    /**
     * @param TAG 标志位
     * @param msg 错误提示
     * @param e   可抛异常
     */
    public static void e(String TAG, String msg, Throwable e) {
        if (DEBUG)
            Log.e(TAG + TAG, msg, e);
    }
}
