package com.wtg.videolibrary.bean;

import com.wtg.videolibrary.annotation.ImageTypeAnont;

import java.io.Serializable;

/**
 * author: admin 2019/10/31
 * desc: 照片的bean 需要上传该照片的需要进行继承此类
 */
public class PhotoBean implements Serializable {
    /**
     * 文件的总大小
     */
    private String size;
    /**
     * 总时长（针对视频文件）
     */
    private int time;
    /**
     * 图片/视频的后缀名
     */
    private String strType;

    /**
     * 文件的类型
     */
    @ImageTypeAnont.ImageType
    private int imageType = ImageTypeAnont.HOLDER_TYPE_IMAGE;

    /**
     * 文件的本地路径
     */
    private String filePath;

    //是否选中
    private boolean isSelect;

    public PhotoBean(){

    }

    public PhotoBean(String filePath) {
        this.filePath = filePath;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @ImageTypeAnont.ImageType
    public int getImageType() {
        return imageType;
    }

    public void setImageType(@ImageTypeAnont.ImageType int imageType) {
        this.imageType = imageType;
    }
}
