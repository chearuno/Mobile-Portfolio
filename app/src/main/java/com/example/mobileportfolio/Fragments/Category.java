package com.example.mobileportfolio.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mobileportfolio.Adaptors.BrowseAdaptor;
import com.example.mobileportfolio.Adaptors.CategeryAdaptor;
import com.example.mobileportfolio.MainActivity;
import com.example.mobileportfolio.Models.ItemData;
import com.example.mobileportfolio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wang.avi.AVLoadingIndicatorView;


import java.util.ArrayList;
import java.util.List;


public class Category extends Fragment {
    private Button save;
    private Context context;
    FirebaseFirestore db;
    private CategeryAdaptor catAdapter;
    private RecyclerView recyclerView;
    AVLoadingIndicatorView avi;
    public List<ItemData> catList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        getActivity().setTitle("Category");
        save = (Button) v.findViewById(R.id.button_add_cat);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_catData);

        avi = v.findViewById(R.id.avi);

        setCatlist();
        db = FirebaseFirestore.getInstance();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();

                fm.beginTransaction().replace(R.id.flContent, new Add_Category()).addToBackStack("null").commit();

            }
        });
        return v;
    }

    private void setCatlist() {
        // avi.show();
        db = FirebaseFirestore.getInstance();
        // DocumentReference docRef = db.collection("xxx").document("sf");
        catList = new ArrayList<>();

        db.collection("Category_data")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("Doc", document.getId() + " => " + document.getData());

                                final String category = document.getString("Category");
                                final String catid = document.getId();

                                ItemData data = new ItemData(category, catid);
                                catList.add(data);
                                addToAdapter();
                            }


                        } else {
                            Log.d("Doc", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void addToAdapter() {
        catAdapter = new CategeryAdaptor(catList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(catAdapter);
        avi.setVisibility(View.GONE);

    }


}
