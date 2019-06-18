package com.example.douyin.util;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class ObjectAnimatorUtil {
    public static ObjectAnimator scaleAni(View view, String propertyName, Float from, Float to , Long time, Long delay){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, propertyName, from, to);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setStartDelay(delay);
        objectAnimator.setDuration(time);
        return objectAnimator;
    }

    public static ObjectAnimator translationX(View view,Float from,Float to,Long time, Long delayTime){
        ObjectAnimator ani = ObjectAnimator.ofFloat(view, "translationX", from, to);
        ani.setInterpolator(new LinearInterpolator());
        ani.setStartDelay(delayTime);
        ani.setDuration(time);
        return ani;
    }

    public static ObjectAnimator translationY(View view, Float from, Float to, Long time, Long delayTime){
        ObjectAnimator ani = ObjectAnimator.ofFloat(view, "translationY", from, to);
        ani.setInterpolator(new LinearInterpolator());
        ani.setStartDelay(delayTime);
        ani.setDuration(time);
        return ani;
    }

    public static ObjectAnimator alphaAni(View view,Float from,Float to,Long time,Long delayTime){
        ObjectAnimator ani = ObjectAnimator.ofFloat(view, "alpha", from, to);
        ani.setInterpolator(new LinearInterpolator());
        ani.setStartDelay(delayTime);
        ani.setDuration(time);
        return ani;
    }

    public static ObjectAnimator rotation(View view,Long time,Long delayTime,Float values){
        ObjectAnimator ani = ObjectAnimator.ofFloat(view, "rotation",values);
        ani.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return input;
            }
        });
        ani.setStartDelay(delayTime);
        ani.setDuration(time);
        return ani;
    }
}
