package com.wtg.videolibrary.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;

import java.util.Arrays;
import java.util.Collections;

/**
 * author: admin 2019/11/1
 * desc: 相机管理类
 */
public class CameraControllerUtils {
    //相机管理类
    private CameraManager cameraManager;
    //相机id
    private String[] cameraIdList;
    //前摄像头id
    private String frontCameraId;
    //前置摄像头信息
    private CameraCharacteristics frontCameraCharacteristics;
    //后摄像头id
    private String backCameraId;
    //后置摄像头信息
    private CameraCharacteristics backCameraCharacteristics;
    //合适的尺寸
    private Size mPreviewSize;
    //相机设备
    private CameraDevice cameraDevice;
    //相机开启回调
    private CameraStateCallback cameraStateCallback;
    //获取图像数据
    private ImageReader mImageReader;
    private MyOnImageAvailableListener myOnImageAvailableListener;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    //摄像头方向
    private Integer mSensorOrientation;
    //是否是后置摄像头 true是 false不是 用来切换摄像头操作
    private boolean isBack = true;
    private AutoFitTextureView textureView;
    private Activity context;
    public void initCamera(Activity context,AutoFitTextureView textureView){
        this.context = context;
        this.textureView = textureView;
        initCameraManager();
        getCameraIdList();
        getCameraId();
        initParams();
    }

    private void initCameraManager(){
        if (myOnImageAvailableListener == null){
            myOnImageAvailableListener = new MyOnImageAvailableListener();
        }
        if (cameraStateCallback == null){
            cameraStateCallback = new CameraStateCallback();
        }
        if (context == null){
            throw new RuntimeException("context is null");
        }
        if (cameraManager == null){
            cameraManager = (CameraManager)context.getSystemService(Context.CAMERA_SERVICE);
        }
    }
    /**
     * 获取相机id
     */
    private void getCameraIdList(){
        if (cameraManager == null){
            return;
        }
        if (cameraIdList != null && cameraIdList.length > 0){
            return;
        }
        try {
            cameraIdList = cameraManager.getCameraIdList();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取前置和后置摄像头的id
     */
    private void getCameraId(){
        if (cameraIdList == null || cameraIdList.length == 0){
            return;
        }
        //摄像头id一斤存在则不要在获取
        if (!TextUtils.isEmpty(frontCameraId) && !TextUtils.isEmpty(backCameraId)){
            return;
        }
        for (String cameraId : cameraIdList) {
            try {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                Integer cameraInt = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                if (cameraInt == null){
                    return;
                }
                switch (cameraInt){
                    case CameraCharacteristics.LENS_FACING_FRONT:
                        frontCameraId = cameraId;
                        frontCameraCharacteristics = cameraCharacteristics;
                        break;
                    case CameraCharacteristics.LENS_FACING_BACK:
                        backCameraId = cameraId;
                        backCameraCharacteristics = cameraCharacteristics;
                        break;
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    private void initParams(){
        StreamConfigurationMap map;
        if (isBack){
            map = backCameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        }else {
            map = frontCameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        }
        if (map == null) {
            return;
        }
        //对于静态图像拍照, 使用最大的可用尺寸
        Size largest = Collections.max(
                Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
                new CameraController.CompareSizesByArea()
        );
        mImageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(), ImageFormat.JPEG, 2);
        mImageReader.setOnImageAvailableListener(myOnImageAvailableListener, mBackgroundHandler);
        //获取手机旋转的角度以调整图片的方向
        int displayRotation = context.getWindowManager().getDefaultDisplay().getRotation();
        if (isBack){
            mSensorOrientation = backCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        }else {
            mSensorOrientation = frontCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        }
        boolean swappedDimensions = false;

        switch (displayRotation) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                if (mSensorOrientation != null){
                    //横屏
                    if (mSensorOrientation == 90 || mSensorOrientation == 270) {
                        swappedDimensions = true;
                    }
                }
                break;
            case Surface.ROTATION_90:
            case Surface.ROTATION_270:
                if (mSensorOrientation != null){
                    //竖屏
                    if (mSensorOrientation == 0 || mSensorOrientation == 180) {
                        swappedDimensions = true;
                    }
                }
                break;
            default:
                Log.e("tag", "Display rotation is invalid: " + displayRotation);
        }

        Point displaySize = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(displaySize);
        int rotatedPreviewWidth = textureView.getWidth();
        int rotatedPreviewHeight = textureView.getHeight();
        int maxPreviewWidth = displaySize.x;
        int maxPreviewHeight = displaySize.y;

        if (swappedDimensions) {
            rotatedPreviewWidth = textureView.getHeight();
            rotatedPreviewHeight = textureView.getWidth();
            maxPreviewWidth = displaySize.y;
            maxPreviewHeight = displaySize.x;
        }

        if (maxPreviewWidth > 1080) {
            maxPreviewWidth = 1080;
        }

        if (maxPreviewHeight > 1920) {
            maxPreviewHeight = 1920;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        Log.d("tag", "widthPixels: " + widthPixels + "____heightPixels:" + heightPixels);

        //设置最佳合适的屏幕比显示预览框
        mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),
                rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth,
                maxPreviewHeight, largest);

        //我们将TextureView的宽高比与我们选择的预览大小相匹配。这样设置不会拉伸,但是不能全屏展示
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏
            textureView.setAspectRatio(
                    mPreviewSize.getWidth(), mPreviewSize.getHeight());
            Log.d("tag", "横屏: " + "width:" + mPreviewSize.getWidth() + "____height:" + mPreviewSize.getHeight());

        } else {
            // 竖屏
            textureView.setAspectRatio(widthPixels, heightPixels);
            Log.d("tag", "竖屏: " + "____height:" + mPreviewSize.getHeight() + "width:" + mPreviewSize.getWidth());
        }

    }

    private static Size chooseOptimalSize(Size[] choices, int textureViewWidth, int textureViewHeight, int maxWidth, int maxHeight, Size aspectRatio) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) textureViewWidth / textureViewHeight;
        if (choices == null) return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = textureViewHeight;

        // Try to find an size match aspect ratio and size
        for (Size size : choices) {
            double ratio = (double) size.getWidth() / size.getHeight();
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.getHeight() - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.getHeight() - targetHeight);
            }
        }
        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Size size : choices) {
                if (Math.abs(size.getHeight() - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.getHeight() - targetHeight);
                }
            }
        }
        return optimalSize;

    }

    /**
     * 开启摄像头
     */
    private void openCamera(){

    }

    private void closeCamera(){
        if (cameraDevice != null){
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    public class CameraStateCallback extends CameraDevice.StateCallback{
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            //相机不在获取到
            camera.close();
            cameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            //内部调用错误关闭
            camera.close();
            cameraDevice = null;
        }
    }
    public class PreviewSurfaceTextureListener implements TextureView.SurfaceTextureListener{
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    }

    public class MyOnImageAvailableListener implements ImageReader.OnImageAvailableListener{
        @Override
        public void onImageAvailable(ImageReader reader) {
           Image image =  reader.acquireLatestImage();
        }
    }
}
