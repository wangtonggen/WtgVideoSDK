package com.wtg.videolibrary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.Display;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

/**
 * author: wtg  2019/10/29 0029
 * desc:
 */
public class ScreenUtils {
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue dp
     * @return px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 获取屏幕高度
     *
     * @param context 上下文
     * @return 高度
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        return height;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context 上下文
     * @return 宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        return width;
    }

    /**
     * 获取状态栏高度
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
