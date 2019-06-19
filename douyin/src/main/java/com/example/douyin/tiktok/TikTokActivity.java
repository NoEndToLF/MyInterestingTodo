package com.example.douyin.tiktok;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.example.baselibrary.glide.MyCenterCrop;
import com.example.douyin.R;
import com.example.douyin.R2;
import com.example.douyin.application.MyApplicationInitImpl;
import com.example.douyin.tiktok.adapter.TikTokVideoAdapter;
import com.example.douyin.tiktok.controller.TikTokController;
import com.example.douyin.tiktok.ijkvideo.MyIjkVideoView;
import com.example.douyin.util.DouyinDatasUtil;
import com.example.douyin.video.adapter.DouyinVideoAdapter;
import com.example.douyin.video.model.DouyinVideoModel;
import com.example.douyin.video.player.MyDouyinVideoPlayer;
import com.example.douyin.video.recycler.OnVideoScrollListener;
import com.example.douyin.video.recycler.ViewPagerLayoutManager;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.Jzvd;

public class TikTokActivity extends AppCompatActivity {
    @BindView(R2.id.recycle_video)
    RecyclerView recycleVideo;
    @BindView(R2.id.sw_video_parent)
    SwipeRefreshLayout swVideoParent;
    private TikTokVideoAdapter tikTokVideoAdapter;
    private List<DouyinVideoModel> douyinVideoModelList;
    private ViewPagerLayoutManager linearLayoutManager;
    private int mCurrentPosition;
    private MyIjkVideoView mIjkVideoView;
    private TikTokController mTikTokController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tik_tok);
        ButterKnife.bind(this);
        ImmersionBar.with(this).init();
        swVideoParent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swVideoParent.setRefreshing(false);
            }
        });
        initVideoPlayer();
        initRecyclerView();
    }

    private void initVideoPlayer() {
        mIjkVideoView = new MyIjkVideoView(this);
        mIjkVideoView.setLooping(true);
        mTikTokController = new TikTokController(this);
        mIjkVideoView.setVideoController(mTikTokController);
    }

    private void initRecyclerView() {
        douyinVideoModelList= DouyinDatasUtil.getTikTokVideoList();
        tikTokVideoAdapter=new TikTokVideoAdapter(R.layout.recycle_video_tiktok,douyinVideoModelList);
        linearLayoutManager=new ViewPagerLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleVideo.setLayoutManager(linearLayoutManager);
        recycleVideo.setAdapter(tikTokVideoAdapter);
        linearLayoutManager.setOnVideoScrollListener(new OnVideoScrollListener() {
            @Override
            public void onInitComplete(View view) {
                startPlay(0,view);
            }
            @Override
            public void onVideoToRelease(View view, int position) {
                if (mCurrentPosition == position) {
                  mIjkVideoView.release();
                }
            }

            @Override
            public void onVideoPlay(View view, int position, boolean isBottom) {
                if (mCurrentPosition == position) return;
                startPlay(position,view);
                mCurrentPosition = position;
            }

        });
    }
    private void startPlay(int position,View view) {
        RecyclerView.ViewHolder viewHolder = recycleVideo.getChildViewHolder(view);
        FrameLayout frameLayout = ((FrameLayout) ((BaseViewHolder) viewHolder).getView(R.id.container));
//        View itemView = recycleVideo.getChildAt(0);
//        FrameLayout frameLayout = itemView.findViewById(R.id.container);
        Glide.with(this)
                .load(douyinVideoModelList.get(position).getImgUrl())
                .transform(new MyCenterCrop())
                .into(mTikTokController.getImageViewThumb());
        ViewParent parent = mIjkVideoView.getParent();
        if (parent instanceof FrameLayout) {
            ((FrameLayout) parent).removeView(mIjkVideoView);
        }
        frameLayout.addView(mIjkVideoView);
        String proxyUrl = MyApplicationInitImpl.getProxy().getProxyUrl(douyinVideoModelList.get(position).getVideoUrl());
        mIjkVideoView.setUrl(proxyUrl);
        mIjkVideoView.setScreenScale(MyIjkVideoView.SCREEN_SCALE_CENTER_CROP_TIKTOK);
        mIjkVideoView.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mIjkVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIjkVideoView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIjkVideoView.release();
    }
}
