package com.example.mangaapp.fragment;

import android.content.Intent;
import android.icu.text.UFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mangaapp.Find;
import com.example.mangaapp.R;
import com.example.mangaapp.detailtruyen.ChapterActivity;
import com.example.mangaapp.truyen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class fragment_banner extends Fragment {
    View view;
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://manga-bead7.appspot.com");
    private ViewFlipper viewFlipper;
    Animation in, out, move_in, move_out;
//    ImageView next, before, find;
    ArrayList<String> list;
    ArrayList<truyen> listtruyen;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner,container,false);
        viewFlipper = view.findViewById(R.id.vfp_banner);
//        next = view.findViewById(R.id.img_next);
//        before = view.findViewById(R.id.img_before);
//        find = view.findViewById(R.id.Icon_search);
        getdata();
//        for (int i=0; i<=listtruyen.size();i++){
//            truyen m = listtruyen.get(i);
//            ImageView imageView = new ImageView(view.getContext());
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            Picasso.get().load(m.banner).into(imageView);
//            viewFlipper.addView(imageView);
//        }


        in = AnimationUtils.loadAnimation(view.getContext(),R.anim.fade_in);
        out = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_out);
//        move_in = AnimationUtils.loadAnimation(view.getContext(),R.anim.anim_move);
//        move_out = AnimationUtils.loadAnimation(view.getContext(),R.anim.anim_move_out);
        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);
        viewFlipper.setFlipInterval(6000);
        viewFlipper.setAutoStart(true);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (viewFlipper.isAutoStart()){
//                    viewFlipper.stopFlipping();
//                    viewFlipper.showNext();
//                    viewFlipper.startFlipping();
//                    viewFlipper.setAutoStart(true);
//                }
//            }
//        });
//        before.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (viewFlipper.isAutoStart()){
//                    viewFlipper.stopFlipping();
//                    viewFlipper.showPrevious();
//                    viewFlipper.startFlipping();
//                    viewFlipper.setAutoStart(true);
//                }
//            }
//        });
//        find.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(view.getContext(), Find.class);
//                startActivity(intent);
//            }
//        });
        viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
    private void getData(){
        list = new ArrayList<>();
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        mData.child("manga").limitToLast(8).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                truyen m = snapshot.getValue(truyen.class);
                listtruyen.add(m);
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
    private void getdata(){
        list = new ArrayList<>();
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        mData.child("banner").limitToLast(8).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String img = snapshot.getValue().toString();
                list.add(img);
                ImageView imageView = new ImageView(view.getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Picasso.get().load(img).into(imageView);
                viewFlipper.addView(imageView);
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
