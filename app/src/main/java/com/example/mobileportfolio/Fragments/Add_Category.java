package com.example.mobileportfolio.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileportfolio.MainActivity;
import com.example.mobileportfolio.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Add_Category extends Fragment {
    private Button save,delete;
    private EditText inputct;
    FirebaseFirestore db;
    AVLoadingIndicatorView avi;
    String currentFirebaseUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_addcatogary, container, false);

        getActivity().setTitle("Add Category");

        db = FirebaseFirestore.getInstance();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        save = (Button) v.findViewById(R.id.btn_save_catogary);
        delete =(Button) v.findViewById(R.id.btn_delete_catogary);
        inputct = (EditText) v.findViewById(R.id.txt_category);
        avi = v.findViewById(R.id.avi);
        avi.hide();
        delete.setVisibility(v.GONE);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                avi.show();
                String catogary = inputct.getText().toString();

                Map<String, Object> cat_data = new HashMap<>();
                cat_data.put("Category", catogary);
                cat_data.put("UID", currentFirebaseUser);
                cat_data.put("date", new Timestamp(new Date()));

                db.collection("Category_data").document()
                        .set(cat_data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //progressDialog.dismiss();
                                Toast.makeText(getActivity(), "DocumentSnapshot successfully written!", Toast.LENGTH_LONG).show();
                                avi.hide();
                                inputct.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Error writing document", Toast.LENGTH_LONG).show();

                            }
                        });

            }
        });

        return v;
    }




}
