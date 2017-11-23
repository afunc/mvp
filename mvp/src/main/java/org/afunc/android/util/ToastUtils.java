package org.afunc.android.util;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;


/**
 * Created by 紫紫 on 2017/8/7
 * Q157596462@outlook.com
 * 描述：Toast统一管理类 默认显示 Toast
 */
public class ToastUtils {

    private static boolean isShow = true;
    private static CharSequence oldMsg;
    private static long first = 0;
    private static long second = 0;


    @IntDef({Toast.LENGTH_LONG, Toast.LENGTH_SHORT})
    @interface Duration {

    }

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
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    private static Toast toast;

    /**
     * 长时间显示Toast
     *
     * @param context 上下文
     * @param message 信息
     */
    public static void showLong(Context context, CharSequence message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  上下文
     * @param message  信息
     * @param duration 时长
     */
    public static void show(Context context, CharSequence message, int duration) {
        showToast(context, message, duration);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  上下文
     * @param message  信息
     * @param duration 时长
     */
    public static void show(Context context, @StringRes int message, @Duration int duration) {
        showToast(context, context.getResources().getString(message), duration);
    }

    private static void showToast(@NonNull Context context, @NonNull CharSequence msg, @NonNull @Duration int time) {
        if (isShow)
            if (null == toast) {
                toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
                toast.show();
                first = System.currentTimeMillis();
            } else {
                second = System.currentTimeMillis();
                if (msg.equals(oldMsg)) {
                    if (second - first > Toast.LENGTH_SHORT) {
                        toast.show();
                    }
                } else {
                    oldMsg = msg;
                    toast.setText(msg);
                    toast.show();
                }
            }
        first = second;
    }

}
