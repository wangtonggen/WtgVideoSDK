package com.wtg.videolibrary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wtg.videolibrary.R;
import com.wtg.videolibrary.annotation.ImageTypeAnont;
import com.wtg.videolibrary.bean.BaseMediaBean;
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
    private List<String> filePaths;

    public PhotoAdapter(Context context, List<BaseMediaBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BaseHolder baseHolder;
        if (i == ImageTypeAnont.HOLDER_TYPE_CAMERA){
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_photo_camera, viewGroup, false);
            baseHolder = new CameraHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_photo, viewGroup, false);
            baseHolder = new PhotoHolder(view);
        }
        baseHolder.setOnItemClickListener(onItemClickListener);
        return baseHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder baseHolder, int i) {
        BaseMediaBean photoBean = list.get(i);
        switch (photoBean.getImageType()) {
            case ImageTypeAnont.HOLDER_TYPE_IMAGE:
                PhotoHolder photoHolder = (PhotoHolder) baseHolder;
                photoHolder.itemView.setTag(i);
                photoHolder.tv_num.setTag(i);
                if (photoHolder.iv_photo != null) {
                    photoHolder.iv_photo.setTag(i);
                }
                if (photoHolder.view != null) {
                    photoHolder.view.setTag(i);
                }
                Log.e("tag",photoHolder.tv_num.getTag()+"---");
                photoHolder.tv_num.setBackgroundResource(photoBean.isSelect() ? R.drawable.shape_num_selected : R.drawable.shape_num_unselect);
                if (photoBean.isSelect()) {
                    if (filePaths != null) {
                        if (filePaths.contains(photoBean.getPath())) {
                            photoHolder.tv_num.setText(String.format("%s", filePaths.indexOf(photoBean.getPath()) + 1));
                        }
                    }
                    photoHolder.view.setVisibility(View.VISIBLE);
                } else {
                    photoHolder.tv_num.setText("");
                    photoHolder.view.setVisibility(View.INVISIBLE);
                }
                break;
            case ImageTypeAnont.HOLDER_TYPE_CAMERA:
                CameraHolder cameraHolder = (CameraHolder) baseHolder;
                cameraHolder.itemView.setTag(i);
                cameraHolder.tv_num.setTag(i);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getImageType();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }
}
