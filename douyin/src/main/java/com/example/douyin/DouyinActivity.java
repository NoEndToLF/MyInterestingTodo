package com.example.douyin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.douyin.util.DouyinDatasUtil;
import com.example.douyin.video.player.MyDouyinVideoPlayer;
import com.example.douyin.video.adapter.DouyinVideoAdapter;
import com.example.douyin.video.model.DouyinVideoModel;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZMediaIjk;
import cn.jzvd.Jzvd;

public class DouyinActivity extends AppCompatActivity {

    @BindView(R2.id.recycle_video)
    RecyclerView recycleVideo;
    @BindView(R2.id.sw_video_parent)
    SwipeRefreshLayout swVideoParent;
    private DouyinVideoAdapter douyinVideoAdapter;
    private List<DouyinVideoModel> douyinVideoModelList;
    private SnapHelper snapHelper;
    private LinearLayoutManager linearLayoutManager;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douyin);
        ButterKnife.bind(this);
        ImmersionBar.with(this).init();
        swVideoParent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swVideoParent.setRefreshing(false);
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        douyinVideoModelList= DouyinDatasUtil.getTikTokVideoList();
        douyinVideoAdapter=new DouyinVideoAdapter(R.layout.recycle_video_douyin,douyinVideoModelList);
        linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleVideo.setLayoutManager(linearLayoutManager);
        recycleVideo.setAdapter(douyinVideoAdapter);
        snapHelper=new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recycleVideo);
        recycleVideo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                        View view = snapHelper.findSnapView(linearLayoutManager);
                        RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                        if (viewHolder != null && viewHolder instanceof BaseViewHolder) {
                            if (((BaseViewHolder)viewHolder).getAdapterPosition()!=position) {
                                Jzvd.releaseAllVideos();
                                ((MyDouyinVideoPlayer) ((BaseViewHolder) viewHolder).getView(R.id.videoplayer)).startVideo();
                            }
                        }
                        position=((BaseViewHolder)viewHolder).getAdapterPosition();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://拖动
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://惯性滑动
                        break;
                }
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.goOnPlayOnPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Jzvd.goOnPlayOnResume();
    }
    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
    }
}
