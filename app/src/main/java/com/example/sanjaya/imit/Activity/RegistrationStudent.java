package com.example.sanjaya.imit.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanjaya.imit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationStudent extends AppCompatActivity {

    Spinner spinner;
    String arr[]={"MCA","MBA","M_Tech"};
    String selectBranch;
    EditText name_a,reg_a,year_a,pass_a,phn_reg_edittext;
    String name_,reg_,year_,pass_,phn_str;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_student);

        name_a=findViewById(R.id.name);
        reg_a=findViewById(R.id.regNo);
        year_a=findViewById(R.id.year);
        pass_a=findViewById(R.id.passReg);
        phn_reg_edittext=findViewById(R.id.phnReg);
        requestQueue=Volley.newRequestQueue(getApplicationContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("Read Below Notice Before Register");

        branchSpinner();

        }

    private void branchSpinner() {

        List<String> daList = new ArrayList<>(Arrays.asList(arr));
        spinner=findViewById(R.id.spinner);

        // spinner.setOnItemSelectedListener(this);

        ArrayAdapter adapter=new ArrayAdapter(this,R.layout.arry_spinner_view,daList){

            @Override

            public boolean isEnabled(int position) {

                if (position == 0) {
                    return true;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = super.getDropDownView(position, convertView, parent);
                TextView text = (TextView) view;
                if (position == 0) {
                    text.setTextColor(Color.BLACK);
                } else {
                    text.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(R.layout.arry_spinner_view);

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(selectBranch));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectBranch= (String) parent.getItemAtPosition(position);
                Toast.makeText(RegistrationStudent.this, "Branch selected :"+selectBranch, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void register(View view) {

        Toast.makeText(this, "Uploading...", Toast.LENGTH_SHORT).show();

        name_=name_a.getText().toString();
        reg_=reg_a.getText().toString();
        year_=year_a.getText().toString();
        pass_=pass_a.getText().toString();
        phn_str=phn_reg_edittext.getText().toString();
        StringRequest request = null;


        if(name_.isEmpty()){
            Toast.makeText(this, "Enter your Name", Toast.LENGTH_SHORT).show();
        }else if(reg_.isEmpty()){
            Toast.makeText(this, "Enter your Registration number", Toast.LENGTH_SHORT).show();
        }
        else if(!(reg_.length()==10))
        {
            Toast.makeText(this, "Registration no should be 10 digit", Toast.LENGTH_SHORT).show();
        }else if(year_.isEmpty()){
            Toast.makeText(this, "Enter Admission Year", Toast.LENGTH_SHORT).show();
        }
        else if(!(year_.length()==4)){
            Toast.makeText(this, "Year must be 4 digit", Toast.LENGTH_SHORT).show();
        }else if(pass_.isEmpty()){
            Toast.makeText(this, "Enter the Password  ", Toast.LENGTH_SHORT).show();
        }else if(pass_.length()<6){
            Toast.makeText(this, "Password Must be 6 digit above ", Toast.LENGTH_SHORT).show();
        }else if(selectBranch.isEmpty()){
            Toast.makeText(this, "Enter select branch", Toast.LENGTH_SHORT).show();
        }else if(phn_str.isEmpty()){
            Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show();
        }else if(phn_str.length()==10){
            Toast.makeText(this, "Enter your phone number correctly without +91 phone", Toast.LENGTH_SHORT).show();
        }else

        //if(!(name_.isEmpty() || reg_.isEmpty() || year_.isEmpty() || pass_.isEmpty()|| selectBranch.isEmpty() || phn_str.length()==9))
        {


            String URL_str="https://sanjayajena.000webhostapp.com/master.php?flag=register";
            //String aUr="sanjayajena.000webhostapp.com/master.php?flag=register&name="+name_+"&department="+selectBranch+"&year="+year_+"&roll="+reg_+"&pass="+pass_;
            request=new StringRequest(Request.Method.POST, URL_str, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    String status = null;
                    String message=null;

                    if(!(response.isEmpty())) {
                        try {
                            JSONObject object = new JSONObject(response);
                            status = object.getString("status");
                            message = object.getString("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(status.equals("success"))
                        {

                            name_a.setText("");
                            reg_a.setText("");
                            year_a.setText("");
                            pass_a.setText("");
                            phn_reg_edittext.setText("");
                            Toast.makeText(RegistrationStudent.this, message, Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(getApplicationContext(),Login_activity.class);
                            finish();
                            startActivity(intent);

                        }else {
                            Toast.makeText(RegistrationStudent.this, "Already Exist :"+message, Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(RegistrationStudent.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(RegistrationStudent.this, "Check your Internet Connection"+error, Toast.LENGTH_SHORT).show();

                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> param=new HashMap<String, String>();
                    param.put("name",name_);
                    param.put("department",selectBranch);
                    param.put("year",year_);
                    param.put("roll",reg_);
                    param.put("pass",pass_);
                    param.put("phone",phn_str);
                    return param;
                }
            };

            requestQueue.add(request);
        }
//        else {
//            Toast.makeText(this, "Enter Your All Data", Toast.LENGTH_SHORT).show();
//        }


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
