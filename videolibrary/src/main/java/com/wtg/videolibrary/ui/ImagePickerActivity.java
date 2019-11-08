package com.wtg.videolibrary.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wtg.videolibrary.R;
import com.wtg.videolibrary.adapter.PhotoAdapter;
import com.wtg.videolibrary.adapter.PhotoTypeAdapter;
import com.wtg.videolibrary.annotation.ImageTypeAnont;
import com.wtg.videolibrary.base.BaseActivity;
import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.bean.FolderBean;
import com.wtg.videolibrary.bean.PhotoTypeBean;
import com.wtg.videolibrary.listener.LoadMediaListener;
import com.wtg.videolibrary.task.AllMediaTask;
import com.wtg.videolibrary.task.ImageMediaTask;
import com.wtg.videolibrary.utils.PhotoUtils;
import com.wtg.videolibrary.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * author: admin 2019/10/31
 * desc: 相册的展示的界面
 */
public class ImagePickerActivity extends BaseActivity implements View.OnClickListener {
    private AppCompatImageView iv_photo_close;
    private LinearLayout ll_select_type;
    private AppCompatTextView tv_photo_type;
    private AppCompatImageView iv_arrow;
    private AppCompatButton btn_finish;
    private AppCompatTextView tv_preview;
    private View view_shade;

    //显示照片
    private RecyclerView recycler_photo;
    private PhotoAdapter photoAdapter;
    private List<BaseMediaBean> photoBeans = new ArrayList<>();

    //显示照片类型
    private RecyclerView recycler_photo_type;
    private PhotoTypeAdapter photoTypeAdapter;
    private List<FolderBean> photoTypeBeans = new ArrayList<>();
    //是否是0度
    private boolean isDegree0 = true;
    private Animation animationDegree0;
    private Animation animationDegree180;

    //pop动画
    private boolean isPopShow = false;
    private Animation animationShowPop;
    private Animation animationDismissPop;

    //遮罩动画
    private Animation animationShadeShow;
    private Animation animationShadeGone;

    private int photoTypePosition = -1;

    private List<String> imagePickerList = new ArrayList<>();

    private Runnable allMediaRunnable;//所有图片视频
    private Runnable imageMediaRunnable;//所有图片
    private Runnable videoMediaRunnable;//所有视频

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setWindowStatusBarColor(R.color.color_333232);
        setContentView(R.layout.activity_photo);

