package org.afunc.android.util;

import android.util.Log;

/**
 * Created by 紫紫 on 2017/8/7
 * Q157596462@outlook.com
 * 描述：日志封装
 */
public class LogUtils {
    private static boolean DEBUG = false;
    private static String mTAG = "紫紫";

    /**
     * @param tag 在application 中 可以设置统一的 mTAG
     */
    public static void init(boolean isDebug, String tag) {
        if (isDebug) {
            DEBUG = true;
            mTAG = tag;
        }
    }

    /**
     * @param msg 错误提示
     */
    public static void e(String msg) {
        e(mTAG, msg, null);
    }

    /**
     * @param msg 错误提示
     * @param e   可抛异常
     */
    public static void e(String msg, Throwable e) {
        e(mTAG, msg, e);
    }


    /**
     * @param TAG 标志位
     * @param msg 错误提示
     */
    public static void e(String TAG, String msg) {
        e(mTAG + TAG, msg, null);
    }

    /**
     * @param tag 标志位
     * @param msg 错误提示
     * @param e   可抛异常
     */
    private static void e(String tag, String msg, Throwable e) {
        if (DEBUG)
            Log.e(tag, msg, e);
    }
}
