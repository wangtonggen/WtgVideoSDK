package com.wtg.videolibrary.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.wtg.videolibrary.R;
import com.wtg.videolibrary.adapter.MediaPreviewAdapter;
import com.wtg.videolibrary.base.BaseActivity;
import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.utils.common.MediaPreviewUtils;

import java.util.List;


/**
 * author: admin 2019/11/4
 * desc: 预览照片
 */
public class MediaPreviewActivity extends BaseActivity {
    private RecyclerView recycler_preview;
    private int mIndex;
    private List<BaseMediaBean> baseMediaBeans;

    private MediaPreviewAdapter mediaPreviewAdapter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_preview);

        //如果在拖拽返回关闭的时候，导航栏上又出现拖拽的view的情况，就用以下代码。就和微信的表现形式一样
        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        recycler_preview = findViewById(R.id.recycler_preview);

        mIndex = MediaPreviewUtils.getInstance().getPosition();
        baseMediaBeans = MediaPreviewUtils.getInstance().getList();
        //使用recycler实现viewPager的翻页效果
        mediaPreviewAdapter = new MediaPreviewAdapter(this, baseMediaBeans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.setInitialPrefetchItemCount(2);
        recycler_preview.setItemViewCacheSize(40);
        recycler_preview.setHasFixedSize(true);
        recycler_preview.setLayoutManager(layoutManager);
        recycler_preview.setAdapter(mediaPreviewAdapter);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recycler_preview);
        moveToPosition(layoutManager, recycler_preview, mIndex);
    }


    /**
     * 跳转到置顶位置
     *
     * @param linearLayoutManager manager
     * @param recyclerView        recyclerView
     * @param n                   位置
     */
    private void moveToPosition(LinearLayoutManager linearLayoutManager, RecyclerView recyclerView, int n) {
        int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
        int latItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        if (n <= firstItem) {
            recyclerView.scrollToPosition(n);
        } else if (n <= latItem) {
            int top = recyclerView.getChildAt(n - firstItem).getTop();
            recyclerView.scrollBy(0, top);
        } else {
            recyclerView.scrollToPosition(n);
        }
    }
}
