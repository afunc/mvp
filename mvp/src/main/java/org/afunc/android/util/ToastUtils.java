package org.afunc.android.util;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by 紫紫 on 2017/8/7
 * Q157596462@outlook.com
 * 描述：Toast统一管理类 默认显示 Toast
 */
public class ToastUtils {

    private static boolean isShow = true;

    /**
     * 可以在application 中设置
     *
     * @param show true or false
     */
    public static void initToastUtils(boolean show) {
        isShow = show;
    }

    /**
     * 短时间显示Toast
     *
     * @param context 上下文
     * @param message 信息
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context 上下文
     * @param message 信息
     */
    public static void showShort(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context 上下文
     * @param message 信息
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context 上下文
     * @param message 信息
     */
    public static void showLong(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  上下文
     * @param message  信息
     * @param duration 时长
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  上下文
     * @param message  信息
     * @param duration 时长
     */
    public static void show(Context context, int message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

}
