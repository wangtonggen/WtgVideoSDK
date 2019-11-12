package com.wtg.videolibrary.holder;

import android.support.annotation.NonNull;
import android.view.View;

import com.github.chrisbanes.photoview.PhotoView;
import com.wtg.videolibrary.R;

import cn.jzvd.JzvdStd;

/**
 * author: wtg  2019/11/12 0012
 * desc: 预览holder
 */
public class MediaPreviewHolder extends BaseHolder{
    public JzvdStd video_player;
    public PhotoView iv_image;
    public MediaPreviewHolder(@NonNull View itemView) {
        super(itemView);

        video_player = itemView.findViewById(R.id.video_player);
        iv_image = itemView.findViewById(R.id.iv_photo_view);
    }

    @Override
    void onMyClick(int position, View view) {

    }

    @Override
    void onMyLongClick(int position, View view) {

    }
}
