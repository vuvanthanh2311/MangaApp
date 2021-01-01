package com.example.mangaapp.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;

    DatabaseReference mData ;
    EditText Name ;
    EditText Email ;
    EditText pasword;
    Button btRegister;
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Anhxa();
        mAuth = FirebaseAuth.getInstance();
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =Name.getText().toString();
                String email =Email.getText().toString();
                String password = pasword.getText().toString();
                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() ){
                    Register(name,email,password);
                }else
                    Toast.makeText(Register.this, "Value Failed", Toast.LENGTH_SHORT).show();

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });


    }
    private void Anhxa(){
        Name =(EditText)findViewById(R.id.edtName);
        Email =(EditText)findViewById(R.id.edtEmail);
        pasword =(EditText)findViewById(R.id.edtPassword);
        btRegister =(Button)findViewById(R.id.bt_register);
        login =(TextView)findViewById(R.id.tvLogin);
    }


    private void Register(final String name, String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // lấy id trong đăng kí user
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userID = firebaseUser.getUid();
                            //tạo 1 child mới tên UserManager và 1 child tên là giá trị của userID
                            mData = FirebaseDatabase.getInstance().getReference().child("user").child(userID);
                            HashMap<String, Object> hashMap =new HashMap<>();
                            hashMap.put("User", userID);
                            hashMap.put("Name",name);

                                mData.setValue(hashMap, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if (error == null) {
                                            Toast.makeText(Register.this, "Register Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent =new Intent(Register.this, Login.class);
                                        startActivity(intent);
                                        finish();
                                        } else
                                            Toast.makeText(Register.this, "Register Failed", Toast.LENGTH_SHORT).show();

                                    }
                                });
//                            Intent intent =new Intent(Register.this,Login.class);
//                            startActivity(intent);

                        } else {
                            Toast.makeText(Register.this,"Register Failed",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }



}