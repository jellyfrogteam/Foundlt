package com.example.foundit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import kotlin.jvm.internal.Intrinsics;

public class QRcodeScan extends AppCompatActivity {

    String scanQR_code;
    DatabaseReference myRef;
    FirebaseDatabase database;
    GoogleSignInAccount acct;
    long myStampCnt;
    ArrayList<String> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scan);
        initQRcodeScanner();

        Log.d("ttt2", "언제실행되나");

        acct = GoogleSignIn.getLastSignedInAccount(this);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(acct.getId());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //myStampCnt = Integer.parseInt(String.valueOf(snapshot.child("stampCnt").getValue()));
                for(DataSnapshot i : snapshot.child("stampCnt").getChildren()){
                    myStampCnt = (long) i.getValue();
                    Log.d("마이스탬프Count", myStampCnt+"");
                }


                for(DataSnapshot i : snapshot.child("myStamp").getChildren()) {
                    Log.d("마이스탬프:", i.getValue()+"");
                    arr.add(String.valueOf(i.getValue()));
                    Log.d("마이스탬프:", arr+"");
//                    if (i.exists()) {
//                        Log.d("dataSS", i.getValue() + "");
//                        HashMap<String, String> stampCodeList = (HashMap<String, String>) i.getValue();
//                        Collection<String> stampTmp = stampCodeList.values();
//                        String[] stampArray = new String[stampTmp.size()];
//                        stampArray = stampTmp.toArray(stampArray);
//                        for (String s : stampArray) {
//                            Log.d("마이스탬프:", s);
//                        }
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


//        myRef.child("myStamp").push().setValue("1");
//        myRef.child("myStamp").push().setValue("2");

//
        //QR코드 값 생성
//        String randomValue = String.valueOf(database.getReference("stampCode").push().getKey());
//        database.getReference("stampCode").child(randomValue).child("code").setValue("MqEpXgFDrJHen2BS8H2");
//        database.getReference("stampCode").child(randomValue).child("cnt").setValue("1");
//        String randomValue2 = String.valueOf(database.getReference("stampCode").push().getKey());
//        database.getReference("stampCode").child(randomValue2).child("code").setValue("aqKplWFrDJZeN5B79G2");
//        database.getReference("stampCode").child(randomValue2).child("cnt").setValue("2");




    }

    void initQRcodeScanner(){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setPrompt("QR코드를 인증해주세요");
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
                scanQR_code = result.getContents();
                Log.d("tttt2", scanQR_code);
                stampAvailable();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        this.finish();
//    }

    public void stampAvailable(){

        // Read from the database
        database.getReference("stampCode").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot i : dataSnapshot.getChildren()){
                    if(i.exists()){
                        Log.d("dataSS", i.getValue()+"");
                        HashMap<String, String> stampCodeList = (HashMap<String, String>) i.getValue();
                        Collection<String> stampTmp = stampCodeList.values();
                        String[] stampArray = new String[stampTmp.size()];
                        stampArray = stampTmp.toArray(stampArray);
                        String stampCode = stampArray[0];
                        int stampCnt = Integer.parseInt(stampArray[1]);

                        Log.d("ttt", "스탬프코드: "+stampCode);
                        Log.d("ttt", "스탬프갯수: "+stampCnt);


                        if(stampArray[0].equals(scanQR_code) && !arr.contains(stampCode)){
                            Log.d("tttt3","합격");
                            myRef.child("myStamp").push().setValue(scanQR_code);
                            long cntTotal = myStampCnt+stampCnt;
                            myRef.child("stampCnt").child("cnt").setValue(cntTotal);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }
}