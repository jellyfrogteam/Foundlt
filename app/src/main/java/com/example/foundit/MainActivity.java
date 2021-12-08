package com.example.foundit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.util.maps.helper.Utility;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Adapter adapter;
    RecyclerView recyclerView;
    ImageView userImg;
    TextView profile_name;
    TextView profile_level;
    Spinner spinner;
    Intent mapIntent;
    TextView coin_count;
    ImageView profile_img;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    GoogleSignInAccount acct_db;
    DatabaseReference myRef;
    FirebaseDatabase database;
    long stampTotal;

    static long myStampCnt;
    static ArrayList<String> rvArray = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profile_level = findViewById(R.id.profile_level);
        profile_img = findViewById(R.id.profile_img);
        acct_db = GoogleSignIn.getLastSignedInAccount(this);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(acct_db.getId());
        coin_count = findViewById(R.id.coin_count);
        Intent my_page = new Intent(this,MyPage.class);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if(acct.getId() == null){
            //FirebaseDatabase database = FirebaseDatabase.getInstance();
            //DatabaseReference myRef = database.getReference(acct.getId());
            myRef.child("stampCnt").child("cnt").setValue(0);
            myRef.child("stampTotal").child("cnt").setValue(0);
        }


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.child("stampCnt").getChildren()){
                    myStampCnt = (long) i.getValue();
                    Log.d("Main_마이스탬프Count", myStampCnt+"");
                }
                coin_count.setText(String.valueOf(myStampCnt));


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ArrayList<dataModel> dataModels = new ArrayList();
    //    dataModels.add(new dataModel("안산호수공원","안산시 안산로",R.drawable.jjj1,"3"));
