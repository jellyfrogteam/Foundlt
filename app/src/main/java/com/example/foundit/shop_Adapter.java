package com.example.foundit;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class shop_Adapter extends RecyclerView.Adapter<shop_Adapter.Holder> {
    ArrayList<shop_DataModel> dataModels;
    Context mContext;
    private ItemClickListener itemClickListener;
    static String[] location_array;
    shop_Adapter(Context context, ArrayList<shop_DataModel> dataModels) {
        this.mContext = context;
        this.dataModels = dataModels;
    }

    interface ItemClickListener{
        void onClick(View view, int position);
    }

    void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.shop_item_rv, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Holder holder, int position) {
        holder.shop_content_stamp_cont.setText(dataModels.get(position).shop_content_stamp_cont);
        holder.shop_content_name.setText(dataModels.get(position).shop_content_name);
        holder.shop_content_img.setImageResource(dataModels.get(position).shop_content_img);
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView shop_content_stamp_cont;
        TextView shop_content_name;
        ImageView shop_content_img;


        public Holder(@NonNull View itemView) {
            super(itemView);
            shop_content_stamp_cont = itemView.findViewById(R.id.shop_content_stamp_cont);
            shop_content_name = itemView.findViewById(R.id.shop_content_name);
            shop_content_img = itemView.findViewById(R.id.shop_content_img);


        }
    }
}
