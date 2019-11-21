package com.wtg.videolibrary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wtg.videolibrary.R;
import com.wtg.videolibrary.annotation.MultiHolderTypeAnont;
import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.holder.MediaPreviewHolder;

import java.util.List;

import cn.jzvd.Jzvd;

/**
 * author: wtg  2019/11/12 0012
 * desc: 预览adapter
 */
public class MediaPreviewAdapter extends BaseAdapter<BaseMediaBean, MediaPreviewHolder> {
    public MediaPreviewAdapter(Context context, List<BaseMediaBean> list) {
        super(context, list);
    }

    @NonNull
    @Override
    public MediaPreviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MediaPreviewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_item_media_preview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MediaPreviewHolder mediaPreviewHolder, int i) {
        mediaPreviewHolder.itemView.setTag(i);
        BaseMediaBean photoBean = mList.get(i);
        switch (photoBean.getHolderType()) {
            case MultiHolderTypeAnont.HOLDER_TYPE_IMAGE://图片
                mediaPreviewHolder.video_player.setVisibility(View.GONE);
                mediaPreviewHolder.iv_image.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(photoBean.getPath()).into(mediaPreviewHolder.iv_image);
                break;
            case MultiHolderTypeAnont.HOLDER_TYPE_VIDEO://视频
                mediaPreviewHolder.video_player.setVisibility(View.VISIBLE);
                mediaPreviewHolder.iv_image.setVisibility(View.GONE);
                Glide.with(mContext).load(photoBean.getPath()).into(mediaPreviewHolder.video_player.thumbImageView);
                mediaPreviewHolder.video_player.setUp(photoBean.getPath(), "", Jzvd.SCREEN_NORMAL);
                break;
        }
    }

}
