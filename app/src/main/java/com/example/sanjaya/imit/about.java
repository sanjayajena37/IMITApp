package com.example.sanjaya.imit;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanjaya.imit.Activity.MainActivity;

public class about extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        MainActivity.viewAtHome=false;
        return inflater.inflate(R.layout.about_activity,container,false);

    }
}