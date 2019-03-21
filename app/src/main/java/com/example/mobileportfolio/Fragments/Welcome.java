package com.example.mobileportfolio.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobileportfolio.R;
import com.squareup.picasso.Picasso;

public class Welcome extends Fragment {


    Animation uptodown,downtoup,uptodownspeed;
    private Button browse;
   private ImageView iamge;
    private TextView text;


    public ImageView oneimage ,twoimage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Mobile Portfolio");
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_welcome, container, false);
//        oneimage = (ImageView) v.findViewById(R.id.imageView3);
//        twoimage = (ImageView) v.findViewById(R.id.imageView2);
//
//        Picasso.get()
//                .load(R.drawable.onetrim)
//                .resize(200, 100)
//                .centerInside()
//                .into(oneimage);
        browse = (Button) v.findViewById(R.id.button_brow);
        iamge = (ImageView) v.findViewById(R.id.imageView3);
        text = (TextView) v.findViewById(R.id.textView5);

        uptodown = AnimationUtils.loadAnimation(getActivity(),R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(getActivity(),R.anim.downtoup);
        uptodownspeed = AnimationUtils.loadAnimation(getActivity(),R.anim.uptodownspeed);
       // l1.setAnimation(uptodown);
        iamge.setAnimation(downtoup);
        browse.setAnimation(uptodownspeed);
        text.setAnimation(uptodown);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();

                fm.beginTransaction().replace(R.id.flContent,new Browse()).addToBackStack("null").commit();

            }
        });        return v;
    }


}
