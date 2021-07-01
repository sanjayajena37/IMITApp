package com.example.sanjaya.imit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sanjaya.imit.Activity.splashscreen;


public class CardWizardActivity extends AppCompatActivity {

    private static final int MAX_STEP=4;
    private ViewPager viewPager;


    
    //final int PERMISSION_REQUEST_CODE=110;
    private MyViewPagerAdapter myViewPagerAdapter;
    String about_title_array[]={
            "Notice Board",
            "Syllabus",
            "Notification",
            "Enjoy Holiday"

    };

    String about_description_array[]={
            "Notice Board Avilable Here",
            "Syllabus are avilable here",
            "You Can get notification about the college updates",
            "You Can Check the Holiday List in this App"

    };

    private int about_img_array[]={ R.drawable.img2,R.drawable.img3, R.drawable.img4,R.drawable.img5 };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_wizard_activity);

        viewPager=findViewById(R.id.view_pager);
        bottomProgressDot(0);

        myViewPagerAdapter=new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListner);

        //Tools.setSystemBarColor(this,setFeatureDrawableAlpha(0,0.2));
    }

    private void bottomProgressDot(int current_index) {

        LinearLayout dotsLayout=findViewById(R.id.layoutDots);
        ImageView[] dots=new ImageView[MAX_STEP];
        dotsLayout.removeAllViews();

        for (int i=0;i<dots.length;i++)
        {
            dots[i]=new ImageView(this);
            int width_height=15;
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height,width_height));
            params.setMargins(10,10,10,10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circular);
            dots[i].setColorFilter(Color.WHITE,PorterDuff.Mode.SRC_IN);
            dotsLayout.addView(dots[i]);
        }

        if(dots.length>0){
            dots[current_index].setImageResource(R.drawable.shape_circular);
            dots[current_index].setColorFilter(getResources().getColor(R.color.colorAccent),PorterDuff.Mode.SRC_IN);
        }

    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListner=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            bottomProgressDot(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private class MyViewPagerAdapter extends PagerAdapter {
        LayoutInflater layoutInflater;
        Button btnNext;

        public MyViewPagerAdapter(){

        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view=layoutInflater.inflate(R.layout.item_card_wizard,container,false);
            ((TextView)view.findViewById(R.id.title)).setText(about_title_array[position]);
            ((TextView)view.findViewById(R.id.description)).setText(about_description_array[position]);
            ((ImageView)view.findViewById(R.id.img_item)).setImageResource(about_img_array[position]);
            btnNext=view.findViewById(R.id.button);

            if(position==about_title_array.length-1){
                btnNext.setText("Get Started");
            }else {
                btnNext.setText("Next");
            }
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int current=viewPager.getCurrentItem()+1;
                    if(current<MAX_STEP){
                        viewPager.setCurrentItem(current);
                    }else {
                        Intent intent=new Intent(getApplicationContext(),splashscreen.class);
                        startActivity(intent);
                    }

                }
            });

            container.addView(view);



            return view;
        }

        @Override
        public int getCount() {
            return about_title_array.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view==o;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view=(View)object;
            container.removeView(view);
        }
    }


}
