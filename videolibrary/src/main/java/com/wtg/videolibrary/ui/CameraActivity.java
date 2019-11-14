package com.wtg.videolibrary.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.iceteck.silicompressorr.SiliCompressor;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wtg.videolibrary.R;
import com.wtg.videolibrary.base.BaseActivity;
import com.wtg.videolibrary.utils.FileUtils;
import com.wtg.videolibrary.utils.ScreenUtils;
import com.wtg.videolibrary.widget.AutoFitTextureView;
import com.wtg.videolibrary.widget.CameraController;
import com.wtg.videolibrary.widget.CircleButtonView;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * author: wtg  2019/10/28 0028
 * desc: 拍照/拍视频界面
 */
public class CameraActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
    private AutoFitTextureView sv_record;
    private CircleButtonView circleButtonView;
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
        setContentView(R.layout.activity_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sv_record = findViewById(R.id.sv_record);
        sv_record.setAspectRatio(ScreenUtils.getScreenWidth(this),ScreenUtils.getScreenHeight(this));
        circleButtonView = findViewById(R.id.circleButtonView);
        iv_video_switch = findViewById(R.id.iv_video_switch);
        iv_video_close = findViewById(R.id.iv_video_close);

        mCameraController = CameraController.getInstance(this);
        mCameraController.setRecordFinishListener((type, path) -> {
            Log.e("tag",path+"---");
            switch (type){
                case IMAGE://图片
                    new Thread(){
                        @Override
                        public void run() {
                            Log.e("tag",path+"---111");
                            String filePath= SiliCompressor.with(CameraActivity.this).compress(path, new File(FileUtils.IMAGE_ROOT),false);
                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri uri = Uri.fromFile(new File(filePath));
                            intent.setData(uri);
                            sendBroadcast(intent);
                            Log.e("eee",filePath+"---");
                        }
                    }.start();
                    break;
                case VIDEO://摄像
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                String filePath1 = SiliCompressor.with(CameraActivity.this).compressVideo(path, FileUtils.IMAGE_ROOT,0,0,10000000);
                                //通知系统刷新
                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + new File(filePath1))));
                                Log.e("eee",filePath1+"---filePath1");
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                                Log.e("wwww",e.getMessage());
                            }
                        }
                    }.start();
                    break;
            }
        });
        final RxPermissions rxPermissions = new RxPermissions(this);
        Disposable permissions = rxPermissions.requestEachCombined(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted){//已经同意
                        mCameraController.setFolderPath(BASE_PATH);
                        mCameraController.InitCamera(sv_record);
                        //预览界面出现时按钮才可以使用
                        circleButtonView.setOnClickListener(()->mCameraController.takePicture());
                        circleButtonView.setOnLongClickListener(new CircleButtonView.OnLongClickListener() {
                            @Override
                            public void onLongClick() {
                                mCameraController.startRecordingVideo();
                            }

                            @Override
                            public void onNoMinRecord(int currentTime) {
                                Toast.makeText(CameraActivity.this,"时间太短了",Toast.LENGTH_SHORT).show();
                                mCameraController.stopRecordingVideo();
                            }

                            @Override
                            public void onRecordFinishedListener() {
                                mCameraController.stopRecordingVideo();
                            }
                        });
                    }else if (permission.shouldShowRequestPermissionRationale){//未同意但是未勾选不在提醒
                        showPermissionDialog(CameraActivity.this,"相机和存储");
                    }else {//未同意勾选不在提醒
                        showPermissionDialog(CameraActivity.this,"相机和存储");
                    }
                });

        iv_video_switch.setOnClickListener(this);
        iv_video_close.setOnClickListener(this);
        sv_record.setOnTouchListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_video_switch){
            Toast.makeText(this,"切换",Toast.LENGTH_SHORT).show();
            isBack = !isBack;
            mCameraController.switchCamera(isBack);
        }else if (id == R.id.iv_video_close){
            Toast.makeText(this,"finish",Toast.LENGTH_SHORT).show();
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
    protected void onDestroy() {
        super.onDestroy();
        mCameraController.closeCamera();
        mCameraController.stopBackgroundThread();
    }
}
