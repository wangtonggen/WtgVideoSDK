package com.wtg.videolibrary.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wtg.videolibrary.R;

/**
 * author: admin 2019/11/3
 * desc: 选择类型文件夹的holder
 */
public class PhotoTypeHolder extends RecyclerView.ViewHolder{
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
    }
}
