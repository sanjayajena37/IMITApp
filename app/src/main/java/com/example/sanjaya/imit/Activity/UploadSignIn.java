package com.example.sanjaya.imit.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanjaya.imit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UploadSignIn extends AppCompatActivity {

    TextView email,pass;
    String mail,password;
    FirebaseAuth mauth;
    Button log;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_sign_in);

        log=findViewById(R.id.login);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        mauth=FirebaseAuth.getInstance();

        email.setText("imit@sanjaya.com");
        pass.setText("imit123");




        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mail=email.getText().toString();
                password=pass.getText().toString();

                if(mail.isEmpty()||password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Fill the both fields", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(UploadSignIn.this, "Please Wait Until Verify..", Toast.LENGTH_LONG).show();
                    mauth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Intent ii=new Intent(UploadSignIn.this,UploadModule.class);
                                startActivity(ii);
                                finishAffinity();

                            }else
                            {
                                Toast.makeText(UploadSignIn.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }



            }
        });




    }

//    public void onBackPressed() {
//        // super.onBackPressed();
//
//
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
