package com.example.sanjaya.imit;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends android.app.Fragment {

    //TextView Name_edittext, Branch_editext, Reg_no, year_ad;
    EditText Name_edittext, Branch_editext, Reg_no, year_ad,phn_editext;
    String name_str, Branch, Reg_no_str, year_str,phn_str;
    ImageView img_view;
    String localimage;
    //Bitmap bitmap;
    View v;
    Button buttonChoose;
    PrefernceManager prefernceManager;
    android.app.FragmentManager fm;
//    LayoutInflater inflater;
//    ViewGroup container;
//    Bundle savedInstanceState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        this.inflater=inflater;
//        this.container=container;
//        this.savedInstanceState=savedInstanceState;

        v = inflater.inflate(R.layout.fragment_profile, container, false);

        Name_edittext = v.findViewById(R.id.setName);
        Branch_editext = v.findViewById(R.id.setBranch);
        Reg_no = v.findViewById(R.id.setReg);
        year_ad = v.findViewById(R.id.setYear);
        phn_editext = v.findViewById(R.id.setPhn);

        fm=getFragmentManager();

        prefernceManager = new PrefernceManager(getActivity());
        name_str = prefernceManager.getString("name");
        Branch = prefernceManager.getString("department");
        Reg_no_str = prefernceManager.getString("reg");
        year_str = prefernceManager.getString("year");
        localimage = prefernceManager.getString("localimage");
        phn_str=prefernceManager.getString("phonedata");





        Name_edittext.setText(name_str);
        Branch_editext.setText(Branch);
        Reg_no.setText(Reg_no_str);
        year_ad.setText(year_str);
        phn_editext.setText(phn_str);
       // buttonChoose=v.findViewById(R.id.buttonChoose);

        img_view = v.findViewById(R.id.chooseData);

        if (localimage != null) {
            String[] split = localimage.substring(1, localimage.length()-1).split(", ");
            byte[] mByte = new byte[split.length];
            for (int i = 0; i < split.length; i++) {
                mByte[i] = Byte.parseByte(split[i]);
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(mByte, 0, mByte.length);
            img_view.setImageBitmap(bitmap);
        //    Toast.makeText(, "", Toast.LENGTH_SHORT).show();
        }



//        buttonChoose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
////                        .setAspectRatio(1, 1).start(getActivity());
//
//                //CropImage.startPickImageActivity(getActivity());
//
//                Intent intent = CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                       .setFixAspectRatio(true)
//                        .setAspectRatio(2,1)
//                       .getIntent(getActivity());
//                getActivity().startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
//
//
//            }
//        });



        return v;


    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        final CropImage.ActivityResult r = CropImage.getActivityResult(data);
//
//                Intent intent = CropImage.activity(imageUri)
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .setFixAspectRatio(true)
//                        .setAspectRatio(2,1)
//                        .getIntent(getActivity());
//                getActivity().startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
//
//
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//                selectedFilePath = resultUri.getPath();
//                imageView.setImageURI(resultUri);
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }
//    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //super.onActivityResult(requestCode, resultCode, data);
////        android.app.Fragment fragment = fm.findFragmentById(R.id.container);
////        fragment.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            final CropImage.ActivityResult r = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//
////                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
////                progressDialog.setMessage("Wait while We are updating your Profile Picture..");
////                progressDialog.setTitle("Please Wait");
////                progressDialog.show();
//
//
//                Uri u = r.getUri();
//                File f = new File(u.getPath());
//                Bitmap b = null;
//                try {
//                    b = new Compressor(getActivity())
//                            .compressToBitmap(f);
//                } catch (Exception e) {
//                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//
//                ByteArrayOutputStream ba = new ByteArrayOutputStream();
//                b.compress(Bitmap.CompressFormat.JPEG, 80, ba);
//                byte[] mByte = ba.toByteArray();
//
//
//                ImageView img_view1 = v.findViewById(R.id.chooseData);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(mByte, 0, mByte.length);
//               // bitmap1 = Bitmap.createScaledBitmap(bitmap,img_view.getWidth(),img_view.getHeight(),true);
//                img_view1.setImageBitmap(bitmap);
//              // prefernceManager.putString("localimage",Arrays.toString(mByte));
//
//
//
//            }
//        }
//
//
//
//    }


}
