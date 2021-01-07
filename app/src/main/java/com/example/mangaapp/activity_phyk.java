package com.example.mangaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;

public class activity_phyk extends AppCompatActivity {

    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    private TextView name, email, content,phyk;
    private EditText edtname, edtemail, edtcontent;
    private Button btn, btnquaylai;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phyk);
        name =findViewById(R.id.tvname);
        email = findViewById(R.id.tvemail);
        content = findViewById(R.id.tvcontent);
        edtcontent = findViewById(R.id.phyk_content);
        edtemail = findViewById(R.id.phyk_email);
        edtname = findViewById(R.id.phyk_name);
        btn = findViewById(R.id.btn_phyk);
        btnquaylai = findViewById(R.id.btn_quaylai);
        phyk =findViewById(R.id.tvphyk);
        back =findViewById(R.id.img_phyk_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtname.getText().toString().isEmpty()){
                    Toast.makeText(activity_phyk.this,"Hãy nhập tên",Toast.LENGTH_SHORT).show();

                }else {
                    if (edtemail.getText().toString().isEmpty()){
                        Toast.makeText(activity_phyk.this,"Hãy nhập email",Toast.LENGTH_SHORT).show();

                    }else {
                        if (edtcontent.getText().toString().isEmpty()){
                            Toast.makeText(activity_phyk.this,"Hãy viết ý kiến phản hồi",Toast.LENGTH_SHORT).show();

                        }else {

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("name", edtname.getText().toString());
                            hashMap.put("content",edtcontent.getText().toString());
                            hashMap.put("email",edtemail.getText().toString());
                            mData.child("phanhoi").push().setValue(hashMap);

                            name.setVisibility(View.GONE);
                            email.setVisibility(View.GONE);
                            content.setVisibility(View.GONE);
                            edtname.setVisibility(View.GONE);
                            edtcontent.setVisibility(View.GONE);
                            edtemail.setVisibility(View.GONE);
                            btn.setVisibility(View.GONE);

                            phyk.setText("Cảm ơn ý kiến của bạn "+ edtname.getText().toString()+ " !");
                            phyk.setVisibility(View.VISIBLE);
                            btnquaylai.setVisibility(View.VISIBLE);
                        }
                    }
                }


            }
        });
        btnquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtcontent.setText("");
                edtname.setText("");
                edtemail.setText("");
                name.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
                content.setVisibility(View.VISIBLE);
                edtname.setVisibility(View.VISIBLE);
                edtcontent.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
                btn.setVisibility(View.VISIBLE);

                phyk.setVisibility(View.GONE);
                btnquaylai.setVisibility(View.GONE);
            }
        });
    }
}