package com.example.sanjaya.imit.Activity;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.sanjaya.imit.Adapter.AllTextAdapter;
import com.example.sanjaya.imit.DTO.TextDto;
import com.example.sanjaya.imit.PrefernceManager;
import com.example.sanjaya.imit.ProfileFragment;
import com.example.sanjaya.imit.R;
import com.example.sanjaya.imit.about;
import com.example.sanjaya.imit.contact;
import com.example.sanjaya.imit.home;
import com.example.sanjaya.imit.mbaJava;
import com.example.sanjaya.imit.mbaPartTime;
import com.example.sanjaya.imit.mca;
import com.example.sanjaya.imit.mtech;
import com.example.sanjaya.imit.principle_activity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import id.zelory.compressor.Compressor;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
        //implements NavigationView.OnNavigationItemSelectedListener, LocationListener
         {


    ViewFlipper push;
    // Toolbar toolbar;
    private DrawerLayout draw;
    private ActionBarDrawerToggle mtoogle;
    Fragment fragment;
    NavigationView navigate;
    String sub = "ALL";
    // String Sub1="ALL";
    AlertDialog ae;
    String name;
    PrefernceManager prefernceManagerData;

    static private final String URL = "https://www.onlinesbi.com/prelogin/icollecthome.htm?corpID=842637";
    String reg_no, year_admission, branch, nameStr, localimage, phn_no;
    String branch_and_year;
    View navigationHeader;
    ImageView imgSmall, imgChoose;
    TextView nav_text, text_small, phnSet;
//    ArrayList<TextDto> mExampleList;
//    AllTextAdapter allTextAdapter;

    //location
//    LocationManager loc;
//    double lat,lng;
//    Geocoder geocoder;

    public static boolean viewAtHome;
    public static int screenWidth,screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefernceManagerData = new PrefernceManager(getApplicationContext());
        screenHeight=getWindowManager().getDefaultDisplay().getHeight();

        //Location Finder
//        loc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Location location = loc.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        onLocationChanged(location);


        reg_no = prefernceManagerData.getString("reg");
        year_admission = prefernceManagerData.getString("year");
        branch = prefernceManagerData.getString("department");
        nameStr = prefernceManagerData.getString("name");
        localimage = prefernceManagerData.getString("localimage");
        phn_no = prefernceManagerData.getString("phonedata");


        branch_and_year = branch + year_admission;


        FirebaseMessaging.getInstance().subscribeToTopic(sub);
        FirebaseMessaging.getInstance().subscribeToTopic(reg_no);
        FirebaseMessaging.getInstance().subscribeToTopic(branch);
        FirebaseMessaging.getInstance().subscribeToTopic(branch_and_year);


        loadFragment();


        //toolbar work
//        toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        //navigation set work 3 dot show but working in onOptionItemSelected
        draw = findViewById(R.id.boom);
        mtoogle = new ActionBarDrawerToggle(this, draw, R.string.Open, R.string.Close);
        mtoogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        //gpsPermission();


        //setListner or clickable to navigation
        navigate = findViewById(R.id.navigate);
        navigate.setNavigationItemSelectedListener(this);

        //checkable home
        navigate.setCheckedItem(R.id.home);


        //Header Photo Store & Data View work
        navigationHeader = navigate.getHeaderView(0);
        nav_text = navigationHeader.findViewById(R.id.nameHeader);
        text_small = navigationHeader.findViewById(R.id.textSmall);
        imgSmall = navigationHeader.findViewById(R.id.imgSmall);
        imgChoose = navigationHeader.findViewById(R.id.chooseImageSrc);
        phnSet = navigationHeader.findViewById(R.id.phnSet);

        text_small.setText("BATCH :" + branch_and_year);
        phnSet.setText(phn_no);

        nav_text.setText(nameStr);


        if (localimage != null) {
            String[] split = localimage.substring(1, localimage.length() - 1).split(", ");
            byte[] mByte = new byte[split.length];
            for (int i = 0; i < split.length; i++) {
                mByte[i] = Byte.parseByte(split[i]);
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(mByte, 0, mByte.length);
            imgSmall.setImageBitmap(bitmap);
            // Toast.makeText(, "", Toast.LENGTH_SHORT).show();
        }

        imgChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1).start(MainActivity.this);
            }
        });


    }



    private void loadFragment() {

        //fragment
        fragment = new home();
        FragmentManager fmk = getFragmentManager();
        FragmentTransaction ftk = fmk.beginTransaction();
        ftk.replace(R.id.fragment1, fragment);
        ftk.commit();
        viewAtHome = true;

    }


    //For CropImage Actrivity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            final CropImage.ActivityResult r = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

