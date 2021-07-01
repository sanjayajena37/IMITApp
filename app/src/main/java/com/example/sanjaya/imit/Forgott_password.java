package com.example.sanjaya.imit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanjaya.imit.Activity.Login_activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Forgott_password extends AppCompatActivity {

    private EditText regupdate,phnupdate,passupdate;
    private String reg_str,phn_str,pass_str;
    RequestQueue requestQueue;
    Button update;
    final String url="http://sanjayajena.000webhostapp.com/master.php?flag=forgot";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgott_password);

        update=findViewById(R.id.updateButton);
        regupdate=findViewById(R.id.regupdate);
        phnupdate=findViewById(R.id.phnupdate);
        passupdate=findViewById(R.id.passupdate);
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Forgott_password.this, "Wait For Verify", Toast.LENGTH_SHORT).show();
                reg_str=regupdate.getText().toString();
                phn_str=phnupdate.getText().toString();
                pass_str=passupdate.getText().toString();

                if(reg_str.isEmpty() || phn_str.isEmpty() || pass_str.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter Both Field", Toast.LENGTH_SHORT).show();
                }else {

                    StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(!(response.isEmpty())) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    String status = object.getString("status");
                                    String message = object.getString("message");

                                    if (status.equals("success")) {

                                        regupdate.setText("");
                                        phnupdate.setText("");
                                        passupdate.setText("");

                                        Intent login=new Intent(getApplicationContext(),Login_activity.class);
                                        startActivity(login);
                                        Toast.makeText(Forgott_password.this, message, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Forgott_password.this, message, Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    Toast.makeText(Forgott_password.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(Forgott_password.this, "Check Your Internet:Respone Null", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(Forgott_password.this,error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    })

                        {
                            @Override
                            protected Map<String, String> getParams () throws AuthFailureError {

                            Map<String, String> param = new HashMap<String, String>();
                            param.put("rollno", reg_str);
                            param.put("phone", phn_str);
                            param.put("chpass", pass_str);

                            return param;
                        }

                    };

                    requestQueue.add(request);

                }


            }
        });

    }

}
