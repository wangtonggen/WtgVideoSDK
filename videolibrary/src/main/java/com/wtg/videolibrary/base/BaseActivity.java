package com.wtg.videolibrary.base;

import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * author: wtg  2019/10/28 0028
 * desc: 基类
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * 修改状态栏颜色
     */
    protected void setWindowStatusBarColor(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(colorId));
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            getWindow().setStatusBarColor(getIntColor(colorId));
        }
    }

    /**
     * 把color的资源id转换成Int
     *
     * @param resId Int
     * @return Int
     */
    public int getIntColor(@ColorRes int resId) {
        if (Build.VERSION.SDK_INT >= 23) {
            return getApplication().getColor(resId);
        } else {
            return getApplication().getResources().getColor(resId);
        }
    }
}
