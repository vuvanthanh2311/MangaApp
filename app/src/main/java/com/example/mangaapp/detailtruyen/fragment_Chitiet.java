package com.example.mangaapp.detailtruyen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mangaapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fragment_Chitiet extends Fragment {
    DatabaseReference mData;
    TextView tvnoidung;
    String id ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chitiet,container,false);
        tvnoidung = view.findViewById(R.id.tv_noidung);
        mData = FirebaseDatabase.getInstance().getReference();
        if (id !=null) {
            Toast.makeText(view.getContext(), id + "chitiet", Toast.LENGTH_SHORT).show();
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
}
