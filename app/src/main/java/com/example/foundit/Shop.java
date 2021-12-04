package com.example.foundit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Shop extends AppCompatActivity {
    shop_Adapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ArrayList<shop_DataModel> shop_DataModels = new ArrayList();

        recyclerView = findViewById(R.id.shop_rv);
        adapter = new shop_Adapter(this, shop_DataModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));


        shop_DataModels.add(new shop_DataModel("45",R.drawable.chicken,"BHC 뿌링클!"));
        shop_DataModels.add(new shop_DataModel("45",R.drawable.chicken,"BHC 뿌링클!"));
        shop_DataModels.add(new shop_DataModel("45",R.drawable.chicken,"BHC 뿌링클!"));
        shop_DataModels.add(new shop_DataModel("45",R.drawable.chicken,"BHC 뿌링클!"));
        shop_DataModels.add(new shop_DataModel("45",R.drawable.chicken,"BHC 뿌링클!"));



    }
}