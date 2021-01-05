package com.example.mangaapp.comment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Comment extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    final DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    EditText comment;
    TextView title;
    ImageView back, send;
    private commentAdapter RcvAdapter;
    private RecyclerView recyclerView;
    private ArrayList<commentclass> listcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        final Intent intent = getIntent();
        final String chap = intent.getStringExtra("chap");
        final String id = intent.getStringExtra("id");

        Anhxa();
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        GetData(id, chap);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            mData.child("user").child(uid).child("Name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    final String u = snapshot.getValue().toString();
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("chapter", chap);
                            hashMap.put("content",comment.getText().toString());
                            hashMap.put("user",u);
                            hashMap.put("time",new Date());
                            mData.child("Comment").child(id).child(chap).push().setValue(hashMap);
                            comment.setText("");
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        title.setText("Chapter " + chap);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void Anhxa() {
        comment = findViewById(R.id.edt_comment);
        title = findViewById(R.id.tv_cm_title);
        back = findViewById(R.id.img_cm_back);
        send = findViewById(R.id.img_cm_send);
        recyclerView = findViewById(R.id.rcv_chapter_comment);

    }
    private void GetData(String id , String chapter){
        recyclerView.setLayoutManager(new GridLayoutManager(Comment.this,1));
        listcm = new ArrayList<>();

        RcvAdapter = new commentAdapter(Comment.this, listcm);
        recyclerView.setAdapter(RcvAdapter);



        mData.child("Comment").child(id).child(chapter).addChildEventListener(new ChildEventListener() {
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