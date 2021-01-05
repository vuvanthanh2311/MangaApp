package com.example.mangaapp.detailtruyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangaapp.comment.Comment;
import com.example.mangaapp.R;
import com.example.mangaapp.RcvchapterAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ChapterActivity extends AppCompatActivity {
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://manga-bead7.appspot.com");

    RecyclerView recyclerView;
    RcvchapterAdapter rcvchapterAdapter;
    ArrayList<String> list;
    ImageView next,before,comment,back;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        final Intent intent = getIntent();
        final String chap =intent.getStringExtra("chap");
        final String id = intent.getStringExtra("id");
        final String size = intent.getStringExtra("size");

        recyclerView = findViewById(R.id.rcv_chapter);
        next = findViewById(R.id.img_chap_next);
        before = findViewById(R.id.img_chap_before);
        comment = findViewById(R.id.img_comment);
        title = findViewById(R.id.Tv_chapter);
        back = findViewById(R.id.img_chap_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ChapterActivity.this, Comment.class);
                intent2.putExtra("id",id);
                intent2.putExtra("chap",chap);
                startActivity(intent2);
            }
        });

        title.setText("Chapter "+chap);

        int Size = 0;

        for (int i= 0; i<1000;i++){
            String k = String.valueOf(i);
            if (k.equals(size)){
                Size = i;
            }
        }

        int Chap = 0;

        for (int i= 0; i<1000;i++){
            String k = String.valueOf(i);
            if (k.equals(chap)){
                 Chap = i;
            }
        }

        if (Chap >1){
            final int cb = Chap -1;
            before.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String chapter = String.valueOf(cb);
                    Intent intent1 = new Intent(ChapterActivity.this, ChapterActivity.class);
                    intent1.putExtra("chap", chapter);
                    intent1.putExtra("id", id);
                    intent1.putExtra("size", size);
                    startActivity(intent1);
                    finish();
                }
            });
        }

        if (Chap < Size){
            final int cn = Chap +1;
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String chapter = String.valueOf(cn);
                    Intent intent1 = new Intent(ChapterActivity.this, ChapterActivity.class);
                    intent1.putExtra("chap", chapter);
                    intent1.putExtra("id", id);
                    intent1.putExtra("size", size);
                    startActivity(intent1);
                    finish();
                }
            });
        }


        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(ChapterActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        rcvchapterAdapter = new RcvchapterAdapter(ChapterActivity.this,list);
        recyclerView.setAdapter(rcvchapterAdapter);



        if (id !=null) {

            final StorageReference listRef = storage.getReference().child(id +"/chapter" +chap);

            listRef.listAll()
                    .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                        @Override
                        public void onSuccess(ListResult listResult) {
                            for (StorageReference prefix : listResult.getPrefixes()) {

                            }

                            for (StorageReference item : listResult.getItems()) {
                                item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String t = uri.toString();
                                        list.add(t);
                                        rcvchapterAdapter.notifyDataSetChanged();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Toast.makeText(ChapterActivity.this,"lay anh that bai",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ChapterActivity.this,"lang nghe that bai",Toast.LENGTH_SHORT).show();
                        }
                    });



        }
    }
}