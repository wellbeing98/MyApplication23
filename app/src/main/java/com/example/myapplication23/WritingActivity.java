package com.example.myapplication23;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class WritingActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;       //파이어베이스 인증
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabase;


    // 사용할 컴포넌트 선언
    EditText title_et, content_et;
    Button reg_button;
    ImageView mimage;
    // 유저아이디 변수
    String userid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_post);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("GodGong");


        FirebaseAuth mAuth;
// ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

// 컴포넌트 초기화
        mimage = findViewById(R.id.imageView2);
        title_et = findViewById(R.id.title_et);
        content_et = findViewById(R.id.content_et);
        reg_button = findViewById(R.id.reg_button);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child("images");
        if(pathReference == null){
            Toast.makeText(WritingActivity.this, "저장소에 사진이 없습니다.", Toast.LENGTH_SHORT).show();}
        else{
            Toast.makeText(WritingActivity.this, "저장소에 사진이 있습니다.", Toast.LENGTH_SHORT).show();
            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

            StorageReference submitProfile = storageReference.child("images/"+firebaseUser.getUid());


            Glide.with(this /* context */)
                    .load(submitProfile)
                    .into(mimage);
        }
        mimage.setImageResource(R.drawable.ic_launcher_background);

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strTitle = title_et.getText().toString();
                String strContent = content_et.getText().toString();
                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
//               ImageView image = mimage.getDrawable(R.drawable.ic_launcher_background);

                Post post = new Post();


                post.setTitle_et(strTitle);
                post.setContent_et(strContent);


                //setValue : database에 insert (삽입) 행위
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("post").setValue(post);

                // Firebase Auth 진행


            }
        });








    }


}