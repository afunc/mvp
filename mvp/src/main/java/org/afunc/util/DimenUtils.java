package org.afunc.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;


/**
 * @author 紫紫 on 2017/8/7
 *         Q157596462@outlook.com
 *         描述：单位转换类
 */
public class DimenUtils {

    private static Context mContext;

    /**
     * @param context 上下文
     */
    public static void init(Context context) {
        mContext = context;
    }

    /**
     * dp转px
     *
     * @param dpVal dp源
     * @return px值
     */
    public static int dp2px(float dpVal) {
        if (mContext != null) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, mContext.getResources().getDisplayMetrics());
        }
        return -1;
    }

    /**
     * sp转px
     *
     * @param spVal sp源
     * @return px值
     */
    public static int sp2px(float spVal) {
        if (mContext != null) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, mContext.getResources().getDisplayMetrics());
        }
        return -1;
    }

    /**
     * px转dp
     *
     * @param pxVal px源
     * @return dp值
     */
    public static float px2dp(float pxVal) {
        if (mContext != null) {
            float scale = mContext.getResources().getDisplayMetrics().density;
            return (pxVal / scale);
        }
        return -1;
    }

    /**
     * px转sp
     *
     * @param pxVal px源
     * @return sp值
     */
    public static float px2sp(float pxVal) {
        if (mContext != null) {
            return (pxVal / mContext.getResources().getDisplayMetrics().scaledDensity);
        }
        return -1;
    }

    /**
     * 获取屏幕尺寸
     *
     * @return 寬高
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static Point getScreenSize() {
        Point point = new Point();
        if (mContext != null) {
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            display.getSize(point);
            return point;
        }
        return point;
    }
}
