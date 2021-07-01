package com.example.sanjaya.imit.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.sanjaya.imit.PrefernceManager;
import com.example.sanjaya.imit.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;
import id.zelory.compressor.Compressor;

public class UploadModule extends AppCompatActivity {


    private FirebaseAuth mauth;
    private EditText title;
    private ImageButton upload;
    private String name;
    private RequestQueue requestQueue;
    //String imgdata;
    //final String url="https://fcm.googleapis.com/fcm/send";

    private EditText title_, topic_, body_;
    private String _title, _topic, _click, _body;
    private LinearLayout notificLay, instantLay;
    private RadioGroup check;
    byte[] mByte;
    private Spinner spinner, spinnerImage;
    private String imgesUpload;
    ArrayList<NameValuePair> nameValuePairs;

    private String arr[] = {"Home", "Notice"};
    private String arIm[] = {"", "imitlogo", "skult"};
    private String selectAction, selectImageAction;
    private RadioGroup radioGroupTopic;
    private RadioButton radioButton, mcaRadio, mbaRadio, m_techRadio;
    private String topic_str="MCA";
    private LinearLayout otherName;
    private EditText otherTopic;
    private RadioGroup radio_grp_allnotice;

    private LinearLayout text_allnotice_linear, img_allnotice_linear;

    private PrefernceManager prefernceManager;
    private Bitmap b;
    private String textUp;
    private String imgUp;
    private String st;
    private EditText title_all_notice;
    private ImageButton all_notice_imagebutton;
    private Button buttonUploadtextData;
    private EditText text_allnotice_edittext;
    private Button buttonNotificationSend;
    private RadioButton otherRadio;
    public String imgLinkFirebase;
    public String NewString, oldStringLink;


    //String imgUpload_in_instant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_module);

        mauth = FirebaseAuth.getInstance();
        upload = findViewById(R.id.upload);
        title = findViewById(R.id.title_upload);
        requestQueue = Volley.newRequestQueue(this);

        title_ = findViewById(R.id.title_ap);
        //topic_=findViewById(R.id.topic);
        body_ = findViewById(R.id.message);
        spinner = findViewById(R.id.spinnerClickAction);
        spinnerImage = findViewById(R.id.spinnerImageAction);

        instantLay = findViewById(R.id.instantNotice);
        notificLay = findViewById(R.id.notificationLay);

        check = findViewById(R.id.radiogrp);
        radioGroupTopic = findViewById(R.id.radiogrpTopic);
        mcaRadio = findViewById(R.id.mcaRadio);
        mbaRadio = findViewById(R.id.mbaRadio);
        m_techRadio = findViewById(R.id.mtechRadio);
        otherRadio = findViewById(R.id.otherRadio);
        otherName = findViewById(R.id.otherLinearLay);

        all_notice_imagebutton = findViewById(R.id.image_all_notice_img);
        buttonUploadtextData = findViewById(R.id.button_upload_text_data);
        text_allnotice_edittext = findViewById(R.id.text_allnotice_edittext);
        buttonNotificationSend = findViewById(R.id.buttonNotificationSend);

        otherTopic = findViewById(R.id.whomSent);
        radio_grp_allnotice = findViewById(R.id.radio_grp_allNotice);
        title_all_notice = findViewById(R.id.title_allnotice_edittext);

        text_allnotice_linear = findViewById(R.id.text_allnotice_linear);
        img_allnotice_linear = findViewById(R.id.image_all_notice_linear);
        prefernceManager = new PrefernceManager(getApplicationContext());

        notificLay.setVisibility(View.GONE);
        instantLay.setVisibility(View.VISIBLE);
        otherName.setVisibility(View.GONE);
        radio_grp_allnotice.setVisibility(View.GONE);
        text_allnotice_linear.setVisibility(View.GONE);
        img_allnotice_linear.setVisibility(View.GONE);

//        image_info=findViewById(R.id.imageData);


