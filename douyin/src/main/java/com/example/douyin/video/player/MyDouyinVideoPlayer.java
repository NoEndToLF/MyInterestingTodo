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
        startVideo();
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        return false;
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == cn.jzvd.R.id.surface_container) {
//            if (state == STATE_PLAYING) {
//                mediaInterface.pause();
//                state = STATE_PAUSE;
//                startButton.setVisibility(VISIBLE);
//                startButton.setClickable(false);
//                startButton.setImageResource(cn.jzvd.R.drawable.jz_click_play_selector);
//            } else if (state == STATE_PAUSE) {
//                mediaInterface.start();
//                state = STATE_PLAYING;
//                startButton.setVisibility(INVISIBLE);
//            }
//        }
//    }
//
//    @Override
//    public void onStateError() {
//        Toast.makeText(getContext(), "发生异常", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro, int thumbImg, int bottomPro, int retryLayout) {
//        topContainer.setVisibility(View.INVISIBLE);
//        bottomContainer.setVisibility(View.INVISIBLE);
//        startButton.setVisibility(View.INVISIBLE);
//        loadingProgressBar.setVisibility(loadingPro);
//        thumbImageView.setVisibility(thumbImg);
//        bottomProgressBar.setVisibility(View.INVISIBLE);
//        mRetryLayout.setVisibility(View.INVISIBLE);
//    }
}
