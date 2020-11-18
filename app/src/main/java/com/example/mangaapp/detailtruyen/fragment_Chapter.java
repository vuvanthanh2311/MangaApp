package com.example.mangaapp.detailtruyen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mangaapp.R;

public class fragment_Chapter extends Fragment {
    String id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_chapter,container,false);
        if (id !=null) {
            Toast.makeText(view.getContext(), id +"chapter", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
    public void getdata(String id){
        this.id =id;
    }
}
