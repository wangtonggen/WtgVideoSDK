package com.wtg.videolibrary.widget;

import android.content.Context;

import com.wtg.videolibrary.R;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

/**
 * author: wtg  2019/11/5 0005
 * desc:
 */
public class DividerItemDecoration extends Y_DividerItemDecoration {
    private Context mContext;

    public DividerItemDecoration(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public Y_Divider getDivider(int itemPosition) {
        Y_Divider divider = null;
        switch (itemPosition % 2) {
            case 0:
//                //每一行第一个显示rignt和bottom
//                divider = new Y_DividerBuilder()
//                        .setRightSideLine(true, 0xff666666, 2, 0, 0)
//                        .setBottomSideLine(true, 0xff666666, 2, 0, 0)
//                        .create();
//                break;
            case 1:
                //第二个显示Left和bottom
                divider = new Y_DividerBuilder()
                        .setRightSideLine(true, mContext.getResources().getColor(R.color.color_333232), 2, 0, 0)
//                        .setLeftSideLine(true, 0xff666666, 2, 0, 0)
                        .setBottomSideLine(true, mContext.getResources().getColor(R.color.color_333232), 2, 0, 0)
                        .create();
                break;
            default:
                break;
        }
        return divider;
    }
}
