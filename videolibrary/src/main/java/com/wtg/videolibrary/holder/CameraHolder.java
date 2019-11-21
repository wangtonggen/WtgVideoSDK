package com.wtg.videolibrary.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.wtg.videolibrary.R;

/**
 * author: wtg  2019/11/5 0005
 * desc: 相机的holder
 */
public class CameraHolder extends BaseHolder {
    public AppCompatTextView tv_num;

    public CameraHolder(@NonNull View itemView) {
        super(itemView);
        tv_num = itemView.findViewById(R.id.tv_num);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onMyClick(int position, View view) {
        onItemClickListener.onItemClickListener(position, view);
    }

    @Override
    public void onMyLongClick(int position, View view) {

    }
}
