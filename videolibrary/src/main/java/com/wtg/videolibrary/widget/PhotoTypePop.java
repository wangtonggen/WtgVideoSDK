package com.wtg.videolibrary.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

/**
 * author: wtg  2019/11/4 0004
 * desc:
 */
public class PhotoTypePop extends PopupWindow {
    public PhotoTypePop(Context context) {
        super(context);
    }

    public PhotoTypePop(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoTypePop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PhotoTypePop(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PhotoTypePop(View contentView) {
        super(contentView);
    }

    public PhotoTypePop(int width, int height) {
        super(width, height);
    }

    public PhotoTypePop(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public PhotoTypePop(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    @Override
    public void showAsDropDown(View anchor) {
//        if (Build.VERSION.SDK_INT >= 24) {
//            Rect rect = new Rect();
//            anchor.getGlobalVisibleRect(rect);
//            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
//            setHeight(h);
//        }
        super.showAsDropDown(anchor);
    }
}
