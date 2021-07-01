package com.example.sanjaya.imit.Activity;



import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanjaya.imit.Adapter.AllImageAdapter;
import com.example.sanjaya.imit.Adapter.AllTextAdapter;
import com.example.sanjaya.imit.DTO.ImgDto;
import com.example.sanjaya.imit.Adapter.MainAdapter;
import com.example.sanjaya.imit.R;
import com.example.sanjaya.imit.DTO.TextDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class NoticeBoard extends AppCompatActivity {


    private RecyclerView txt_recycler,img_recycler;

    public static ArrayList<TextDto> mExampleList;
    private RequestQueue requestQueue;
    private AllImageAdapter allImageAdapter;
    public static ArrayList<ImgDto> imgArray;
    private AllTextAdapter allTextAdapter;
    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManagerData,another;
    public static ArrayList<Object> objects=new ArrayList<>();
    private MainAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LottieAnimationView lottieAnimationView;
    //private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notice_activity);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        lottieAnimationView=findViewById(R.id.lottie);
        lottieAnimationView.setVisibility(View.VISIBLE);
        //swipeRefreshLayout.setRefreshing(true);
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Wait for Loading Data");
//        progressDialog.setTitle("Please Wait");
        //progressDialog.show();

//        txt_recycler=findViewById(R.id.recyclerview_text);
//        img_recycler=findViewById(R.id.recyclerview_with_image);
//        txt_recycler.setHasFixedSize(true);

//
//        another=new LinearLayoutManager(this,LinearLayout.HORIZONTAL,true);
//        layoutManagerData= new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//        txt_recycler.setLayoutManager(another);
//
//        img_recycler.setHasFixedSize(true);
//        img_recycler.setLayoutManager(layoutManagerData);
//        //txt_recycler.setNestedScrollingEnabled(false);
//        //img_recycler.setNestedScrollingEnabled(true);

        recyclerView = findViewById(R.id.recycler_View);
        mExampleList = new ArrayList<>();
        imgArray=new ArrayList<>();


        //allTextAdapter=new AllTextAdapter(getApplicationContext(),)


        requestQueue = Volley.newRequestQueue(this);
        textAllNotice();
        imgAllNotice();


        //loadFirstRecycle();





        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(true);
                loadRecyclerViewData();
                lottieAnimationView.setVisibility(View.VISIBLE);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
//        lottieAnimationView.setVisibility(View.GONE);







    }

    private void loadRecyclerViewData() {

        imgArray.clear();
        mExampleList.clear();



        textAllNotice();
        imgAllNotice();
        swipeRefreshLayout.setRefreshing(false);
       // progressDialog.dismiss();
    }

//    private void loadFirstRecycle(){
//        textAllNotice();
//        imgAllNotice();
//        //swipeRefreshLayout.setAlpha((float) 0.8);
//        //swipeRefreshLayout.setRefreshing(false);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                lottieAnimationView.setVisibility(View.GONE);
//            }
//        },9000);
//    }

//    private ArrayList<Object> getObject() {
//        objects.add(mExampleList.get(0));
//        objects.add(imgArray.get(0));
//        return objects;
//



    private void imgAllNotice() {
        final String URLTEXT="http://sanjayajena.000webhostapp.com/master.php?flag=shownoticedtls";

         JsonObjectRequest requestdata=new JsonObjectRequest(Request.Method.GET, URLTEXT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

               // Toast.makeText(NoticeBoard.this, "On Success", Toast.LENGTH_SHORT).show();

                try {
                    JSONArray jsonArray = response.getJSONArray("vdata");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);

                        String maintext = hit.getString("title");
                        String imgdata = hit.getString("titleimg");
                        String date = hit.getString("tod_date");

                        imgArray.add(new ImgDto(imgdata,date,maintext));
                    }

//                    Toast.makeText(NoticeBoard.this, "On Success"+imgArray, Toast.LENGTH_SHORT).show();
////                    allImageAdapter=new AllImageAdapter(getApplicationContext(),imgArray);
////                    txt_recycler.setAdapter(allImageAdapter);
                    objects.add(imgArray.get(0));

                    adapter = new MainAdapter(getApplicationContext(), objects);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    lottieAnimationView.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(NoticeBoard.this, "Error is :"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(requestdata);

    }


    private void textAllNotice() {

        final String URLTEXT="http://sanjayajena.000webhostapp.com/master.php?flag=noticedtls";

         JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URLTEXT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("vdata");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject hit = jsonArray.getJSONObject(i);

                            String maintext = hit.getString("notice");
                            String date = hit.getString("tod_date");


                            mExampleList.add(new TextDto(maintext, date));

                        }



                    objects.add(mExampleList.get(0));
                   // Toast.makeText(NoticeBoard.this, "Object is"+objects, Toast.LENGTH_SHORT).show();




//                        allTextAdapter = new AllTextAdapter(getApplicationContext(), mExampleList);
//                        txt_recycler.setAdapter(allTextAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(NoticeBoard.this, "Error is :"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);

    }


}
