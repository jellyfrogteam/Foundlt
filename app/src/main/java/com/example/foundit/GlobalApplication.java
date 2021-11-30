package com.example.foundit;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {
    private static GlobalApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        // Kakao SDK 초기화
        KakaoSdk.init(this, "kakao4501290d024e16e12f7bb36c771d585f");
    }
}
