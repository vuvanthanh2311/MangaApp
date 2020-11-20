package com.example.mangaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RcvchapterAdapter extends RecyclerView.Adapter<RcvchapterAdapter.RcvViewholder> {
    Context context;
    List<String> list;

    public RcvchapterAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RcvViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.item_chapter,parent,false);

        return new RcvViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RcvViewholder holder, int position) {
        String hinh = list.get(position);
        Picasso.get().load(hinh).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RcvViewholder extends RecyclerView.ViewHolder {
        ImageView img;
        public RcvViewholder(@NonNull View itemView) {
            super(itemView);
            img =itemView.findViewById(R.id.img_chapter);
        }
    }
}
