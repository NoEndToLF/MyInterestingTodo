package com.example.douyin.tiktok.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.example.baselibrary.glide.MyCenterCrop;
import com.example.douyin.R;
import com.example.douyin.video.model.DouyinVideoModel;
import com.example.douyin.video.player.MyDouyinVideoPlayer;


import java.util.List;


public class TikTokVideoAdapter extends BaseQuickAdapter<DouyinVideoModel,BaseViewHolder> {
    public TikTokVideoAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DouyinVideoModel item) {
        Glide.with(mContext)
                .load(item.getImgUrl()).
                transform(new MyCenterCrop()).into(((ImageView)helper.getView(R.id.iv_thumb)));
        Log.v("position=",helper.getAdapterPosition()+"");
    }
}
