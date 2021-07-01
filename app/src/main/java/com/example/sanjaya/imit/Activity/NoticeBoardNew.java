package com.example.sanjaya.imit.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanjaya.imit.Adapter.AllImageAdapter;
import com.example.sanjaya.imit.DTO.ImgDto;
import com.example.sanjaya.imit.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class NoticeBoardNew extends AppCompatActivity {

    private RecyclerView img_recycler;

    private Request.Priority priority = Request.Priority.HIGH;

    private ArrayList<ImgDto> imgArray;
   // private ArrayList<PhotoGetData> imgArray;
    //private RequestQueue requestQueue;
    RequestQueue requestQueueData;
    private AllImageAdapter allImageAdapter;
    String url2="http://sanjayajena.000webhostapp.com/master.php?flag=shownoticedtls";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board_new);
        requestQueueData = Volley.newRequestQueue(this);
        img_recycler=findViewById(R.id.noticeRecycler);
        imgArray=new ArrayList<>();
        //allImageAdapter=new AllImageAdapter();
        //imgAllNotice();
        fetchRecyclerViewData();





    }

    private void fetchRecyclerViewData() {
         JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray=response.getJSONArray("vdata");

                    for(int i=0;i<=jsonArray.length();i++){

                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        String header=jsonObject.getString("title");
                        String date=jsonObject.getString("tod_date");
                        String imgurl=jsonObject.getString("titleimg");

                        imgArray.add(new ImgDto(imgurl,date,header));
                    }

                    allImageAdapter=new AllImageAdapter(NoticeBoardNew.this,imgArray);
                    img_recycler.setAdapter(allImageAdapter);
                    img_recycler.setHasFixedSize(true);
                    img_recycler.setLayoutManager(new LinearLayoutManager(NoticeBoardNew.this,LinearLayoutManager.VERTICAL,true));


                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(NoticeBoardNew.this, "Error is : "+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Priority getPriority() {
                return priority;
            }
        };
        requestQueueData.add(request);

    }

//    private void imgAllNotice() {
//        final String URLTEXT2="http://sanjayajena.000webhostapp.com/master.php?flag=shownoticedtls";
//
//        String newUrl="http://sanjayajena.000webhostapp.com/master.php?flag=shownoticedtls";
//        JsonObjectRequest requestdata2=new JsonObjectRequest(Request.Method.GET, newUrl, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                // Toast.makeText(NoticeBoard.this, "On Success", Toast.LENGTH_SHORT).show();
//
//                try {
//                    JSONArray jsonArray = response.getJSONArray("vdata");
//
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject hit = jsonArray.getJSONObject(i);
//
//                        String maintext = hit.getString("title");
//                        String imgdata = hit.getString("titleimg");
//                        String date = hit.getString("tod_date");
//
//                        imgArray.add(new ImgDto(imgdata,date,maintext));
//                    }
//
////                    Toast.makeText(NoticeBoard.this, "On Success"+imgArray, Toast.LENGTH_SHORT).show();
//////                    allImageAdapter=new AllImageAdapter(getApplicationContext(),imgArray);
//////                    txt_recycler.setAdapter(allImageAdapter);
////                    objects.add(imgArray.get(0));
////
//                    allImageAdapter = new AllImageAdapter(NoticeBoardNew.this, imgArray);
//                    img_recycler.setAdapter(allImageAdapter);
//                    //img_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                    img_recycler.setLayoutManager(new LinearLayoutManager(NoticeBoardNew.this, LinearLayoutManager.HORIZONTAL, true));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(getApplicationContext(), "Error is :"+error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        requestQueueData.add(requestdata2);
//
////        StringRequest request=new StringRequest(Request.Method.POST, URLTEXT2, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////
////
////                try {
////                    //JSONArray jsonArray = response.getJSONArray("vdata");
////                    JSONArray
////
////                    for (int i = 0; i < jsonArray.length(); i++) {
////                        JSONObject hit = jsonArray.getJSONObject(i);
////
////                        String maintext = hit.getString("title");
////                        String imgdata = hit.getString("titleimg");
////                        String date = hit.getString("tod_date");
////
////                        imgArray.add(new ImgDto(imgdata,date,maintext));
////                    }
////
//////                    Toast.makeText(NoticeBoard.this, "On Success"+imgArray, Toast.LENGTH_SHORT).show();
////////                    allImageAdapter=new AllImageAdapter(getApplicationContext(),imgArray);
////////                    txt_recycler.setAdapter(allImageAdapter);
//////                    objects.add(imgArray.get(0));
//////
////                    allImageAdapter = new AllImageAdapter(NoticeBoardNew.this, imgArray);
////                    img_recycler.setAdapter(allImageAdapter);
////                    //img_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
////                    img_recycler.setLayoutManager(new LinearLayoutManager(NoticeBoardNew.this, LinearLayoutManager.HORIZONTAL, true));
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
////
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////
////            }
////        });
//
//    }



}
