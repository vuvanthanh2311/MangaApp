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
import android.util.Log;
import android.widget.Toast;

import com.example.mangaapp.R;
import com.example.mangaapp.RcvchapterAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ChapterActivity extends AppCompatActivity {
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://manga-bead7.appspot.com");

    RecyclerView recyclerView;
    RcvchapterAdapter rcvchapterAdapter;
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        Intent intent = getIntent();
        String chap =intent.getStringExtra("chap");
        String id = intent.getStringExtra("id");

        recyclerView = findViewById(R.id.rcv_chapter);

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