package com.example.foundit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class Login extends AppCompatActivity{

    private static final String TAG = "Login";
    private Button kakao_login = null;
    private Button google_login = null;
    private FirebaseAuth mAuth = null;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       // String keyHash = Utility.getKeyHash(this);
        Intent main = new Intent(getApplicationContext(), MainActivity.class);
        kakao_login = findViewById(R.id.btn_kakao_login);
        google_login = findViewById(R.id.btn_google_login);

        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken != null) {
                    Log.i("user", oAuthToken.getAccessToken() + " " + oAuthToken.getRefreshToken());
                }
                if (throwable != null) {
                    // TBD
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());
                }
                updateKakaoLoginUi();

                return null;
            }
        };

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            finish();
        }

google_login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        signIn();
    }
});
        kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(Login.this)) {
                    // 카카오톡이 있을 경우?
                    UserApiClient.getInstance().loginWithKakaoTalk(Login.this, callback);
                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(Login.this, callback);
                }
            }
        });
        updateKakaoLoginUi();

            }

public void updateKakaoLoginUi(){
        // 카카오 UI 가져오는 메소드 (로그인 핵심 기능)
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>(){
@Override
public Unit invoke(User user,Throwable throwable){
        if(user!=null){
        // 유저 정보가 정상 전달 되었을 경우
        Log.i(TAG,"id "+user.getId());   // 유저의 고유 아이디를 불러옵니다.
        Log.i(TAG,"invoke: nickname="+user.getKakaoAccount().getProfile().getNickname());  // 유저의 닉네임을 불러옵니다.
        Log.i(TAG,"userimage "+user.getKakaoAccount().getProfile().getProfileImageUrl());    // 유저의 이미지 URL을 불러옵니다.

        // 이 부분에는 로그인이 정상적으로 되었을 경우 어떤 일을 수행할 지 적으면 됩니다.
        }
        if(throwable!=null){
        // 로그인 시 오류 났을 때
        // 키해시가 등록 안 되어 있으면 오류 납니다.
        Log.w(TAG,"invoke: "+throwable.getLocalizedMessage());
        }
        return null;
        }
        });

        }
  /*  private void kakaoLogin() {
        TestKakaoLogin.getInstance().setListener(this);
        TestKakaoLogin.getInstance().login(this);
        Log.d("error", "hi");
    }
    @Override
    public void onKakaoLoginResult(User user) {
        //콜백메서드

    }*/
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);

      // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
      if (requestCode == RC_SIGN_IN) {
          Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
          try {
              // Google Sign In was successful, authenticate with Firebase
              GoogleSignInAccount account = task.getResult(ApiException.class);
              firebaseAuthWithGoogle(account);
          } catch (ApiException e) {
          }
      }
  }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent main = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(main);
                        } else {
                            // If sign in fails, display a message to the user.

                        }
                    }
                });
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}