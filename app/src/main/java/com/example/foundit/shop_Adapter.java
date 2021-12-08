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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class shop_Adapter extends RecyclerView.Adapter<shop_Adapter.Holder> {
    ArrayList<shop_DataModel> dataModels;
    Context mContext;
    private ItemClickListener itemClickListener;
    static String[] location_array;
    Long myStampCnt = MainActivity.myStampCnt;

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


        holder.shop_layout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.shop_content_stamp.setVisibility(View.VISIBLE);
                holder.shop_content_buy.setVisibility(View.VISIBLE);
                holder.shop_content_buy_text.setVisibility(View.VISIBLE);
                holder.shop_content_img.setVisibility(View.VISIBLE);
                holder.shop_content_stamp_cont.setVisibility(View.VISIBLE);
                holder.shop_content_name.setVisibility(View.VISIBLE);
                holder.shopCode.setVisibility(View.GONE);
            }
        });

        holder.shop_content_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("stampCnt", "스탬프 현황1: "+myStampCnt);
                Long stampCnt = Long.parseLong((String) holder.shop_content_stamp_cont.getText());
                Log.d("stampCnt", "상품 가격"+stampCnt);

                if(myStampCnt - stampCnt < 0){
                    Toast.makeText(holder.itemView.getContext(), "스탬프가 부족합니다.", Toast.LENGTH_SHORT).show();
                }else{
                    myStampCnt -= stampCnt;
                    Log.d("stampCnt", "스탬프 현황2: "+myStampCnt);
                    holder.shop_content_stamp.setVisibility(View.GONE);
                    holder.shop_content_buy.setVisibility(View.GONE);
                    holder.shop_content_buy_text.setVisibility(View.GONE);
                    holder.shop_content_img.setVisibility(View.GONE);
                    holder.shop_content_stamp_cont.setVisibility(View.GONE);
                    holder.shop_content_name.setVisibility(View.GONE);
                    holder.shopCode.setVisibility(View.VISIBLE);
                }

                GoogleSignInAccount acct_db = GoogleSignIn.getLastSignedInAccount(holder.itemView.getContext());
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(acct_db.getId());

                myRef.child("stampCnt").child("cnt").setValue(myStampCnt);

                Shop.stampCnt.setText(String.valueOf(myStampCnt));
                Log.d("stampCnt", "스탬프 현황3: "+myStampCnt);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView shop_content_stamp_cont;
        TextView shop_content_name;
        TextView shop_content_stamp;
        ImageView shop_content_img;
        ImageButton shop_content_buy;
        TextView shop_content_buy_text;
        TextView shopCode;
        ConstraintLayout shop_layout_1;


        public Holder(@NonNull View itemView) {
            super(itemView);
            shop_content_stamp_cont = itemView.findViewById(R.id.shop_content_stamp_cont);
            shop_content_name = itemView.findViewById(R.id.shop_content_name);
            shop_content_img = itemView.findViewById(R.id.shop_content_img);
            shop_content_buy = itemView.findViewById(R.id.shop_content_buy);
            shop_layout_1 = itemView.findViewById(R.id.shop_layout_1);
            shopCode = itemView.findViewById(R.id.shop_code);
            shop_content_buy_text = itemView.findViewById(R.id.shop_content_buy_text);
            shop_content_stamp = itemView.findViewById(R.id.shop_content_stamp);
        }
    }
}
