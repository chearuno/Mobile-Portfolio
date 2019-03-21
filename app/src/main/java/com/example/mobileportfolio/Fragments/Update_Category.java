package com.example.mobileportfolio.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileportfolio.MainActivity;
import com.example.mobileportfolio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class Update_Category extends Fragment {
    private Button save, delete;
    private EditText inputct;
    FirebaseFirestore db;
    AVLoadingIndicatorView avi;
    private Context context;
    String currentFirebaseUser, catname, catdocid;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_addcatogary, container, false);

        getActivity().setTitle("Update Category");
        db = FirebaseFirestore.getInstance();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        save = (Button) v.findViewById(R.id.btn_save_catogary);
        delete = (Button) v.findViewById(R.id.btn_delete_catogary);
        inputct = (EditText) v.findViewById(R.id.txt_category);
        avi = v.findViewById(R.id.avi);
        avi.hide();
        save.setText("Update");

        catname = getArguments().getString("adName");
        catdocid = getArguments().getString("adcatid");
        inputct.setText(catname);
        delete.setText("Delete");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdelete();
            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                avi.show();

                DocumentReference docRef = db.collection("Category_data").document(catdocid);
                String catogary = inputct.getText().toString();

                Map<String, Object> cat_data = new HashMap<>();
                cat_data.put("Category", catogary);
                cat_data.put("UID", currentFirebaseUser);
                cat_data.put("date", new Timestamp(new Date()));

                docRef.update(cat_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "Category successfully Updated!", Toast.LENGTH_LONG).show();
                        avi.hide();
                        inputct.setText("");
                    }
                });
            }

        });
        return v;

    } public void alertdelete()
    {
        AlertDialog.Builder alertDialog2 = new
                AlertDialog.Builder(
                getActivity());

        // Setting Dialog Title
        alertDialog2.setTitle("Confirm Delete");

        // Setting Dialog Message
        alertDialog2.setMessage("Are you sure you want to Delete?");

        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        deletecat();

                    }
                });

        // Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
//                        Toast.makeText(getApplicationContext(),
//                                "You clicked on NO", Toast.LENGTH_SHORT)
//                                .show();
                        dialog.cancel();
                    }
                });

        // Showing Alert Dialog
        alertDialog2.show();


    }

    public void deletecat() {
        avi.show();
        db = FirebaseFirestore.getInstance();
        // DocumentReference docRef = db.collection("xxx").document("sf");
        // catList = new ArrayList<>();

        db.collection("Category_data").document(catdocid).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firebase", "Category successfully deleted!");
                        Toast.makeText(getActivity(), "Category successfully deleted!", Toast.LENGTH_LONG).show();
                        avi.hide();
                        FragmentManager fm = (getActivity()).getSupportFragmentManager();
                        Category addFragment = new Category();
                        fm.beginTransaction().replace(R.id.flContent, addFragment).addToBackStack("null").commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firebase", "Error deleting document", e);
                        Toast.makeText(getActivity(), "Error deleting Category!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
