package org.afunc.android.util;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by 紫紫 on 2017/8/7
 * Q157596462@outlook.com
 * 描述：Toast统一管理类 默认显示 Toast
 */
public class ToastUtils {
    /**
     * 显示时长
     */
    @IntDef({LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    private static Toast toast;
    private static CharSequence oldMsg;
    private static long first = 0;
    private static long second = 0;
    private static Context mContext;

    /**
     * 可以在application 中设置
     *
     * @param context 上下文
     */
    public static void initToastUtils(Context context) {
        mContext = context;
    }

    /**
     * 短时间显示Toast
     *
     * @param message 信息
     */
    public static void showShort(CharSequence message) {
        showToast(message, LENGTH_SHORT);
    }

    /**
     * 长时间显示Toast
     *
     * @param message 信息
     */
    public static void showLong(CharSequence message) {
        showToast(message, LENGTH_LONG);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message  信息
     * @param duration 时长
     */
    public static void show(CharSequence message, int duration) {
        showToast(message, duration);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message  信息
     * @param duration 时长
     */
    public static void show(@StringRes int message, @Duration int duration) {
        showToast(mContext.getResources().getString(message), duration);
    }

    /**
     * @param message  信息
     * @param duration 时长
     */
    private static void showToast(@NonNull CharSequence message, @NonNull @Duration int duration) {
        if (mContext != null)
            if (null == toast) {
                toast = Toast.makeText(mContext.getApplicationContext(), message, duration);
                toast.show();
                first = System.currentTimeMillis();
            } else {
                second = System.currentTimeMillis();
                if (message.equals(oldMsg)) {
                    if (second - first > 1000) {
                        toast.show();
                    }
                } else {
                    oldMsg = message;
                    toast.setText(message);
                    toast.show();
                }
            }
        first = second;
    }
}
