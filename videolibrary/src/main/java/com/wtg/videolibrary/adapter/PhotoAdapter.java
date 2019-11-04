package com.wtg.videolibrary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wtg.videolibrary.R;
import com.wtg.videolibrary.bean.PhotoBean;
import com.wtg.videolibrary.holder.PhotoHolder;

import java.util.List;

/**
 * author: admin 2019/10/31
 * desc: 相册的adapter
 */
public class PhotoAdapter extends BaseAdapter<PhotoHolder> {
    private Context context;
    private List<PhotoBean> list;
    private List<String> filePaths;

    public PhotoAdapter(Context context, List<PhotoBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_photo, viewGroup, false);
        PhotoHolder photoHolder = new PhotoHolder(view);
        photoHolder.setOnItemClickListener(onItemClickListener);
        return photoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder photoHolder, int i) {
        photoHolder.itemView.setTag(i);
        photoHolder.tv_num.setTag(i);
        photoHolder.iv_photo.setTag(i);
        photoHolder.view.setTag(i);
        PhotoBean photoBean = list.get(i);
        photoHolder.tv_num.setBackgroundResource(photoBean.isSelect() ? R.drawable.shape_num_selected : R.drawable.shape_num_unselect);
        if (photoBean.isSelect()) {
            if (filePaths != null){
                if (filePaths.contains(photoBean.getFilePath())){
                    photoHolder.tv_num.setText(String.format("%s", filePaths.indexOf(photoBean.getFilePath())+1));
                }
            }
            photoHolder.view.setVisibility(View.VISIBLE);
        } else {
            photoHolder.tv_num.setText("");
            photoHolder.view.setVisibility(View.GONE);
        }
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
