package com.example.douyin.video.player;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import cn.jzvd.JzvdStd;

public class MyDouyinVideoPlayer extends JzvdStd {
    public MyDouyinVideoPlayer(Context context) {
        super(context);
    }

    public MyDouyinVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        startVideo();
    }
}