//                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//                progressDialog.setMessage("Wait while We are updating your Profile Picture..");
//                progressDialog.setTitle("Please Wait");
//                progressDialog.show();


                Uri u = r.getUri();
                File f = new File(u.getPath());
                Bitmap b = null;
                try {
                    b = new Compressor(MainActivity.this)
                            .compressToBitmap(f);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


                ByteArrayOutputStream ba = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 10, ba);
                byte[] mByte = ba.toByteArray();

                prefernceManagerData.putString("localimage", Arrays.toString(mByte));

                imgSmall = navigationHeader.findViewById(R.id.imgSmall);

                Bitmap bitmap = BitmapFactory.decodeByteArray(mByte, 0, mByte.length);
                // img_view = findViewById(R.id.chooseData);
                // bitmap1 = Bitmap.createScaledBitmap(bitmap,img_view.getWidth(),img_view.getHeight(),true);
                imgSmall.setImageBitmap(bitmap);

            }
        }


    }


    //navigation action work and FRAGMENT
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:

                fragment = new home();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment1, fragment);
                ft.commit();
                draw.closeDrawer(GravityCompat.START);
                viewAtHome = false;
                return true;

            case R.id.principal:

                fragment = new principle_activity();
                FragmentManager fmkk = getFragmentManager();
                FragmentTransaction ftkk = fmkk.beginTransaction();
                ftkk.replace(R.id.fragment1, fragment);
                ftkk.commit();
                draw.closeDrawer(GravityCompat.START);
                viewAtHome = false;
                return true;

            case R.id.mca:

                fragment = new mca();
                FragmentManager tc = getFragmentManager();
                FragmentTransaction bd = tc.beginTransaction();
                bd.replace(R.id.fragment1, fragment);
                bd.addToBackStack(fragment.toString());
                bd.commit();
                viewAtHome = false;
                draw.closeDrawer(GravityCompat.START);
                return true;


            case R.id.mba:
                fragment = new mbaJava();
                FragmentManager fmk = getFragmentManager();
                FragmentTransaction ftk = fmk.beginTransaction();
                ftk.replace(R.id.fragment1, fragment);
                ftk.commit();
                draw.closeDrawer(GravityCompat.START);
                viewAtHome = false;
                return true;

            case R.id.mba_part:
                fragment = new mbaPartTime();
                FragmentManager fma = getFragmentManager();
                FragmentTransaction fta = fma.beginTransaction();
                fta.replace(R.id.fragment1, fragment);
                fta.commit();
                draw.closeDrawer(GravityCompat.START);
                viewAtHome = false;
                return true;

            case R.id.mtech:
                fragment = new mtech();
                FragmentManager fmb = getFragmentManager();
                FragmentTransaction ftb = fmb.beginTransaction();
                ftb.replace(R.id.fragment1, fragment);
                ftb.commit();
                draw.closeDrawer(GravityCompat.START);
                viewAtHome = false;
                return true;


            case R.id.login:
                Intent upload = new Intent(MainActivity.this, UploadSignIn.class);
                startActivity(upload);
                break;

            case R.id.fee:

                Intent ik = new Intent(Intent.ACTION_VIEW);
                ik.setData(Uri.parse(URL));
                startActivity(ik);
                draw.closeDrawer(GravityCompat.START);


                fragment = new home();
                navigate.setCheckedItem(R.id.home);
                FragmentManager fml = getFragmentManager();
                FragmentTransaction fth = fml.beginTransaction();
                fth.replace(R.id.fragment1, fragment);
                fth.addToBackStack(fragment.toString());
                fth.commit();
                viewAtHome = false;
                return true;


            case R.id.contact:
                fragment = new contact();
                FragmentManager fm3 = getFragmentManager();
                FragmentTransaction ft3 = fm3.beginTransaction();
                ft3.replace(R.id.fragment1, fragment);
                ft3.addToBackStack(fragment.toString());
                ft3.commit();
                draw.closeDrawer(GravityCompat.START);
                viewAtHome = false;
                return true;

            case R.id.tender:
                Toast.makeText(getApplicationContext(), "Tender aseba wait", Toast.LENGTH_SHORT).show();
                break;
            case R.id.app:
                fragment = new about();
                FragmentManager fm2 = getFragmentManager();
                FragmentTransaction ft2 = fm2.beginTransaction();
                ft2.replace(R.id.fragment1, fragment);
                ft2.addToBackStack(fragment.toString());
                ft2.commit();
                viewAtHome = false;
                draw.closeDrawer(GravityCompat.START);
                return true;
            case R.id.profileStudent:
                fragment = new ProfileFragment();
                FragmentManager fmk2 = getFragmentManager();
                FragmentTransaction ftk2 = fmk2.beginTransaction();
                ftk2.replace(R.id.fragment1, fragment);
                ftk2.addToBackStack(fragment.toString());
                ftk2.commit();
                viewAtHome = false;
                draw.closeDrawer(GravityCompat.START);
                return true;
            case R.id.logout_student:

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do you want to Logout ?");
                builder.setIcon(R.drawable.logovector);
                builder.setTitle("IMIT");
                String positiveText = "Yes";
                String NegativeText="No";
                builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PrefernceManager prefernceManager = new PrefernceManager(getApplicationContext());

                        reg_no = prefernceManager.getString("reg");
                        year_admission = prefernceManager.getString("year");
                        branch = prefernceManager.getString("department");

                        branch_and_year = branch + year_admission;


                        FirebaseMessaging.getInstance().unsubscribeFromTopic(reg_no).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Toast.makeText(MainActivity.this, "UnSubscribe" + reg_no, Toast.LENGTH_SHORT).show();
                            }
                        });
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(branch_and_year).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //  Toast.makeText(MainActivity.this, "UnSubscribe" + branch_and_year, Toast.LENGTH_SHORT).show();
                            }
                        });
                        ;
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(branch).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Toast.makeText(MainActivity.this, "UnSubscribe" + branch, Toast.LENGTH_SHORT).show();
                            }
                        });
                        ;


                        prefernceManager.removeKey("reg");
                        prefernceManager.removeKey("department");
                        prefernceManager.removeKey("year");
                        prefernceManager.removeKey("name");
                        prefernceManager.removeKey("localimage");
                        prefernceManager.removeKey("phonedata");
                        prefernceManager.removeKey("phone");


                        prefernceManager.setLogin(false);

                        Intent login = new Intent(getApplicationContext(), Login_activity.class);

                        finishAffinity();
                        finish();
                        startActivity(login);
                    }
                });
                builder.setNegativeButton(NegativeText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                draw.closeDrawer(GravityCompat.START);
                return true;

        }


        return true;
    }


    //option menu in action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //It is used to open 3 dot nav
        if (mtoogle.onOptionsItemSelected(item)) {
            return true;
        } else
            switch (item.getItemId()) {
                case R.id.notification:
                    Intent oi = new Intent(MainActivity.this, NoticeActivity.class);


                    //session end
                    startActivity(oi);
                    break;
                case R.id.all_notification:
                    Intent notice = new Intent(getApplicationContext(), NoticeBoard.class);
                    startActivity(notice);
                    // Toast.makeText(this, "Work in progress", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.logoutStudent:
                    //Session

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Do you want to Logout ?");
                    builder.setIcon(R.drawable.logovector);
                    builder.setTitle("IMIT");
                    String positiveText = "Yes";
                    String NegativeText="No";
                    builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PrefernceManager prefernceManager = new PrefernceManager(getApplicationContext());

                            reg_no = prefernceManager.getString("reg");
                            year_admission = prefernceManager.getString("year");
                            branch = prefernceManager.getString("department");

                            branch_and_year = branch + year_admission;


                            FirebaseMessaging.getInstance().unsubscribeFromTopic(reg_no).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Toast.makeText(MainActivity.this, "UnSubscribe" + reg_no, Toast.LENGTH_SHORT).show();
                                }
                            });
                            FirebaseMessaging.getInstance().unsubscribeFromTopic(branch_and_year).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                  //  Toast.makeText(MainActivity.this, "UnSubscribe" + branch_and_year, Toast.LENGTH_SHORT).show();
                                }
                            });
                            ;
                            FirebaseMessaging.getInstance().unsubscribeFromTopic(branch).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                   // Toast.makeText(MainActivity.this, "UnSubscribe" + branch, Toast.LENGTH_SHORT).show();
                                }
                            });
                            ;


                            prefernceManager.removeKey("reg");
                            prefernceManager.removeKey("department");
                            prefernceManager.removeKey("year");
                            prefernceManager.removeKey("name");
                            prefernceManager.removeKey("localimage");
                            prefernceManager.removeKey("phonedata");
                            prefernceManager.removeKey("phone");


                            prefernceManager.setLogin(false);

                            Intent login = new Intent(getApplicationContext(), Login_activity.class);

                            finishAffinity();
                            finish();
                            startActivity(login);
                        }
                    });
                    builder.setNegativeButton(NegativeText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

//                    PrefernceManager prefernceManager = new PrefernceManager(getApplicationContext());
//
//                    reg_no = prefernceManager.getString("reg");
//                    year_admission = prefernceManager.getString("year");
//                    branch = prefernceManager.getString("department");
//
//                    branch_and_year = branch + year_admission;
//
//
//                    FirebaseMessaging.getInstance().unsubscribeFromTopic(reg_no).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(MainActivity.this, "UnSubscribe" + reg_no, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    FirebaseMessaging.getInstance().unsubscribeFromTopic(branch_and_year).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(MainActivity.this, "UnSubscribe" + branch_and_year, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    ;
//                    FirebaseMessaging.getInstance().unsubscribeFromTopic(branch).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(MainActivity.this, "UnSubscribe" + branch, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    ;
//
//
//                    prefernceManager.removeKey("reg");
//                    prefernceManager.removeKey("department");
//                    prefernceManager.removeKey("year");
//                    prefernceManager.removeKey("name");
//                    prefernceManager.removeKey("localimage");
//                    prefernceManager.removeKey("phonedata");
//                    prefernceManager.removeKey("phone");
//
//
//                    prefernceManager.setLogin(false);
//
//                    Intent login = new Intent(getApplicationContext(), Login_activity.class);
//
//                    finishAffinity();
//                    finish();
//                    startActivity(login);

                    //session end

            }


        return true;
    }


    //view flipper calling method
    public void flipImagess(int image) {
        ImageView imagek = new ImageView(MainActivity.this);
        imagek.setBackgroundResource(image);

        push.addView(imagek);
        push.setFlipInterval(3000);
        push.setAutoStart(true);

        push.setInAnimation
                (this, android.R.anim.slide_in_left);
        push.setOutAnimation
                (this, android.R.anim.slide_out_right);


    }

    @Override
    public void onBackPressed() {

        // super.onBackPressed();

        if (viewAtHome) {
            //final Activity xo = new Activity();

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
        } else {
            loadFragment();
        }
    }



