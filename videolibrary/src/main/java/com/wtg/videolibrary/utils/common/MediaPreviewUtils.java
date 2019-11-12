package com.wtg.videolibrary.utils.common;

import com.wtg.videolibrary.bean.BaseMediaBean;

import java.util.List;

/**
 * author: wtg  2019/11/12 0012
 * desc: 在使用Intent传递数据时会有1M的大小限制 解决预览问题 使用单例模式 把需要传递的数据保存起来
 */
public class MediaPreviewUtils {
    private static MediaPreviewUtils previewUtils;
    private List<BaseMediaBean> list;
    private int position = 0;
    private MediaPreviewUtils(){

    }

    public static MediaPreviewUtils getInstance(){
        if (previewUtils == null){
            synchronized (MediaPreviewUtils.class){
                if (previewUtils == null){
                    previewUtils = new MediaPreviewUtils();
                }
            }
        }
        return previewUtils;
    }

    /**
     * 设置是的数据
     * @return data
     */
    public List<BaseMediaBean> getList() {
        return list;
    }

    /**
     * 需要预览的照片
     * @param list data
     */
    public void setList(List<BaseMediaBean> list) {
        this.list = list;
    }

    /**
     * 开始预览的位置
     * @return position
     */
    public int getPosition() {
        return position;
    }

    /**
     * 设置预览的位置
     * @param position position
     */
    public void setPosition(int position) {
        this.position = position;
    }
}
