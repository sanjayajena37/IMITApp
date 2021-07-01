package com.example.sanjaya.imit;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class mca extends Fragment {


    static private final String URL="https://drive.google.com/open?id=1k8-z--cb4mtKyVhbBXv9Gb6wsrcbNKQc";

    private LinearLayout linearLayout;
    private


    Animation animation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View v=inflater.inflate(R.layout.mca_lay,container,false);
        linearLayout=v.findViewById(R.id.mcaLayout);
        animation=AnimationUtils.loadAnimation(getActivity(),R.anim.moveup);
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.setAnimation(animation);


        return v;
    }



}