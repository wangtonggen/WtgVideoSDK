package com.wtg.videolibrary.utils.common;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * activity 的管理类
 */
public class ActivityManagerUtils {
    // Activity栈
    private static Stack<Activity> activityStack = new Stack<>();
    // 单例模式
    private static ActivityManagerUtils instance;
    private final String TAG = getClass().getSimpleName();

    private ActivityManagerUtils() {

    }

    /**
     * 单一实例
     */
    public static ActivityManagerUtils getAppManager() {
        if (instance == null) {
            instance = new ActivityManagerUtils();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        return activityStack.size() == 0 ? null : activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishCurrentActivity() {
        if (activityStack.size() != 0) {
            Activity activity = activityStack.lastElement();
            finishActivity(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && activityStack.contains(activity)) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size();i++ ){
            if (activityStack.get(i).getClass().equals(cls)) {
                finishActivity(activityStack.get(i));
                i--;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 关闭之前的activity
     */
    public void finishBeforeAllActivity() {
        for (int i = 0; i < activityStack.size() - 1; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
                activityStack.remove(i);
                i--;
            }
        }
    }

    /**
     * 关闭第一个之后的的activity
     */
    public void finishAfterAllActivity() {
        for (int i = 1; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
                activityStack.remove(i);
                i--;
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
//            LogUtils.e(TAG, e.getMessage());
        }
    }
}
