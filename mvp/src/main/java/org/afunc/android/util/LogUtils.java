package org.afunc.android.util;

import android.util.Log;

/**
 * @author  紫紫 on 2017/8/7
 * Q157596462@outlook.com
 * 描述：日志封装
 */
public class LogUtils {

    private static boolean DEBUG = false;
    private static String mTAG = "紫紫";

    /**
     * @param tag 在application 中 可以设置统一的 mTAG
     */
    public static void init(String tag) {
        DEBUG = true;
        mTAG = tag;
    }

    /**
     *
     * @param debug 是否显示
     */
    public static void init(boolean debug) {
        DEBUG = debug;
    }
    /**
     *
     * @param tag tag 字符串
     * @param debug 是否显示
     */
    public static void init(String tag ,boolean debug) {
        DEBUG = debug;
        mTAG = tag;
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
     * @param tag 标志位
     * @param msg 错误提示
     */
    public static void e(String tag, String msg) {
        e(mTAG + tag, msg, null);
    }

    /**
     * @param tag 标志位
     * @param msg 错误提示
     * @param e   可抛异常
     */
    private static void e(String tag, String msg, Throwable e) {
        if (DEBUG){
            Log.e(tag, msg, e);
        }else {
            Log.e(tag,"Log hide ！ if need show log use LogUtils.init(true) ");
        }
    }
}
