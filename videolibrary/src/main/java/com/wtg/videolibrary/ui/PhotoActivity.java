package com.wtg.videolibrary.ui;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wtg.videolibrary.R;
import com.wtg.videolibrary.adapter.PhotoAdapter;
import com.wtg.videolibrary.adapter.PhotoTypeAdapter;
import com.wtg.videolibrary.base.BaseActivity;
import com.wtg.videolibrary.bean.PhotoBean;
import com.wtg.videolibrary.bean.PhotoTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author: admin 2019/10/31
 * desc: 相册的展示的界面
 */
public class PhotoActivity extends BaseActivity implements View.OnClickListener {
    ConstraintLayout cl_toolbar;
    AppCompatImageView iv_photo_close;
    LinearLayout ll_select_type;
    AppCompatTextView tv_photo_type;
    AppCompatImageView iv_arrow;
    AppCompatButton btn_finish;

    private RecyclerView recycler_photo;
    private PhotoAdapter photoAdapter;
    private List<PhotoBean> photoBeans;

    private PopupWindow photoTypePop;

    //是否是0度
    private boolean isDegress0 = true;
    private Animation animationDegree0;
    private Animation animationDegree180;

    private int[] location = new int[2];
    private View photoTypeView;
    private RecyclerView recycler_photo_type;
    private PhotoTypeAdapter photoTypeAdapter;
    private List<PhotoTypeBean> photoTypeBeans = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setWindowStatusBarColor(R.color.color_333232);
        setContentView(R.layout.activity_photo);

        initView();
        initArrowAnim();
        initPhotoType();
        iniPop();

        photoBeans = new ArrayList<>();
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_photo_close) {
            finish();
        } else if (id == R.id.ll_select_type) {
            startArrowAnim();
            showODismissPop();
        } else if (id == R.id.btn_finish) {

        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        cl_toolbar = findViewById(R.id.cl_toolbar);
        iv_photo_close = findViewById(R.id.iv_photo_close);
        ll_select_type = findViewById(R.id.ll_select_type);
        tv_photo_type = findViewById(R.id.tv_photo_type);
        iv_arrow = findViewById(R.id.iv_arrow);
        btn_finish = findViewById(R.id.btn_finish);
        recycler_photo = findViewById(R.id.recycler_photo);

        iv_photo_close.setOnClickListener(this);
        ll_select_type.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
    }

    /**
     * 初始化选择照片类型的箭头的动画
     */
    private void initArrowAnim() {
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
        photoTypeView = LayoutInflater.from(this).inflate(R.layout.pop_photo_type, null);
        recycler_photo_type = photoTypeView.findViewById(R.id.recycler_photo_type);
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
     * 初始化photoType的pop
     * 实现微信的那种做法，在pop布局里面设置一个外布局，填充整个屏幕，设置点击外面不可消失
     */
    private void iniPop() {
        if (photoTypeView == null) {
            return;
        }
        cl_toolbar.getLocationOnScreen(location);
        photoTypePop = new PopupWindow(photoTypeView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        photoTypePop.setAnimationStyle(R.style.pop_photo_type_style);
        photoTypePop.setOutsideTouchable(true);
        photoTypePop.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        photoTypePop.setBackgroundDrawable(dw);
    }

    /**
     * 打开pop
     */
    private void showODismissPop(){
        if (isDegress0){//打开
            if (photoTypePop == null || photoTypePop.isShowing()){
                return;
            }
            photoTypePop.showAsDropDown(iv_arrow, Gravity.NO_GRAVITY,0,0);
            backgroundAlpha(0.5f);
        }else {//关闭
            if (photoTypePop != null && photoTypePop.isShowing()){
                photoTypePop.dismiss();
                backgroundAlpha(1.0f);
            }
        }
    }

    // 设置popupWindow背景半透明
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;// 0.0-1.0
        getWindow().setAttributes(lp);
    }
}