//             private class JSONParse extends AsyncTask<String, String, JSONObject> {
//                 private ProgressDialog pDialog;
//                 final String URL2="http://sanjayajena.000webhostapp.com/master.php?flag=noticedtls";
//                 @Override
//                 protected void onPreExecute() {
//                     super.onPreExecute();
//                     pDialog = new ProgressDialog(MainActivity.this);
//                     mExampleList=new ArrayList<>();
//                     pDialog.setMessage("Getting Data ...");
//                     pDialog.setIndeterminate(false);
//                     pDialog.setCancelable(true);
//                     pDialog.show();
//
//                 }
//
//                 @Override
//                 protected JSONObject doInBackground(String... args) {
//                     JSONParser jParser = new JSONParser();
//
//                     // Getting JSON from URL
//                     JSONObject json = jParser.getJSONFromUrl(URL2);
//                     return json;
//                 }
//                 @Override
//                 protected void onPostExecute(JSONObject json) {
//                     pDialog.dismiss();
//                     try {
//                         // Getting JSON Array
//                         JSONArray user = json.getJSONArray("vdata");
//
//                         for(int i=0;i<user.length();i++) {
//                             JSONObject c = user.getJSONObject(0);
//
//                             // Storing  JSON item in a Variable
//                             String maintext = c.getString("notice");
//                             String date = c.getString("tod_date");
//
//                             //Set JSON Data in TextView
//                             mExampleList.add(new TextDto(maintext, date));
//                         }
//
//                         allTextAdapter = new AllTextAdapter(getApplicationContext(), mExampleList);
//                        txt_recycler.setAdapter(allTextAdapter);
//
//                     } catch (JSONException e) {
//                         e.printStackTrace();
//                     }
//
//                 }
//             }
//
//             public class JSONParser {
//
//                  InputStream is = null;
//                  JSONObject jObj = null;
//                  String json = "";
//
//                 // constructor
//                 public JSONParser() {
//
//                 }
//
//                 public JSONObject getJSONFromUrl(String url) {
//
//                     // Making HTTP request
//                     try {
//                         // defaultHttpClient
//                         DefaultHttpClient httpClient = new DefaultHttpClient();
//                         HttpPost httpPost = new HttpPost(url);
//
//                         HttpResponse httpResponse = httpClient.execute(httpPost);
//                         HttpEntity httpEntity = httpResponse.getEntity();
//                         is = httpEntity.getContent();
//
//                     } catch (UnsupportedEncodingException e) {
//                         e.printStackTrace();
//                     } catch (ClientProtocolException e) {
//                         e.printStackTrace();
//                     } catch (IOException e) {
//                         e.printStackTrace();
//                     }
//
//                     try {
//                         BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
//                         StringBuilder sb = new StringBuilder();
//                         String line = null;
//                         while ((line = reader.readLine()) != null) {
//                             sb.append(line + "n");
//                         }
//                         is.close();
//                         json = sb.toString();
//                     } catch (Exception e) {
//                         Log.e("Buffer Error", "Error converting result " + e.toString());
//                     }
//
//                     // try parse the string to a JSON object
//                     try {
//                         jObj = new JSONObject(json);
//                     } catch (JSONException e) {
//                         Log.e("JSON Parser", "Error parsing data " + e.toString());
//                     }
//
//                     // return JSON String
//                     return jObj;
//
//                 }

//    @Override
//    public void onLocationChanged(Location location) {
//
//        lat = location.getLatitude();
//        lng = location.getLongitude();
//
//
//        geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
//        try {
//            List<Address> adress=geocoder.getFromLocation(lat,lng,1);
//            if(adress!=null && adress.size()>0){
//
//               // String addresses = adress.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                String city = adress.get(0).getLocality();
//                String state = adress.get(0).getAdminArea();
////                String country = adress.get(0).getCountryName();
////                String postalCode = adress.get(0).getPostalCode();
////                String knownName = adress.get(0).getFeatureName();
//
//                String titleLoc=city+","+state;
//
//               getSupportActionBar().setSubtitle(titleLoc);
//
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//
//    }
}
