package com.wtg.videolibrary.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.CompoundButton;

import com.wtg.videolibrary.R;
import com.wtg.videolibrary.base.BaseActivity;
import com.wtg.videolibrary.utils.ScreenUtils;
import com.wtg.videolibrary.widget.CircleButtonView;
import com.wtg.videolibrary.widget.MyRecordButton;
import com.wtg.videolibrary.widget.RecordButtonConfig;

/**
 * author: wtg  2019/10/28 0028
 * desc: 拍照/拍视频界面
 */
public class VideoActivity extends BaseActivity {
    private SurfaceView sv_record;
    private CircleButtonView circleButtonView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setWindowStatusBarColor(R.color.color_black);

        circleButtonView = findViewById(R.id.circleButtonView);

        circleButtonView.setOnClickListener(new CircleButtonView.OnClickListener() {
            @Override
            public void onClick() {
                Log.e("eee","onClick");
            }
        });

        circleButtonView.setOnLongClickListener(new CircleButtonView.OnLongClickListener() {
            @Override
            public void onLongClick() {
                Log.e("eee","onLongClick");
            }

            @Override
            public void onNoMinRecord(int currentTime) {
                Log.e("eee","onNoMinRecord");
            }

            @Override
            public void onRecordFinishedListener() {
                Log.e("eee","onRecordFinishedListener");
            }
        });
    }
}
