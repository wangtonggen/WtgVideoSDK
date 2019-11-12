package com.wtg.videolibrary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wtg.videolibrary.R;
import com.wtg.videolibrary.annotation.MediaTypeAnont;
import com.wtg.videolibrary.annotation.MultiHolderTypeAnont;
import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.holder.BaseHolder;
import com.wtg.videolibrary.holder.CameraHolder;
import com.wtg.videolibrary.holder.PhotoHolder;

import java.util.List;

/**
 * author: admin 2019/10/31
 * desc: 相册的adapter
 */
public class PhotoAdapter extends BaseAdapter<BaseMediaBean,BaseHolder> {
    private List<BaseMediaBean> filePaths;

    public PhotoAdapter(Context context, List<BaseMediaBean> list) {
      super(context,list);
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BaseHolder baseHolder;
        if (i == MultiHolderTypeAnont.Holder_TYPE_CAMERA) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_photo_camera, viewGroup, false);
            baseHolder = new CameraHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_photo, viewGroup, false);
            baseHolder = new PhotoHolder(view);
        }
        baseHolder.setOnItemClickListener(onItemClickListener);
        return baseHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder baseHolder, int i) {
        BaseMediaBean photoBean = mList.get(i);
        switch (photoBean.getHolderType()) {
            case MultiHolderTypeAnont.HOLDER_TYPE_IMAGE:
                PhotoHolder photoHolder = (PhotoHolder) baseHolder;
                photoHolder.itemView.setTag(i);
                photoHolder.tv_num.setTag(i);
                photoHolder.tv_video_time.setVisibility(View.INVISIBLE);
                if (photoHolder.iv_photo != null) {
                    Glide.with(mContext).load(photoBean.getPath()).into(photoHolder.iv_photo);
                }
                if (photoHolder.view != null) {
                    photoHolder.view.setTag(i);
                }
                photoHolder.tv_num.setBackgroundResource(photoBean.isSelect() ? R.drawable.shape_num_selected : R.drawable.shape_num_unselect);
                photoHolder.view.setBackgroundResource(photoBean.isSelect() ? R.color.color_99000000 : R.color.color_33000000);
                if (photoBean.isSelect()) {
                    if (filePaths != null) {
                        if (filePaths.contains(photoBean)) {
                            photoHolder.tv_num.setText(String.format("%s", filePaths.indexOf(photoBean) + 1));
                        }
                    }
                } else {
                    photoHolder.tv_num.setText("");
                }
                break;
            case MultiHolderTypeAnont.HOLDER_TYPE_VIDEO:
                PhotoHolder videoHolder = (PhotoHolder) baseHolder;
                videoHolder.itemView.setTag(i);
                videoHolder.tv_num.setTag(i);
                Log.e("video","videoTime="+photoBean.getDuration());
                videoHolder.tv_video_time.setVisibility(View.INVISIBLE);
                if (videoHolder.iv_photo != null) {
                    Glide.with(mContext).load(photoBean.getPath()).into(videoHolder.iv_photo);
                }
                if (videoHolder.view != null) {
                    videoHolder.view.setTag(i);
                }
                videoHolder.tv_num.setBackgroundResource(photoBean.isSelect() ? R.drawable.shape_num_selected : R.drawable.shape_num_unselect);
                videoHolder.view.setBackgroundResource(photoBean.isSelect() ? R.color.color_99000000 : R.color.color_33000000);
                if (photoBean.isSelect()) {
                    if (filePaths != null) {
                        if (filePaths.contains(photoBean)) {
                            videoHolder.tv_num.setText(String.format("%s", filePaths.indexOf(photoBean) + 1));
                        }
                    }
                } else {
                    videoHolder.tv_num.setText("");
                }
                break;
            case MultiHolderTypeAnont.Holder_TYPE_CAMERA:
                CameraHolder cameraHolder = (CameraHolder) baseHolder;
                cameraHolder.itemView.setTag(i);
                cameraHolder.tv_num.setTag(i);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getHolderType();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public List<BaseMediaBean> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<BaseMediaBean> filePaths) {
        this.filePaths = filePaths;
    }
}
