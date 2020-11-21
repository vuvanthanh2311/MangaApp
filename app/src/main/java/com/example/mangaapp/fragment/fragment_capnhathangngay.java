package com.example.mangaapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaapp.R;
import com.example.mangaapp.RcvdexuatAdapter;
import com.example.mangaapp.RcvhangngayAdapter;
import com.example.mangaapp.detailtruyen.detail_truyen;
import com.example.mangaapp.truyen;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fragment_capnhathangngay extends Fragment {
    View view;
    private RcvhangngayAdapter RcvAdapter;
    private RecyclerView recyclerView;
    private ArrayList<truyen> listtruyen;
    ArrayList<String> listID = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_capnhathangngay,container,false);
        recyclerView =view.findViewById(R.id.rcv_capnhathangngay);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        GetData();
        RcvAdapter.setOnItemTouchListener(new RcvhangngayAdapter.OnItemTouchListener() {
            @Override
            public void OnItemTouch(View view, int position) {
                truyen item = listtruyen.get(position);
                Intent intent = new Intent(view.getContext(), detail_truyen.class);
                intent.putExtra("id",item.id);
                intent.putExtra("tentruyen",item.tentruyen);
                startActivity(intent);
            }
        });
        return view;
    }

    private void GetData(){
        final DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        listtruyen = new ArrayList<>();

        RcvAdapter = new RcvhangngayAdapter(view.getContext(),listtruyen);
        recyclerView.setAdapter(RcvAdapter);


        mData.child("manga").limitToLast(12).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                truyen truyens = snapshot.getValue(truyen.class);
                listtruyen.add(truyens);
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
