package com.example.douyin.application;

import android.app.Application;
import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;
import com.example.baselibrary.IApplicationInit;

public class MyApplicationInitImpl implements IApplicationInit {
    public static HttpProxyCacheServer proxy;
    private static Context context;
    @Override
    public void init(Context context) {
          this.context=context;
    }
    public static HttpProxyCacheServer getProxy() {
        return proxy == null ? (proxy = newProxy()) : proxy;
    }
    private static HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(context)
                .maxCacheSize(1024 * 1024 * 1024)       // 1 Gb for cache
                .build();
    }
}
