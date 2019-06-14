package com.example.myinterestingtodo.application;

import android.app.Application;

import com.example.baselibrary.IApplicationInit;

public class MyApplication extends Application {
    private static final String[] MODULESLIST =
            {"com.example.douyin.application.MyApplicationInitImpl"};
    @Override
    public void onCreate() {
        super.onCreate();
        modulesApplicationInit();
    }

    private void modulesApplicationInit() {
        for (String moduleImpl : MODULESLIST){
            try {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof IApplicationInit){
                    ((IApplicationInit) obj).init(getApplicationContext());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

    }

}
