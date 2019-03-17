package com.example.mobileportfolio.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.mobileportfolio.Adaptors.BrowseAdaptor;
import com.example.mobileportfolio.Adaptors.ItemData;
import com.example.mobileportfolio.Models.Browse_data;
import com.example.mobileportfolio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;


public class Browse extends Fragment {
    public List<Browse_data> myDataset = new ArrayList<>();
    private RecyclerView recyclerView;
    private BrowseAdaptor mAdapter;
    FirebaseFirestore db;
    FirebaseStorage storage;

    AVLoadingIndicatorView avi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        getActivity().setSupportActionBar(toolbar);
        avi =view.findViewById(R.id.avi);

        prepareMovieData();
        setDinner();
        db = FirebaseFirestore.getInstance();


        recyclerView = (RecyclerView) view.findViewById(R.id.rv_browseData);
                return view;

    }

    private void prepareMovieData() {
        Browse_data data = new Browse_data("Mad Max: Fury Road", "Action & Adventure", "2015");
        myDataset.add(data);

        data = new Browse_data("Inside Out", "Animation, Kids & Family", "2015");
        myDataset.add(data);
        // mAdapter.notifyDataSetChanged();


    }

    private void setDinner() {
         avi.show();
        db = FirebaseFirestore.getInstance();
        // DocumentReference docRef = db.collection("xxx").document("sf");
        myDataset = new ArrayList<>();
        db.collection("Main_data")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("Doc", document.getId() + " => " + document.getData());

                                String Title = document.getString("Title");
                                String Category = document.getString("Category");


                                Browse_data data = new Browse_data(Title, "Action & Adventure", Category);
                                myDataset.add(data);
                            }
                            addToAdapter();

                        } else {
                            Log.d("Doc", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    private void addToAdapter() {
        mAdapter = new BrowseAdaptor(myDataset);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        avi.setVisibility(View.GONE);

    }

}
