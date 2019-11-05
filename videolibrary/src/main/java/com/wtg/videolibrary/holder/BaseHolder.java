package com.wtg.videolibrary.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wtg.videolibrary.listener.OnChildClickListener;
import com.wtg.videolibrary.listener.OnItemClickListener;
import com.wtg.videolibrary.listener.OnItemLongClickListener;

/**
 * author: admin 2019/11/4
 * desc: holder基类
 */
public abstract class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
    //item点击事件
    protected OnItemClickListener onItemClickListener;
    //子view点击事件
    protected OnChildClickListener onChildClickListener;

    protected OnItemLongClickListener onLongClickListener;

    public BaseHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null){
            if (v != null){
                int position = (int)v.getTag();
                onMyClick(position,v);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (onLongClickListener != null){
            if (v != null){
                int position = (int)v.getTag();
                onMyLongClick(position,v);
                return true;
            }else {
                return true;
            }
        }else {
            return false;
        }
    }

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

    public OnItemLongClickListener getOnLongClickListener() {
        return onLongClickListener;
    }

    public void setOnLongClickListener(OnItemLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    abstract void onMyClick(int position,View view);

    abstract void onMyLongClick(int position,View view);

}
