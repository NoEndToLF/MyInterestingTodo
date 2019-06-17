package com.example.douyin.video.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.baselibrary.IApplicationInit;
import com.example.baselibrary.glide.MyCenterCrop;
import com.example.douyin.R;
import com.example.douyin.application.MyApplicationInitImpl;
import com.example.douyin.video.player.MyDouyinVideoPlayer;
import com.example.douyin.video.model.DouyinVideoModel;
import com.example.douyin.widget.LoveLayout;

import java.util.List;

import cn.jzvd.JZMediaIjk;
import cn.jzvd.Jzvd;

public class DouyinVideoAdapter extends BaseQuickAdapter<DouyinVideoModel,BaseViewHolder> {
    public DouyinVideoAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DouyinVideoModel item) {
        String proxyUrl = MyApplicationInitImpl.getProxy().getProxyUrl(item.getVideoUrl());
        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_WIDTH_FILL_HEIGHT_WRAP);
        ((MyDouyinVideoPlayer)helper.getView(R.id.videoplayer)).setUp(proxyUrl,"", Jzvd.SCREEN_NORMAL, JZMediaIjk.class);
        Glide.with(mContext)
                .load(item.getImgUrl()).
                transform(new MyCenterCrop()).into(((MyDouyinVideoPlayer)helper.getView(R.id.videoplayer)).thumbImageView);
        ((LoveLayout)helper.getView(R.id.love_parent)).setOnLikeListener(new LoveLayout.OnLikeListener() {
            @Override
            public void onLike() {
                Log.v("haha","onDoubleClick");
            }

            @Override
            public void onClick() {
                Log.v("haha","onClick");

            }

            @Override
            public void onLongPress() {
                Log.v("haha","onLongPress");

            }
        });
        if (helper.getAdapterPosition()==0){
            ((MyDouyinVideoPlayer)helper.getView(R.id.videoplayer)).startVideo();
        }
    }
}
