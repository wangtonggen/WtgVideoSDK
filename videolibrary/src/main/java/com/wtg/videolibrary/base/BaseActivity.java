package com.wtg.videolibrary.base;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.wtg.videolibrary.utils.SystemUtils;
import com.wtg.videolibrary.utils.common.ActivityManagerUtils;

/**
 * author: wtg  2019/10/28 0028
 * desc: 基类
 */
public class BaseActivity extends AppCompatActivity {

    protected AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManagerUtils.getAppManager().addActivity(this);
    }

    /**
     * 修改状态栏颜色
     */
    protected void setWindowStatusBarColor(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(colorId));
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


    /**
     * 权限设置提醒dialog
     *
     * @param message 消息
     */
    public void showPermissionDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("权限申请");
        builder.setMessage(String.format("在设置-应用-51山克油-权限中开启%s权限，以正常使用51山克油功能", message));
        builder.setNegativeButton("去设置", (dialog, which) -> {
            //存储空间、定位和电话
            SystemUtils.permissionSetting(context);
        });
        builder.setPositiveButton("取消", (dialog, which) -> {
            if (message.equals("存储空间和电话")) {
//                ActivityManager.getAppManager().finishAllActivity();
            }
            dialog.dismiss();
        });
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerUtils.getAppManager().finishActivity(this);
    }
}
