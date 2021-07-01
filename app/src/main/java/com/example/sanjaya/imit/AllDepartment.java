package com.example.sanjaya.imit;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AllDepartment extends AppCompatActivity {

    Button mca,mba,mba_part,mtech;
    Fragment fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_department);

        ActionBar a=getSupportActionBar();
        a.setDisplayHomeAsUpEnabled(true);
        mca=findViewById(R.id.mca_frag);
        mba=findViewById(R.id.mba_frag);
        mba_part=findViewById(R.id.mbapart_frag);
        mtech=findViewById(R.id.mtech_frag);


    }
}
