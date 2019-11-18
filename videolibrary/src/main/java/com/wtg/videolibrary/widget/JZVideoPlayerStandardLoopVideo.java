package com.wtg.videolibrary.widget;

import android.content.Context;
import android.util.AttributeSet;

import cn.jzvd.JzvdStd;

/**
 * author: wtg  2019/11/18 0018
 * desc: 重复播放视频
 */
public class JZVideoPlayerStandardLoopVideo extends JzvdStd {
    public JZVideoPlayerStandardLoopVideo(Context context) {
        super(context);
    }

    public JZVideoPlayerStandardLoopVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        startVideo();
    }
}
