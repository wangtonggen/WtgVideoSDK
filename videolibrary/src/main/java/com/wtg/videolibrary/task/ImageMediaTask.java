package com.wtg.videolibrary.task;

import android.content.Context;

import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.listener.LoadMediaListener;
import com.wtg.videolibrary.utils.image.BaseImageMedia;
import com.wtg.videolibrary.utils.image.ImageMediaImp;

/**
 * author: admin 2019/11/7
 * desc: 查询图片的任务
 */
public class ImageMediaTask extends BaseMediaTask {
    public ImageMediaTask(Context context, LoadMediaListener listener) {
        super(context, listener);
    }

    @Override
    BaseImageMedia<BaseMediaBean> getScanner() {
        return new ImageMediaImp(mContext);
    }
}
