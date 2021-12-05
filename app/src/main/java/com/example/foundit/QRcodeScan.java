package com.example.foundit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.Nullable;

import kotlin.jvm.internal.Intrinsics;

public class QRcodeScan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scan);
        initQRcodeScanner();
    }

    void initQRcodeScanner(){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setPrompt("QR코트를 인증해주세요");
        intentIntegrator.initiateScan();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult var10000 = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Intrinsics.checkNotNullExpressionValue(var10000, "IntentIntegrator.parseAc…stCode, resultCode, data)");
        IntentResult result = var10000;
      //  new Toast((Context)this);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("tttt", "QR코드 인증이 취소되었습니다.");
                this.finish();
            } else {
                Log.d("tttt2", result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}