package com.wtg.videolibrary.task;

import android.content.Context;

import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.listener.LoadMediaListener;
import com.wtg.videolibrary.utils.image.BaseImageMedia;
import com.wtg.videolibrary.utils.image.ImageMediaImp;
import com.wtg.videolibrary.utils.image.VideoMediaImp;

import java.util.ArrayList;

/**
 * author: wtg  2019/11/8 0008
 * desc: 查询所有的图片和视频
 */
public class AllMediaTask extends BaseMediaTask {
    private BaseImageMedia<BaseMediaBean> videoMedia;
    public AllMediaTask(Context context, LoadMediaListener listener) {
        super(context, listener);
        videoMedia = new VideoMediaImp(mContext);
    }

    @Override
    BaseImageMedia<BaseMediaBean> getScanner() {
        return new ImageMediaImp(mContext);
    }

    @Override
    public void run() {
        //存放所有照片
        ArrayList<BaseMediaBean> imageFileList = new ArrayList<>();
        //存放所有视频
        ArrayList<BaseMediaBean> videoFileList = new ArrayList<>();

        if (baseImageMedia != null) {
            imageFileList = baseImageMedia.querySource();
        }
        if (videoMedia != null) {
            videoFileList = videoMedia.querySource();
        }

        if (mListener != null) {
            mListener.loadMediaSuccess(MediaHandler.getMediaFolder(mContext, imageFileList, videoFileList));
        }
    }
}
