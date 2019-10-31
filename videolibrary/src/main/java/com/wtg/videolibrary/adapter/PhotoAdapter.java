package com.wtg.videolibrary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wtg.videolibrary.bean.PhotoBean;
import com.wtg.videolibrary.holder.PhotoHolder;

import java.util.List;

/**
 * author: admin 2019/10/31
 * desc: 相册的adapter
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
    private List<PhotoBean> list;
    private Context context;
    public PhotoAdapter(Context context,List<PhotoBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder photoHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
