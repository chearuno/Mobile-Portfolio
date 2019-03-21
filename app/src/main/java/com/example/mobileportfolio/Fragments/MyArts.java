package com.example.mobileportfolio.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Toast;

import com.example.mobileportfolio.Adaptors.BrowseAdaptor;
import com.example.mobileportfolio.Adaptors.MyitemAdaptor;
import com.example.mobileportfolio.Models.Browse_data;
import com.example.mobileportfolio.Models.Myarts_data;
import com.example.mobileportfolio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MyArts extends Fragment {
    public List<Myarts_data> myDataset = new ArrayList<>();
    public List<Myarts_data> myDatasetfilter;
    private RecyclerView recyclerView;
    private MyitemAdaptor mAdapter;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;
    private Context context;
    private String imageuri;
    String currentFirebaseUser;
    private Button cat,grid,az;

    AVLoadingIndicatorView avi;
    private String DOWNLOAD_DIR = Environment.getExternalStoragePublicDirectory
            (Environment.DIRECTORY_DOWNLOADS).getPath();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("My Arts");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        getActivity().setSupportActionBar(toolbar);
        avi = view.findViewById(R.id.avi);
        cat = view.findViewById(R.id.button_cat);
        grid = view.findViewById(R.id.button_grid);
        az = view.findViewById(R.id.A_Z);

        cat.setVisibility(View.GONE);
        grid.setVisibility(View.GONE);
        az.setVisibility(View.GONE);
        setHasOptionsMenu(true);

        setDatalist();
        db = FirebaseFirestore.getInstance();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        storage = FirebaseStorage.getInstance();


        recyclerView = (RecyclerView) view.findViewById(R.id.rv_browseData);
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.toolbarmenu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }

    private void setDatalist() {
        avi.show();
        db = FirebaseFirestore.getInstance();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("Doc", currentFirebaseUser);
        // DocumentReference docRef = db.collection("xxx").document("sf");
        myDataset = new ArrayList<>();

        db.collection("Main_data").whereEqualTo("UID",currentFirebaseUser)

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                    Log.d("Doc", document.getId() + " => " + document.getData());

                                    final String Title = document.getString("Title");
                                    final String Category = document.getString("Category");
                                    final String image = document.getString("image");
                                    final String Docid = document.getId();
                                    final String discrip = document.getString("Discription");

                                    StorageReference storageRef = storage.getReference();
                                    StorageReference downloadRef = storageRef.child("images/" + image);
                                    downloadRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Log.d("Doc1", " => " + uri);
                                            String asd = uri.toString();
                                            //return asd;
                                            Myarts_data data = new Myarts_data(Title, Category, image, Docid, discrip, asd);
                                            myDataset.add(data);
                                            addToAdapter();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                        }
                                    });


                                            } else{
                                            Log.d("Doc", "Error getting documents: ", task.getException());
                            Toast.makeText(getActivity(), "Error deleting Data", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                 });


    }

    public void addToAdapter() {
        mAdapter = new MyitemAdaptor(myDataset, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        avi.setVisibility(View.GONE);

    }

}
