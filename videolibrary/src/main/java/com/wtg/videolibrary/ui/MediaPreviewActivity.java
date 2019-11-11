package com.wtg.videolibrary.ui;

import android.app.SharedElementCallback;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.dragclosehelper.library.DragCloseHelper;
import com.github.chrisbanes.photoview.PhotoView;
import com.wtg.videolibrary.R;
import com.wtg.videolibrary.base.BaseActivity;
import com.wtg.videolibrary.utils.PhotoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * author: admin 2019/11/4
 * desc: 预览照片
 */
public class MediaPreviewActivity extends BaseActivity implements DragCloseHelper.DragCloseListener {
    private ViewPager view_pager;
    private ConstraintLayout constraintLayout;
    DragCloseHelper dragCloseHelper;
    private List<View> views = new ArrayList<>();
    private int index;
    private boolean scrolling;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_preview);

        //如果在拖拽返回关闭的时候，导航栏上又出现拖拽的view的情况，就用以下代码。就和微信的表现形式一样
        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        view_pager = findViewById(R.id.view_pager);
        constraintLayout = findViewById(R.id.constraintLayout);
        index = getIntent().getIntExtra("index",0);
        initViewPager();
//        initDragHelper();
    }

    private void initDragHelper() {
        dragCloseHelper = new DragCloseHelper(this);
        dragCloseHelper.setShareElementMode(true);
        dragCloseHelper.setDragCloseViews(constraintLayout, view_pager);

        dragCloseHelper.setDragCloseListener(this);

        dragCloseHelper.setClickListener((view, isLongClick) -> {
            int currentIndex = ((ViewPager) view).getCurrentItem();
            Log.d("test", currentIndex + (isLongClick ? "被长按" : "被点击"));
        });

        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
                //sharedElements 是本页面共享元素的view   sharedElementSnapshots是本页面真正执行动画的view
                Log.d("test enter b", "onSharedElementStart");
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                //sharedElements 是本页面共享元素的view   sharedElementSnapshots是本页面真正执行动画的view
                Log.d("test enter b", "onSharedElementEnd");
            }

            @Override
            public void onRejectSharedElements(List<View> rejectedSharedElements) {
                super.onRejectSharedElements(rejectedSharedElements);
                //屏蔽的view
                Log.d("test enter b", "onRejectSharedElements");
            }

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                super.onMapSharedElements(names, sharedElements);
                //sharedElements 是本页面共享元素的view
                Log.d("test enter b", "onMapSharedElements");
            }

            @Override
            public View onCreateSnapshotView(Context context, Parcelable snapshot) {
                //新的iv执行动画的真正iv
                Log.d("test enter b", "onCreateSnapshotView");
//                View view = super.onCreateSnapshotView(context, snapshot);
                return super.onCreateSnapshotView(context, snapshot);
            }

            @Override
            public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
                //sharedElements 是本页面共享元素的view
                Log.d("test enter b", "onSharedElementsArrived");
                super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
            }
        });
    }

    private void initViewPager(){
        view_pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(views.get(position));
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }
        });

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //每次页面变化，都要通知上一个页面更新共享元素的键值对
//                RxBus.get().post("updateIndex", position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                scrolling = state != 0;
            }
        });

        view_pager.setCurrentItem(index);
    }

    @Override
    public boolean intercept() {
        //默认false 不拦截 如果图片是放大状态，或者处于滑动返回状态，需要拦截
//        return scrolling || ((PhotoView) views.get(view_pager.getCurrentItem())).getScale() != 1;
        return false;
    }

    @Override
    public void dragStart() {

    }

    @Override
    public void dragging(float percent) {

    }

    @Override
    public void dragCancel() {

    }

    @Override
    public void dragClose(boolean isShareElementMode) {
        //拖拽关闭，如果是共享元素的页面，需要执行activity的onBackPressed方法，注意如果使用finish方法，则返回的时候没有共享元素的返回动画
        if (isShareElementMode) {
            onBackPressed();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (dragCloseHelper.handleEvent(ev)) {
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }
}
