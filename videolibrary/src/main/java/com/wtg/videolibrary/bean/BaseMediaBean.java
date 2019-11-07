package com.wtg.videolibrary.bean;

import com.wtg.videolibrary.annotation.ImageTypeAnont;

import java.io.Serializable;

/**
 * author: admin 2019/11/7
 * desc: bean文件的基类
 */
public class BaseMediaBean implements Serializable {
    //是否选中
    private boolean isSelect;
    //文件的类型
    @ImageTypeAnont.ImageType
    private int imageType = ImageTypeAnont.HOLDER_TYPE_IMAGE;
    //文件路径
    private String path;
    //文件后缀
    private String mime;
    //文件夹id
    private Integer folderId;
    //文件夹名字
    private String folderName;
    //视频文件时长
    private long duration;
    //添加日期
    private long dateToken;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDateToken() {
        return dateToken;
    }

    public void setDateToken(long dateToken) {
        this.dateToken = dateToken;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

}
