package com.example.sanjaya.imit.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sanjaya.imit.CardWizardActivity;
import com.example.sanjaya.imit.PrefernceManager;
import com.example.sanjaya.imit.R;

public class splashscreen extends AppCompatActivity {

    PrefernceManager prefernceManager;
    private static final int ACCESS_FINE_LOCATION_RESPONSE =112;
    ImageView logo;
    Animation fadein;
    final int PERMISSION_REQUEST_CODE=110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        logo=findViewById(R.id.logo);

        fadein=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.moveup);
        logo.setVisibility(View.VISIBLE);
        logo.startAnimation(fadein);
        //requestPermission();


//        try {
//            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                boolean hasPermission = (ContextCompat.checkSelfPermission(getBaseContext(),
//                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
//                        ==PackageManager.PERMISSION_GRANTED);
//                if (!hasPermission) {
//                    ActivityCompat.requestPermissions(splashscreen.this,
//                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
//                            }, ACCESS_FINE_LOCATION_RESPONSE
//                    );
//
//
//
//                }else {

//                    if(checkNetworkConnection()) {
//
//                        prefernceManager = new PrefernceManager(splashscreen.this);
//
//
//                        new Handler().postDelayed(new Runnable() {
//
//                            @Override
//                            public void run() {
////                Intent i = new Intent(splashscreen.this, Login_activity.class);
////                startActivity(i);
////                finish();
//                                if (prefernceManager.isLoggedIn()) {
//                                    Intent i = new Intent(splashscreen.this, MainActivity.class);
//
//                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                                    // Add new Flag to start new Activity
//                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(i);
//                                    finish();
//                                } else {
//                                    Intent i = new Intent(splashscreen.this, Login_activity.class);
//                                    finish();
//                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(i);
//                                }
//
//                            }
//                        }, 2000);
//
//                    }
//                    else {
//                        Intent intent=new Intent(splashscreen.this,NoInternet.class);
//
//                        finishAffinity();
//                        finish();
//                        startActivity(intent);
//
//                    }

//                }
//            }
//
//        }catch (SecurityException e) {
//            Toast.makeText(this, "Wait:" + e, Toast.LENGTH_SHORT).show();
//        }
//

        //First time execute
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here

            Intent intent=new Intent(splashscreen.this,CardWizardActivity.class);
            startActivity(intent);
            //requestPermission();
            //Toast.makeText(this, "1st time when install", Toast.LENGTH_SHORT).show();
            // mark first time has ran.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }else{
//            if (checkPermission()) {

                intentDataMethod();

//            } else {
//                requestPermission();
//            }
        }
    }

    private boolean checkPermission() {

            int res = 0;
            String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            for (String perms : permissions) {
                res = checkCallingOrSelfPermission(perms);
                if (!(res == PackageManager.PERMISSION_GRANTED)) {
                    return false;
                }
            }
            return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                for (int res : grantResults) {
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                allowed = false;
                break;
        }
        if (allowed) {
            Toast.makeText(getApplicationContext(), "Location permission granted", Toast.LENGTH_SHORT).show();

            intentDataMethod();


        } else {
            Toast.makeText(getApplicationContext(), "permission denied", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                       /* if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permission",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }*/

                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showMessageOKCancel("You need to allow access permission",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermission();
                                    }
                                }
                            });
                }
            }
        }
    }

    private void requestPermission() {
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) splashscreen.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener oKListner) {
        new AlertDialog.Builder(splashscreen.this)
                .setMessage(message)
                .setPositiveButton("OK", oKListner)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void intentDataMethod(){
        if(checkNetworkConnection()) {

            prefernceManager = new PrefernceManager(splashscreen.this);


            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
//                Intent i = new Intent(splashscreen.this, Login_activity.class);
//                startActivity(i);
//                finish();
                    if (prefernceManager.isLoggedIn()) {
                        Intent i = new Intent(splashscreen.this, MainActivity.class);

                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(splashscreen.this, Login_activity.class);
                        finish();
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }

                }
            }, 2000);

        }
        else {
            Intent intent=new Intent(splashscreen.this,NoInternet.class);

            finishAffinity();
            finish();
            startActivity(intent);

        }
    }
}
