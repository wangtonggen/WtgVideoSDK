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
public class PhotoTypeHolder extends BaseHolder{
    private AppCompatImageView iv_photo;
    private AppCompatTextView tv_photo_type;
    private AppCompatTextView tv_photo_num;
    private AppCompatImageView iv_photo_select;
    public PhotoTypeHolder(@NonNull View itemView) {
        super(itemView);
        iv_photo = itemView.findViewById(R.id.iv_photo);
        tv_photo_type = itemView.findViewById(R.id.tv_photo_type);
        tv_photo_num = itemView.findViewById(R.id.tv_photo_num);
        iv_photo_select = itemView.findViewById(R.id.iv_photo_select);

        itemView.setOnClickListener(this);
    }

    @Override
    void onMyClick(int position,View view) {
        onItemClickListener.onItemClickListener(position,view);
    }

    @Override
    void onMyLongClick(int position,View view) {
        onLongClickListener.onItemLongClickListener(position,view);
    }

    public AppCompatImageView getIv_photo() {
        return iv_photo;
    }

    public AppCompatTextView getTv_photo_type() {
        return tv_photo_type;
    }

    public AppCompatTextView getTv_photo_num() {
        return tv_photo_num;
    }

    public AppCompatImageView getIv_photo_select() {
        return iv_photo_select;
    }
}
