package com.example.douyin.video.player;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import cn.jzvd.JZUtils;
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
        thumbImageView.setVisibility(VISIBLE);
        startVideo();
    }
    @Override
    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro, int thumbImg, int bottomPro, int retryLayout) {
        topContainer.setVisibility(View.INVISIBLE);
        bottomContainer.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        loadingProgressBar.setVisibility(INVISIBLE);
        thumbImageView.setVisibility(thumbImg);
        bottomProgressBar.setVisibility(View.INVISIBLE);
        mRetryLayout.setVisibility(View.INVISIBLE);
    }
}
