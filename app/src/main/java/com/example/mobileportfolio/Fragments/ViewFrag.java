package com.example.mobileportfolio.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mobileportfolio.R;
import com.squareup.picasso.Picasso;




public class ViewFrag extends Fragment {
    String Id, Title, Discrip, Cat, imageUri;
    EditText inputTitle, inputDisc, inputCat;
    private ImageView image_view;
    private Button add,deleteB;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("View Image");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view, container, false);
        inputTitle = (EditText) v.findViewById(R.id.txt_title);
        inputDisc = (EditText) v.findViewById(R.id.txt_discription);
        inputCat = (EditText) v.findViewById(R.id.txt_category);
        image_view = (ImageView) v.findViewById(R.id.imageView5);

        add = (Button) v.findViewById(R.id.btn_add);
        add.setVisibility(v.GONE);

        deleteB = (Button) v.findViewById(R.id.btn_delte);
        deleteB.setVisibility(v.GONE);
        inputTitle.setFocusable(false);
        inputDisc.setFocusable(false);
        inputCat.setFocusable(false);

        Title = getArguments().getString("adTitle");
        Discrip = getArguments().getString("addiscrip");
        Cat = getArguments().getString("adCategory");
        imageUri = getArguments().getString("URI");


        inputTitle.setText(Title);
        inputDisc.setText(Discrip);
        inputCat.setText(Cat);
        Picasso.get()
                .load(imageUri)
                .resize(640, 480)
                .centerInside()
                .placeholder(R.drawable.iconsloadpng)
                .error(R.drawable.errorcloud)
                .into(image_view);

        return v;


    }


}







