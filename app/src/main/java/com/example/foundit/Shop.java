package com.example.foundit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Shop extends AppCompatActivity {
    shop_Adapter adapter;
    RecyclerView recyclerView;
    static TextView stampCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        ArrayList<shop_DataModel> shop_DataModels = new ArrayList();

        recyclerView = findViewById(R.id.shop_rv);
        adapter = new shop_Adapter(this, shop_DataModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));


        shop_DataModels.add(new shop_DataModel("30",R.drawable.chicken2,"BBQ 황금올리브!"));
        shop_DataModels.add(new shop_DataModel("45",R.drawable.chicken,"BHC 뿌링클!"));
        shop_DataModels.add(new shop_DataModel("45",R.drawable.chicken,"BHC 뿌링클!"));
        shop_DataModels.add(new shop_DataModel("45",R.drawable.chicken,"BHC 뿌링클!"));
        shop_DataModels.add(new shop_DataModel("45",R.drawable.chicken,"BHC 뿌링클!"));



        stampCnt = findViewById(R.id.stamp_cnt);
        Long myStampCnt = MainActivity.myStampCnt;
        stampCnt.setText(String.valueOf(myStampCnt));

    }
}