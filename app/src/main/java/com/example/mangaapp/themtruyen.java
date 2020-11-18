package com.example.mangaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;

public class themtruyen extends AppCompatActivity {
    // Get a non-default Storage bucket
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://manga-bead7.appspot.com");
    StorageReference storageRef = storage.getReference();


    DatabaseReference mData;
    DatabaseReference cData;
    ImageView imghinh;
    EditText tacgia, tentruyen, id;
    Button btnsave;
    CheckBox hd, bh, hhuoc,kd, mh, snl, th, tt, ty, vt, xk, hhuyen, dm, df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themtruyen);
        Anhxa();
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();

                final StorageReference mountainsRef = storageRef.child("Image"+calendar.getTimeInMillis()+".png");
                // Get the data from an ImageView as bytes
                imghinh.setDrawingCacheEnabled(true);
                imghinh.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imghinh.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(themtruyen.this,"save img Failed",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(themtruyen.this," save img Successful" ,Toast.LENGTH_SHORT).show();
                    }
                });

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return mountainsRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            assert downloadUri != null;
                            mData = FirebaseDatabase.getInstance().getReference().child("manga").child(id.getText().toString());
                            HashMap<String, Object> hashMap =new HashMap<>();
                            hashMap.put("tentruyen",tentruyen.getText().toString());
                            hashMap.put("linkhinh", String.valueOf(downloadUri));
                            hashMap.put("tentacgia",tacgia.getText().toString());
                            hashMap.put("id", id.getText().toString());
                            mData.setValue(hashMap, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                    if (error == null){
                                        Toast.makeText(themtruyen.this,"save data Successful" ,Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(themtruyen.this,"save data Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            if (hd.isChecked()){
                                mData.child("category").child("Hành động").setValue("Hành động");
                            }
                            if (hhuoc.isChecked()){
                                mData.child("category").child("Hài hước").setValue("Hài hước");
                            }
                            if (bh.isChecked()){
                                mData.child("category").child("Bách hợp").setValue("Bách hợp");
                            }
                            if (kd.isChecked()){
                                mData.child("category").child("Kinh dị").setValue("Kinh dị");
                            }
                            if (mh.isChecked()){
                                mData.child("category").child("Mạo hiểm").setValue("Mạo hiểm");
                            }
                            if (snl.isChecked()){
                                mData.child("category").child("Siêu năng lực").setValue("Siêu năng lực");
                            }
                            if (th.isChecked()){
                                mData.child("category").child("Trường học").setValue("Trường học");
                            }
                            if (ty.isChecked()){
                                mData.child("category").child("Tình yêu").setValue("Tình yêu");
                            }
                            if (tt.isChecked()){
                                mData.child("category").child("Tổng tài").setValue("Tổng tài");
                            }
                            if (vt.isChecked()){
                                mData.child("category").child("Viễn tưởng").setValue("Viễn tưởng");
                            }
                            if (xk.isChecked()){
                                mData.child("category").child("Xuyên không").setValue("Xuyên không");
                            }
                            if (hhuyen.isChecked()){
                                mData.child("category").child("Huyền huyễn").setValue("Huyền huyễn");
                            }
                            if (dm.isChecked()){
                                mData.child("category").child("Đam mỹ").setValue("Đam mỹ");
                            }
                            if (df.isChecked()){
                                mData.child("category").child("Đã full").setValue("Đã full");
                            }
                            cData = FirebaseDatabase.getInstance().getReference().child("category");
                            if (hd.isChecked()){
                                cData.child("Hành động").child("id"+ calendar.getTimeInMillis()).setValue(id.getText().toString());
                            }
                            if (hhuoc.isChecked()){
                                cData.child("Hài hước").child("id"+ calendar.getTimeInMillis()).setValue(id.getText().toString());
                            }
                            if (bh.isChecked()){
                                cData.child("Bách hợp").child("id"+ calendar.getTimeInMillis()).setValue(id.getText().toString());
                            }
                            if (kd.isChecked()){
                                cData.child("Kinh dị").child("id"+ calendar.getTimeInMillis()).setValue(id.getText().toString());
                            }
                            if (mh.isChecked()){
                                cData.child("Mạo hiểm").child("id"+ calendar.getTimeInMillis()).setValue(id.getText().toString());
                            }
                            if (snl.isChecked()){
                                cData.child("Siêu năng lực").child("id"+ calendar.getTimeInMillis()).setValue(id.getText().toString());
                            }
                            if (th.isChecked()){
                                cData.child("Trường học").child("id"+ calendar.getTimeInMillis()).setValue(id.getText().toString());
                            }
                            if (ty.isChecked()){
                                cData.child("Tình yêu").child("id"+ calendar.getTimeInMillis()).setValue(id.getText().toString());
                            }
                            if (tt.isChecked()){
                                cData.child("Tổng tài").child("id"+ calendar.getTimeInMillis()).setValue(id.getText().toString());
                            }
                            if (vt.isChecked()){
                                cData.child("Viễn tưởng").child("id"+ calendar.getTimeInMillis()).setValue(id.getText().toString());
                            }
                            if (xk.isChecked()){
                                cData.child("Xuyên không").child("id"+ calendar.getTimeInMillis()).setValue(id.getText().toString());
                            }
                            if (hhuyen.isChecked()){
                                cData.child("Huyền huyễn").child("id"+ calendar.getTimeInMillis()).setValue(id.getText().toString());
                            }
                            if (dm.isChecked()){
                                cData.child("Đam mỹ").child("id"+ calendar.getTimeInMillis()).setValue(id.getText().toString());
                            }
                            if (df.isChecked()){
                                cData.child("Đã full").child("id"+ calendar.getTimeInMillis()).setValue(id.getText().toString());
                            }


                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });
            }
        });
    }

    private void Anhxa(){
        btnsave= (Button)findViewById(R.id.btnSave);
        imghinh = (ImageView)findViewById(R.id.img_truyen);
        tacgia = (EditText)findViewById(R.id.edt_tacgia);
        tentruyen = (EditText)findViewById(R.id.edtnametruyen);
        id = (EditText)findViewById(R.id.edt_id);

        hd = (CheckBox)findViewById(R.id.hanhdong);
        bh = (CheckBox)findViewById(R.id.bachhop);
        hhuoc = (CheckBox)findViewById(R.id.haihuoc);
        kd = (CheckBox)findViewById(R.id.kinhdi);
        mh = (CheckBox)findViewById(R.id.maohiem);
        snl = (CheckBox)findViewById(R.id.sieunangluc);
        th = (CheckBox)findViewById(R.id.truonghoc);
        tt = (CheckBox)findViewById(R.id.tongtai);
        ty = (CheckBox)findViewById(R.id.tinhyeu);
        vt = (CheckBox)findViewById(R.id.vientuong);
        xk = (CheckBox)findViewById(R.id.xuyenkhong);
        hhuyen = (CheckBox)findViewById(R.id.huyenhuyen);
        dm = (CheckBox)findViewById(R.id.dammy);
        df = (CheckBox)findViewById(R.id.dafull);

    }
}