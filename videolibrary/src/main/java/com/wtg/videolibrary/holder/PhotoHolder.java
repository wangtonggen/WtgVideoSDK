package com.wtg.videolibrary.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.wtg.videolibrary.R;

/**
 * author: admin 2019/10/31
 * desc: 照片的holder
 */
public class PhotoHolder extends BaseHolder{
    public AppCompatImageView iv_photo;
    public AppCompatTextView tv_num;
    public View view;

    public PhotoHolder(@NonNull View itemView) {
        super(itemView);
        iv_photo = itemView.findViewById(R.id.iv_photo);
        tv_num = itemView.findViewById(R.id.tv_num);
        view = itemView.findViewById(R.id.view);

        itemView.setOnClickListener(this);
        tv_num.setOnClickListener(this);
        view.setOnClickListener(this);
    }

    @Override
    void onMyClick(int position,View view) {
        onItemClickListener.onItemClickListener(position,view);
    }

    @Override
    void onMyLongClick(int position,View view) {
        onLongClickListener.onItemLongClickListener(position,view);
    }
}
