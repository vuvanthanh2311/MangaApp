package com.example.mangaapp.bottomnav;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaapp.Find;
import com.example.mangaapp.R;
import com.example.mangaapp.RcvhangngayAdapter;
import com.example.mangaapp.detailtruyen.detail_truyen;
import com.example.mangaapp.truyen;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Explorefragment extends Fragment {
    private RcvhangngayAdapter RcvAdapter;
    private RecyclerView recyclerView;
    private ArrayList<truyen> listtruyen,listfind;
    private EditText edt_find;
    private Button btn_find;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.explore_fragment, container, false);
        final DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        edt_find = view.findViewById(R.id.edt_find1);
        btn_find = view.findViewById(R.id.btn_find1);
        recyclerView = view.findViewById(R.id.rcv_Find1);

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
        return view;
    }
    public void searchTruyen(String s, ArrayList<truyen> listtruyen){
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        listfind = new ArrayList<>();

        RcvAdapter = new RcvhangngayAdapter(getContext(),listfind);
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
