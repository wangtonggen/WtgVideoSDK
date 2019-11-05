package com.wtg.videolibrary.utils.image;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * author: admin 2019/11/5
 * desc: 查询图片的工具
 */
public class ImageMediaImp extends BaseImageMedia {
    public ImageMediaImp(Context context) {
        super(context);
    }

    @Override
    public Uri getUri() {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }

    @Override
    public String[] getProjection() {
        return new String[]{
                MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DISPLAY_NAME,
                MediaStore.Images.ImageColumns.SIZE, MediaStore.Images.ImageColumns.DATE_ADDED
        };
    }

    @Override
    public void query() {
        mCursor = mContentResolver.query(mUri, mProjection, null, null,
                MediaStore.Images.Media.DATE_ADDED + " desc");
        if (mCursor == null) {
            return;
        }

        if (mCursor.getCount() != 0) {
            while (mCursor.moveToNext()) {
                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                String fileName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                String size = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                String date = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
//                FileItem item = new FileItem(path, fileName, size, date);
//                item.setType(FileItem.Type.Image);
//                mMedias.add(item);
            }
        }

        closeCursor();
    }
}
