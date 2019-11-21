package com.dsrz.wtgvidosdk.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.dsrz.wtgvidosdk.R;
import com.dsrz.wtgvidosdk.adapter.ImageVideoAdapter;
import com.wtg.videolibrary.annotation.CameraAnont;
import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.listener.OnItemClickListener;
import com.wtg.videolibrary.utils.CameraUtils;
import com.wtg.videolibrary.utils.PhotoUtils;

import java.util.ArrayList;
import java.util.List;

import static com.wtg.videolibrary.annotation.MediaTypeAnont.MEDIA_TYPE_ALL;
import static com.wtg.videolibrary.annotation.MediaTypeAnont.MEDIA_TYPE_IMAGE;
import static com.wtg.videolibrary.annotation.MultiHolderTypeAnont.HOLDER_TYPE_IMAGE;
import static com.wtg.videolibrary.annotation.MultiHolderTypeAnont.HOLDER_TYPE_VIDEO;
import static com.wtg.videolibrary.result.MediaParams.MEDIA_CAMERA;
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

    String[] single_list = {"拍摄", "从相册中选择"};

    private List<BaseMediaBean> media_List;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camrea_result);

        baseMediaBean = (BaseMediaBean) getIntent().getSerializableExtra(MEDIA_CAMERA);
        media_List = (List<BaseMediaBean>) getIntent().getSerializableExtra(MEDIA_PARAMS_NAME);
        if (media_List != null) {
            baseMediaBeans.addAll(media_List);
        }
        if (baseMediaBean != null) {
            baseMediaBeans.add(baseMediaBean);
        }
        recycler_result = findViewById(R.id.recycler_result);
        recycler_result.setHasFixedSize(true);
        recycler_result.setItemViewCacheSize(20);
        recycler_result.setLayoutManager(new GridLayoutManager(this, 3));
        imageVideoAdapter = new ImageVideoAdapter(this, baseMediaBeans);
        if (baseMediaBeans != null && baseMediaBeans.size() > 0) {
            if (baseMediaBeans.get(0).getHolderType() == HOLDER_TYPE_IMAGE) {
                imageVideoAdapter.setShowAdd(true);
            } else {
                imageVideoAdapter.setShowAdd(false);
            }
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
                    if (baseMediaBeans.size() == 1) {
                        imageVideoAdapter.setShowAdd(true);
                    }
                    baseMediaBeans.remove(position);
                    imageVideoAdapter.notifyItemRemoved(position);
                    imageVideoAdapter.notifyItemRangeChanged(0, baseMediaBeans.size() + 1);
                } else {
                    int size = baseMediaBeans.size();
                    if (size < 9 && size > 0) {//小于9
                        if (position == baseMediaBeans.size()) {
                            //弹出dialog 选择拍摄还是照片
                            showSingleChoiceDialog(1);
                        } else {
                            //跳转预览界面
                            Toast.makeText(CameraResultActivity.this, "预览界面", Toast.LENGTH_SHORT).show();
                        }
                    } else if (baseMediaBeans.size() == 0) {
                        //弹出dialog 选择拍摄还是照片
                        showSingleChoiceDialog(0);
                    } else {//大于等于9
                        //跳转预览界面
                        Toast.makeText(CameraResultActivity.this, "预览界面", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void showSingleChoiceDialog(int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("单选对话框");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setSingleChoiceItems(single_list, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0://拍摄
                        if (type == 1) {
                            CameraUtils.getInstance().setCameraType(CameraAnont.CAMERA_IMAGE).startCameraActivity(CameraResultActivity.this, 1008);
                        } else {
                            CameraUtils.getInstance().setCameraType(CameraAnont.CAMERA_ALL).startCameraActivity(CameraResultActivity.this, 1008);
                        }
                        break;
                    case 1://从相册中选则
                        if (type == 1) {
                            PhotoUtils.getInstance().setMaxNum(9 - baseMediaBeans.size()).setMinNum(1).setMediaType(MEDIA_TYPE_IMAGE).startImagePicker(CameraResultActivity.this, 1002);
                        } else {
                            PhotoUtils.getInstance().setMaxNum(9 - baseMediaBeans.size()).setMinNum(1).setMediaType(MEDIA_TYPE_ALL).startImagePicker(CameraResultActivity.this, 1002);
                        }
                        break;
                }
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 1002) {
                baseMediaBeans.addAll((ArrayList<BaseMediaBean>) data.getSerializableExtra(MEDIA_PARAMS_NAME));
                if (PhotoUtils.getInstance().isOnlyOneVideo()) {
                    if (baseMediaBeans.get(0).getHolderType() == HOLDER_TYPE_VIDEO) {
                        imageVideoAdapter.setShowAdd(false);
                    }
                }
                imageVideoAdapter.notifyDataSetChanged();
            } else if (requestCode == 1008) {//从当前打开的页面
                baseMediaBeans.add((BaseMediaBean) data.getSerializableExtra(MEDIA_CAMERA));
                if (PhotoUtils.getInstance().isOnlyOneVideo()) {
                    if (baseMediaBeans.get(0).getHolderType() == HOLDER_TYPE_VIDEO) {
                        imageVideoAdapter.setShowAdd(false);
                    }
                }
                imageVideoAdapter.notifyDataSetChanged();
            }
        }
    }
}
