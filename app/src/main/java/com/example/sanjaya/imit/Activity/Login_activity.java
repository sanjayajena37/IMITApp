package com.example.sanjaya.imit.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanjaya.imit.Forgott_password;
import com.example.sanjaya.imit.PrefernceManager;
import com.example.sanjaya.imit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_activity extends AppCompatActivity {

    EditText reg_edittext,pass_edittext;
    //String reg_str,pass_str;
    Button login_button;
    TextInputLayout tx_roll,tx_pass;
    ImageView imgLongPress;

    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activit_studenty);

        reg_edittext=findViewById(R.id.regStudent);
        pass_edittext=findViewById(R.id.passStudent);

        login_button=findViewById(R.id.loginButton);
        requestQueue=Volley.newRequestQueue(this);
        tx_roll=findViewById(R.id.tx_roll);
        tx_pass=findViewById(R.id.tx_pass);
        imgLongPress=findViewById(R.id.imgLongPress);

        imgLongPress.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Intent intent=new Intent(getApplicationContext(),UploadSignIn.class);
                startActivity(intent);
                return true;
            }
        });


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String reg_str = reg_edittext.getText().toString();
                String pass_str = pass_edittext.getText().toString();
                //String URL="https://sanjayajena.000webhostapp.com/master.php?flag=login&rollno="+reg_str+"&password="+pass_str;


                if (reg_str.isEmpty()) {
                    tx_roll.setError("Enter Valid Reg Number");
                    tx_roll.setHintEnabled(false);
                    //Toast.makeText(Login_activity.this, "Enter Registrartion Number", Toast.LENGTH_SHORT).show();
                } else if (!(reg_str.length() == 10)) {
                    Toast.makeText(Login_activity.this, "Registration Number Must be 10 digit", Toast.LENGTH_SHORT).show();
                }else if (pass_str.isEmpty()) {
                    Toast.makeText(Login_activity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else {

                    String URL_Login = "https://sanjayajena.000webhostapp.com/master.php?flag=login";

                    Toast.makeText(Login_activity.this, "Wait for verification", Toast.LENGTH_SHORT).show();
                    StringRequest request = new StringRequest(Request.Method.POST, URL_Login, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (!(response.isEmpty())) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    String status = object.getString("status");
                                    String message = object.getString("message");
                                    String roll = object.getString("rollno");
                                    String year = object.getString("year");
                                    String department = object.getString("department");
                                    String name = object.getString("name");
                                    String phoneno = object.getString("phone");


                                    if (status.equals("success")) {
                                        Intent in = new Intent(getApplicationContext(), MainActivity.class);
                                        PrefernceManager prefernceManager = new PrefernceManager(getApplicationContext());
                                        prefernceManager.setLogin(true); //set login false in notice menu in main activity
                                        prefernceManager.putString("reg", roll);
                                        prefernceManager.putString("department", department);
                                        prefernceManager.putString("year", year);
                                        prefernceManager.putString("name", name);
                                        prefernceManager.putString("phonedata", phoneno);

                                        //Toast.makeText(Login_activity.this, "Status"+status+" message "+message+" roll "+roll+" year "+year+" department "+department, Toast.LENGTH_LONG).show();
                                        Toast.makeText(Login_activity.this, message, Toast.LENGTH_SHORT).show();

                                        finishAffinity();
                                        startActivity(in);
                                    } else {
                                        Toast.makeText(Login_activity.this, message, Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {

                                    Toast.makeText(Login_activity.this, "Please Input Valid User name and Password", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(Login_activity.this, "Check Your Internet", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            if(error.equals("com.android.volley.TimeoutError")) {
                                Toast.makeText(Login_activity.this, "Server is Slow ! Please Try After Some time", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Login_activity.this, "Error is : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    })

                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            String reg_str = reg_edittext.getText().toString();
                            String pass_str = pass_edittext.getText().toString();

                            Map<String, String> param = new HashMap<String, String>();
                            param.put("rollno", reg_str);
                            param.put("password", pass_str);

                            return param;
                        }

                    };

                    requestQueue.add(request);
                }
            }
        });

    }


    public void signClick(View view) {

        Intent intent=new Intent(this,RegistrationStudent.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {


            AlertDialog.Builder builder = new AlertDialog.Builder(Login_activity.this);
            builder.setMessage("Do you want to close this app ?");
            builder.setIcon(R.drawable.logovector);
            builder.setTitle("IMIT");
            String positiveText = "OK";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // positive button1 logic
                            finish();
                            System.exit(0);

//                       xo.onBackPressed();

                        }
                    });

            String negativeText = getString(android.R.string.cancel);

            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // negative button1 logic

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

    }

    public void forgotPass(View view) {

        Intent intenta=new Intent(getApplicationContext(),Forgott_password.class);
        startActivity(intenta);

    }
}
