package com.dsrz.wtgvidosdk.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.dsrz.wtgvidosdk.R;
import com.wtg.videolibrary.holder.BaseHolder;

/**
 * author: wtg  2019/11/19 0019
 * desc: 图片的holder
 */
public class ImageVideoHolder extends BaseHolder {
    public AppCompatImageView iv_delete;
    public AppCompatImageView iv_image;
    public AppCompatImageView iv_play;
    public ImageVideoHolder(@NonNull View itemView) {
        super(itemView);

        iv_delete = itemView.findViewById(R.id.iv_delete);
        iv_image = itemView.findViewById(R.id.iv_image);
        iv_play = itemView.findViewById(R.id.iv_play);

        itemView.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
    }

    @Override
    public void onMyClick(int position, View view) {
        onItemClickListener.onItemClickListener(position,view);
    }

    @Override
    public void onMyLongClick(int position, View view) {

    }
}
