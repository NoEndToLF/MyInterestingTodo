package com.example.douyin.video.adapter;

import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.baselibrary.IApplicationInit;
import com.example.douyin.R;
import com.example.douyin.application.MyApplicationInitImpl;
import com.example.douyin.video.player.MyDouyinVideoPlayer;
import com.example.douyin.video.model.DouyinVideoModel;

import java.util.List;

import cn.jzvd.JZMediaIjk;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class DouyinVideoAdapter extends BaseQuickAdapter<DouyinVideoModel,BaseViewHolder> {
    public DouyinVideoAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DouyinVideoModel item) {
        String proxyUrl = MyApplicationInitImpl.getProxy().getProxyUrl(item.getVideoUrl());
        ((MyDouyinVideoPlayer)helper.getView(R.id.videoplayer)).setUp(proxyUrl,"", Jzvd.SCREEN_NORMAL, JZMediaIjk.class);
        Glide.with(((MyDouyinVideoPlayer)helper.getView(R.id.videoplayer)).getContext())
                .load(item.getImgUrl()).centerCrop().into(((MyDouyinVideoPlayer)helper.getView(R.id.videoplayer)).thumbImageView);
        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_WIDTH_FILL_HEIGHT_WRAP);
        if (helper.getAdapterPosition()==0){
            ((MyDouyinVideoPlayer)helper.getView(R.id.videoplayer)).startVideo();
        }

    }
}
