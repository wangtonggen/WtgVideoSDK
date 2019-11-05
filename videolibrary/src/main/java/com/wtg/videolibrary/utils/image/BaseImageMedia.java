package com.wtg.videolibrary.utils.image;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.wtg.videolibrary.bean.PhotoBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: admin 2019/11/5
 * desc: 获取本地图片和视频的基类
 */
public abstract class BaseImageMedia {
    private Context mContext;
    //获取map key是文件夹 value数据列表
    protected Map<String, List<PhotoBean>> mMap = new HashMap<>();
    protected ContentResolver mContentResolver;
    protected Cursor mCursor;
    protected String[] mProjection;
    protected Uri mUri;
    public BaseImageMedia(Context context){
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        mProjection = getProjection();
        mUri = getUri();
    }

    public Map<String, List<PhotoBean>> getmMap() {
        return mMap;
    }

    /**
     * 获取URI
     * @return uri
     */
    public abstract Uri getUri();

    /**
     * 获取查询条件
     * @return 条件集合
     */
    public abstract String[] getProjection();

    /**
     * 查询
     */
    public abstract void query();

    /**
     * 关闭数据库游标
     */
    public void closeCursor(){
        if (mCursor != null){
            mCursor.close();
        }
    }
}
