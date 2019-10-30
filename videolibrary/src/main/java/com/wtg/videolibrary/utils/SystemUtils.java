package com.wtg.videolibrary.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import static android.content.Context.WINDOW_SERVICE;

/**
 * @author: wtg by 2018/8/9 0009
 * @desc: 系统的一些功能的工具类
 **/
public class SystemUtils {
    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void dialPhone(Context context,String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)) {
            Toast.makeText(context,"无手机号",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context context,String phoneNum) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            context.startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否缺少权限
     */
    private static boolean lacksPermission(Context context,String permission) {
        return ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    /**
     * 跳转到权限设置界面
     */
    public static void permissionSetting(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(intent);
    }

    //获取软件的相关信息

    /**
     * 获取versionCode
     *
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager packageManager = context.getPackageManager();
            versionCode = packageManager.getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取versonName
     *
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取versonName
     *
     * @return
     */
    public static String getAPPName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取屏幕状态
     *
     * @return
     */
    public static Display getDisplay(Context context) {
        try {
            WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
            return windowManager.getDefaultDisplay();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取一个随机数
     *
     * @return
     */
    public static int getRandomNumber() {
        int randomNumber = (int) (System.currentTimeMillis() % 10000);
        return randomNumber;
    }

    /**
     * 复制文本到粘贴板
     * @param context
     * @param text
     */
    public static void CopyToClipboard(Context context, String text) {
        ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //clip.getText(); // 粘贴
        clip.setText(text); // 复制
        Toast.makeText(context,"复制成功",Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取时间戳
     * @return
     */
    public static long getTimestamp(){
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 判断是否打开消息通知
     * @param context 上下文
     * @return true 打开
     */
    public static boolean isNotificationEnabled(Context context){
        boolean isOpened = false;
        try {
            isOpened = NotificationManagerCompat.from(context).areNotificationsEnabled();
        } catch (Exception e) {
            e.printStackTrace();
            isOpened = false;
        }
        return isOpened;
    }

    /**
     * 跳转设置界面
     */
    public static void goSettings(Context context){
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 26) {
            // android 8.0引导
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= 21) {
            // android 5.0-7.0
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else {
            // 其他
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
