package com.wtg.videolibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.wtg.videolibrary.listener.OnChildClickListener;
import com.wtg.videolibrary.listener.OnChildLongClickListener;
import com.wtg.videolibrary.listener.OnItemClickListener;
import com.wtg.videolibrary.listener.OnItemLongClickListener;

import java.util.List;

/**
 * author: wtg  2019/11/4 0004
 * desc: adapter 适配器的基类
 */
public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected Context mContext;
    protected List<T> mList;

    public BaseAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
    }

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
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
