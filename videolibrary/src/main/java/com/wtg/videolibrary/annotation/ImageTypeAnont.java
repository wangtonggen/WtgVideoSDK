package com.wtg.videolibrary.annotation;

import android.support.annotation.IntDef;

/**
 * author: wtg  2019/11/5 0005
 * desc: 照片类型
 */
public class ImageTypeAnont {
    //视频
    public static final int HOLDER_TYPE_VIDEO = 0;
    //照片
    public static final int HOLDER_TYPE_IMAGE = 1;
    //相机显示相机
    public static final int HOLDER_TYPE_CAMERA = 2;
    //所有类型
    public static final int HOLDER_TYPE_ALL = 3;

    @IntDef({HOLDER_TYPE_VIDEO,HOLDER_TYPE_IMAGE,HOLDER_TYPE_CAMERA,HOLDER_TYPE_ALL})
    public @interface ImageType{}
}
