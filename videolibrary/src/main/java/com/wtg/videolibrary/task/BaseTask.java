package com.wtg.videolibrary.task;

import android.content.Context;

import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.listener.LoadMediaListener;
import com.wtg.videolibrary.utils.image.BaseImageMedia;

import java.util.ArrayList;

/**
 * author: admin 2019/11/7
 * desc: 扫描任务的基类
 */
public abstract class BaseTask implements Runnable{
    Context mContext;
    private LoadMediaListener mListener;
    private BaseImageMedia<BaseMediaBean> baseImageMedia;
    BaseTask(Context context, LoadMediaListener listener) {
        this.mContext = context;
        this.mListener = listener;
        baseImageMedia = getScanner();
    }

    @Override
    public void run() {
        ArrayList<BaseMediaBean> mediaBeans = new ArrayList<>();
        if (baseImageMedia != null){
            mediaBeans = baseImageMedia.querySouurce();
        }

        if (mListener != null) {
            mListener.loadMediaSuccess(MediaHandler.getImageFolder(mContext, mediaBeans));
        }
    }

    abstract BaseImageMedia<BaseMediaBean> getScanner();
}
