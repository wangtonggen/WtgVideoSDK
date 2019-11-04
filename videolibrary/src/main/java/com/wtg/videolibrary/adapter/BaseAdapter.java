package com.wtg.videolibrary.adapter;

import android.support.v7.widget.RecyclerView;

import com.wtg.videolibrary.listener.OnChildClickListener;
import com.wtg.videolibrary.listener.OnChildLongClickListener;
import com.wtg.videolibrary.listener.OnItemClickListener;
import com.wtg.videolibrary.listener.OnItemLongClickListener;

/**
 * author: wtg  2019/11/4 0004
 * desc: adapter 适配器的基类
 */
public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    //item点击事件
    protected OnItemClickListener onItemClickListener;
    //子view点击事件
    protected OnChildClickListener onChildClickListener;

    protected OnItemLongClickListener onItemLongClickListener;

    protected OnChildLongClickListener onChildLongClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnChildClickListener getOnChildClickListener() {
        return onChildClickListener;
    }

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public OnChildLongClickListener getOnChildLongClickListener() {
        return onChildLongClickListener;
    }

    public void setOnChildLongClickListener(OnChildLongClickListener onChildLongClickListener) {
        this.onChildLongClickListener = onChildLongClickListener;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
