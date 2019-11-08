package com.wtg.videolibrary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wtg.videolibrary.R;
import com.wtg.videolibrary.bean.FolderBean;
import com.wtg.videolibrary.bean.PhotoTypeBean;
import com.wtg.videolibrary.holder.PhotoTypeHolder;

import java.util.List;


/**
 * author: admin 2019/11/3
 * desc: 选择照片类型的adapter
 */
public class PhotoTypeAdapter extends BaseAdapter<PhotoTypeHolder> {
    private Context context;
    private List<FolderBean> list;

    public PhotoTypeAdapter(Context context, List<FolderBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PhotoTypeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_photo_type, viewGroup, false);
        PhotoTypeHolder photoTypeHolder = new PhotoTypeHolder(view);
        photoTypeHolder.setOnItemClickListener(onItemClickListener);
        return photoTypeHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoTypeHolder photoTypeHolder, int i) {
        photoTypeHolder.itemView.setTag(i);
        FolderBean photoTypeBean = list.get(i);
        photoTypeHolder.getIv_photo_select().setVisibility(photoTypeBean.isChecked() ? View.VISIBLE : View.INVISIBLE);
//        photoTypeHolder.getTv_photo_type().setText(photoTypeBean.getPhotoType());
        photoTypeHolder.getTv_photo_num().setText(String.format("%s",photoTypeBean.getMediaFileList().size()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
