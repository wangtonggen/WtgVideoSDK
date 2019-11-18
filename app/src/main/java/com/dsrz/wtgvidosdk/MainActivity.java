package com.dsrz.wtgvidosdk;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.ui.activity.CameraActivity;
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
    TextView tv_test;
    TextView tv_camera;
    private List<BaseMediaBean> baseMediaBeans = new ArrayList<>();

    private int mediaType = MEDIA_TYPE_ALL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_test = findViewById(R.id.tv_test);
        tv_camera = findViewById(R.id.tv_camera);
        cb_compress = findViewById(R.id.cb_compress);
        cb_original = findViewById(R.id.cb_original);
        cb_camera = findViewById(R.id.cb_camera);
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
        tv_test.setOnClickListener(v -> PhotoUtils.getInstance().setMaxNum(6).setMinNum(1).setMediaType(mediaType).setOriginalData(cb_original.isChecked()).setOriginalDataList(baseMediaBeans).setCompress(cb_compress.isChecked()).setShowCamera(cb_camera.isChecked()).startImagePicker(this, 1002)
        );

        tv_camera.setOnClickListener(v -> startActivity(new Intent(this, CameraActivity.class)));
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
