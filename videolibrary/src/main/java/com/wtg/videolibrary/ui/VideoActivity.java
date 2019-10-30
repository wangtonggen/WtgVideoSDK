package com.wtg.videolibrary.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.iceteck.silicompressorr.SiliCompressor;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wtg.videolibrary.R;
import com.wtg.videolibrary.base.BaseActivity;
import com.wtg.videolibrary.utils.ScreenUtils;
import com.wtg.videolibrary.widget.AutoFitTextureView;
import com.wtg.videolibrary.widget.CameraController;
import com.wtg.videolibrary.widget.CircleButtonView;

import java.io.File;
import java.net.URISyntaxException;

import io.reactivex.disposables.Disposable;

/**
 * author: wtg  2019/10/28 0028
 * desc: 拍照/拍视频界面
 */
public class VideoActivity extends BaseActivity{
    private AutoFitTextureView sv_record;
    private CircleButtonView circleButtonView;

    private CameraController mCameraController;

    public static String BASE_PATH = Environment.getExternalStorageDirectory() + "/AAA";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setWindowStatusBarColor(R.color.color_black);

        sv_record = findViewById(R.id.sv_record);
        sv_record.setAspectRatio(ScreenUtils.getScreenWidth(this),ScreenUtils.getScreenHeight(this));
        circleButtonView = findViewById(R.id.circleButtonView);

        mCameraController = CameraController.getmInstance(this);

        mCameraController.setRecordFinishListener((type, path) -> {
            Log.e("tag",path+"---");
            switch (type){
                case IMAGE://图片
                    new Thread(){
                        @Override
                        public void run() {
                            Log.e("tag",path+"---111");
                            String filePath= SiliCompressor.with(VideoActivity.this).compress(path, new File("/storage/emulated/0/AAA"),true);
                            Log.e("eee",filePath+"---");
                        }
                    }.start();
                    break;
                case VIDEO://摄像

                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                String filePath1 = SiliCompressor.with(VideoActivity.this).compressVideo(path, "/storage/emulated/0/AAA",0,0,6000000);
                                Log.e("eee",filePath1+"---filePath1");
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                                Log.e("wwww",e.getMessage());
                            }
                        }
                    }.start();
//                    new Handler().postDelayed(()->{
//
//
//                    },2000);

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
                        circleButtonView.setOnClickListener(new CircleButtonView.OnClickListener() {
                            @Override
                            public void onClick() {
                                mCameraController.takePicture();
                            }
                        });

                        circleButtonView.setOnLongClickListener(new CircleButtonView.OnLongClickListener() {
                            @Override
                            public void onLongClick() {
                                mCameraController.startRecordingVideo();
                            }

                            @Override
                            public void onNoMinRecord(int currentTime) {
                                Toast.makeText(VideoActivity.this,"时间太短了",Toast.LENGTH_SHORT).show();
                                mCameraController.stopRecordingVideo();
                            }

                            @Override
                            public void onRecordFinishedListener() {
                                mCameraController.stopRecordingVideo();
                            }
                        });
                    }else if (permission.shouldShowRequestPermissionRationale){//未同意但是未勾选不在提醒
                        showPermissionDialog(VideoActivity.this,"相机和存储");
                    }else {//未同意勾选不在提醒
                        showPermissionDialog(VideoActivity.this,"相机和存储");
                    }
                });
    }

}
