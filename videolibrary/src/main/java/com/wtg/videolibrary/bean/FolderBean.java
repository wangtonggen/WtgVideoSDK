package com.wtg.videolibrary.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * author: admin 2019/11/7
 * desc: 文件夹类型
 */
public class FolderBean {
    private int folderId;
    private String folderName;//文件夹名字
    private String folderCover;
    private ArrayList<BaseMediaBean> mediaFileList;//文件夹下的图片
    private boolean isChecked;//是否选中

    public FolderBean(int folderId, String folderName, String folderCover, ArrayList<BaseMediaBean> mediaFileList) {
        this.folderId = folderId;
        this.folderName = folderName;
        this.folderCover = folderCover;
        this.mediaFileList = mediaFileList;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderCover() {
        return folderCover;
    }

    public void setFolderCover(String folderCover) {
        this.folderCover = folderCover;
    }

    public ArrayList<BaseMediaBean> getMediaFileList() {
        return mediaFileList;
    }

    public void setMediaFileList(ArrayList<BaseMediaBean> mediaFileList) {
        this.mediaFileList = mediaFileList;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
