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
public class PhotoAdapter extends BaseAdapter<BaseHolder> {
    private Context context;
    private List<BaseMediaBean> list;
    private List<BaseMediaBean> filePaths;
    private SparseArray<String> paths = new SparseArray<>();

    public PhotoAdapter(Context context, List<BaseMediaBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BaseHolder baseHolder;
        if (i == MultiHolderTypeAnont.Holder_TYPE_CAMERA) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_photo_camera, viewGroup, false);
            baseHolder = new CameraHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_photo, viewGroup, false);
            baseHolder = new PhotoHolder(view);
        }
        baseHolder.setOnItemClickListener(onItemClickListener);
        return baseHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder baseHolder, int i) {
        BaseMediaBean photoBean = list.get(i);
        Log.e("ppp", photoBean.isSelect() + "---" + i);
        switch (photoBean.getHolderType()) {
            case MultiHolderTypeAnont.HOLDER_TYPE_IMAGE:
                PhotoHolder photoHolder = (PhotoHolder) baseHolder;
                photoHolder.itemView.setTag(i);
                photoHolder.tv_num.setTag(i);
                if (photoHolder.iv_photo != null) {
                    Glide.with(context).load(photoBean.getPath()).into(photoHolder.iv_photo);
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
                PhotoHolder photoHolder1 = (PhotoHolder) baseHolder;
                photoHolder1.itemView.setTag(i);
                photoHolder1.tv_num.setTag(i);
                if (photoHolder1.iv_photo != null) {
                    Glide.with(context).load(photoBean.getPath()).into(photoHolder1.iv_photo);
                }
                if (photoHolder1.view != null) {
                    photoHolder1.view.setTag(i);
                }
                photoHolder1.tv_num.setBackgroundResource(photoBean.isSelect() ? R.drawable.shape_num_selected : R.drawable.shape_num_unselect);
                photoHolder1.view.setBackgroundResource(photoBean.isSelect() ? R.color.color_99000000 : R.color.color_33000000);
                if (photoBean.isSelect()) {
                    if (filePaths != null) {
                        if (filePaths.contains(photoBean)) {
                            photoHolder1.tv_num.setText(String.format("%s", filePaths.indexOf(photoBean) + 1));
                        }
                    }
                } else {
                    photoHolder1.tv_num.setText("");
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
        return list.get(position).getHolderType();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<BaseMediaBean> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<BaseMediaBean> filePaths) {
        this.filePaths = filePaths;
    }
}
