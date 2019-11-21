package com.dsrz.wtgvidosdk.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dsrz.wtgvidosdk.R;
import com.wtg.videolibrary.annotation.CameraAnont;
import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.ui.activity.CameraActivity;
import com.wtg.videolibrary.utils.CameraUtils;
import com.wtg.videolibrary.utils.PhotoUtils;

import java.util.ArrayList;
import java.util.List;

import static com.wtg.videolibrary.annotation.MediaTypeAnont.MEDIA_TYPE_ALL;
import static com.wtg.videolibrary.annotation.MediaTypeAnont.MEDIA_TYPE_IMAGE;
import static com.wtg.videolibrary.annotation.MediaTypeAnont.MEDIA_TYPE_VIDEO;
import static com.wtg.videolibrary.result.MediaParams.MEDIA_PARAMS_NAME;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    AppCompatCheckBox cb_compress;
    AppCompatCheckBox cb_original;
    AppCompatCheckBox cb_camera;
    AppCompatImageView iv_image;
    TextView tv_test;
    TextView tv_camera;
    private List<BaseMediaBean> baseMediaBeans = new ArrayList<>();

    private int mediaType = MEDIA_TYPE_ALL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///storage/emulated/0/AAA/2019-11-20 09:54:39.jpg
        tv_test = findViewById(R.id.tv_test);
        tv_camera = findViewById(R.id.tv_camera);
        cb_compress = findViewById(R.id.cb_compress);
        cb_original = findViewById(R.id.cb_original);
        cb_camera = findViewById(R.id.cb_camera);
        iv_image = findViewById(R.id.iv_image);
        Glide.with(this).load("/storage/emulated/0/AAA/2019-11-20 09:54:39.jpg").into(iv_image);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.rb_all:
                    mediaType = MEDIA_TYPE_ALL;
                    break;
                case R.id.rb_photo:
                    mediaType = MEDIA_TYPE_IMAGE;
                    break;
                case R.id.rb_video:
                    mediaType = MEDIA_TYPE_VIDEO;
                    break;
            }
        });
        tv_test.setOnClickListener(v -> PhotoUtils.getInstance().setMaxNum(6).setMinNum(1).
                setMediaType(mediaType).setShowCamera(cb_camera.isChecked()).
                setOpenActivity(CameraResultActivity.class).startImagePicker(this)
        );

        tv_camera.setOnClickListener(v -> CameraUtils.getInstance().setCameraType(CameraAnont.CAMERA_ALL).
                setOpenActivity(CameraResultActivity.class).startCameraActivity(this));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 1002) {
                baseMediaBeans.clear();
                baseMediaBeans.addAll((ArrayList<BaseMediaBean>) data.getSerializableExtra(MEDIA_PARAMS_NAME));
            }
        }
    }
}
