package com.example.douyin.tiktok.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyVolumView extends View {
    private Paint paint;
    private int maxVolum,currentVolum;
    public int getMaxVolum() {
        return maxVolum;
    }

    public void setMaxVolum(int maxVolum) {
        this.maxVolum = maxVolum;
        invalidate();
    }

    public int getCurrentVolum() {
        return currentVolum;
    }

    public void setCurrentVolum(int currentVolum) {
        this.currentVolum = currentVolum;
        invalidate();
    }

    public MyVolumView(Context context) {
        super(context);
    }

    public MyVolumView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
        paint.setColor(Color.parseColor("#FACE15"));
        paint.setAntiAlias(true);
    }

    public MyVolumView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float percent=(float)currentVolum/(float)maxVolum;
        canvas.drawRect(0,0,percent*getWidth(),getHeight(),paint);
    }
}
