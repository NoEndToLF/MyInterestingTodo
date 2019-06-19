package com.example.douyin.tiktok.volum;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

public class MyVolumReceiver extends BroadcastReceiver {
    private AudioManager audioManager;

    public MyVolumReceiver(AudioManager audioManager) {
        this.audioManager = audioManager;
    }

    public interface onVolumChangeListener{
        void onVolumChange(int volume);
    }
    private onVolumChangeListener onVolumChangeListener;

    public void setAudioManager(AudioManager audioManager) {
        this.audioManager = audioManager;
    }

    public void setOnVolumChangeListener(MyVolumReceiver.onVolumChangeListener onVolumChangeListener) {
        this.onVolumChangeListener = onVolumChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
            int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (onVolumChangeListener!=null){
                onVolumChangeListener.onVolumChange(currVolume);
            }
        }
    }
}
