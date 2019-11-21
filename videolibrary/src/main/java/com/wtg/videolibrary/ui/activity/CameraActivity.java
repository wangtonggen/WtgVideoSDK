package com.wtg.videolibrary.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wtg.videolibrary.R;
import com.wtg.videolibrary.base.BaseActivity;
import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.result.ResultCode;
import com.wtg.videolibrary.utils.CameraUtils;
import com.wtg.videolibrary.utils.ScreenUtils;
import com.wtg.videolibrary.widget.AutoFitTextureView;
import com.wtg.videolibrary.widget.CameraController;
import com.wtg.videolibrary.widget.CircleButtonView;

import java.io.File;

import io.reactivex.disposables.Disposable;

import static com.wtg.videolibrary.annotation.CameraAnont.CAMERA_ALL;
import static com.wtg.videolibrary.annotation.CameraAnont.CAMERA_IMAGE;
import static com.wtg.videolibrary.annotation.CameraAnont.CAMERA_VIDEO;
import static com.wtg.videolibrary.annotation.MultiHolderTypeAnont.HOLDER_TYPE_IMAGE;
import static com.wtg.videolibrary.annotation.MultiHolderTypeAnont.HOLDER_TYPE_VIDEO;
import static com.wtg.videolibrary.result.MediaParams.MEDIA_CAMERA;

/**
 * author: wtg  2019/10/28 0028
 * desc: 拍照/拍视频界面
 */
public class CameraActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
    private AutoFitTextureView sv_record;
    private CircleButtonView circleButtonView;
    private AppCompatTextView tv_hint;
    private AppCompatImageView iv_video_switch;
    private AppCompatImageView iv_video_close;

    private CameraController mCameraController;

    public static String BASE_PATH = Environment.getExternalStorageDirectory() + "/AAA";

    private boolean isBack = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);
        sv_record = findViewById(R.id.sv_record);
        sv_record.setAspectRatio(ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenHeight(this));
        circleButtonView = findViewById(R.id.circleButtonView);
        iv_video_switch = findViewById(R.id.iv_video_switch);
        iv_video_close = findViewById(R.id.iv_video_close);
        tv_hint = findViewById(R.id.tv_hint);

        switch (CameraUtils.getInstance().getCameraType()) {
            case CAMERA_IMAGE:
                tv_hint.setText(R.string.str_button_hint_image);
                break;
            case CAMERA_VIDEO:
                tv_hint.setText(R.string.str_button_hint_video);
                break;
            case CAMERA_ALL:
                tv_hint.setText(R.string.str_button_hint_all);
                break;
        }
        mCameraController = CameraController.getInstance(this);
        mCameraController.setRecordFinishListener((type, path) -> {
            Intent cameraIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(new File(path));
            cameraIntent.setData(uri);
            sendBroadcast(cameraIntent);
            BaseMediaBean baseMediaBean = new BaseMediaBean();
            baseMediaBean.setPath(path);
            switch (type) {
                case IMAGE://图片
                    baseMediaBean.setHolderType(HOLDER_TYPE_IMAGE);
                    break;
                case VIDEO://摄像
                    baseMediaBean.setHolderType(HOLDER_TYPE_VIDEO);
                    break;
            }
            Intent intent = new Intent(CameraActivity.this, CameraPreviewActivity.class);
            intent.putExtra(MEDIA_CAMERA, baseMediaBean);
            if (CameraUtils.getInstance().getOpenActivity() != null) {
                startActivity(intent);
            } else {
                startActivityForResult(intent, CameraUtils.getInstance().getRequestCode());
            }
        });
        final RxPermissions rxPermissions = new RxPermissions(this);
        Disposable permissions = rxPermissions.requestEachCombined(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {//已经同意
                        mCameraController.setFolderPath(BASE_PATH);
                        mCameraController.InitCamera(sv_record);
                        //预览界面出现时按钮才可以使用
                        circleButtonView.setOnClickListener(() -> mCameraController.takePicture());
                        circleButtonView.setOnLongClickListener(new CircleButtonView.OnLongClickListener() {
                            @Override
                            public void onLongClick() {
                                mCameraController.startRecordingVideo();
                            }

                            @Override
                            public void onNoMinRecord(int currentTime) {
                                Toast.makeText(CameraActivity.this, "时间太短了", Toast.LENGTH_SHORT).show();
                                mCameraController.stopRecordingVideo();
                            }

                            @Override
                            public void onRecordFinishedListener() {
                                mCameraController.stopRecordingVideo();
                            }
                        });
                    } else if (permission.shouldShowRequestPermissionRationale) {//未同意但是未勾选不在提醒
                        showPermissionDialog(CameraActivity.this, "相机和存储");
                    } else {//未同意勾选不在提醒
                        showPermissionDialog(CameraActivity.this, "相机和存储");
                    }
                });

        iv_video_switch.setOnClickListener(this);
        iv_video_close.setOnClickListener(this);
        sv_record.setOnTouchListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_video_switch) {
            isBack = !isBack;
            mCameraController.switchCamera(isBack);
        } else if (id == R.id.iv_video_close) {
            finish();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        onFocus(new Point((int)event.getX(),(int)event.getY()),this);
//        mCameraController.touchFoucs(event);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == CameraUtils.getInstance().getRequestCode()) {
            Intent intent = new Intent();
            intent.putExtra(MEDIA_CAMERA, data.getSerializableExtra(MEDIA_CAMERA));
            setResult(ResultCode.RESULT_MEDIA_CODE, intent);
            finish();
        }
    }

    @Override
    protected void onResume() {//显示预览 拍摄完成后跳转到预览图片和预览视频的界面
        super.onResume();
    }

    @Override
    protected void onPause() {//停止相机预览
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraController.closeCamera();
        mCameraController.stopBackgroundThread();
    }
}
