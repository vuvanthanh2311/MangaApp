package com.example.mangaapp.person;


import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.example.mangaapp.R;

public class activity_gtct extends AppCompatActivity {

    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gtct);
        back = findViewById(R.id.img_gtct_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}