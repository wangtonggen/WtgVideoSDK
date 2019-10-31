package com.wtg.videolibrary.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wtg.videolibrary.R;

/**
 * author: admin 2019/10/31
 * desc:
 */
public class PhotoHolder extends RecyclerView.ViewHolder{
    private AppCompatImageView iv_photo;
    private AppCompatTextView tv_num;
    private View view;

    public PhotoHolder(@NonNull View itemView) {
        super(itemView);
        iv_photo = itemView.findViewById(R.id.iv_photo);
        tv_num = itemView.findViewById(R.id.tv_num);
        view = itemView.findViewById(R.id.view);
    }
}
