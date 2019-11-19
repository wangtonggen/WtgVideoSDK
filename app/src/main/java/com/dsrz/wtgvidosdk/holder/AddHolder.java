package com.dsrz.wtgvidosdk.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.dsrz.wtgvidosdk.R;
import com.wtg.videolibrary.holder.BaseHolder;

/**
 * author: wtg  2019/11/19 0019
 * desc: 添加图片的holder
 */
public class AddHolder extends BaseHolder {
    public AppCompatImageView iv_add;
    public AddHolder(@NonNull View itemView) {
        super(itemView);
        iv_add = itemView.findViewById(R.id.iv_add);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onMyClick(int position, View view) {
        onItemClickListener.onItemClickListener(position,view);
    }

    @Override
    public void onMyLongClick(int position, View view) {

    }
}
