package com.wtg.videolibrary.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceView;

import com.wtg.videolibrary.R;
import com.wtg.videolibrary.base.BaseActivity;

/**
 * author: wtg  2019/10/28 0028
 * desc: 拍照/拍视频界面
 */
public class VideoActivity extends BaseActivity {
    private SurfaceView sv_record;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setWindowStatusBarColor(R.color.color_black);

//        sv_record = findViewById(R.id.sv_record);
//        sv_record.setOutlineProvider(new TextureVideoViewOutlineProvider(60));
//        sv_record.setClipToOutline(true);
    }
}
