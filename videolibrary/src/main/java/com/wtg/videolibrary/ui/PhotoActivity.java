package com.wtg.videolibrary.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.wtg.videolibrary.R;
import com.wtg.videolibrary.adapter.PhotoAdapter;
import com.wtg.videolibrary.base.BaseActivity;
import com.wtg.videolibrary.bean.PhotoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author: admin 2019/10/31
 * desc: 相册的展示的界面
 */
public class PhotoActivity extends BaseActivity {
    private RecyclerView recycler_photo;
    private PhotoAdapter photoAdapter;
    private List<PhotoBean> photoBeans;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setWindowStatusBarColor(R.color.color_333232);
        setContentView(R.layout.activity_photo);

        recycler_photo = findViewById(R.id.recycler_photo);

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
        photoAdapter = new PhotoAdapter(this,photoBeans);
//        GridItemDecoration gridItemDecoration = new GridItemDecoration.Builder(this)
//                .drawInsideEachOfItem(true).columnDivider(new ColorDivider(Color.RED))
//                .rowDivider(new ColorDivider(Color.RED)).build();
//        recycler_photo.addItemDecoration(gridItemDecoration);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
//        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int i) {
//                return (i + 1) % 5 == 0 ? gridLayoutManager.getSpanCount() : 1;
//            }
//        });
//        gridLayoutManager.setReverseLayout(true);
        recycler_photo.setLayoutManager(gridLayoutManager);
        recycler_photo.setHasFixedSize(true);
        recycler_photo.setItemViewCacheSize(40);
        recycler_photo.setAdapter(photoAdapter);

    }
}
