package com.wtg.videolibrary.annotation;

import android.support.annotation.IntDef;

/**
 * author: wtg  2019/11/5 0005
 * desc: 照片类型
 */
public class MediaTypeAnont {
    //视频
    public static final int MEDIA_TYPE_VIDEO = 0;
    //照片
    public static final int MEDIA_TYPE_IMAGE = 1;
    //所有类型
    public static final int MEDIA_TYPE_ALL = 3;

    @IntDef({MEDIA_TYPE_VIDEO, MEDIA_TYPE_IMAGE, MEDIA_TYPE_ALL})
    public @interface MediaType {
    }
}
