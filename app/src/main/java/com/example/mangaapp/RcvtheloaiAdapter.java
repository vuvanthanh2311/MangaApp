package com.example.mangaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RcvtheloaiAdapter extends RecyclerView.Adapter<RcvtheloaiAdapter.RcvViewholder> {
    Context context;
    List<String> list;

    public RcvtheloaiAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RcvtheloaiAdapter.RcvViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_theloai,parent,false);
        return new RcvViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RcvtheloaiAdapter.RcvViewholder holder, int position) {
        String theloai = list.get(position);
        holder.tvtheloai.setText(theloai);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RcvViewholder extends RecyclerView.ViewHolder {
        TextView tvtheloai;
        public RcvViewholder(@NonNull View itemView) {
            super(itemView);
            tvtheloai = itemView.findViewById(R.id.tv_theloai);
        }
    }
}
