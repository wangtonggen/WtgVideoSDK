package com.wtg.videolibrary.widget;

import android.graphics.Color;

/**
 * author: wtg  2019/10/29 0029
 * desc: 状态的配置文件
 */
public class RecordButtonConfig {
    //录制按钮外圈的颜色
    private int button_border_color = Color.RED;
    //录制按钮内部颜色
    private int button_inside_color = Color.RED;
    //录制的模式
    private MyRecordButton.RecordMode button_recode_mode = MyRecordButton.RecordMode.LONG_CLICK;
    //矩形软件
    private float cicle_corner = 0;
    //外部环形宽度
    private float circleStrokeWidth = 0;
    //最小圆环宽度
    private int mMinCircleStrokeWidth;
    //最大圆环宽度
    private int mMaxCircleStrokeWidth;

    public RecordButtonConfig() {

    }

    public int getButton_border_color() {
        return button_border_color;
    }

    public void setButton_border_color(int button_border_color) {
        this.button_border_color = button_border_color;
    }

    public int getButton_inside_color() {
        return button_inside_color;
    }

    public void setButton_inside_color(int button_inside_color) {
        this.button_inside_color = button_inside_color;
    }

    public MyRecordButton.RecordMode getButton_recode_mode() {
        return button_recode_mode;
    }

    public void setButton_recode_mode(MyRecordButton.RecordMode button_recode_mode) {
        this.button_recode_mode = button_recode_mode;
    }

    public float getCicle_corner() {
        return cicle_corner;
    }

    public void setCicle_corner(float cicle_corner) {
        this.cicle_corner = cicle_corner;
    }

    public float getCircleStrokeWidth() {
        return circleStrokeWidth;
    }

    public void setCircleStrokeWidth(float circleStrokeWidth) {
        this.circleStrokeWidth = circleStrokeWidth;
    }

    public int getmMinCircleStrokeWidth() {
        return mMinCircleStrokeWidth;
    }

    public void setmMinCircleStrokeWidth(int mMinCircleStrokeWidth) {
        this.mMinCircleStrokeWidth = mMinCircleStrokeWidth;
    }

    public int getmMaxCircleStrokeWidth() {
        return mMaxCircleStrokeWidth;
    }

    public void setmMaxCircleStrokeWidth(int mMaxCircleStrokeWidth) {
        this.mMaxCircleStrokeWidth = mMaxCircleStrokeWidth;
    }
}
