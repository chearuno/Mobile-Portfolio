package com.example.mobileportfolio.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobileportfolio.Adaptors.BrowseAdaptor;
import com.example.mobileportfolio.Models.Browse_data;
import com.example.mobileportfolio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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


public class Browse extends Fragment {
    public List<Browse_data> myDataset = new ArrayList<>();
    private RecyclerView recyclerView;
    private BrowseAdaptor mAdapter;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;
    private Context context;
    private String imageuri;

    AVLoadingIndicatorView avi;
    private String DOWNLOAD_DIR = Environment.getExternalStoragePublicDirectory
            (Environment.DIRECTORY_DOWNLOADS).getPath();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        getActivity().setSupportActionBar(toolbar);
        avi = view.findViewById(R.id.avi);


        setDinner();
        db = FirebaseFirestore.getInstance();


        storage = FirebaseStorage.getInstance();

//        File fileNameOnDevice = new File(DOWNLOAD_DIR + "/" + "33b155d6-842f-45b9-a632-2126cc888fd9");
//
//        downloadRef.getFile(fileNameOnDevice).addOnSuccessListener(
//                new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                        Log.d("File RecylerView", "downloaded the file");
////                        Toast.makeText(context,
////                                "Downloaded the file",
////                                Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Log.e("File RecylerView", "Failed to download the file");
////                Toast.makeText(context,
////                        "Couldn't be downloaded",
////                        Toast.LENGTH_SHORT).show();
//            }
//        });


        recyclerView = (RecyclerView) view.findViewById(R.id.rv_browseData);
        return view;

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

                                final  String Title = document.getString("Title");
                                final String Category = document.getString("Category");
                                final String image = document.getString("image");
                                final String Docid = document.getId();
                                final String discrip = document.getString("Discription");

                                StorageReference storageRef = storage.getReference();
                                StorageReference downloadRef = storageRef.child("images/"+image);
                                downloadRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Log.d("Doc1",  " => " + uri);
                                        String asd = uri.toString();
                                       //return asd;
                                        Browse_data data = new Browse_data(Title, Category, image, Docid, discrip,asd);
                                        myDataset.add(data);
                                        addToAdapter();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                    }
                                });

                            }


                        } else {
                            Log.d("Doc", "Error getting documents: ", task.getException());
                        }
                    }
                });

//        islandRef = storageRef.child("images/island.jpg");
//
//        File localFile = File.createTempFile("images", "jpg");
//
//        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                // Local temp file has been created
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//            }
//        });


    }

    public void addToAdapter() {
        mAdapter = new BrowseAdaptor(myDataset, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        avi.setVisibility(View.GONE);

    }

}
