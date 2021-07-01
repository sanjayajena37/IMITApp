package com.example.sanjaya.imit.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanjaya.imit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Syllabus extends AppCompatActivity {


    TextView mcaT,mbaT,mba_partT,mtechT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        ActionBar ac=getSupportActionBar();
        ac.setDisplayHomeAsUpEnabled(true);


        mcaT=findViewById(R.id.mcaX);
        mbaT=findViewById(R.id.mbaX);
        mba_partT=findViewById(R.id.mba_parttimeX);
        mtechT=findViewById(R.id.mtechX);

        final DatabaseReference syllabus= FirebaseDatabase.getInstance().getReference().child("syllabus");



        mcaT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Syllabus.this, "Please Wait Downloading...", Toast.LENGTH_SHORT).show();
                syllabus.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String mca=dataSnapshot.child("mca").getValue().toString();
                        Intent mca_pdf=new Intent(Intent.ACTION_VIEW);
                        mca_pdf.setData(Uri.parse(mca));
                        startActivity(mca_pdf);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });


        mbaT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Syllabus.this, "Please Wait Downloading...", Toast.LENGTH_SHORT).show();


                syllabus.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String mca=dataSnapshot.child("mba").getValue().toString();
                        Intent mca_pdf=new Intent(Intent.ACTION_VIEW);
                        mca_pdf.setData(Uri.parse(mca));
                        startActivity(mca_pdf);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });

        mba_partT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Syllabus.this, "Please Wait Downloading...", Toast.LENGTH_SHORT).show();

                syllabus.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String mca=dataSnapshot.child("mba_part").getValue().toString();
                        Intent mca_pdf=new Intent(Intent.ACTION_VIEW);
                        mca_pdf.setData(Uri.parse(mca));
                        startActivity(mca_pdf);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });

        mtechT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Syllabus.this, "Please Wait Downloading...", Toast.LENGTH_SHORT).show();
                syllabus.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String mca=dataSnapshot.child("m_tech").getValue().toString();
                        Intent mca_pdf=new Intent(Intent.ACTION_VIEW);
                        mca_pdf.setData(Uri.parse(mca));
                        startActivity(mca_pdf);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



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
