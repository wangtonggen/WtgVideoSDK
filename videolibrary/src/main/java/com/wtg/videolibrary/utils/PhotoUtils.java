package com.wtg.videolibrary.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.wtg.videolibrary.annotation.MediaTypeAnont;
import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.ui.activity.ImagePickerActivity;

import java.io.Serializable;
import java.util.List;

import static com.wtg.videolibrary.result.MediaParams.MEDIA_PARAMS_NAME;

/**
 * author: admin 2019/11/4
 * desc: 打开相册的工具类
 */
public class PhotoUtils {
    private static PhotoUtils photoUtils;
    private boolean isShowCamera = false;//默认显示相机
    private int mediaType = MediaTypeAnont.MEDIA_TYPE_ALL;//默认显示所有的类型
    private boolean isCompress = false;//默认压缩
    private boolean isOriginalData = false;//是否有原始数据
    private List<BaseMediaBean> originalDataList;
    private int maxNum = 9;//选择照片的最大数 默认9
    private int minNum = 1;//选择照片的最小值 默认1

    private Class<?> tClass;
    private PhotoUtils() {

    }

    /**
     * 单例模式
     *
     * @return photoUtils
     */
    public static PhotoUtils getInstance() {
        if (photoUtils == null) {
            synchronized (PhotoUtils.class) {
                if (photoUtils == null) {
                    photoUtils = new PhotoUtils();
                }
            }
        }
        return photoUtils;
    }

    /**
     * 是否显示相机
     *
     * @param showCamera true 显示 false不显示
     * @return photoUtils
     */
    public PhotoUtils setShowCamera(boolean showCamera) {
        isShowCamera = showCamera;
        return photoUtils;
    }

    public PhotoUtils setMediaType(@MediaTypeAnont.MediaType int mediaType) {
        this.mediaType = mediaType;
        return photoUtils;
    }

    /**
     * 是否压缩
     *
     * @param compress true是
     * @return photoUtils
     */
    public PhotoUtils setCompress(boolean compress) {
        isCompress = compress;
        return photoUtils;
    }

    public boolean isShowCamera() {
        return isShowCamera;
    }

    /**
     * @return 要显示的媒体类型
     */
    @MediaTypeAnont.MediaType
    public int getMediaType() {
        return mediaType;
    }

    /**
     * 是否压缩
     *
     * @return true是false否
     */
    public boolean isCompress() {
        return isCompress;
    }

    public int getMaxNum() {
        return maxNum;
    }

    /**
     * 设置最大选中值
     *
     * @param maxNum 最大值9
     * @return photoUtils
     */
    public PhotoUtils setMaxNum(int maxNum) {
        if (maxNum <= 0) {
            this.maxNum = 1;
        } else if (maxNum > 9) {
            this.maxNum = 9;
        } else {
            this.maxNum = maxNum;
        }
        return photoUtils;
    }

    public int getMinNum() {
        return minNum;
    }

    /**
     * 设置选中的最小值
     *
     * @param minNum 默认1
     * @return photoUtils
     */
    public PhotoUtils setMinNum(int minNum) {
        if (minNum <= 0) {
            this.minNum = 1;
        } else if (minNum >= 9) {
            this.minNum = 9;
        } else {
            this.minNum = minNum;
        }
        return photoUtils;
    }

    /**
     * 打开相册（最后调用）
     *
     * @param context     上下文
     * @param requestCode 请求码
     */
    public void startImagePicker(Activity context, int requestCode) {
        if (photoUtils.getMaxNum() < photoUtils.getMinNum()) {
            Toast.makeText(context, "maxNum is less than minNum", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context, ImagePickerActivity.class);
        if (isOriginalData && originalDataList != null) {
            intent.putExtra(MEDIA_PARAMS_NAME, (Serializable) originalDataList);
        }
        context.startActivityForResult(intent, requestCode);
    }

    public List<BaseMediaBean> getOriginalDataList() {
        return originalDataList;
    }

    /**
     * 设置元数据
     *
     * @param originalDataList 元数据
     * @return photoUtils
     */
    public PhotoUtils setOriginalDataList(List<BaseMediaBean> originalDataList) {
        this.originalDataList = originalDataList;
        return photoUtils;
    }

    /**
     * 是否有元数据
     *
     * @return true 有 false无
     */
    public boolean isOriginalData() {
        return isOriginalData;
    }

    public PhotoUtils settClass(Class<?> tClass) {
        this.tClass = tClass;
        return photoUtils;
    }

    public Class<?> gettClass() {
        return tClass;
    }

    /**
     * 设置是否有元数据
     *
     * @param originalData 元数据
     * @return this
     */
    public PhotoUtils setOriginalData(boolean originalData) {
        isOriginalData = originalData;
        return photoUtils;
    }
}
