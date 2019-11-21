package com.wtg.videolibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.wtg.videolibrary.annotation.CameraAnont;
import com.wtg.videolibrary.ui.activity.CameraActivity;

/**
 * author: wtg  2019/11/19 0019
 * desc: 摄像头工具类 来控制是否可以拍照，是否可以拍视频,跳转的逻辑
 */
public class CameraUtils {

    private static CameraUtils cameraUtils;
    private Class<?> openActivity;
    private int requestCode;
    @CameraAnont.CameraType
    private int cameraType = CameraAnont.CAMERA_ALL;

    private CameraUtils() {

    }

    /**
     * 单例模式
     *
     * @return cameraUtils
     */
    public static CameraUtils getInstance() {
        if (cameraUtils == null) {
            synchronized (PhotoUtils.class) {
                if (cameraUtils == null) {
                    cameraUtils = new CameraUtils();
                }
            }
        }
        return cameraUtils;
    }

    public Class<?> getOpenActivity() {
        return openActivity;
    }

    public CameraUtils setOpenActivity(Class<?> openActivity) {
        this.openActivity = openActivity;
        return cameraUtils;
    }

    @CameraAnont.CameraType
    public int getCameraType() {
        return cameraType;
    }

    public CameraUtils setCameraType(@CameraAnont.CameraType int cameraType) {
        this.cameraType = cameraType;
        return cameraUtils;
    }

    /**
     * 打开界面
     *
     * @param context     上下文
     * @param requestCode 请求码
     */
    public void startCameraActivity(Activity context, int requestCode) {
        setOpenActivity(null);
        this.requestCode = requestCode;
        Intent intent = new Intent(context, CameraActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    public int getRequestCode() {
        return requestCode;
    }

    /**
     * 设置打开的目标activity
     *
     * @param context 上下文
     */
    public void startCameraActivity(Context context) {
        if (getOpenActivity() == null) {
            Toast.makeText(context, "请设置目标activity", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context, CameraActivity.class);
        context.startActivity(intent);
    }
}
