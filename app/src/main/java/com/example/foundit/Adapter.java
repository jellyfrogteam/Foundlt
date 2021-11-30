package com.example.foundit;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    ArrayList<dataModel> dataModels;
    Context mContext;
    private ItemClickListener itemClickListener;
    Adapter(Context context, ArrayList<dataModel> dataModels) {
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
        View view = inflater.inflate(R.layout.item_rv, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Holder holder, int position) {
        holder.main_title.setText(dataModels.get(position).list_title);
        holder.main_title_sub.setText(dataModels.get(position).list_title_sub);
        holder.content_img.setImageResource(dataModels.get(position).list_img);
        holder.stamp_cnt.setText(dataModels.get(position).list_stamp_cnt);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.findStamp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("test", String.valueOf(position));
                        Log.d("spinnerRV", String.valueOf(MainActivity.rvArray.get(position)));

                        Intent mapIntent = new Intent(mContext, Map.class);
                        mContext.startActivity(mapIntent);
                    }
                });
            }
        }, 1000); //딜레이 타임 조절

    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView main_title;
        TextView main_title_sub;
        ImageView content_img;
        TextView stamp_cnt;
        Button findStamp;


        public Holder(@NonNull View itemView) {
            super(itemView);
            main_title = itemView.findViewById(R.id.main_title);
            main_title_sub = itemView.findViewById(R.id.main_title_sub);
            content_img = itemView.findViewById(R.id.content_img);
            stamp_cnt = itemView.findViewById(R.id.stamp_cnt);
            findStamp = itemView.findViewById(R.id.find_stamp);

        }
    }
}
