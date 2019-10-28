package com.example.douyin.video.adapter;

import android.animation.AnimatorSet;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.baselibrary.glide.MyCenterCrop;
import com.example.douyin.R;
import com.example.douyin.application.MyApplicationInitImpl;
import com.example.douyin.util.ObjectAnimatorUtil;
import com.example.douyin.video.player.MyDouyinVideoPlayer;
import com.example.douyin.video.model.DouyinVideoModel;
import com.example.douyin.tiktok.widget.TikTokLayout;

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
        ImageView imageView=((ImageView)helper.getView(R.id.iv_start));
        ((TikTokLayout)helper.getView(R.id.love_parent)).setOnLikeListener(new TikTokLayout.OnLikeListener() {
            @Override
            public void onLike() {
            }
            @Override
            public void onClick() {
                if (((MyDouyinVideoPlayer)helper.getView(R.id.videoplayer)).mediaInterface.isPlaying()){
                ((MyDouyinVideoPlayer)helper.getView(R.id.videoplayer)).mediaInterface.pause();
                    imageView.setVisibility(View.VISIBLE);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.play(ObjectAnimatorUtil.scaleAni(imageView, "scaleX", 1.5f, 1f, 100L, 0L))
                            .with(ObjectAnimatorUtil.scaleAni(imageView, "scaleY", 1.5f, 1f, 100l, 0l))
                            .with(ObjectAnimatorUtil.alphaAni(imageView, 0F, 1F, 100l, 0L));
                    animatorSet.start();
                }
                else {
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.play(ObjectAnimatorUtil.alphaAni(imageView, 1F, 0F, 100l, 0L));
                    animatorSet.start();
                    ((MyDouyinVideoPlayer)helper.getView(R.id.videoplayer)).mediaInterface.start();
                }
            }

            @Override
            public void onLongPress() {
                Toast.makeText(mContext, "长按事件", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
