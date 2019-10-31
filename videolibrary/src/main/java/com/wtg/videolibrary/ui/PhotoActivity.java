package com.wtg.videolibrary.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.wtg.videolibrary.R;
import com.wtg.videolibrary.base.BaseActivity;


/**
 * author: admin 2019/10/31
 * desc: 相册的展示的界面
 */
public class PhotoActivity extends BaseActivity {
    private RecyclerView recycler_photo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        recycler_photo = findViewById(R.id.recycler_photo);

    }
}
