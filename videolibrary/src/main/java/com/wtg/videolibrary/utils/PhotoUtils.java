package com.wtg.videolibrary.utils;

import android.content.Context;
import android.content.Intent;

import com.wtg.videolibrary.annotation.ImageTypeAnont;
import com.wtg.videolibrary.ui.ImagePickerActivity;

/**
 * author: admin 2019/11/4
 * desc: 打开相册的工具类
 */
public class PhotoUtils {
    private static PhotoUtils photoUtils;

    private boolean isShowCamera = true;//默认显示相机
    private int imageType = ImageTypeAnont.HOLDER_TYPE_ALL;//默认显示所有的类型
    private boolean isCompress = true;//默认压缩
    private boolean isOriginalData = true;//是否有原始数据
    private int maxNum = 9;//选择照片的最大数 默认9
    private int minNum = 1;//选择照片的最小值 默认1

    private PhotoUtils() {

    }

    /**
     * 单例模式
     *
     * @return photoUtils
     */
    public static PhotoUtils getInstance() {
        if (photoUtils == null) {
            synchronized (PhotoUtils.class) {
                if (photoUtils == null) {
                    photoUtils = new PhotoUtils();
                }
            }
        }
        return photoUtils;
    }

    public PhotoUtils setShowCamera(boolean showCamera) {
        isShowCamera = showCamera;
        return photoUtils;
    }

    public PhotoUtils setImageType(@ImageTypeAnont.ImageType int imageType) {
        this.imageType = imageType;
        return photoUtils;
    }

    public PhotoUtils setCompress(boolean compress) {
        isCompress = compress;
        return photoUtils;
    }

    public boolean isShowCamera() {
        return isShowCamera;
    }

    @ImageTypeAnont.ImageType
    public int getImageType() {
        return imageType;
    }

    public boolean isCompress() {
        return isCompress;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public PhotoUtils setMaxNum(int maxNum) {
        this.maxNum = maxNum;
        return photoUtils;
    }

    public int getMinNum() {
        return minNum;
    }

    public PhotoUtils setMinNum(int minNum) {
        this.minNum = minNum;
        return photoUtils;
    }

    /**
     * 打开相册（最后调用）
     *
     * @param context 上下文
     */
    public void startAlbum(Context context) {
        context.startActivity(new Intent(context, ImagePickerActivity.class));
    }

    public boolean isOriginalData() {
        return isOriginalData;
    }

    public void setOriginalData(boolean originalData) {
        isOriginalData = originalData;
    }
}
