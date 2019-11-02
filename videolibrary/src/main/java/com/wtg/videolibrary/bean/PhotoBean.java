package com.wtg.videolibrary.bean;

/**
 * author: admin 2019/10/31
 * desc: 照片的bean 需要上传该照片的需要进行继承此类
 */
public class PhotoBean {
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
    private ImageType imageType;

    /**
     * 文件的本地路径
     */
    private String filePath;

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

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public enum ImageType{
        TYPE_IMAGE,//图片类型
        TYPE_VIDEO//视频类型
    }
}