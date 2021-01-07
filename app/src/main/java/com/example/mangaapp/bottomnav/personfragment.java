package com.example.mangaapp.bottomnav;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mangaapp.R;
import com.example.mangaapp.activity_phyk;
import com.example.mangaapp.login.Login;
import com.example.mangaapp.login.Start;
import com.example.mangaapp.person.activity_blct;
import com.example.mangaapp.person.activity_gtct;
import com.example.mangaapp.themtruyen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class personfragment extends Fragment {
    private FirebaseAuth mAuth;
    DatabaseReference mData;
    ImageView imgperson;
    TextView tvten, tvnaptien, tvavatar, tvtinnhan, tvbinhluan, tvphanhoi, tvgioithieu, tvdannhap, tvdangxuat;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.person_fragment, container, false);
        imgperson = view.findViewById(R.id.img_person);
        tvten = view.findViewById(R.id.tv_person);
//        tvnaptien = view.findViewById(R.id.tv_naptien);
//        tvavatar= view.findViewById(R.id.tv_avatar);
//        tvtinnhan= view.findViewById(R.id.tv_tinnhan);
        tvbinhluan= view.findViewById(R.id.tv_binhluan);
        tvphanhoi = view.findViewById(R.id.tv_phanhoi);
        tvgioithieu= view.findViewById(R.id.tv_gioithieu);
        tvdannhap = view.findViewById(R.id.tv_dangnhap);
        tvdangxuat = view.findViewById(R.id.tv_dangxuat);
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();

        setdata();

        tvphanhoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), activity_phyk.class);
                startActivity(intent);
            }
        });
        tvbinhluan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), activity_blct.class);
                startActivity(intent);
            }
        });

        tvgioithieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), activity_gtct.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void setdata(){
        tvdannhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Start.class);
                startActivity(intent);

            }
        });
        tvdangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser currentUser) {

        if(currentUser!=null){
            String uid = currentUser.getUid();
//            Toast.makeText(getContext(),uid,Toast.LENGTH_SHORT).show();
            tvdannhap.setVisibility(View.GONE);
            tvdangxuat.setVisibility(View.VISIBLE);
            mData.child("user").child(uid).child("Name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String ten = String.valueOf(snapshot.getValue());
                    tvten.setText(ten);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else {
            tvdannhap.setVisibility(View.VISIBLE);
            tvdangxuat.setVisibility(View.GONE);
        }
    }
}
