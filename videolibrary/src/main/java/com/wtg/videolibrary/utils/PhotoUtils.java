package com.wtg.videolibrary.utils;

import com.wtg.videolibrary.bean.PhotoBean;

import static com.wtg.videolibrary.bean.PhotoBean.ImageType.TYPE_ALL;

/**
 * author: admin 2019/11/4
 * desc: 打开相册的工具类
 */
public class PhotoUtils {
    private static PhotoUtils photoUtils;

    private boolean isShowCamera = true;//默认显示相机
    private PhotoBean.ImageType imageType = TYPE_ALL;//默认显示所有的类型
    private boolean isCompress = true;//默认压缩
    private int maxNum = 9;//选择照片的最大数 默认9
    private int minNum = 1;//选择照片的最小值 默认1
    private PhotoUtils(){

    }

    /**
     * 单例模式
     * @return photoUtils
     */
    public static PhotoUtils getInstance(){
        if (photoUtils == null){
            synchronized (PhotoUtils.class){
                if (photoUtils == null){
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

    public PhotoUtils setImageType(PhotoBean.ImageType imageType) {
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

    public PhotoBean.ImageType getImageType() {
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
}
