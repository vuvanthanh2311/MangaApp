package com.example.mangaapp.detailtruyen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaapp.R;
import com.example.mangaapp.RcvchapterAdapter;
import com.example.mangaapp.RcvhangngayAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class fragment_Chapter extends Fragment {
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://manga-bead7.appspot.com");


    ListView listView;
    String idtruyen;
    ArrayList<String> list, listchap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.fragment_chapter,container,false);
        listView = view.findViewById(R.id.lv_chapter);
        list = new ArrayList<>();
        listchap = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_list_item_1,listchap);
        listView.setAdapter(adapter);
        if (idtruyen != null){
            StorageReference listRef = storage.getReference().child(idtruyen);

            listRef.listAll()
                    .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                        @Override
                        public void onSuccess(ListResult listResult) {
                            for (StorageReference prefix : listResult.getPrefixes()) {
                                list.add(prefix.toString());

                            }
                            if (list!=null) {

                                for (int i=0; i<list.size();i++){
                                    int chap = i+1;
                                    listchap.add("Chapter "+ chap);

                                }
                                adapter.notifyDataSetChanged();
                            }
                            final String size = String.valueOf(listchap.size());

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String chap = String.valueOf(position+1);
                                    Intent intent = new Intent(view.getContext(),ChapterActivity.class);
                                    intent.putExtra("chap",chap);
                                    intent.putExtra("id",idtruyen);
                                    intent.putExtra("size",size);
                                    startActivity(intent);
                                }
                            });

                            for (StorageReference item : listResult.getItems()) {

                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "that bai", Toast.LENGTH_SHORT).show();
                        }
                    });
        }



        return view;
    }
    public void getdata(String idtruyen){
        this.idtruyen =idtruyen;
    }


}
