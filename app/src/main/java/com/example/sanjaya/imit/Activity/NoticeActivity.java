package com.example.sanjaya.imit.Activity;


import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.example.sanjaya.imit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class NoticeActivity extends AppCompatActivity {

    FirebaseAuth mauth;
    TextView nameText;
    ImageView pic;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        mauth=FirebaseAuth.getInstance();

        nameText=findViewById(R.id.title);
        pic=findViewById(R.id.upload_img);

        android.support.v7.app.ActionBar ae=getSupportActionBar();
        ae.setDisplayHomeAsUpEnabled(true);


        progress= new ProgressDialog(NoticeActivity.this);
        progress.setMessage("Loading...");
        progress.setTitle("Please Wait");
        progress.show();

        DatabaseReference d= FirebaseDatabase.getInstance().getReference().child("notification_database").child("Data");
        d.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name=dataSnapshot.child("name").getValue().toString();
                String p=dataSnapshot.child("image").getValue().toString();
                nameText.setText(name);

                Picasso.with(getApplicationContext()).load(p).into(pic);
                progress.dismiss();
                Toast.makeText(NoticeActivity.this, "Fetching...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(NoticeActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                progress.dismiss();
            }

        });



    }
}
