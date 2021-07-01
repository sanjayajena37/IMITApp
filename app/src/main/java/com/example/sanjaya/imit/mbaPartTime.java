package com.example.sanjaya.imit;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class mbaPartTime extends Fragment {

    LinearLayout linearLayout;
    Animation animation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View v=inflater.inflate(R.layout.mba_part_time,container,false);
        linearLayout=v.findViewById(R.id.mbaLayout);
        animation=AnimationUtils.loadAnimation(getActivity(),R.anim.moveup);
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.setAnimation(animation);

        return v;
    }
}