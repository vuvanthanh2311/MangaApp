package com.example.mangaapp.detailtruyen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaapp.R;
import com.example.mangaapp.comment.Comment;
import com.example.mangaapp.comment.commentAdapter;
import com.example.mangaapp.comment.commentclass;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fragment_Chitiet extends Fragment {
    DatabaseReference mData;
    TextView tvnoidung;
    String id ;
    private commentAdapter RcvAdapter;
    private RecyclerView recyclerView;
    private ArrayList<commentclass> listcm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chitiet,container,false);
        tvnoidung = view.findViewById(R.id.tv_noidung);
        recyclerView = view.findViewById(R.id.rcv_chitiet_comment);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));



        mData = FirebaseDatabase.getInstance().getReference();
        if (id !=null) {
            GetData(id);
            mData.child("detailmanga").child(id).child("nội dung").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String noidung = String.valueOf(snapshot.getValue());
                    tvnoidung.setText(noidung);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        return view;
    }
    public void getdata(String id){
        this.id =id;
    }
    private void GetData(String id){
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        listcm = new ArrayList<>();

        RcvAdapter = new commentAdapter(getContext(), listcm);
        recyclerView.setAdapter(RcvAdapter);

        for (int i=1;i<=100;i++){
            String chap = String.valueOf(i);
                mData.child("Comment").child(id).child(chap).limitToLast(15).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        commentclass cm = snapshot.getValue(commentclass.class);
                        listcm.add(cm);
                        RcvAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        }



    }
}
