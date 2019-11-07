package com.wtg.videolibrary.task;

import android.content.Context;
import android.util.SparseArray;

import com.wtg.videolibrary.R;
import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.bean.FolderBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: admin 2019/11/7
 * desc: 文件处理类 对图片，视频，文件夹进行分类处理
 */
public class MediaHandler {
    public static final int ALL_MEDIA_FOLDER = -1;//全部媒体
    public static final int ALL_VIDEO_FOLDER = -2;//全部视频

    /**
     * 对查询到的图片进行聚类（相册分类）
     *
     * @param context 上下文
     * @param imageFileList 图片集合
     * @return 文件夹
     */
    public static ArrayList<FolderBean> getImageFolder(Context context, ArrayList<BaseMediaBean> imageFileList) {
        return getMediaFolder(context, imageFileList, null);
    }


    /**
     * 对查询到的视频进行聚类（相册分类）
     *
     * @param context 上下文
     * @param imageFileList 视频集合
     * @return 文件夹
     */
    public static List<FolderBean> getVideoFolder(Context context, ArrayList<BaseMediaBean> imageFileList) {
        return getMediaFolder(context, null, imageFileList);
    }


    /**
     * 对查询到的图片和视频进行聚类（相册分类）
     *
     * @param context 上下文
     * @param imageFileList 图片集合
     * @param videoFileList 视频集合
     * @return 文件夹
     */
    public static ArrayList<FolderBean> getMediaFolder(Context context, ArrayList<BaseMediaBean> imageFileList, ArrayList<BaseMediaBean> videoFileList) {

        //根据媒体所在文件夹Id进行聚类（相册）
        SparseArray<FolderBean> sparseArray = new SparseArray<>();
//        Map<Integer, FolderBean> mediaFolderMap = new HashMap<>();

        //全部图片、视频文件
        ArrayList<BaseMediaBean> mediaFileList = new ArrayList<>();
        if (imageFileList != null) {
            mediaFileList.addAll(imageFileList);
        }
        if (videoFileList != null) {
            mediaFileList.addAll(videoFileList);
        }

        //对媒体数据进行排序
        Collections.sort(mediaFileList, (o1, o2) -> {
//            if (o1.getDateToken() > o2.getDateToken()) {
//                return -1;
//            } else if (o1.getDateToken() < o2.getDateToken()) {
//                return 1;
//            } else {
//                return 0;
//            }
            return Long.compare(o2.getDateToken(),o1.getDateToken());
        });

        //全部图片或视频
        if (!mediaFileList.isEmpty()) {
            FolderBean allMediaFolder = new FolderBean(ALL_MEDIA_FOLDER, context.getString(R.string.all_media), mediaFileList.get(0).getPath(), mediaFileList);
            sparseArray.put(ALL_MEDIA_FOLDER, allMediaFolder);
        }

        //全部视频
        if (videoFileList != null && !videoFileList.isEmpty()) {
            FolderBean allVideoFolder = new FolderBean(ALL_VIDEO_FOLDER, context.getString(R.string.all_video), videoFileList.get(0).getPath(), videoFileList);
            sparseArray.put(ALL_VIDEO_FOLDER, allVideoFolder);
        }

        //对图片进行文件夹分类
        if (imageFileList != null && !imageFileList.isEmpty()) {
            int size = imageFileList.size();
            //添加其他的图片文件夹
            for (int i = 0; i < size; i++) {
                BaseMediaBean mediaFile = imageFileList.get(i);
                int imageFolderId = mediaFile.getFolderId();
                FolderBean mediaFolder = mediaFolderMap.get(imageFolderId);
                if (mediaFolder == null) {
                    mediaFolder = new FolderBean(imageFolderId, mediaFile.getFolderName(), mediaFile.getPath(), new ArrayList<>());
                }
                ArrayList<BaseMediaBean> imageList = mediaFolder.getMediaFileList();
                imageList.add(mediaFile);
                mediaFolder.setMediaFileList(imageList);
                mediaFolderMap.put(imageFolderId, mediaFolder);
            }
        }

        //整理聚类数据
        ArrayList<FolderBean> mediaFolderList = new ArrayList<>();
        for (Integer folderId : mediaFolderMap.keySet()) {
            mediaFolderList.add(mediaFolderMap.get(folderId));
        }

        //按照图片文件夹的数量排序
        Collections.sort(mediaFolderList, (o1, o2) -> {
//            if (o1.getMediaFileList().size() > o2.getMediaFileList().size()) {
//                return -1;
//            } else if (o1.getMediaFileList().size() < o2.getMediaFileList().size()) {
//                return 1;
//            } else {
//                return 0;
//            }
           return Integer.compare(o2.getMediaFileList().size(),o1.getMediaFileList().size());
        });


        return mediaFolderList;
    }

}
