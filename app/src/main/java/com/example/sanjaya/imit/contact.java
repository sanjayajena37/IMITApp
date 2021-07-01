package com.example.sanjaya.imit;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanjaya.imit.Activity.MainActivity;

public class contact extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View new_root=inflater.inflate(R.layout.contact_activity,container,false);
        MainActivity.viewAtHome=false;
        FloatingActionButton fabb=new_root.findViewById(R.id.fab);
        PrefernceManager prefernceManager=new PrefernceManager(getActivity());
        final String name=prefernceManager.getString("name");
        final String roll=prefernceManager.getString("reg");

        fabb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("email"));
                String[] s={"imitcuttack@rediffmail.com"};

                i.putExtra(Intent.EXTRA_EMAIL,s);
                i.putExtra(Intent.EXTRA_SUBJECT,"My Name is "+name+" and My Roll number is "+roll+" am sending this message using app");
                i.setType("message/rfc822");
                Intent chooser=Intent.createChooser(i,"Launch Email");
                startActivity(chooser);
            }
        });
        return new_root;
    }
}