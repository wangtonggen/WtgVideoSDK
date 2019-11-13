package com.wtg.videolibrary.task;

import android.content.Context;

import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.listener.LoadMediaListener;
import com.wtg.videolibrary.utils.image.BaseImageMedia;
import com.wtg.videolibrary.utils.image.ImageMediaImp;
import com.wtg.videolibrary.utils.image.VideoMediaImp;

import java.util.ArrayList;

/**
 * author: admin 2019/11/7
 * desc: 扫描任务的基类
 */
public abstract class BaseMediaTask implements Runnable{
    Context mContext;
    LoadMediaListener mListener;
    BaseImageMedia<BaseMediaBean> baseImageMedia;
    BaseMediaTask(Context context, LoadMediaListener listener) {
        this.mContext = context;
        this.mListener = listener;
        baseImageMedia = getScanner();
    }

    @Override
    public void run() {
        ArrayList<BaseMediaBean> mediaBeans = new ArrayList<>();
        if (baseImageMedia != null){
            mediaBeans = baseImageMedia.querySource();
        }

        if (mListener != null) {
            if (baseImageMedia instanceof VideoMediaImp){
                mListener.loadMediaSuccess(MediaHandler.getVoideFolder(mContext, mediaBeans));
            }else if (baseImageMedia instanceof ImageMediaImp){
                mListener.loadMediaSuccess(MediaHandler.getImageFolder(mContext, mediaBeans));
            }
        }
    }

    abstract BaseImageMedia<BaseMediaBean> getScanner();
}
