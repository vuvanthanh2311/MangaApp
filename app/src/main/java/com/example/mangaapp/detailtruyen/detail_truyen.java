package com.example.mangaapp.detailtruyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangaapp.R;
import com.example.mangaapp.RcvtheloaiAdapter;
import com.example.mangaapp.login.Login;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class detail_truyen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://manga-bead7.appspot.com");
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    RcvtheloaiAdapter rcvtheloaiAdapter;
    List<String> list ;
    ArrayList<String> listID = null;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    fragment_Chitiet chitiet;
    fragment_Chapter chapter;
    TextView tvtentruyen;
    LinearLayout  llfavorite,llfavoritered;
    ImageView imgtruyen ,imgshare, back;
    Button btnbdx;
    RecyclerView rcvtheloai;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_truyen);
        viewPager =findViewById(R.id.vpg_detailtruyen);
        tabLayout =findViewById(R.id.tbl_detail);
        tvtentruyen = findViewById(R.id.tvdetail_tentruyen);
        imgtruyen = findViewById(R.id.img_detailtruyen);
        rcvtheloai = findViewById(R.id.rcv_detailtheloai);
        btnbdx = findViewById(R.id.btn_bdx);
        imgshare =findViewById(R.id.img_share);
        llfavorite = findViewById(R.id.ll_favorite);
        llfavoritered =findViewById(R.id.ll_favorite_red);
        back = findViewById(R.id.img_detail_back);
        mAuth = FirebaseAuth.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        String ten = intent.getStringExtra("tentruyen");
        getData(id,ten);

        chitiet = new fragment_Chitiet();
        chapter = new fragment_Chapter();
        chitiet.getdata(id);
        chapter.getdata(id);
        btnbdx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(detail_truyen.this, ChapterActivity.class);
                intent.putExtra("chap","1");
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        setViewpager();



    }
    private void setViewpager(){
        Viewpagerdetail viewpagerdetail = new Viewpagerdetail(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewpagerdetail.add(chitiet,"Chi tiết");
        viewpagerdetail.add(chapter, "Chapter");
        viewPager.setAdapter(viewpagerdetail);

    }
    private void getData(String id,String ten){
        tvtentruyen.setText(ten);
        StorageReference storageRef = storage.getReference();
        storageRef.child("imgmanga/"+id+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imgtruyen);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        rcvtheloai.setItemAnimator(new DefaultItemAnimator());
        rcvtheloai.addItemDecoration(new DividerItemDecoration(rcvtheloai.getContext(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvtheloai.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        rcvtheloaiAdapter = new RcvtheloaiAdapter(this,list);
        rcvtheloai.setAdapter(rcvtheloaiAdapter);
        if (id!=null){
            mData.child("manga").child(id).child("category").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot item : snapshot.getChildren()){
                        String ID = (String) item.getValue();
                        list.add(ID);
                        rcvtheloaiAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser!=null){
            final String uid = currentUser.getUid();
            mData.child("follow").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listID = new ArrayList<>();
                    for (DataSnapshot item : snapshot.getChildren()){
                        String idd = (String) item.getValue();
                        listID.add(idd);
                    }
                    for (int i = 0 ; i < listID.size() ; i++){
                        if (listID.get(i).equals(id)){
                            llfavorite.setVisibility(View.GONE);
                            llfavoritered.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            llfavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llfavorite.setVisibility(View.GONE);
                    llfavoritered.setVisibility(View.VISIBLE);
                    mData.child("follow").child(uid).child(id).setValue(id);
                }
            });
            llfavoritered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llfavoritered.setVisibility(View.GONE);
                    llfavorite.setVisibility(View.VISIBLE);
                    mData.child("follow").child(uid).child(id).removeValue();
                }
            });
        }
    }
}