package com.example.douyin.video.recycler;

import android.view.View;

public interface OnVideoScrollListener {
    //初始化成功
    void onInitComplete(View view);
    //释放
    void onVideoToRelease(View view, int position);
    //播放
    void onVideoPlay(View view,int position, boolean isBottom);
}