        chooseRadioButton();
        topicRadioButton();
        uploadDataForInstantNotice();
        clickActionSpinner();
        spinnerImageAction();
        allNoticeRadioGrpwork();
        allNoticeImagebuttonClick();
        perticularUserAndOtherNotificationSend();
        uploadTextNoticeBoard();


    }

    private void uploadTextNoticeBoard() {

        buttonUploadtextData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String URL = "http://sanjayajena.000webhostapp.com/master.php?flag=notice";
                StringRequest requestdata = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(UploadModule.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                        text_allnotice_edittext.setText("");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(UploadModule.this, "NOT Added" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        String text_notice = text_allnotice_edittext.getText().toString();
                        Map<String, String> param = new HashMap<String, String>();
                        param.put("noticetext", text_notice);
                        return param;
                    }
                };
                requestQueue.add(requestdata);
            }
        });

    }

    private void topicRadioButton() {

        radioGroupTopic.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.otherRadio:
                        otherName.setVisibility(View.VISIBLE);
                        otherName.setFocusable(true);
                        //topic_str=null;

                        break;
                    case R.id.mcaRadio:
                        otherName.setVisibility(View.GONE);
                        text_allnotice_linear.setVisibility(View.GONE);
                        img_allnotice_linear.setVisibility(View.GONE);
                        topic_str = mcaRadio.getText().toString();
                        otherTopic.setText("");
                        Toast.makeText(UploadModule.this, "Text Is:" + topic_str, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.mbaRadio:
                        otherName.setVisibility(View.GONE);
                        text_allnotice_linear.setVisibility(View.GONE);
                        img_allnotice_linear.setVisibility(View.GONE);
                        topic_str = mbaRadio.getText().toString();
                        otherTopic.setText("");
                        Toast.makeText(UploadModule.this, "Text Is:" + topic_str, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.mtechRadio:
                        otherName.setVisibility(View.GONE);
                        text_allnotice_linear.setVisibility(View.GONE);
                        img_allnotice_linear.setVisibility(View.GONE);
                        topic_str = m_techRadio.getText().toString();
                        otherTopic.setText("");
                        Toast.makeText(UploadModule.this, "Text Is:" + topic_str, Toast.LENGTH_SHORT).show();
                        break;
                }

            }

        });

    }

    private void perticularUserAndOtherNotificationSend() {

        buttonNotificationSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (otherRadio.isChecked()) {

                    topic_str = otherTopic.getText().toString();

                    Toast.makeText(UploadModule.this, "Click is:" + topic_str, Toast.LENGTH_SHORT).show();

                    _title = title_.getText().toString();

                    //_click=click_.getText().toString();
                    _body = body_.getText().toString();



                    // Toast.makeText(UploadModule.this, "Text Is:"+topic_str, Toast.LENGTH_SHORT).show();

                    final String url = "https://fcm.googleapis.com/fcm/send";

                    StringRequest requestq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getApplicationContext(), "Response is:" + response, Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(), "Response is:" + error, Toast.LENGTH_SHORT).show();
                        }
                    }) {

                        //for header input
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();

                            final String auth = "key=AAAAESaQgfE:APA91bFo6tEm0RvB5kJXqbfXBZtGRctgptHuKDt3s9GhCrJKkD7h4kniTn6Um2ohU30LCFBpydjDF_pZyI8M-a4x1fIBeZdJnI14MsatscXM84uQfSZ8WHs1uTfuFyphxWz1mDqx-1l_Ut8YKJHEVCHE9wDIYTKCDg";
                            String type = "application/json";
                            params.put("Content-Type", type);
                            params.put("Authorization", auth);

                            return params;
                        }


                        //for raw data input
                        @Override
                        public byte[] getBody() throws AuthFailureError {
//                String _topic="News";
//                String _title="New Notification";
//                String click="NOTICE";
                            String str = "{\"to\":\"/topics/" + topic_str + "\",\"data\":{\"extra_information\":\"" + selectImageAction + "\"},\"notification\":{\"title\":\"" + _title + "\",\"body\":\"" + _body + "\",\"click_action\":\"" + selectAction + "\"}}";
                            return str.getBytes();
                        }


                    };
                    requestQueue.add(requestq);

                } else if(!otherRadio.isChecked()) {

                    Toast.makeText(UploadModule.this, "Click is:" + topic_str, Toast.LENGTH_SHORT).show();

                    _title = title_.getText().toString();
                    //_topic=topic_.getText().toString();
                    //_click=click_.getText().toString();
                    _body = body_.getText().toString();
                    // imgesUpload=image_info.getText().toString();


                    // Toast.makeText(UploadModule.this, "Text Is:"+topic_str, Toast.LENGTH_SHORT).show();

                    final String url = "https://fcm.googleapis.com/fcm/send";

                    StringRequest requestq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getApplicationContext(), "Response is:" + response, Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(), "Response is:" + error, Toast.LENGTH_SHORT).show();
                        }
                    }) {

                        //for header input
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();

                            final String auth = "key=AAAAESaQgfE:APA91bFo6tEm0RvB5kJXqbfXBZtGRctgptHuKDt3s9GhCrJKkD7h4kniTn6Um2ohU30LCFBpydjDF_pZyI8M-a4x1fIBeZdJnI14MsatscXM84uQfSZ8WHs1uTfuFyphxWz1mDqx-1l_Ut8YKJHEVCHE9wDIYTKCDg";
                            String type = "application/json";
                            params.put("Content-Type", type);
                            params.put("Authorization", auth);

                            return params;
                        }


                        //for raw data input
                        @Override
                        public byte[] getBody() throws AuthFailureError {
//                String _topic="News";
//                String _title="New Notification";
//                String click="NOTICE";
                            String str = "{\"to\":\"/topics/" + topic_str + "\",\"data\":{\"extra_information\":\"" + selectImageAction + "\"},\"notification\":{\"title\":\"" + _title + "\",\"body\":\"" + _body + "\",\"click_action\":\"" + selectAction + "\"}}";
                            return str.getBytes();
                        }


                    };
                    requestQueue.add(requestq);
                }

                //  requestQueue.add(requestq);

            }
        });
    }

    private void allNoticeImagebuttonClick() {

        all_notice_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String imagecrop = "101";

                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAllowRotation(true)
                        .start(UploadModule.this);
                prefernceManager.putString("imagecropped", imagecrop);


            }
        });
    }

    private void allNoticeRadioGrpwork() {

        radio_grp_allnotice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.textallnotice:
                        text_allnotice_linear.setVisibility(View.VISIBLE);
                        img_allnotice_linear.setVisibility(View.GONE);
                        break;
                    case R.id.imageallnotice:
                        img_allnotice_linear.setVisibility(View.VISIBLE);
                        text_allnotice_linear.setVisibility(View.GONE);
                        break;


                }

            }
        });
    }


    private void uploadDataForInstantNotice() {
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
//                        .setAspectRatio(2,3).start(UploadModule.this);
                String imagecrop = "100";

                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAllowRotation(true).start(UploadModule.this);
                prefernceManager.putString("imagecropped", imagecrop);


            }
        });
    }



    private void chooseRadioButton() {
        check.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

//                int child=group.getChildCount();
//
//                for (int i=0;i<child;i++){
//                    RadioButton radioButton= (RadioButton) group.getChildAt(i);
//                    switch (radioButton.getId())
//                    {
//                        case R.id.InstantNoticeRadio:
//                            notificLay.setVisibility(View.GONE);
//                            instantLay.setVisibility(View.VISIBLE);
//
//                            break;
//                        case R.id.noticeRadio:
//                            instantLay.setVisibility(View.GONE);
//                            notificLay.setVisibility(View.VISIBLE);
//                            break;
//
//                    }
////                    if(radioButton.getId()==R.id.InstantNoticeRadio){
////                        notificLay.setVisibility(View.GONE);
////                            instantLay.setVisibility(View.VISIBLE);
////                    }else if(radioButton.getId()==R.id.noticeRadio){
////                        instantLay.setVisibility(View.GONE);
////                           notificLay.setVisibility(View.VISIBLE);
////                    }
//                }

                switch (checkedId) {
                    case R.id.InstantNoticeRadio:
                        notificLay.setVisibility(View.GONE);
                        instantLay.setVisibility(View.VISIBLE);
                        radio_grp_allnotice.setVisibility(View.GONE);
                        text_allnotice_linear.setVisibility(View.GONE);
                        img_allnotice_linear.setVisibility(View.GONE);
                        break;
                    case R.id.noticeRadio:
                        instantLay.setVisibility(View.GONE);
                        notificLay.setVisibility(View.VISIBLE);
                        radio_grp_allnotice.setVisibility(View.GONE);
                        text_allnotice_linear.setVisibility(View.GONE);
                        img_allnotice_linear.setVisibility(View.GONE);
                        break;
                    case R.id.allnoticeRadio:

                        instantLay.setVisibility(View.GONE);
                        notificLay.setVisibility(View.GONE);
                        text_allnotice_linear.setVisibility(View.VISIBLE);
                        img_allnotice_linear.setVisibility(View.GONE);
                        radio_grp_allnotice.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void spinnerImageAction() {

        List<String> daList = new ArrayList<>(Arrays.asList(arIm));
        // spinner=findViewById(R.id.spinner);

        // spinner.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.arry_spinner_view, daList) {

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

        spinnerImage.setAdapter(adapter);
        spinnerImage.setSelection(adapter.getPosition(selectImageAction));
        spinnerImage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectImageAction = (String) parent.getItemAtPosition(position);
                Toast.makeText(UploadModule.this, "Branch selected :" + selectImageAction, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void clickActionSpinner() {


        List<String> daList = new ArrayList<>(Arrays.asList(arr));
        // spinner=findViewById(R.id.spinner);

        // spinner.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.arry_spinner_view, daList) {

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
        spinner.setSelection(adapter.getPosition(selectAction));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectAction = (String) parent.getItemAtPosition(position);
                Toast.makeText(UploadModule.this, "Branch selected :" + selectAction, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String datapreference = prefernceManager.getString("imagecropped");

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {


            final CropImage.ActivityResult r = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                if (datapreference.equals("100")) {
                    Toast.makeText(this, "100 re pasila", Toast.LENGTH_SHORT).show();
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Wait while We are updating your Profile Picture..");
                    progressDialog.setTitle("Please Wait");
                    progressDialog.show();


                    Uri u = r.getUri();
                    File f = new File(u.getPath());
                    Bitmap b = null;
                    try {
                        b = new Compressor(UploadModule.this)
                                .compressToBitmap(f);
                    } catch (Exception e) {
                        Toast.makeText(UploadModule.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    ByteArrayOutputStream ba = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.JPEG, 80, ba);  //quality 80 but i put 10
                    mByte = ba.toByteArray();


                    StorageReference s = FirebaseStorage.getInstance().getReference().child("notification").child("notice.jpg");
                    s.putBytes(mByte).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadModule.this, "Failed in storage:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String url = taskSnapshot.getDownloadUrl().toString();

                            title = findViewById(R.id.title_upload);
                            name = title.getText().toString();


                            DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("notification_database").child("Data");
                            Map m = new HashMap();
                            m.put("name", name);
                            m.put("image", url);

                            d.updateChildren(m).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {

                                    DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("notification_database").child("Data");
                                    d.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            //String name=dataSnapshot.child("name").getValue().toString();
                                            imgLinkFirebase = dataSnapshot.child("image").getValue().toString();
                                            StringBuilder str = new StringBuilder(imgLinkFirebase);

                                            str.delete(0, 105);
                                            NewString = str.toString();

                                            // oldStringLink=NewString.replace("https://firebasestorage.googleapis.com/v0/b/imit-9a8b1.appspot.com/o/notification%2Fnotice.jpg?alt=media&amp;","");


                                            //Picasso.with(getApplicationContext()).load(p).into(pic);
                                            //progress.dismiss();
                                            prefernceManager.removeKey("Firebaselink");
                                            //prefernceManager.putString("Firebaselink", str.toString());
                                            Toast.makeText(UploadModule.this, "Image Link  :" + str.toString(), Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                            Toast.makeText(UploadModule.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                                            //progress.dismiss();
                                        }

                                    });

                                    //After Image upload it send the Notice to all
                                    final String url = "https://fcm.googleapis.com/fcm/send";

                                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            Toast.makeText(getApplicationContext(), "Response is:" + response, Toast.LENGTH_SHORT).show();

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                            Toast.makeText(getApplicationContext(), "Error is:" + error, Toast.LENGTH_SHORT).show();
                                        }
                                    }) {

                                        //for header input
                                        @Override
                                        public Map<String, String> getHeaders() throws AuthFailureError {
                                            Map<String, String> params = new HashMap<String, String>();

                                            final String auth = "key=AAAAESaQgfE:APA91bFo6tEm0RvB5kJXqbfXBZtGRctgptHuKDt3s9GhCrJKkD7h4kniTn6Um2ohU30LCFBpydjDF_pZyI8M-a4x1fIBeZdJnI14MsatscXM84uQfSZ8WHs1uTfuFyphxWz1mDqx-1l_Ut8YKJHEVCHE9wDIYTKCDg";
                                            String type = "application/json";
                                            params.put("Content-Type", type);
                                            params.put("Authorization", auth);

                                            return params;
                                        }


                                        //for raw data input
                                        @Override
                                        public byte[] getBody() throws AuthFailureError {
                                            String _topic = "ALL";
                                            String _title = "IMIT";
                                            String click = "Notice";

                                            String str = "{\"to\":\"/topics/" + _topic + "\",\"data\":{\"extra_information\":\"" + NewString + "\"},\"notification\":{\"title\":\"" + _title + "\",\"body\":\"" + name + "\",\"click_action\":\"" + click + "\"}}";
                                            return str.getBytes();
                                        }

                                        ;

                                    };

                                    requestQueue.add(request);
                                    Toast.makeText(UploadModule.this, "Upload Successfully", Toast.LENGTH_SHORT).show();

                                    progressDialog.dismiss();

                                    //Image Link

                                    //End

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UploadModule.this, "Failed in Database:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });

                        }
                    });

                } else if (datapreference.equals("101")) {

                    Toast.makeText(this, "101 re pasila", Toast.LENGTH_SHORT).show();
                    Uri u = r.getUri();
                    File f = new File(u.getPath());
                    b = null;
                    try {
                        b = new Compressor(UploadModule.this)
                                .compressToBitmap(f);
                    } catch (Exception e) {
                        Toast.makeText(UploadModule.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    String imgUpload = encodeImage(b);
                    try {
                        String text = title_all_notice.getText().toString();

                        textUp = URLEncoder.encode(text, "UTF-8");
                        imgUp = URLEncoder.encode(imgUpload, "UTF-8");

                        new uploadData().execute();

                    } catch (UnsupportedEncodingException e) {

                    }


                }

            }
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.logout_menu, menu);
        return true;

    }


    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logoutc:
                mauth.signOut();

                Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
                Intent ii = new Intent(UploadModule.this, MainActivity.class);
                startActivity(ii);
                finishAffinity();
                finish();
                break;


        }


        return true;
    }


    //Notification to whom


    public String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.DEFAULT);
        return imgString;
    }


    private class uploadData extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://sanjayajena.000webhostapp.com/master.php?flag=noticeimage");

            try {
                //ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs = new ArrayList<NameValuePair>();


                nameValuePairs.add(new BasicNameValuePair("noticetext", textUp));
                nameValuePairs.add(new BasicNameValuePair("noticephoto", imgUp));


                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                cz.msebera.android.httpclient.HttpResponse response = httpClient.execute(httpPost);
                st = EntityUtils.toString(response.getEntity());

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return st;
        }

        @Override
        protected void onPostExecute(String s) {

            if (!s.equals("")) {


                Toast.makeText(UploadModule.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                title_all_notice.setText("");
//                String status = null;
//                String message = null;
//                try {
//                    JSONObject object = new JSONObject(s);
//                    status = object.getString("status");
//
//                    message = object.getString("message");
//
//                    if (status.equals("success")) {
//                        Toast.makeText(getApplicationContext(), "Successfully Upload", Toast.LENGTH_SHORT).show();
//
//
//                    }else {
//                        Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                } catch(JSONException e){
//                    e.printStackTrace();
//                    Toast.makeText(UploadModule.this, "Error is :"+e.getMessage(), Toast.LENGTH_SHORT).show();
//                }

                super.onPostExecute(s);
            }
        }
    }
}
