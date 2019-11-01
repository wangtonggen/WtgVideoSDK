package com.wtg.videolibrary.widget;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Size;
import android.view.SurfaceHolder;

/**
 * author: wtg  2019/11/1 0001
 * desc: 相机管理类
 */
public class CameraControllerUtils {
    private Handler handler;
    private HandlerThread handlerThread;
    public final String TAG = getClass().getSimpleName();
    private CameraManager cameraManager;
    private Size previewSize;
    private SurfaceHolder surfaceHolder;
    private CameraDevice cameraDevice;
    private ImageReader imageReader;
    private CameraCaptureSession cameraCaptureSession;
    private CaptureRequest.Builder previewBuilder;
    private CaptureRequest.Builder captureBuilder;

    //打开相机回调
    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            Log.i(TAG, "onOpened");
            //相机开启，打开预览
            cameraDevice = camera;
//            startPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            Log.i(TAG, "onDisconnected");
            //相机关闭
            camera.close();
            cameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            Log.i(TAG, "onError");
            //相机报错
            camera.close();
            cameraDevice = null;
        }
    };

    //创建session回调
    private CameraCaptureSession.StateCallback sessionStateCallback = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(@NonNull CameraCaptureSession session) {
            try {
                cameraCaptureSession = session;
                //自动对焦
                previewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                cameraCaptureSession.setRepeatingRequest(previewBuilder.build(), null, handler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
            session.close();
            cameraCaptureSession = null;
            cameraDevice.close();
            cameraDevice = null;
        }
    };

    //拍完照回调
    private CameraCaptureSession.CaptureCallback captureCallback = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
            super.onCaptureProgressed(session, request, partialResult);
        }

        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
            try {
                //自动对焦
                captureBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CaptureRequest.CONTROL_AF_TRIGGER_CANCEL);
                //重新打开预览
                session.setRepeatingRequest(previewBuilder.build(), null, handler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
            super.onCaptureFailed(session, request, failure);
            cameraCaptureSession.close();
            cameraCaptureSession = null;
            cameraDevice.close();
            cameraDevice = null;
        }
    };

    private void initData(Context context) {
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        //Camera2全程异步
        handlerThread = new HandlerThread("Camera2");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }
}
