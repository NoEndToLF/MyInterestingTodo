package com.example.douyin.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.douyin.R;
import com.example.douyin.util.ObjectAnimatorUtil;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;

public class TikTokLayout extends FrameLayout {
    private float[] num = new float[]{-35f, -25f, 0f, 25f, 35f};
    private long[] mHits = new long[2];
    private int  downX,downY ;
    private int  imageCount ;
    private long downTime;
    private int clickCount;
    private MyHandler handler;
    private boolean isMove;
public void showLikeView(MotionEvent e){
    imageCount++;
    final ImageView imageView = new ImageView(getContext());
    int width= (int)(getWidth()/2.7f);
    int height=(int)(getWidth()/2.7f);
    //在事件产生的坐标处添加心形组件
    LayoutParams layoutParams = new LayoutParams(width,height);
    layoutParams.leftMargin = (int)(e.getX()-width/2);
    layoutParams.topMargin = (int)(e.getY()-height);
    imageView.setImageDrawable(getResources().getDrawable(R.mipmap.like_heart));
    imageView.setLayoutParams(layoutParams);
    imageView.bringToFront();
    addView(imageView);
    //为组件添加动画
    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.play(//缩放动画，X轴2倍缩小至0.9倍
            ObjectAnimatorUtil.scaleAni(imageView, "scaleX", 2f, 0.9f, 100L, 0L))
            //缩放动画，Y轴2倍缩放至0.9倍
            .with(ObjectAnimatorUtil.scaleAni(imageView, "scaleY", 2f, 0.9f, 100l, 0l))
//                    //旋转动画，随机旋转角
            .with(ObjectAnimatorUtil.rotation(imageView, 200l, 0l, num[new Random().nextInt(4)]))
//                    //渐变透明动画，透明度从0-1
            .with(ObjectAnimatorUtil.alphaAni(imageView, 0F, 1F, 200l, 0L))
//                    //缩放动画，X轴0.9倍缩小至
            .with(ObjectAnimatorUtil.scaleAni(imageView, "scaleX", 0.9f, 1F, 50L, 100L))
//                    //缩放动画，Y轴0.9倍缩放至
            .with(ObjectAnimatorUtil.scaleAni(imageView, "scaleY", 0.9f, 1F, 50L, 100L))
//                    //位移动画，Y轴从0上移至
            .with(ObjectAnimatorUtil.translationY(imageView, 0F, (float) -width*1.5f, 500L, 500L))
//                    //透明动画，从1-0
            .with(ObjectAnimatorUtil.alphaAni(imageView, 1F, 0F, 500L, 500L))
//                    //缩放动画，X轴1至1.8倍
            .with(ObjectAnimatorUtil.scaleAni(imageView, "scaleX", 1F, 1.8f, 500L, 500L))
//                    //缩放动画，Y轴1至1.8倍
            .with(ObjectAnimatorUtil.scaleAni(imageView, "scaleY", 1F, 1.8f, 500L, 500L));
    animatorSet.start();
    animatorSet.addListener(new AnimatorListenerAdapter(){
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            removeViewInLayout(imageView);
            imageCount--;
            Log.v("imageCount=",imageCount+"");
        }
    });

    if (onLikeListener!=null){
        onLikeListener.onLike();
    }
}
    public TikTokLayout(Context context) {
        this(context,null);
    }

    public TikTokLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        handler=new MyHandler(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()& MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                isMove=false;
                downTime=SystemClock.uptimeMillis();
                downX = (int) event.getX();
                downY= (int) event.getY();
                System.arraycopy(mHits,1, mHits,0,mHits.length - 1);
                mHits[mHits.length-1] = SystemClock.uptimeMillis();
                if (mHits[0] >= (SystemClock.uptimeMillis() - 200)){
                    showLikeView(event);
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isMove&&imageCount<=1){

                        onLikeListener.onLongPress();
                        handler.removeCallbacksAndMessages(null);
                        }
                    }
                },700);//延时timeout后执行run方法中的代码
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = downX - (int) event.getX();
                int deltaY = downY - (int) event.getY();
                if (Math.abs(deltaY)> ViewConfiguration.get(getContext()).getScaledTouchSlop()){
                    isMove=true;
                    if(Math.abs(deltaX)<Math.abs(deltaY)){
                        //父控件拦截
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else{
                        //父控件不拦截
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                int clickX = downX - (int) event.getX();
                int clickY = downY - (int) event.getY();
                if (Math.abs(clickX)<= ViewConfiguration.get(getContext()).getScaledTouchSlop()&&
                        Math.abs(clickY)<= ViewConfiguration.get(getContext()).getScaledTouchSlop()){
                    if (Math.abs(SystemClock.uptimeMillis()-downTime)<=ViewConfiguration.getLongPressTimeout()) {
                        clickCount++;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (clickCount == 1&&imageCount<=1) {
                                    onLikeListener.onClick();
                                }
                                handler.removeCallbacksAndMessages(null);
                                //清空handler延时，并防内存泄漏
                                clickCount = 0;//计数清零
                            }
                        }, ViewConfiguration.getDoubleTapTimeout());//延时timeout后执行run方法中的代码
                    }

                }
                break;
                default:
                    break;
        }

        return true;
    }

    public void setOnLikeListener(OnLikeListener onLikeListener) {
        this.onLikeListener = onLikeListener;
    }

    private OnLikeListener onLikeListener;
    public interface OnLikeListener{
        void onLike();
        void onClick();
        void onLongPress();

    }
    static class MyHandler extends Handler
    {
        WeakReference<Context> mWeakReference;
        public MyHandler(Context context)
        {
            mWeakReference=new WeakReference<Context>(context);
        }
        @Override
        public void handleMessage(Message msg)
        {
            final Context context=mWeakReference.get();
            if(context!=null)
            {
                if (msg.what == 1)
                {

                }
            }
        }
    }
}
