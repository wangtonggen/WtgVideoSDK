package com.wtg.videolibrary.bean;

/**
 * author: admin 2019/11/7
 * desc: 视频文件类
 */
public class VideoMediaBean extends BaseMediaBean {
    /**
     * 总时长（针对视频文件）
     */
    private int time;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