//        dataModels.add(new dataModel("안산호수공원2","안산시 안산로232",R.drawable.samplebg,"5"));
//        dataModels.add(new dataModel("안산호수공원3","안산시 안산로323",R.drawable.samplebg,"2"));
//        dataModels.add(new dataModel("안산호수공원4","안산시 안산로434",R.drawable.samplebg,"6"));
        Intent Shop = new Intent(this,Shop.class);
        mapIntent = new Intent(this, Map.class);

        recyclerView = findViewById(R.id.main_rv);
        adapter = new Adapter(this, dataModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        profile_name = findViewById(R.id.profile_name);
        userImg = findViewById(R.id.profile_img);
        String userName;
        Uri userURL;
        if (user != null) {
            userName = user.getDisplayName();
            userURL = user.getPhotoUrl();
            SlicedImg(this,userURL.toString(),userImg);
            profile_name.setText(userName);
        }

        spinner = findViewById(R.id.main_spinner);
        //Spinner 선언
        String[] kinds1 = getResources().getStringArray(R.array.state);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(getBaseContext(),R.layout.spinner_item,kinds1);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setDropDownWidth(600);
        int myWidth = getBaseContext().getDisplay().getWidth();
        spinner.setDropDownVerticalOffset(30);
        spinner.setDropDownHorizontalOffset(myWidth/2-300);
        spinner.setPopupBackgroundResource(R.drawable.style_spinner_popup);


        ImageButton shopBtn = findViewById(R.id.btn_shop);
        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(Shop);
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //원래는 DB 써야하는데 하드코딩으로 대체
                switch (position){
                    case 0 : {
                        dataModels.clear();
                        rvArray.clear();
                        dataModels.add(new dataModel("롯데월드","서울 송파구 올림픽로 240",R.drawable.lotteworld_img,"(최대)3!"));
                        dataModels.add(new dataModel("여의도 한강공원","서울 영등포구 여의동로 330 한강사업본부 여의도안내센터",R.drawable.hangang_img,"(최대)5!"));
                        rvArray.add("37.51125194753548, 127.09813481146018, 1.아***스 \n 2.***급 \n 3.*이*드*");
                        rvArray.add("37.52854099043265, 126.93307809796607, 1.터*분수 \n 2.자***여소 \n 3.**무대");
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case 1 : {
                        dataModels.clear();
                        rvArray.clear();
                        dataModels.add(new dataModel("송월동동화마을","인천 중구 송월동3가",R.drawable.donghwa_img,"(최대)3!"));
                        dataModels.add(new dataModel("월미테마파크","인천광역시 중구 북성동 월미문화로 81",R.drawable.wallmido_img,"(최대)4!"));
                        rvArray.add("37.47773770851114, 126.62055051145941, 1.인**주 \n 2.도**방망이 \n 3.이상*나*의**스");
                        rvArray.add("37.47146273512526, 126.5962977268001, 1.디*코 \n 2.**킹 \n 3.*퍼*");
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case 2 : {
                        dataModels.clear();
                        rvArray.clear();
                        dataModels.add(new dataModel("에버랜드","경기 용인시 처인구 포곡읍 에버랜드로 199",R.drawable.everland_img,"(최대)3!"));
                        dataModels.add(new dataModel("한국민속촌","경기 용인시 기흥구 민속촌로 90 한국민속촌",R.drawable.min_img,"(최대)5!"));
                        rvArray.add("37.29439860788894, 127.20237434795669, 1.**프*스 \n 2.아***스프*스 \n 3.**풀스");
                        rvArray.add("37.25860693222674, 127.1169528005702, 1.패***스타 \n 2.대*간 \n 3.선*집");
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case 3 : {
                        dataModels.clear();
                        rvArray.clear();
                        dataModels.add(new dataModel("대관령양떼목장","강원 평창군 대관령면 대관령마루길 483-32 대관령양떼목장",R.drawable.sheep_img,"(최대)5!"));
                        dataModels.add(new dataModel("bts 버스정류장","강원 강릉시 주문진읍 향호리 8-55",R.drawable.bts_img,"(최대)1!"));
                        rvArray.add("37.68866627429973, 128.75276782680464, 1.*네 \n 2.올*이*못 \n 3.*막");
                        rvArray.add("37.91238328961611, 128.81702674030342, 1.힌트없음");
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case 4 : {
                        dataModels.clear();
                        rvArray.clear();
                        dataModels.add(new dataModel("청주랜드동물원","충북 청주시 상당구 명암로 224",R.drawable.cheong_img,"(최대)3!"));
                        rvArray.add("36.65210592638873, 127.52308955749714, 1.하**나 \n 2.히*라***이 \n 3.다***숭이");
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case 5 : {
                        dataModels.clear();
                        rvArray.clear();
                        dataModels.add(new dataModel("경주월드","경북 경주시 보문로 544",R.drawable.gyongworld_img,"(최대)4!"));
                        rvArray.add("35.837192957157754, 129.28231292676747, 1.*라* \n 2.발** \n 3.토**도");
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case 6 : {
                        dataModels.clear();
                        rvArray.clear();
                        dataModels.add(new dataModel("전주 한옥마을","전북 전주시 완산구 기린대로 99",R.drawable.hanok_img,"(최대)5!"));
                        rvArray.add("35.81430238443171, 127.15003221327255, 1.하*비 \n 2.한*당 \n 3.전**당");
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(my_page);
            }
        });


    }

    public void SlicedImg(Context context, String url, ImageView imv){
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().circleCrop()) //이미지 둥글게 잘라주는 옵션
                .into(imv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        coin_count = findViewById(R.id.coin_count);
        acct_db = GoogleSignIn.getLastSignedInAccount(this);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(acct_db.getId());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.child("stampCnt").getChildren()){
                    myStampCnt = (long) i.getValue();
                    Log.d("Main_Resume_마이스탬프Count", myStampCnt+"");
                }
                coin_count.setText(String.valueOf(myStampCnt));

                for(DataSnapshot i : snapshot.child("stampTotal").getChildren()){
                    stampTotal = (long) i.getValue();
                    Log.d("Main_마이스탬프Total", stampTotal+"");
                }
                profile_level.setText(String.valueOf(stampTotal/10));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}