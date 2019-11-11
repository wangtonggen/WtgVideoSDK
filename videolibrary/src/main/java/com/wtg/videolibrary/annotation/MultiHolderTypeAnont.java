package com.wtg.videolibrary.annotation;

import android.support.annotation.IntDef;

/**
 * author: wtg  2019/11/11 0011
 * desc: 具体类型的注解
 */
public class MultiHolderTypeAnont {
    //视频
    public static final int HOLDER_TYPE_VIDEO = 0;
    //照片
    public static final int HOLDER_TYPE_IMAGE = 1;
    //相机显示相机
    public static final int Holder_TYPE_CAMERA = 2;

    @IntDef({HOLDER_TYPE_VIDEO, HOLDER_TYPE_IMAGE, Holder_TYPE_CAMERA})
    public @interface MultiHolderType {}
}
