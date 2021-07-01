package com.example.sanjaya.imit;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
//import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.sanjaya.imit.Activity.NoticeActivity;
import com.example.sanjaya.imit.Activity.NoticeBoard;
import com.example.sanjaya.imit.Activity.NoticeBoardNew;
import com.example.sanjaya.imit.Activity.Syllabus;
import com.example.sanjaya.imit.Activity.UploadSignIn;
import com.example.sanjaya.imit.Adapter.AllTextAdapter;
import com.example.sanjaya.imit.DTO.TextDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class home extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{


    private SliderLayout mDemoSlider;
    Context con;
    Fragment fragment;
    View root;
    public  ArrayList<TextDto> textarraylist;
    RecyclerView textrecyclerView;
    RequestQueue requestQueue;
    AllTextAdapter allTextAdapter;
    //LinearLayout linearAnim;
    ImageView about_app,login,contact,notice,syllabus,hostel_fee,college_fee,time_tableB,all_Department;
    private final String url="https://www.onlinesbi.com/prelogin/icollecthome.htm?corpID=841591";
    static private final String URL = "https://www.onlinesbi.com/prelogin/icollecthome.htm?corpID=842637";

   // Animation animation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.home_act,container,false);
        mDemoSlider = (SliderLayout)root.findViewById(R.id.slider);
        slider();

        //Recycler
        textarraylist=new ArrayList<>();
        textrecyclerView=root.findViewById(R.id.noticeRecycler);
        requestQueue= Volley.newRequestQueue(getActivity());

        fetchTextNotice();



       // boolean viewAtHome=false;



        about_app=root.findViewById(R.id.About_app);
        login=root.findViewById(R.id.login_section);
        contact=root.findViewById(R.id.contact_us);
        notice=root.findViewById(R.id.notice);
        syllabus=root.findViewById(R.id.sylla);
        hostel_fee=root.findViewById(R.id.hostel);
        college_fee=root.findViewById(R.id.college);
        time_tableB=root.findViewById(R.id.time_table);
        all_Department=root.findViewById(R.id.all_dept);

       // linearAnim=root.findViewById(R.id.linearAnim);
//        animation=AnimationUtils.loadAnimation(getActivity(),R.anim.moveup);
//        linearAnim.setVisibility(View.VISIBLE);
//        linearAnim.setAnimation(animation);



        all_Department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oo=new Intent(getActivity(),AllDepartment.class);
                startActivity(oo);
            }
        });


        time_tableB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent time=new Intent(getActivity(),TimeTable.class);
                startActivity(time);
            }
        });


        college_fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent kk=new Intent(Intent.ACTION_VIEW);
                kk.setData(Uri.parse(URL));
                startActivity(kk);

            }
        });



        hostel_fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent io=new Intent(Intent.ACTION_VIEW);
                io.setData(Uri.parse(url));
                startActivity(io);
            }
        });

        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent syll_intent=new Intent(getActivity(),Syllabus.class);
                startActivity(syll_intent);
            }
        });



        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oi=new Intent(getActivity(),NoticeActivity.class);
                startActivity(oi);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new contact();
                FragmentManager fm3 = getFragmentManager();
                FragmentTransaction ft3 = fm3.beginTransaction();
                ft3.replace(R.id.fragment1, fragment);
                ft3.addToBackStack(fragment.toString());
                ft3.commit();

            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upload=new Intent(getActivity(),UploadSignIn.class);
                startActivity(upload);
            }
        });




        about_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new about();
                FragmentManager fm2 = getFragmentManager();
                FragmentTransaction ft2 = fm2.beginTransaction();
                ft2.replace(R.id.fragment1, fragment);
                ft2.addToBackStack(fragment.toString());
                ft2.commit();

            }
        });


        return root;
    }

    private void fetchTextNotice() {



            final String URLTEXT="http://sanjayajena.000webhostapp.com/master.php?flag=noticedtls";
//            JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URLTEXT, null, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//
//                    try {
//                        JSONArray jsonArray = response.getJSONArray("vdata");
//
//
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject hit = jsonArray.getJSONObject(i);
//
//                            String maintext = hit.getString("notice");
//                            String date = hit.getString("tod_date");
//
//
//                            textarraylist.add(new TextDto(maintext, date));
//
//                        }
//
//
//
//                       // objects.add(mExampleList.get(0));
//                        // Toast.makeText(NoticeBoard.this, "Object is"+objects, Toast.LENGTH_SHORT).show();
//
//
//
//
//
//                        allTextAdapter = new AllTextAdapter(getActivity(), textarraylist);
//                        textrecyclerView.setAdapter(allTextAdapter);
//                        textrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true));
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                    Toast.makeText(getActivity(), "Error is :"+error.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//            requestQueue.add(request);

//        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, URLTEXT, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//               // JSONArray jsonArray = response.getJSONArray("vdata"
//
//                        for (int i = 0; i < response.length(); i++) {
//                            JSONObject hit = null;
//
//                                hit = response.getJSONObject(i);
//                                String maintext = hit.getString("notice");
//                                String date = hit.getString("tod_date");
//
//
//                                textarraylist.add(new TextDto(maintext, date));
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });



    }

    private void slider() {

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Principal Speech",R.drawable.pi11);
        file_maps.put("Prize Distribution",R.drawable.pic1);
        file_maps.put("IGNITION",R.drawable.pic2);
        file_maps.put("Faculties", R.drawable.pic3);
        file_maps.put("Function ", R.drawable.pic4);
        file_maps.put("Alumini", R.drawable.pic5);
        file_maps.put("Auditorium", R.drawable.pic6);
        file_maps.put("Annual Function Dance", R.drawable.pic8);
        file_maps.put("Computer LAB", R.drawable.pic9);
        file_maps.put("DJ Night", R.drawable.pic7);


        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(),slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
     public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    //view flipper calling method





}