package com.wtg.videolibrary.listener;

import com.wtg.videolibrary.bean.FolderBean;

import java.util.ArrayList;

/**
 * author: admin 2019/11/7
 * desc: 加载监听类
 */
public interface LoadMediaListener {
    void loadMediaSuccess(ArrayList<FolderBean> mediaList);
}
