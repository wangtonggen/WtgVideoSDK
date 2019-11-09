package com.dsrz.wtgvidosdk;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.widget.TextView;

import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.ui.ImagePickerActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView tv_test;
    private List<BaseMediaBean> baseMediaBeans = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_test = findViewById(R.id.tv_test);

        tv_test.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ImagePickerActivity.class);
            intent.putExtra("list",(Serializable) baseMediaBeans);
            startActivityForResult(intent,1001);
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (requestCode == 1001){
                baseMediaBeans.clear();
                baseMediaBeans.addAll((List<BaseMediaBean>)data.getSerializableExtra("list"));
            }
        }
    }
}
