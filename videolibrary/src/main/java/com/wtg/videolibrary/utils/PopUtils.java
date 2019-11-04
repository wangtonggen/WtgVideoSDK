package com.wtg.videolibrary.utils;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * author: wtg  2019/11/4 0004
 * desc:
 */
public class PopUtils {

    public static void showAsDropDown(PopupWindow pw, View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            // 7.1 版本处理
            if (Build.VERSION.SDK_INT == 25) {
                //【note!】Gets the screen height without the virtual key
                WindowManager wm = (WindowManager) pw.getContentView().getContext().getSystemService(Context.WINDOW_SERVICE);
                int screenHeight = wm.getDefaultDisplay().getHeight();
                /*
                /*
                 * PopupWindow height for match_parent,
                 * will occupy the entire screen, it needs to do special treatment in Android 7.1
                */
                pw.setHeight(screenHeight - location[1] - anchor.getHeight() - yoff);
            }
            pw.showAtLocation(anchor, Gravity.NO_GRAVITY, xoff, location[1] + anchor.getHeight() + yoff);
        } else {
            pw.showAsDropDown(anchor, xoff, yoff);
        }
    }

    public static void showAsDropDown1(PopupWindow pw, View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            pw.setHeight(height);
            pw.showAsDropDown(anchor, xoff, yoff);
        } else {
            pw.showAsDropDown(anchor, 0, 0);
        }
    }

}
