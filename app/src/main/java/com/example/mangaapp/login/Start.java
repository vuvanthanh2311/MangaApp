package com.example.mangaapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mangaapp.R;

public class Start extends AppCompatActivity {

    Button btLogin, btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Anhxa();
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Start.this, Login.class);
                startActivity(intent);
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Start.this, Register.class);
                startActivity(intent);
            }
        });
    }
    public void Anhxa(){
        btLogin =(Button)findViewById(R.id.btlogin);
        btRegister =(Button)findViewById(R.id.btregister);
    }
}