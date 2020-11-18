package com.example.mangaapp;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaapp.fragment.fragment_dexuat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RcvdexuatAdapter extends RecyclerView.Adapter<RcvdexuatAdapter.RcvViewholder> {
    // tạo đồ để setClick cho Recyclerview
    private OnItemTouchListener onItemTouchListener;
    public interface OnItemTouchListener{
        public void OnItemTouch(View view ,int position);
    }
    public void setOnItemTouchListener(OnItemTouchListener listener){
        onItemTouchListener = listener;
    }
    Context context;
    ArrayList<truyen> arrtruyen;

    public RcvdexuatAdapter(Context context, ArrayList<truyen> arrtruyen) {
        this.context = context;
        this.arrtruyen = arrtruyen;
    }
    @NonNull
    @Override
    public RcvdexuatAdapter.RcvViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dexuat,parent,false);

        return new RcvViewholder(view,onItemTouchListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RcvdexuatAdapter.RcvViewholder holder, int position) {
        truyen listtruyen = arrtruyen.get(position);
        holder.tvtentruyen.setText(listtruyen.tentruyen);
        Picasso.get().load(listtruyen.linkhinh).into(holder.imgtruyen);

    }

    @Override
    public int getItemCount() {
        return arrtruyen.size();
    }

    public class RcvViewholder extends RecyclerView.ViewHolder {

        ImageView imgtruyen;
        TextView tvtentruyen;
        public RcvViewholder(@NonNull View itemView , final OnItemTouchListener listener) {
            super(itemView);
            imgtruyen = itemView.findViewById(R.id.imgdx_truyen);
            tvtentruyen = itemView.findViewById(R.id.tvdx_truyen);
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
