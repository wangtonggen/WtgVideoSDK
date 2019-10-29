package com.wtg.videolibrary.utils;

import android.content.Context;

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
}
