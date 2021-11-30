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
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Adapter adapter;
    RecyclerView recyclerView;
    ImageView userImg;
    TextView profile_name;
    Spinner spinner;
    Intent mapIntent;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    static ArrayList<String> rvArray = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<dataModel> dataModels = new ArrayList();
        dataModels.add(new dataModel("안산호수공원","안산시 안산로",R.drawable.jjj1,"3"));
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
                        rvArray.add("롯데월드");
                        rvArray.add("여의도 한강공원");
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case 1 : {
                        dataModels.clear();
                        rvArray.clear();
                        dataModels.add(new dataModel("송월동동화마을","인천 중구 송월동3가",R.drawable.donghwa_img,"(최대)3!"));
                        dataModels.add(new dataModel("월미도","인천 중구 북성동1가 98-352",R.drawable.wallmido_img,"(최대)4!"));
                        rvArray.add("송월동동화마을");
                        rvArray.add("월미도");
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case 2 : {
                        dataModels.clear();
                        rvArray.clear();
                        dataModels.add(new dataModel("에버랜드","경기 용인시 처인구 포곡읍 에버랜드로 199",R.drawable.everland_img,"(최대)3!"));
                        dataModels.add(new dataModel("한국민속촌","경기 용인시 기흥구 민속촌로 90 한국민속촌",R.drawable.min_img,"(최대)5!"));
                        rvArray.add("에버랜드");
                        rvArray.add("한국민속촌");
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case 3 : {
                        dataModels.clear();
                        rvArray.clear();
                        dataModels.add(new dataModel("대관령양떼목장","강원 평창군 대관령면 대관령마루길 483-32 대관령양떼목장",R.drawable.sheep_img,"(최대)5!"));
                        dataModels.add(new dataModel("bts 버스정류장","강원 강릉시 주문진읍 향호리 8-55",R.drawable.bts_img,"(최대)1!"));
                        rvArray.add("대관령양떼목장");
                        rvArray.add("bts 버스정류장");
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case 4 : {
                        dataModels.clear();
                        rvArray.clear();
                        dataModels.add(new dataModel("청주랜드동물원","충북 청주시 상당구 명암로 224",R.drawable.cheong_img,"(최대)3!"));
                        rvArray.add("청주랜드동물원");
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case 5 : {
                        dataModels.clear();
                        rvArray.clear();
                        dataModels.add(new dataModel("경주월드","경북 경주시 보문로 544",R.drawable.gyongworld_img,"(최대)4!"));
                        rvArray.add("경주월드");
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case 6 : {
                        dataModels.clear();
                        rvArray.clear();
                        dataModels.add(new dataModel("전주 한옥마을","전북 전주시 완산구 기린대로 99",R.drawable.hanok_img,"(최대)5!"));
                        rvArray.add("전주 한옥마을");
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public void SlicedImg(Context context, String url, ImageView imv){
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().circleCrop()) //이미지 둥글게 잘라주는 옵션
                .into(imv);
    }

}