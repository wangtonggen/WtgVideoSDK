package com.dsrz.wtgvidosdk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.dsrz.wtgvidosdk.R;
import com.dsrz.wtgvidosdk.adapter.ImageVideoAdapter;
import com.wtg.videolibrary.annotation.MediaTypeAnont;
import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.listener.OnItemClickListener;
import com.wtg.videolibrary.utils.PhotoUtils;

import java.util.ArrayList;
import java.util.List;

import static com.wtg.videolibrary.annotation.MediaTypeAnont.MEDIA_TYPE_ALL;
import static com.wtg.videolibrary.annotation.MediaTypeAnont.MEDIA_TYPE_IMAGE;
import static com.wtg.videolibrary.annotation.MultiHolderTypeAnont.HOLDER_TYPE_IMAGE;
import static com.wtg.videolibrary.result.MediaParams.MEDIA_PARAMS_NAME;

/**
 * author: wtg  2019/11/19 0019
 * desc: 目标activity
 */
public class CameraResultActivity extends AppCompatActivity {
    private RecyclerView recycler_result;
    private BaseMediaBean baseMediaBean;
    private ImageVideoAdapter imageVideoAdapter;

    private List<BaseMediaBean> baseMediaBeans = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camrea_result);

        baseMediaBean = (BaseMediaBean) getIntent().getSerializableExtra("media");
        recycler_result = findViewById(R.id.recycler_result);
        recycler_result.setHasFixedSize(true);
        recycler_result.setItemViewCacheSize(20);
        recycler_result.setLayoutManager(new GridLayoutManager(this, 3));
        imageVideoAdapter = new ImageVideoAdapter(this, baseMediaBeans);
        if (baseMediaBean != null) {
            if (baseMediaBean.getHolderType() == HOLDER_TYPE_IMAGE) {
                imageVideoAdapter.setShowAdd(true);
            } else {
                imageVideoAdapter.setShowAdd(false);
            }
            baseMediaBeans.add(baseMediaBean);
        }
        recycler_result.setAdapter(imageVideoAdapter);

        setOnClickListener();
    }

    private void setOnClickListener() {
        imageVideoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                int id = view.getId();
                if (id == R.id.iv_delete) {
                    baseMediaBeans.remove(position);
                    imageVideoAdapter.notifyItemRemoved(position);
                } else {
                    int size = baseMediaBeans.size();
                    if (size < 9 && size > 0) {//小于9
                        if (position == baseMediaBeans.size()) {
                            //跳转选择照片相册页面
                            PhotoUtils.getInstance().setMaxNum(9 - baseMediaBeans.size()).setMinNum(1).setMediaType(MEDIA_TYPE_IMAGE).setOriginalDataList(baseMediaBeans).startImagePicker(CameraResultActivity.this, 1002);
                        } else {
                            //跳转预览界面
                            Toast.makeText(CameraResultActivity.this, "预览界面", Toast.LENGTH_SHORT).show();
                        }
                    } else if (baseMediaBeans.size() == 0) {
                        PhotoUtils.getInstance().setMaxNum(9 - baseMediaBeans.size()).setMinNum(1).setMediaType(MEDIA_TYPE_ALL).setOriginalDataList(baseMediaBeans).startImagePicker(CameraResultActivity.this, 1002);
                    } else {//大于等于9
                        //跳转预览界面
                        Toast.makeText(CameraResultActivity.this, "预览界面", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 1002) {
                baseMediaBeans.addAll((ArrayList<BaseMediaBean>) data.getSerializableExtra(MEDIA_PARAMS_NAME));
                imageVideoAdapter.notifyDataSetChanged();
            }
        }
    }
}
