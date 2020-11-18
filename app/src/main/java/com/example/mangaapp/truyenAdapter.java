package com.example.mangaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class truyenAdapter extends RecyclerView.Adapter<truyenAdapter.RcvViewholder> {
    // tạo đồ để setClick cho Recyclerview
    private truyenAdapter.OnItemTouchListener onItemTouchListener;
    public interface OnItemTouchListener{
        public void OnItemTouch(View view ,int position);
    }
    public void setOnItemTouchListener(truyenAdapter.OnItemTouchListener listener){
        onItemTouchListener = listener;
    }
    Context context;
    ArrayList<truyen> arrtruyen;

    public truyenAdapter(Context context, ArrayList<truyen> arrtruyen) {
        this.context = context;
        this.arrtruyen = arrtruyen;
    }
    @NonNull
    @Override
    public truyenAdapter.RcvViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_truyen,parent,false);

        return new truyenAdapter.RcvViewholder(view,onItemTouchListener);
    }

    @Override
    public void onBindViewHolder(@NonNull truyenAdapter.RcvViewholder holder, int position) {
        truyen listtruyen = arrtruyen.get(position);
        holder.tvtentruyen.setText(listtruyen.tentruyen);
        holder.tentacgia.setText(listtruyen.tentacgia);
//        holder.theloai.setText(listtruyen.category);
        Picasso.get().load(listtruyen.linkhinh).into(holder.imgtruyen);


    }

    @Override
    public int getItemCount() {
        return arrtruyen.size();
    }

    public class RcvViewholder extends RecyclerView.ViewHolder {

        ImageView imgtruyen;
        TextView tvtentruyen ,tentacgia ,theloai;
        public RcvViewholder(@NonNull View itemView , final truyenAdapter.OnItemTouchListener listener) {
            super(itemView);
            imgtruyen = itemView.findViewById(R.id.imgtruyen);
            tvtentruyen = itemView.findViewById(R.id.tv_tentruyen);
            tentacgia =itemView.findViewById(R.id.tv_tentacgia);
            theloai = itemView.findViewById(R.id.tv_theloai);
            // tạo đồ để setClick cho Recyclerview
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnItemTouch(v,position);
                        }
                    }
                }
            });
        }
    }
}
