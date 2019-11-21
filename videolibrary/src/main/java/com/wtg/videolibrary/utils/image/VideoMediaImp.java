package com.wtg.videolibrary.utils.image;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.wtg.videolibrary.annotation.MultiHolderTypeAnont;
import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.bean.VideoMediaBean;

/**
 * author: admin 2019/11/7
 * desc: 视频类
 */
public class VideoMediaImp extends BaseImageMedia<BaseMediaBean> {
    public VideoMediaImp(Context mContext) {
        super(mContext);
    }

    @Override
    protected Uri getScanUri() {
        return MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    }

    @Override
    protected String[] getProjection() {
        return new String[]{
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.BUCKET_ID,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DATE_TAKEN
        };
    }

    @Override
    protected String getSelection() {
        return null;
    }

    @Override
    protected String[] getSelectionArgs() {
        return null;
    }

    @Override
    protected String getSort() {
        return MediaStore.Video.Media.DATE_TAKEN + " desc";
    }

    @Override
    protected BaseMediaBean parse(Cursor cursor) {
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        String mime = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE));
        Integer folderId = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID));
        String folderName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
        long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
        long dateToken = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DATE_TAKEN));

        VideoMediaBean mediaFile = new VideoMediaBean();
        mediaFile.setPath(path);
        mediaFile.setMime(mime);
        mediaFile.setHolderType(MultiHolderTypeAnont.HOLDER_TYPE_VIDEO);
        mediaFile.setFolderId(folderId);
        mediaFile.setFolderName(folderName);
        mediaFile.setDuration(duration);
        mediaFile.setDateToken(dateToken);

        return mediaFile;
    }
}
