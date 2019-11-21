package com.wtg.videolibrary.task;

import android.content.Context;
import android.util.Log;

import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.listener.LoadMediaListener;
import com.wtg.videolibrary.utils.image.BaseImageMedia;
import com.wtg.videolibrary.utils.image.VideoMediaImp;

/**
 * author: admin 2019/11/7
 * desc: 查询视频的任务
 */
public class VideoMediaTask extends BaseMediaTask {
    public VideoMediaTask(Context context, LoadMediaListener listener) {
        super(context, listener);
        Log.e("type", "VideoMediaTask");
    }

    @Override
    BaseImageMedia<BaseMediaBean> getScanner() {
        return new VideoMediaImp(mContext);
    }
}
