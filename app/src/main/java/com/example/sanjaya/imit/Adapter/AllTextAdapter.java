package com.example.sanjaya.imit.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sanjaya.imit.DTO.TextDto;
import com.example.sanjaya.imit.R;

import java.util.ArrayList;

public class AllTextAdapter extends RecyclerView.Adapter<AllTextAdapter.AllTextHolder> {

    Context context;
    ArrayList<TextDto> arrayList;



    public AllTextAdapter(Context context,ArrayList<TextDto> mExampleList) {

        arrayList=mExampleList;
        this.context=context;
    }

    @NonNull
    @Override
    public AllTextHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(context).inflate(R.layout.view_allnotice_text,parent,false);
        return new AllTextHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AllTextHolder holder, int position) {

        TextDto currentItem=arrayList.get(position);

        String maintext=currentItem.getNotice();
        String date=currentItem.getDate();

        holder.maintext.setText(maintext);
        holder.dateget.setText("Date :" +date);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class AllTextHolder extends RecyclerView.ViewHolder {

        TextView maintext;
        TextView dateget;

        public AllTextHolder(View itemView) {
            super(itemView);
            maintext=itemView.findViewById(R.id.mainText);
            dateget=itemView.findViewById(R.id.date);

        }
    }
}
