package com.example.sanjaya.imit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TimeTable extends AppCompatActivity {
    ImageView mca_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        ActionBar a=getSupportActionBar();
        a.setDisplayHomeAsUpEnabled(true);

       final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Until Loading");
        progressDialog.show();

        mca_time=findViewById(R.id.mca_timetable);

        DatabaseReference db2= FirebaseDatabase.getInstance().getReference().child("Time_table").child("mca");
        db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String mca_pic=dataSnapshot.getValue().toString();
                Picasso.with(getApplicationContext()).load(mca_pic).into(mca_time);
                progressDialog.dismiss();
                Toast.makeText(TimeTable.this, "Fetching...", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }

//    public void onBackPressed() {
//        // super.onBackPressed();
//
//        final Activity xo=new Activity();
//
//        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getApplicationContext());
//        builder.setMessage("Do you want to close this app ?");
//        String positiveText = "OK";
//        builder.setPositiveButton(positiveText,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // positive button1 logic
//                        finish();
//                        System.exit(0);
//
////                       xo.onBackPressed();
//
//                    }
//                });
//
//        String negativeText = getString(android.R.string.cancel);
//
//        builder.setNegativeButton(negativeText,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // negative button1 logic
//
//                    }
//                });
//        android.app.AlertDialog dialog = builder.create();
//        dialog.show();
//    }
}
