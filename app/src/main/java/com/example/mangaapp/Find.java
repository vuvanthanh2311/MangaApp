package com.example.mangaapp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.mangaapp.detailtruyen.detail_truyen;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Find extends AppCompatActivity {
    private RcvhangngayAdapter RcvAdapter;
    private RecyclerView recyclerView;
    private ArrayList<truyen> listtruyen,listfind;
    private EditText edt_find;
    private Button btn_find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        final DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        edt_find = findViewById(R.id.edt_find);
        btn_find = findViewById(R.id.btn_find);
        recyclerView =findViewById(R.id.rcv_Find);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        listtruyen = new ArrayList<>();
        mData.child("manga").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                truyen truyens = snapshot.getValue(truyen.class);
                listtruyen.add(truyens);
                btn_find.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = edt_find.getText().toString();
                        searchTruyen(s,listtruyen);
                    }
                });

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

    public void searchTruyen(String s, ArrayList<truyen> listtruyen){
        recyclerView.setLayoutManager(new GridLayoutManager(Find.this,2));
        listfind = new ArrayList<>();

        RcvAdapter = new RcvhangngayAdapter(Find.this,listfind);
        recyclerView.setAdapter(RcvAdapter);

        s = s.toUpperCase();

        for( int i=0;i<listtruyen.size();i++){
            truyen t = listtruyen.get(i);
            String ten = t.tentruyen.toUpperCase();
            if(ten.indexOf(s)>=0){
                listfind.add(t);
                RcvAdapter.notifyDataSetChanged();
            }
        }
        RcvAdapter.setOnItemTouchListener(new RcvhangngayAdapter.OnItemTouchListener() {
            @Override
            public void OnItemTouch(View view, int position) {
                truyen item = listfind.get(position);
                Intent intent = new Intent(view.getContext(), detail_truyen.class);
                intent.putExtra("id",item.id);
                intent.putExtra("tentruyen",item.tentruyen);
                startActivity(intent);
            }
        });

    }
}