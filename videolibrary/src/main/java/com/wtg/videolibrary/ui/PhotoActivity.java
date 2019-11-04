package com.wtg.videolibrary.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.wtg.videolibrary.R;
import com.wtg.videolibrary.adapter.PhotoAdapter;
import com.wtg.videolibrary.adapter.PhotoTypeAdapter;
import com.wtg.videolibrary.base.BaseActivity;
import com.wtg.videolibrary.bean.PhotoBean;
import com.wtg.videolibrary.bean.PhotoTypeBean;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * author: admin 2019/10/31
 * desc: 相册的展示的界面
 */
public class PhotoActivity extends BaseActivity implements View.OnClickListener {
    private AppCompatImageView iv_photo_close;
    private LinearLayout ll_select_type;
    private AppCompatTextView tv_photo_type;
    private AppCompatImageView iv_arrow;
    private AppCompatButton btn_finish;
    private View view_shade;

    //显示照片
    private RecyclerView recycler_photo;
    private PhotoAdapter photoAdapter;
    private List<PhotoBean> photoBeans = new ArrayList<>();

    //显示照片类型
    private RecyclerView recycler_photo_type;
    private PhotoTypeAdapter photoTypeAdapter;
    private List<PhotoTypeBean> photoTypeBeans = new ArrayList<>();
    //是否是0度
    private boolean isDegress0 = true;
    private Animation animationDegree0;
    private Animation animationDegree180;

    //pop动画
    private boolean isPopShow = false;
    private Animation animationShowPop;
    private Animation animationDimissPop;

    //遮罩动画
    private Animation animationShadeShow;
    private Animation animationShadeGone;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setWindowStatusBarColor(R.color.color_333232);
        setContentView(R.layout.activity_photo);

        initView();
        initAnim();
        initPhotoType();
        initPhoto();
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
        recycler_photo = findViewById(R.id.recycler_photo);

        view_shade = findViewById(R.id.view_shade);
        recycler_photo_type = findViewById(R.id.recycler_photo_type);

    }

    /**
     * 设置监听
     */
    private void setOnClickListener(){
        iv_photo_close.setOnClickListener(this);
        ll_select_type.setOnClickListener(this);
        btn_finish.setOnClickListener(this);

        photoAdapter.
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
                isDegress0 = !isDegress0;
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
                isDegress0 = !isDegress0;
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

        animationDimissPop = AnimationUtils.loadAnimation(this, R.anim.anim_pop_photo_type_hide);
        animationDimissPop.setAnimationListener(new Animation.AnimationListener() {
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
        iv_arrow.startAnimation(isDegress0 ? animationDegree0 : animationDegree180);
    }

    /**
     * 初始化数据类型(pop)
     */
    private void initPhotoType() {
        view_shade.setOnClickListener(v -> showOrDismissPop());
        photoTypeBeans.add(new PhotoTypeBean());
        photoTypeBeans.add(new PhotoTypeBean());
        photoTypeBeans.add(new PhotoTypeBean());
        photoTypeBeans.add(new PhotoTypeBean());
        photoTypeAdapter = new PhotoTypeAdapter(this, photoTypeBeans);
        recycler_photo_type.setLayoutManager(new LinearLayoutManager(this));
        recycler_photo_type.setHasFixedSize(true);
        recycler_photo_type.setItemViewCacheSize(10);
        recycler_photo_type.setAdapter(photoTypeAdapter);
    }

    /**
     * 初始化照片
     */
    private void initPhoto() {
        photoBeans.add(new PhotoBean());
        photoBeans.add(new PhotoBean());
        photoBeans.add(new PhotoBean());
        photoBeans.add(new PhotoBean());
        photoBeans.add(new PhotoBean());
        photoBeans.add(new PhotoBean());
        photoBeans.add(new PhotoBean());
        photoBeans.add(new PhotoBean());
        photoBeans.add(new PhotoBean());
        photoBeans.add(new PhotoBean());
        photoBeans.add(new PhotoBean());
        photoBeans.add(new PhotoBean());
        photoBeans.add(new PhotoBean());
        photoBeans.add(new PhotoBean());
        photoBeans.add(new PhotoBean());
        photoBeans.add(new PhotoBean());
        photoAdapter = new PhotoAdapter(this, photoBeans);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recycler_photo.setLayoutManager(gridLayoutManager);
        recycler_photo.setHasFixedSize(true);
        recycler_photo.setItemViewCacheSize(40);
        recycler_photo.setAdapter(photoAdapter);
    }

    /**
     * 打开pop
     */
    private void showOrDismissPop() {
        if (recycler_photo_type.getVisibility() != View.VISIBLE) {
            recycler_photo_type.setVisibility(View.VISIBLE);
            recycler_photo_type.startAnimation(animationShowPop);
        } else {
            recycler_photo_type.startAnimation(animationDimissPop);
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

    @Override
    public void onBackPressed() {
        if (view_shade != null && view_shade.getVisibility() == View.VISIBLE) {
            showOrDismissPop();
        } else {
            super.onBackPressed();
        }
    }
}
