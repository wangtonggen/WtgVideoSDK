package com.dsrz.wtgvidosdk.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dsrz.wtgvidosdk.R;
import com.dsrz.wtgvidosdk.holder.AddHolder;
import com.dsrz.wtgvidosdk.holder.ImageVideoHolder;
import com.wtg.videolibrary.adapter.BaseAdapter;
import com.wtg.videolibrary.annotation.MultiHolderTypeAnont;
import com.wtg.videolibrary.bean.BaseMediaBean;
import com.wtg.videolibrary.holder.BaseHolder;
import com.wtg.videolibrary.utils.PhotoUtils;

import java.util.List;

/**
 * author: wtg  2019/11/19 0019
 * desc: 图片和视频的adapter
 */
public class ImageVideoAdapter extends BaseAdapter<BaseMediaBean, BaseHolder> {
    private static final int VIEW = 0;
    private static final int VIEW_ADD = 1;

    private boolean isShowAdd = true;

    public ImageVideoAdapter(Context context, List<BaseMediaBean> list) {
        super(context, list);
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BaseHolder baseHolder;
        if (i == VIEW_ADD) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_add, viewGroup, false);
            baseHolder = new AddHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_image, viewGroup, false);
            baseHolder = new ImageVideoHolder(view);
        }
        if (onItemClickListener != null) {
            baseHolder.setOnItemClickListener(onItemClickListener);
        }
        return baseHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder viewHolder, int i) {
        if (viewHolder instanceof ImageVideoHolder) {
            ImageVideoHolder imageVideoHolder = (ImageVideoHolder) viewHolder;
            imageVideoHolder.itemView.setTag(viewHolder.getLayoutPosition());
            imageVideoHolder.iv_delete.setTag(viewHolder.getLayoutPosition());
            BaseMediaBean baseMediaBean = mList.get(viewHolder.getLayoutPosition());
            if (baseMediaBean.getHolderType() == MultiHolderTypeAnont.HOLDER_TYPE_IMAGE) {
                imageVideoHolder.iv_delete.setVisibility(View.VISIBLE);
                imageVideoHolder.iv_play.setVisibility(View.INVISIBLE);
                Glide.with(mContext).load(baseMediaBean.getPath()).into(imageVideoHolder.iv_image);
            } else if (baseMediaBean.getHolderType() == MultiHolderTypeAnont.HOLDER_TYPE_VIDEO) {
                imageVideoHolder.iv_delete.setVisibility(View.VISIBLE);
                imageVideoHolder.iv_play.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(baseMediaBean.getPath()).into(imageVideoHolder.iv_image);
            }
        } else if (viewHolder instanceof AddHolder) {
            AddHolder addHolder = (AddHolder) viewHolder;
            addHolder.itemView.setTag(viewHolder.getLayoutPosition());
        }
    }

    @Override
    public int getItemCount() {
        if (mList.size() == 0) {
            return mList.size() + 1;
        }
        if (isShowAdd) {
            return mList.size() < 9 ? mList.size() + 1 : mList.size();
        } else {
            return mList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAdd(position) && isShowAdd) {
            return VIEW_ADD;
        } else {
            return VIEW;
        }
    }

    /**
     * 是否显示 add
     *
     * @return
     */
    public boolean isShowAdd() {
        return isShowAdd;
    }

    /**
     * 是否显示 添加图片的按钮
     *
     * @param showAdd true 显示 false不显示
     */
    public void setShowAdd(boolean showAdd) {
        isShowAdd = showAdd;
    }

    //根据 position 判断显示什么布局
    private boolean isShowAdd(int position) {
        //position=0 的时候  uris.size() 也等于0，返回 true，那么就是显示 添加的图片，postion =1，
        //uris.size()=3 (因为 position下标从 0 开始 而且最大数量是 9 张 那么就是有3张图片 返回 false)
        //以此类推
//        int size = baseMediaBeans.size() == 0 ? 0 : baseMediaBeans.size();
        return position == mList.size();
    }

}
