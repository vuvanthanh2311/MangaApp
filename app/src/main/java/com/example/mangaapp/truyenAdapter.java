package com.example.mangaapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    public void onBindViewHolder(@NonNull final truyenAdapter.RcvViewholder holder, int position) {
        truyen listtruyen = arrtruyen.get(position);
        holder.tvtentruyen.setText(listtruyen.tentruyen);
        holder.tentacgia.setText(listtruyen.tentacgia);
        Picasso.get().load(listtruyen.linkhinh).into(holder.imgtruyen);
        holder.rcvtheloai.setItemAnimator(new DefaultItemAnimator());
        holder.rcvtheloai.addItemDecoration(new DividerItemDecoration(holder.rcvtheloai.getContext(), 0));
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.rcvtheloai.setLayoutManager(layoutManager);
        holder.list = new ArrayList<>();
        holder.rcvtheloaiAdapter = new RcvtheloaiAdapter(context,holder.list);
        holder.rcvtheloai.setAdapter(holder.rcvtheloaiAdapter);
        if (listtruyen.id!=null){
            holder.mData.child("manga").child(listtruyen.id).child("category").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot item : snapshot.getChildren()){
                        String ID = (String) item.getValue();
                        holder.list.add(ID);
                        holder.rcvtheloaiAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return arrtruyen.size();
    }

    public class RcvViewholder extends RecyclerView.ViewHolder {
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        RcvtheloaiAdapter rcvtheloaiAdapter;
        List<String> list ;
        ImageView imgtruyen;
        TextView tvtentruyen ,tentacgia ;
        RecyclerView rcvtheloai;
        public RcvViewholder(@NonNull View itemView , final truyenAdapter.OnItemTouchListener listener) {
            super(itemView);
            imgtruyen = itemView.findViewById(R.id.imgtruyen);
            tvtentruyen = itemView.findViewById(R.id.tv_tentruyen);
            tentacgia =itemView.findViewById(R.id.tv_tentacgia);
            rcvtheloai = itemView.findViewById(R.id.rcv_theloai);


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
