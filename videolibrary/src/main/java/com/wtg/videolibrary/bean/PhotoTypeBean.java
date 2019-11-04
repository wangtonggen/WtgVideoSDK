package com.wtg.videolibrary.bean;

/**
 * author: admin 2019/11/3
 * desc: 图片类型
 */
public class PhotoTypeBean {
    //图片文件夹类型
    private String photoType;
    //文件夹显示的类型
    private String imagePath;
    //文件夹里面的文件书泪
    private int photoNum;
    //是否选中
    private boolean isSelect = false;

    public String getPhotoType() {
        return photoType;
    }

    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getPhotoNum() {
        return photoNum;
    }

    public void setPhotoNum(int photoNum) {
        this.photoNum = photoNum;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
