package com.wtg.videolibrary.utils.image;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

/**
 * author: admin 2019/11/5
 * desc: 获取本地图片和视频的基类
 */
public abstract class BaseImageMedia<T> {
    private Context mContext;
    //获取map key是文件夹 value数据列表

    BaseImageMedia(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 查询数据
     *
     * @return 数据
     */
    public ArrayList<T> querySource() {
        ArrayList<T> list = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(getScanUri(), getProjection(), getSelection(), getSelectionArgs(), getSort());
        if (cursor != null) {
            while (cursor.moveToNext()) {
                T t = parse(cursor);
                list.add(t);
            }
            cursor.close();
        }
        return list;
    }

    /**
     * 查询URI
     *
     * @return uri
     */
    protected abstract Uri getScanUri();

    /**
     * 查询列名
     *
     * @return 列名
     */
    protected abstract String[] getProjection();

    /**
     * 查询条件
     *
     * @return 条件
     */
    protected abstract String getSelection();

    /**
     * 查询条件值
     *
     * @return 筛选条件
     */
    protected abstract String[] getSelectionArgs();

    /**
     * 查询排序
     *
     * @return 排序
     */
    protected abstract String getSort();

    /**
     * 对外暴露游标，让开发者灵活构建对象
     *
     * @param cursor 游标
     * @return cursor
     */
    protected abstract T parse(Cursor cursor);
}
