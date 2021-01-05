package com.example.mangaapp.comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaapp.R;
import com.example.mangaapp.truyen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class commentAdapter extends RecyclerView.Adapter<commentAdapter.RcvViewholder> {

    Context context;
    ArrayList<commentclass> arrcomment;

    public commentAdapter(Context context, ArrayList<commentclass> arrcomment) {
        this.context = context;
        this.arrcomment = arrcomment;
    }
    @NonNull
    @Override
    public RcvViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coment,parent,false);
        return new RcvViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RcvViewholder holder, int position) {

        commentclass Listcomment = arrcomment.get(position);
        holder.content.setText(Listcomment.content);
        holder.name.setText(Listcomment.user);
        holder.chapter.setText("Chapter "+Listcomment.chapter);
    }

    @Override
    public int getItemCount() {
        return arrcomment.size();
    }

    public class RcvViewholder extends RecyclerView.ViewHolder {
//        ImageView person;
        TextView content, name, chapter,time;
        public RcvViewholder(@NonNull View itemView) {
            super(itemView);
//            person = itemView.findViewById(R.id.img_cm_person);
            content = itemView.findViewById(R.id.tv_cm_content);
            name = itemView.findViewById(R.id.tv_cm_name);
            chapter = itemView.findViewById(R.id.tv_cm_chapter);
//            time = itemView.findViewById(R.id.tv_cm_date);
        }
    }
}
