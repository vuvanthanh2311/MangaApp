package com.example.mangaapp.phanloaifragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaapp.R;
import com.example.mangaapp.detailtruyen.detail_truyen;
import com.example.mangaapp.truyen;
import com.example.mangaapp.truyenAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fragment_xuyenkhong extends Fragment {
    View view;
    private truyenAdapter RcvAdapter;
    private RecyclerView recyclerView;
    private ArrayList<truyen> listtruyen;
    ArrayList<String> listID = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_xuyenkhong,container,false);
        recyclerView =view.findViewById(R.id.rcv_xuyenkhong);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        GetData();
        RcvAdapter.setOnItemTouchListener(new truyenAdapter.OnItemTouchListener() {
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        listtruyen = new ArrayList<>();
        RcvAdapter = new truyenAdapter(view.getContext(),listtruyen);
        recyclerView.setAdapter(RcvAdapter);

        mData.child("category").child("Xuyên không").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listID = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()){
                    String ID = (String) item.getValue();
                    listID.add(ID);
                }
                for (int i = 0; i < listID.size(); i++){

                    mData.child("manga").child(listID.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            truyen truyens = snapshot.getValue(truyen.class);
                            listtruyen.add(truyens);
                            RcvAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
