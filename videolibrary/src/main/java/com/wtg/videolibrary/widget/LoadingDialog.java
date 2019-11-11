package com.wtg.videolibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wtg.videolibrary.R;
import com.wtg.videolibrary.utils.ScreenUtils;

/**
 * author: wtg  2019/11/11 0011
 * desc: 加载框dialog
 */
public class LoadingDialog extends Dialog {
    protected View mView;
    protected Context mContext;
    protected Window mDialogWindow;
    private WindowManager.LayoutParams mLayoutParams;
    private float mDimAmount = 0.5f;//dialog显示后背景变暗的程度
    private int mGravity = Gravity.CENTER;//dialog显示的位置
    public LoadingDialog(@NonNull Context context) {
        this(context,0);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context,themeResId);
        this.mContext = context;
        initParams();
    }

    /**
     * 初始化数据
     */
    private void initParams(){
        mView = LayoutInflater.from(mContext).inflate(R.layout.view_dialog_loading,null);
        if (mDialogWindow != null && mLayoutParams != null){
            mDialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mLayoutParams = mDialogWindow.getAttributes();
            mLayoutParams.width = (int) (ScreenUtils.getScreenWidth(mContext)*0.8);
            mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mLayoutParams.gravity = mGravity;
            mLayoutParams.dimAmount = mDimAmount;
            mDialogWindow.setAttributes(mLayoutParams);
        }
        mDialogWindow = getWindow();
        setContentView(mView);
        mDialogWindow.setWindowAnimations(R.style.dialog_loading_style);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
}
