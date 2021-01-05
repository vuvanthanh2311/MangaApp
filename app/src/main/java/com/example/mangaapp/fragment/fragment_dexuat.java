package com.example.mangaapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaapp.R;
import com.example.mangaapp.RcvdexuatAdapter;
import com.example.mangaapp.detailtruyen.detail_truyen;
import com.example.mangaapp.truyen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;


public class fragment_dexuat extends Fragment {
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://manga-bead7.appspot.com");
    View view;
    private RcvdexuatAdapter RcvAdapter;
    private RecyclerView recyclerView;
    private ArrayList<truyen> listtruyen;
    private TextView xemthem;
    ArrayList<String> listID = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dexuat,container,false);
        recyclerView =view.findViewById(R.id.rcv_dexuat);
        xemthem = view.findViewById(R.id.tvcnhn_xemthem);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 0));
        GetData();
        RcvAdapter.setOnItemTouchListener(new RcvdexuatAdapter.OnItemTouchListener() {
            @Override
            public void OnItemTouch(View view, int position) {
                truyen item = listtruyen.get(position);
                Intent intent = new Intent(view.getContext(), detail_truyen.class);
                intent.putExtra("id",item.id);
                intent.putExtra("tentruyen",item.tentruyen);
                startActivity(intent);
            }
        });
        xemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),activity_dexuat.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void GetData(){
        final DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        listtruyen = new ArrayList<>();
        RcvAdapter = new RcvdexuatAdapter(view.getContext(),listtruyen);
        recyclerView.setAdapter(RcvAdapter);

        mData.child("offer").addListenerForSingleValueEvent(new ValueEventListener() {
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
