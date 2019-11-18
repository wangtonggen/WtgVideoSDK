package com.wtg.videolibrary.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.wtg.videolibrary.R;
import com.wtg.videolibrary.base.BaseActivity;
import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.widget.JZVideoPlayerStandardLoopVideo;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static com.wtg.videolibrary.annotation.MultiHolderTypeAnont.HOLDER_TYPE_IMAGE;
import static com.wtg.videolibrary.annotation.MultiHolderTypeAnont.HOLDER_TYPE_VIDEO;
import static com.wtg.videolibrary.annotation.MultiHolderTypeAnont.Holder_TYPE_CAMERA;

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
        //如果在拖拽返回关闭的时候，导航栏上又出现拖拽的view的情况，就用以下代码。就和微信的表现形式一样
        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
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
