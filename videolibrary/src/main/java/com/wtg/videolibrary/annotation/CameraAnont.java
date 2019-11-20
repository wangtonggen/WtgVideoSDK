package com.wtg.videolibrary.annotation;

import android.support.annotation.IntDef;

/**
 * author: wtg  2019/11/20 0020
 * desc: 相机的注解 用来区分是可以拍照，拍视频还是都可以
 */
public class CameraAnont {
    public static final int CAMERA_IMAGE = 0;//只允许拍照
    public static final int CAMERA_VIDEO = 1;//只允许拍视频
    public static final int CAMERA_ALL = 2;//允许所有

    @IntDef({CAMERA_IMAGE, CAMERA_VIDEO, CAMERA_ALL})
    public @interface CameraType {}
}
