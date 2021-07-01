package com.example.sanjaya.imit.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sanjaya.imit.DTO.ImgDto;
import com.example.sanjaya.imit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllImageAdapter extends RecyclerView.Adapter<AllImageAdapter.AllImageHolder> {

    Context context;
    ArrayList<ImgDto> arrayListmg;

    public AllImageAdapter(Context context, ArrayList<ImgDto> arrayListmg){
        this.context=context;
        this.arrayListmg=arrayListmg;

    }

//    public AllImageAdapter(ArrayList<ImgDto> imgArray) {
//
//        arrayListmg =imgArray;
//    }

    @NonNull
    @Override
    public AllImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(context).inflate(R.layout.view_image_notice,parent,false);

        return new AllImageHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AllImageHolder holder, int position) {

        ImgDto currenitem= arrayListmg.get(position);

        String imgdata=currenitem.getImg();
        String date=currenitem.getDate();
        String title=currenitem.getTitle();
        holder.txtdata.setText(date);
        holder.title.setText(title);
        Picasso.with(context).load(imgdata).resize(800, 800).centerInside().placeholder(R.drawable.ic_launcher_foreground).into(holder.imgdata);


    }

    @Override
    public int getItemCount() {
        return arrayListmg.size();
    }

    class AllImageHolder extends RecyclerView.ViewHolder {

        ImageView imgdata;
        TextView txtdata,title;

        public AllImageHolder(View itemView) {
            super(itemView);

            imgdata=itemView.findViewById(R.id.imageview);
            txtdata=itemView.findViewById(R.id.dateImg);
            title=itemView.findViewById(R.id.titleimgall_notice);


        }
    }
}
