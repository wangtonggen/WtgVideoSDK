package com.wtg.videolibrary.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.wtg.videolibrary.R;

/**
 * author: admin 2019/11/3
 * desc: 选择类型文件夹的holder
 */
public class PhotoTypeHolder extends BaseHolder {
    public AppCompatImageView iv_photo;
    public AppCompatTextView tv_photo_type;
    public AppCompatTextView tv_photo_num;
    public AppCompatImageView iv_photo_select;

    public PhotoTypeHolder(@NonNull View itemView) {
        super(itemView);
        iv_photo = itemView.findViewById(R.id.iv_photo);
        tv_photo_type = itemView.findViewById(R.id.tv_photo_type);
        tv_photo_num = itemView.findViewById(R.id.tv_photo_num);
        iv_photo_select = itemView.findViewById(R.id.iv_photo_select);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onMyClick(int position, View view) {
        onItemClickListener.onItemClickListener(position, view);
    }

    @Override
    public void onMyLongClick(int position, View view) {
        onLongClickListener.onItemLongClickListener(position, view);
    }
}
