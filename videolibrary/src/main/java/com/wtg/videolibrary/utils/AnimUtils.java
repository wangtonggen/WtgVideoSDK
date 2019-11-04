package com.wtg.videolibrary.utils;

import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * author: wtg  2019/11/4 0004
 * desc: 动画工具类
 */
public class AnimUtils {
    /**
     * 生成ScaleAnimation
     * <p>
     * time=360
     */
    public static Animation getScaleAnimation(float fromX,
                                              float toX,
                                              float fromY,
                                              float toY,
                                              int pivotXType,
                                              float pivotXValue,
                                              int pivotYType,
                                              float pivotYValue) {
        Animation scaleAnimation = new ScaleAnimation(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType,
                pivotYValue
        );
        scaleAnimation.setDuration(360);
        return scaleAnimation;
    }

    public static Animation createTranslateAnimation(float fromX, float toX, float fromY, float toY) {
        Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                fromX,
                Animation.RELATIVE_TO_SELF,
                toX,
                Animation.RELATIVE_TO_SELF,
                fromY,
                Animation.RELATIVE_TO_SELF,
                toY);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        return animation;
    }

    /**
     * 生成TranslateAnimation
     *
     * @param durationMillis 动画显示时间
     * @param start          初始位置
     */
    public static Animation getTranslateVerticalAnimation(float start, float end, int durationMillis) {
        Animation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, start, Animation.RELATIVE_TO_PARENT, end);
        translateAnimation.setDuration(durationMillis);
        return translateAnimation;
    }
}
