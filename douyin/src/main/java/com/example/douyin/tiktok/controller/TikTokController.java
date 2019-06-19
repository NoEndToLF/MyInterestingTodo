package com.example.douyin.tiktok.controller;

import android.animation.AnimatorSet;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.example.douyin.R;
import com.example.douyin.tiktok.widget.MyVolumView;
import com.example.douyin.util.ObjectAnimatorUtil;
import com.example.douyin.tiktok.widget.TikTokLayout;

public class TikTokController extends BaseVideoController {

    private ImageView imageViewThumb,imageViewStart;
    private TikTokLayout tikTokLayout;



    private MyVolumView myVolumView;
    private boolean isClickPause;
    public TikTokController(@NonNull Context context) {
        super(context);
    }

    public TikTokController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TikTokController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public MyVolumView getMyVolumView() {
        return myVolumView;
    }
    public ImageView getImageViewThumb() {
        return imageViewThumb;
    }
    @Override
    protected void initView() {
        super.initView();
        imageViewThumb=(ImageView)mControllerView.findViewById(R.id.iv_thumb);
        imageViewStart=(ImageView)mControllerView.findViewById(R.id.iv_start);
        tikTokLayout=(TikTokLayout)mControllerView.findViewById(R.id.love_parent);
        myVolumView=(MyVolumView) mControllerView.findViewById(R.id.view_volum);
        myVolumView.setVisibility(View.GONE);
        tikTokLayout.setOnLikeListener(new TikTokLayout.OnLikeListener() {
            @Override
            public void onLike() {

            }
            @Override
            public void onClick() {
                if (mMediaPlayer.isPlaying()){
                    isClickPause=true;
                    mMediaPlayer.pause();
                }
                else {
                    mMediaPlayer.start();
                }
            }
            @Override
            public void onLongPress() {
                Toast.makeText(getContext(), "长按", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setPlayState(int playState) {
        super.setPlayState(playState);
        AnimatorSet animatorSet;
        switch (playState) {
            case IjkVideoView.STATE_IDLE:
                imageViewThumb.setVisibility(VISIBLE);
                imageViewStart.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_PLAYING:
                imageViewThumb.setVisibility(GONE);
                animatorSet = new AnimatorSet();
                animatorSet.play(ObjectAnimatorUtil.alphaAni(imageViewStart, 1F, 0F, 100l, 0L));
                animatorSet.start();
                break;
            case IjkVideoView.STATE_PAUSED:
                imageViewStart.setVisibility(View.VISIBLE);
                if (isClickPause){
                 animatorSet = new AnimatorSet();
                animatorSet.play(ObjectAnimatorUtil.scaleAni(imageViewStart, "scaleX", 1.5f, 1f, 100L, 0L))
                        .with(ObjectAnimatorUtil.scaleAni(imageViewStart, "scaleY", 1.5f, 1f, 100l, 0l))
                        .with(ObjectAnimatorUtil.alphaAni(imageViewStart, 0.5F, 1F, 100l, 0L));
                animatorSet.start();
                    isClickPause=false;
                }
                break;
            case IjkVideoView.STATE_PREPARED:
                break;
        }
    }



    @Override
    protected int getLayoutId() {
        return R.layout.video_tiktok;
    }
}