        initView();
        initAnim();
        initPhotoType();
        initParams();
        initPhoto();
        initTask();
        setOnClickListener();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_photo_close) {
            finish();
        } else if (id == R.id.ll_select_type) {
            startArrowAnim();
            showOrDismissPop();
        } else if (id == R.id.btn_finish) {
            finish();
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        iv_photo_close = findViewById(R.id.iv_photo_close);
        ll_select_type = findViewById(R.id.ll_select_type);
        tv_photo_type = findViewById(R.id.tv_photo_type);
        iv_arrow = findViewById(R.id.iv_arrow);
        btn_finish = findViewById(R.id.btn_finish);
        tv_preview = findViewById(R.id.tv_preview);
        recycler_photo = findViewById(R.id.recycler_photo);

        view_shade = findViewById(R.id.view_shade);
        recycler_photo_type = findViewById(R.id.recycler_photo_type);
    }

    /**
     * 设置监听
     */
    private void setOnClickListener() {
        iv_photo_close.setOnClickListener(this);
        ll_select_type.setOnClickListener(this);
        btn_finish.setOnClickListener(this);

        photoTypeAdapter.setOnItemClickListener((position, view) -> {
            if (photoTypePosition == -1) {
                photoTypeBeans.get(position).setChecked(true);
                photoTypeAdapter.notifyItemChanged(position, 0);
            } else {
                if (photoTypePosition != position) {
                    photoTypeBeans.get(photoTypePosition).setChecked(false);
                    photoTypeBeans.get(position).setChecked(true);
                    photoTypeAdapter.notifyItemChanged(photoTypePosition, 0);
                    photoTypeAdapter.notifyItemChanged(position, 0);
                }
            }
            photoTypePosition = position;
        });

        photoAdapter.setOnItemClickListener((position, view) -> {
            int id = view.getId();
            BaseMediaBean photoBean = photoBeans.get(position);
            if (id == R.id.tv_num) {//选中取消选中
                if (photoBean.isSelect()) {
                    imagePickerList.remove(photoBean.getPath());
                } else {
                    if (imagePickerList.size() >= PhotoUtils.getInstance().getMaxNum()) {
                        Toast.makeText(ImagePickerActivity.this, "最多可选择9张", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!imagePickerList.contains(photoBean.getPath())) {
                        imagePickerList.add(photoBean.getPath());
                    }
                }
                photoBean.setSelect(!photoBean.isSelect());
                photoAdapter.setFilePaths(imagePickerList);
                photoAdapter.notifyDataSetChanged();
                updateFinishButton();
            } else if (id == R.id.view) {//大图跳转预览页面
                Log.e("222", "遮罩");
            } else if (id == R.id.iv_photo) {//跳转预览页页面
                Log.e("333", "photo");
            } else {
                if (photoBean.getImageType() == ImageTypeAnont.HOLDER_TYPE_CAMERA){
                    //跳转拍照页面
                    return;
                }
                Log.e("item", "item");
            }
        });
    }

    /**
     * 初始化选择照片类型的箭头的动画
     */
    private void initAnim() {
        //从0°旋转180°
        animationDegree0 = AnimationUtils.loadAnimation(this, R.anim.anim_arrow_degree0);
        animationDegree0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isDegree0 = !isDegree0;
                ll_select_type.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //从180°旋转到360°
        animationDegree180 = AnimationUtils.loadAnimation(this, R.anim.anim_arrow_degree180);

        animationDegree180.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isDegree0 = !isDegree0;
                ll_select_type.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationShowPop = AnimationUtils.loadAnimation(this, R.anim.anim_pop_photo_type_show);
        animationShowPop.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isPopShow = !isPopShow;
                showOrGoneShade();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationDismissPop = AnimationUtils.loadAnimation(this, R.anim.anim_pop_photo_type_hide);
        animationDismissPop.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isPopShow = !isPopShow;
                recycler_photo_type.setVisibility(GONE);
                showOrGoneShade();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationShadeShow = AnimationUtils.loadAnimation(this, R.anim.anim_photo_type_shade_visable);
        animationShadeShow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view_shade.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationShadeGone = AnimationUtils.loadAnimation(this, R.anim.anim_photo_type_shade_gone);
        animationShadeGone.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view_shade.setVisibility(GONE);
                view_shade.setEnabled(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 开启动画旋转180°
     */
    private void startArrowAnim() {
        if (iv_arrow == null) {
            return;
        }
        ll_select_type.setEnabled(false);
        iv_arrow.startAnimation(isDegree0 ? animationDegree0 : animationDegree180);
    }

    /**
     * 初始化数据类型(pop)
     */
    private void initPhotoType() {
        view_shade.setOnClickListener(v -> showOrDismissPop());
        photoTypeAdapter = new PhotoTypeAdapter(this, photoTypeBeans);
        recycler_photo_type.setLayoutManager(new LinearLayoutManager(this));
        recycler_photo_type.setHasFixedSize(true);
        recycler_photo_type.setItemViewCacheSize(10);
        recycler_photo_type.setAdapter(photoTypeAdapter);
    }

    private void initParams(){
        if (PhotoUtils.getInstance().isShowCamera()){
            BaseMediaBean photoBean = new BaseMediaBean();
            photoBean.setPath("camera");
            photoBean.setImageType(ImageTypeAnont.HOLDER_TYPE_CAMERA);
            photoBeans.add(photoBean);
        }
    }

    /**
     * 初始化照片
     */
    private void initPhoto() {
        photoAdapter = new PhotoAdapter(this, photoBeans);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recycler_photo.addItemDecoration(new DividerItemDecoration(this));
        recycler_photo.setLayoutManager(gridLayoutManager);
        recycler_photo.setHasFixedSize(true);
        recycler_photo.setItemViewCacheSize(40);
        recycler_photo.setAdapter(photoAdapter);
    }

    /**
     * 初始化扫描任务
     */
    private void initTask(){
        allMediaRunnable = new AllMediaTask(this, new LoadMediaListener() {
            @Override
            public void loadMediaSuccess(ArrayList<FolderBean> mediaList) {
                photoBeans.addAll(mediaList.get(0).getMediaFileList());
                photoAdapter.notifyDataSetChanged();

                photoTypeBeans.addAll(mediaList);
                photoTypeAdapter.notifyDataSetChanged();
            }
        });

        allMediaRunnable.run();

        imageMediaRunnable = new ImageMediaTask(this, new LoadMediaListener() {
            @Override
            public void loadMediaSuccess(ArrayList<FolderBean> mediaList) {

            }
        });

        videoMediaRunnable = new ImageMediaTask(this, new LoadMediaListener() {
            @Override
            public void loadMediaSuccess(ArrayList<FolderBean> mediaList) {

            }
        });
    }

    /**
     * 打开pop
     */
    private void showOrDismissPop() {
        if (recycler_photo_type.getVisibility() != View.VISIBLE) {
            recycler_photo_type.setVisibility(View.VISIBLE);
            recycler_photo_type.startAnimation(animationShowPop);
        } else {
            recycler_photo_type.startAnimation(animationDismissPop);
        }

    }

    /**
     * 打开或者隐藏遮罩
     */
    private void showOrGoneShade() {
        if (view_shade.getVisibility() != View.VISIBLE) {
            view_shade.setVisibility(View.VISIBLE);
            view_shade.startAnimation(animationShadeShow);
        } else {
            view_shade.startAnimation(animationShadeGone);
        }
    }

    /**
     * 更新完成按钮和预览图片状态
     */
    private void updateFinishButton() {
        btn_finish.setEnabled(imagePickerList.size() >= PhotoUtils.getInstance().getMinNum());
        btn_finish.setText(imagePickerList.size() == 0 ? getResources().getString(R.string.str_button_finish) : String.format("完成(%s/%s)", imagePickerList.size(), PhotoUtils.getInstance().getMaxNum()));

        tv_preview.setEnabled(imagePickerList.size() > 0);
        tv_preview.setTextColor(imagePickerList.size() >= PhotoUtils.getInstance().getMinNum() ? getResources().getColor(R.color.color_white) : getResources().getColor(R.color.color_888888));
        tv_preview.setText(imagePickerList.size() == 0 ? getResources().getString(R.string.str_preview) : String.format("预览(%s/%s)", imagePickerList.size(), PhotoUtils.getInstance().getMaxNum()));
    }

    /**
     * 开启加载弹框
     */
    private void openLoadingDialog(){

    }

    /**
     * 关闭加载弹框
     */
    private void closeLoadingDialog(){

    }


    @Override
    public void onBackPressed() {
        if (view_shade != null && view_shade.getVisibility() == View.VISIBLE) {
            showOrDismissPop();
        } else {
            super.onBackPressed();
        }
    }
}
