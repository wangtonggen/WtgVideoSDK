package com.wtg.videolibrary.utils;

import android.os.Environment;

import java.io.File;

/**
 * author: admin 2019/10/30
 * desc: 文件工具类
 */
public class FileUtils {
    //    public static String IMAGE_ROOT = Environment.getExternalStorageState()+"/AAA";
    public static String IMAGE_ROOT = Environment.getExternalStorageDirectory() + "/AAA";

    public static void createCompress(String dir) {
        File file = new File(dir);
        if (!file.isDirectory() || !file.exists()) {
            file.mkdirs();
        }
    }
}
