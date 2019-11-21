package com.wtg.videolibrary.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.iceteck.silicompressorr.SiliCompressor;
import com.wtg.videolibrary.R;
import com.wtg.videolibrary.base.BaseActivity;
import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.result.ResultCode;
import com.wtg.videolibrary.utils.CameraUtils;
import com.wtg.videolibrary.utils.FileUtils;
import com.wtg.videolibrary.utils.PhotoUtils;
import com.wtg.videolibrary.utils.common.ActivityManagerUtils;
import com.wtg.videolibrary.widget.JZVideoPlayerStandardLoopVideo;

import java.io.File;
import java.net.URISyntaxException;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static com.wtg.videolibrary.annotation.MultiHolderTypeAnont.HOLDER_TYPE_IMAGE;
import static com.wtg.videolibrary.annotation.MultiHolderTypeAnont.HOLDER_TYPE_VIDEO;
import static com.wtg.videolibrary.annotation.MultiHolderTypeAnont.Holder_TYPE_CAMERA;
import static com.wtg.videolibrary.result.MediaParams.MEDIA_CAMERA;

/**
 * author: wtg  2019/11/18 0018
 * desc: 拍摄完成后预览界面（图片和视频预览）
 */
public class CameraPreviewActivity extends BaseActivity {
    private PhotoView photoView;
    private JZVideoPlayerStandardLoopVideo video_player;
    private AppCompatImageView iv_cancel;
    private AppCompatImageView iv_done;
    private BaseMediaBean baseMediaBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera_preview);
        baseMediaBean = (BaseMediaBean) getIntent().getSerializableExtra("media");

        photoView = findViewById(R.id.photoView);
        video_player = findViewById(R.id.video_player);
        iv_cancel = findViewById(R.id.iv_cancel);
        iv_done = findViewById(R.id.iv_done);

        if (baseMediaBean != null){
            switch (baseMediaBean.getHolderType()){
                case Holder_TYPE_CAMERA:
                    break;
                case HOLDER_TYPE_VIDEO://视频
                    photoView.setVisibility(View.GONE);
                    video_player.setVisibility(View.VISIBLE);
                    video_player.setUp(baseMediaBean.getPath(),"", Jzvd.SCREEN_NORMAL);
                    video_player.startVideo();
                    break;
                case HOLDER_TYPE_IMAGE://图片
                    photoView.setVisibility(View.VISIBLE);
                    video_player.setVisibility(View.GONE);
                    Glide.with(this).load(baseMediaBean.getPath()).into(photoView);
                    break;
            }
        }

        iv_cancel.setOnClickListener(v -> finish());

        iv_done.setOnClickListener(v -> {
            switch (baseMediaBean.getHolderType()){
                case Holder_TYPE_CAMERA:
                    break;
                case HOLDER_TYPE_IMAGE:
                    new Thread(){
                        @Override
                        public void run() {
                            String filePath= SiliCompressor.with(CameraPreviewActivity.this).compress(baseMediaBean.getPath(), new File(FileUtils.IMAGE_ROOT),false);
                            baseMediaBean.setCompressMediaPath(filePath);
                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri uri = Uri.fromFile(new File(filePath));
                            intent.setData(uri);
                            sendBroadcast(intent);
                            //跳转
                            startActivity();
                        }
                    }.start();
                    break;
                case HOLDER_TYPE_VIDEO:
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                String filePath1 = SiliCompressor.with(CameraPreviewActivity.this).compressVideo(baseMediaBean.getPath(), FileUtils.IMAGE_ROOT,0,0,10000000);
                                //通知系统刷新
                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + new File(filePath1))));
                                Log.e("eee",filePath1+"---filePath1");
                                startActivity();
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                                Log.e("wwww",e.getMessage());
                            }
                        }
                    }.start();
                    break;
            }
        });
    }

    /**
     * 开启activity
     */
    private void startActivity(){
        if (CameraUtils.getInstance().getOpenActivity() != null){
            Intent intent = new Intent(this, CameraUtils.getInstance().getOpenActivity());
            intent.putExtra(MEDIA_CAMERA,baseMediaBean);
            startActivity(intent);
            ActivityManagerUtils.getAppManager().finishActivity(CameraActivity.class);
            finish();
        }else {
            Intent intent = new Intent();
            intent.putExtra(MEDIA_CAMERA, baseMediaBean);
            setResult(ResultCode.RESULT_MEDIA_CODE,intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (video_player.getVisibility() == View.VISIBLE){
            JzvdStd.goOnPlayOnResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (video_player.getVisibility() == View.VISIBLE){
            JzvdStd.goOnPlayOnPause();
        }
    }
}
