package com.wtg.videolibrary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wtg.videolibrary.R;
import com.wtg.videolibrary.bean.FolderBean;
import com.wtg.videolibrary.bean.PhotoTypeBean;
import com.wtg.videolibrary.holder.PhotoTypeHolder;

import java.util.List;


/**
 * author: admin 2019/11/3
 * desc: 选择照片类型的adapter
 */
public class PhotoTypeAdapter extends BaseAdapter<FolderBean,PhotoTypeHolder> {

    public PhotoTypeAdapter(Context context, List<FolderBean> list) {
       super(context, list);
    }

    @NonNull
    @Override
    public PhotoTypeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_photo_type, viewGroup, false);
        PhotoTypeHolder photoTypeHolder = new PhotoTypeHolder(view);
        photoTypeHolder.setOnItemClickListener(onItemClickListener);
        return photoTypeHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoTypeHolder photoTypeHolder, int i) {
        photoTypeHolder.itemView.setTag(i);
        FolderBean photoTypeBean = mList.get(i);
        photoTypeHolder.iv_photo_select.setVisibility(photoTypeBean.isChecked() ? View.VISIBLE : View.INVISIBLE);
        photoTypeHolder.tv_photo_type.setText(photoTypeBean.getFolderName());
        Glide.with(mContext).load(photoTypeBean.getMediaFileList().get(0).getPath()).into(photoTypeHolder.iv_photo);
        photoTypeHolder.tv_photo_num.setText(String.format("%s",photoTypeBean.getMediaFileList().size()));
    }
}
