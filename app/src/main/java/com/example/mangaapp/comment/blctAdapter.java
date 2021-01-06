package com.example.mangaapp.comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaapp.R;

import java.util.ArrayList;

public class blctAdapter extends RecyclerView.Adapter<blctAdapter.RcvViewholder> {

    Context context;
    ArrayList<commentclass> arrcomment;

    public blctAdapter(Context context, ArrayList<commentclass> arrcomment) {
        this.context = context;
        this.arrcomment = arrcomment;
    }
    @NonNull
    @Override
    public blctAdapter.RcvViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coment,parent,false);
        return new blctAdapter.RcvViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull blctAdapter.RcvViewholder holder, int position) {

        commentclass Listcomment = arrcomment.get(position);
        holder.content.setText(Listcomment.content);
        holder.name.setText(Listcomment.user);
        holder.chapter.setText("Chapter "+Listcomment.chapter);
        holder.tentruyen.setText(Listcomment.tentruyen);
        holder.tentruyen.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return arrcomment.size();
    }

    public class RcvViewholder extends RecyclerView.ViewHolder {
        //        ImageView person;
        TextView content, name, chapter,time,tentruyen;
        public RcvViewholder(@NonNull View itemView) {
            super(itemView);
//            person = itemView.findViewById(R.id.img_cm_person);
            content = itemView.findViewById(R.id.tv_cm_content);
            name = itemView.findViewById(R.id.tv_cm_name);
            chapter = itemView.findViewById(R.id.tv_cm_chapter);
            tentruyen = itemView.findViewById(R.id.tv_cm_tentruyen);
//            time = itemView.findViewById(R.id.tv_cm_date);
        }
    }
}

