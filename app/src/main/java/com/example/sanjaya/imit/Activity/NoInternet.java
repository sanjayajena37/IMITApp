package com.example.sanjaya.imit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.sanjaya.imit.Activity.splashscreen;
import com.example.sanjaya.imit.R;

public class NoInternet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
    }

    public void tryAgain(View view) {

        Intent tryon=new Intent(getApplicationContext(),splashscreen.class);
        finish();
        finishAffinity();
        startActivity(tryon);

    }
}
