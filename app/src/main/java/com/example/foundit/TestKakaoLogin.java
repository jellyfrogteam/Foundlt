package com.example.foundit;

import android.content.Context;
import android.util.Log;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class TestKakaoLogin {
    interface IKLoginResult{
        void onKakaoLoginResult(User user);
    }
    private static TestKakaoLogin testKakaoLogin = new TestKakaoLogin();
    IKLoginResult listener;
    public void setListener(IKLoginResult listener){
        this.listener = listener;
    }
    public static TestKakaoLogin getInstance() {
        return testKakaoLogin;
    }
    private TestKakaoLogin() {
        if (testKakaoLogin != null)
            throw new AssertionError();
    }
    private Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit> () {
        @Override
        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {

            if (throwable != null) {
                Log.e("error",throwable.getLocalizedMessage());
            } else if (oAuthToken != null) {
                Log.i("success", "로그인 성공");
                UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                    @Override
                    public Unit invoke(User user, Throwable throwable) {
                        if (user != null) {
                            listener.onKakaoLoginResult(user);

                        }
                        return null;
                    }
                });
            }
            return null;
        }
    };
    public void login(Context context) {
        if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(context)) {
            UserApiClient.getInstance().loginWithKakaoTalk(context, callback);
        }
    }
}
