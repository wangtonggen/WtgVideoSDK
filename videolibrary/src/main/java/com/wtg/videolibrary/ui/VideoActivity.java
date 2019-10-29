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
import com.wtg.videolibrary.widget.MyRecordButton;
import com.wtg.videolibrary.widget.RecordButtonConfig;

/**
 * author: wtg  2019/10/28 0028
 * desc: 拍照/拍视频界面
 */
public class VideoActivity extends BaseActivity {
    private SurfaceView sv_record;
    private MyRecordButton btn_record;
    private SwitchCompat switchCompat;
    private RecordButtonConfig singleRecordCofig;//拍照片
    private RecordButtonConfig recordButtonConfig;//视频录制
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setWindowStatusBarColor(R.color.color_black);

        singleRecordCofig = new RecordButtonConfig();
        singleRecordCofig.setButton_border_color(Color.parseColor("#66ffffff"));
        singleRecordCofig.setButton_inside_color(Color.WHITE);
        singleRecordCofig.setButton_recode_mode(MyRecordButton.RecordMode.SINGLE_CLICK);
        singleRecordCofig.setCircleStrokeWidth(ScreenUtils.dip2px(this,5));

        recordButtonConfig = new RecordButtonConfig();
        recordButtonConfig.setButton_border_color(Color.parseColor("#F46565"));
        recordButtonConfig.setButton_inside_color(Color.parseColor("#F92B2B"));
        singleRecordCofig.setButton_recode_mode(MyRecordButton.RecordMode.LONG_CLICK);
        singleRecordCofig.setCircleStrokeWidth(ScreenUtils.dip2px(this,5));

        btn_record = findViewById(R.id.btn_record);
        switchCompat = findViewById(R.id.switchCompat);

//        btn_record.setmRecordMode(MyRecordButton.RecordMode.SINGLE_CLICK);
        btn_record.setmRecordMode(MyRecordButton.RecordMode.LONG_CLICK);
//        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                btn_record.setRecordBurronConfig(isChecked?recordButtonConfig:singleRecordCofig);
//            }
//        });

//        btn_record.setOnRecordStateChangedListener(new RecordButton.OnRecordStateChangedListener() {
//            @Override
//            public void onRecordStart() {
//                Log.e("tag","start");
//            }
//
//            @Override
//            public void onLongPressRecordStart() {
//                Log.e("tag","onLongPressRecordStart");
//            }
//
//            @Override
//            public void onRecordStop() {
//                Log.e("tag","onRecordStop");
//            }
//
//            @Override
//            public void onZoom(float percentage) {
//                Log.e("tag","onZoom");
//            }
//        });

//        btn_record.setmRecordMode(MyRecordButton.RecordMode.SINGLE_CLICK);

        btn_record.setOnRecordStateChangedListener(new MyRecordButton.OnRecordStateChangedListener() {
            @Override
            public void onRecordStart() {
                Log.e("tag","start");
            }

            @Override
            public void onLongPressRecordStart() {
                Log.e("tag","onLongPressRecordStart");
            }

            @Override
            public void onRecordStop() {
                switch (btn_record.getmRecordMode()){
                    case ORIGIN:
                    case LONG_CLICK://长按
                        break;
                    case SINGLE_CLICK://单次点击
                        //拍照
                        break;
                }
                Log.e("tag","onRecordStop");
            }

            @Override
            public void onZoom(float percentage) {
                Log.e("tag","onZoom");
            }
        });
    }
}
