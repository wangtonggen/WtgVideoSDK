package com.wtg.videolibrary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wtg.videolibrary.R;
import com.wtg.videolibrary.bean.PhotoTypeBean;
import com.wtg.videolibrary.holder.PhotoTypeHolder;

import java.util.List;


/**
 * author: admin 2019/11/3
 * desc: 选择照片类型的adapter
 */
public class PhotoTypeAdapter extends RecyclerView.Adapter<PhotoTypeHolder> {
    private Context context;
    private List<PhotoTypeBean> list;
    public PhotoTypeAdapter(Context context, List<PhotoTypeBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PhotoTypeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_photo_type,viewGroup,false);
        return new PhotoTypeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoTypeHolder photoTypeHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
