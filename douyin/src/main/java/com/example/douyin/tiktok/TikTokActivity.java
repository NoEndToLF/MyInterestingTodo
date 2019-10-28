package com.example.douyin.tiktok;

import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.baselibrary.glide.MyCenterCrop;
import com.example.douyin.R;
import com.example.douyin.R2;
import com.example.douyin.application.MyApplicationInitImpl;
import com.example.douyin.tiktok.adapter.TikTokVideoAdapter;
import com.example.douyin.tiktok.controller.TikTokController;
import com.example.douyin.tiktok.ijkvideo.MyIjkVideoView;
import com.example.douyin.tiktok.volum.MyVolumReceiver;
import com.example.douyin.tiktok.widget.CircularCoverView;
import com.example.douyin.util.DouyinDatasUtil;
import com.example.douyin.video.model.DouyinVideoModel;
import com.example.douyin.video.recycler.OnVideoScrollListener;
import com.example.douyin.video.recycler.ViewPagerLayoutManager;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TikTokActivity extends AppCompatActivity {
    @BindView(R2.id.recycle_video)
    RecyclerView recycleVideo;
    @BindView(R2.id.sw_video_parent)
    SwipeRefreshLayout swVideoParent;
    @BindView(R2.id.ccv_ads)
    CircularCoverView ccvAds;
    private TikTokVideoAdapter tikTokVideoAdapter;
    private List<DouyinVideoModel> douyinVideoModelList;
    private ViewPagerLayoutManager linearLayoutManager;
    private int mCurrentPosition;
    private MyIjkVideoView mIjkVideoView;
    private TikTokController mTikTokController;
    private AudioManager audioManager;
    private MyVolumReceiver myVolumReceiver;
    private Timer volumTimer;
    private DismissVolumViewTimerTask dismissVolumViewTimerTask;
    private int volumCurrent, volumMax;
    private int WHAT_DISMISS_VOLUMVIEW = 11;
    private String type;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT_DISMISS_VOLUMVIEW) {
                mTikTokController.getMyVolumView().setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tik_tok);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        if (type.equals("one")) {
            ImmersionBar.with(this).init();
            ccvAds.setVisibility(View.GONE);
        } else {
            ImmersionBar.with(this).statusBarColor(R.color.black).fitsSystemWindows(true).init();

        }
        swVideoParent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swVideoParent.setRefreshing(false);
            }
        });
        initAudioManager();
        registerVolumReceiver();
        initVideoPlayer();
        initRecyclerView();
    }

    private void registerVolumReceiver() {
        myVolumReceiver = new MyVolumReceiver(audioManager);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(myVolumReceiver, filter);
        myVolumReceiver.setOnVolumChangeListener(new MyVolumReceiver.onVolumChangeListener() {
            @Override
            public void onVolumChange(int volume) {
                Log.v("volume=", volume + "");
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                downVolum();
                showVolumView();
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                upVolum();
                showVolumView();
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void downVolum() {
        if (volumCurrent > 0) {
            volumCurrent--;
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumCurrent, 0);
            mTikTokController.getMyVolumView().setCurrentVolum(volumCurrent);
        }

    }

    private void upVolum() {
        if (volumCurrent < volumMax) {
            volumCurrent++;
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumCurrent, 0);
            mTikTokController.getMyVolumView().setCurrentVolum(volumCurrent);
        }
    }

    private void showVolumView() {
        if (volumCurrent == 0) return;
        mTikTokController.getMyVolumView().setVisibility(View.VISIBLE);
        cancleTimer();
        volumTimer = new Timer();
        dismissVolumViewTimerTask = new DismissVolumViewTimerTask();
        volumTimer.schedule(dismissVolumViewTimerTask, 2500);
    }

    private void cancleTimer() {
        if (volumTimer != null) {
            volumTimer.cancel();
        }
        if (dismissVolumViewTimerTask != null) {
            dismissVolumViewTimerTask.cancel();
        }
    }


    private void initAudioManager() {
        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        volumMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volumCurrent = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    private void initVideoPlayer() {
        mIjkVideoView = new MyIjkVideoView(this);
        mIjkVideoView.setLooping(true);
        mTikTokController = new TikTokController(this);
        mIjkVideoView.setVideoController(mTikTokController);
        mTikTokController.getMyVolumView().setMaxVolum(volumMax);
        mTikTokController.getMyVolumView().setCurrentVolum(volumCurrent);
    }

    private void initRecyclerView() {
        douyinVideoModelList = DouyinDatasUtil.getTikTokVideoList();
        tikTokVideoAdapter = new TikTokVideoAdapter(R.layout.recycle_video_tiktok, douyinVideoModelList);
        linearLayoutManager = new ViewPagerLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        linearLayoutManager.setInitialPrefetchItemCount(1);
        recycleVideo.setLayoutManager(linearLayoutManager);
        recycleVideo.setAdapter(tikTokVideoAdapter);
        linearLayoutManager.setOnVideoScrollListener(new OnVideoScrollListener() {
            @Override
            public void onInitComplete(View view) {
                startPlay(0, view);
            }

            @Override
            public void onVideoToRelease(View view, int position) {
                if (mCurrentPosition == position) {
                    mIjkVideoView.release();
                    mTikTokController.getMyVolumView().setVisibility(View.GONE);
                }
            }

            @Override
            public void onVideoPlay(View view, int position, boolean isBottom) {
                if (mCurrentPosition == position) return;
                startPlay(position, view);
                mCurrentPosition = position;
            }

        });
    }

    private void startPlay(int position, View view) {
//        RecyclerView.ViewHolder viewHolder = recycleVideo.getChildViewHolder(view);
//        FrameLayout frameLayout = ((FrameLayout) ((BaseViewHolder) viewHolder).getView(R.id.container));
        View itemView = recycleVideo.getChildAt(0);
        FrameLayout frameLayout = itemView.findViewById(R.id.container);
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
        handler.removeCallbacksAndMessages(null);
        unregisterReceiver(myVolumReceiver);
        mIjkVideoView.release();
    }

    public class DismissVolumViewTimerTask extends TimerTask {

        @Override
        public void run() {
            handler.sendEmptyMessage(WHAT_DISMISS_VOLUMVIEW);
        }
    }
}
