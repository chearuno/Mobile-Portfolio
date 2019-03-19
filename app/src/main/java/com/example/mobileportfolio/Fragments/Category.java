package com.example.mobileportfolio.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mobileportfolio.MainActivity;
import com.example.mobileportfolio.R;


public class Category extends Fragment {
    private Button save;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        save = (Button) v.findViewById(R.id.button_add_cat);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();

                fm.beginTransaction().replace(R.id.flContent,new Add_Category()).addToBackStack("null").commit();

            }
        });
        return v;
    }


}
