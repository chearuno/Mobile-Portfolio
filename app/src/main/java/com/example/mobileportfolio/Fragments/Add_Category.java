package com.example.mobileportfolio.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileportfolio.MainActivity;
import com.example.mobileportfolio.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Map;


public class Add_Category extends Fragment {
    private Button save;
    private EditText inputct;
    FirebaseFirestore db;
    AVLoadingIndicatorView avi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_addcatogary, container, false);
        db = FirebaseFirestore.getInstance();
        save = (Button) v.findViewById(R.id.btn_save_catogary);
        inputct = (EditText) v.findViewById(R.id.txt_category);
        avi = v.findViewById(R.id.avi);
        avi.hide();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avi.show();
                String catogary = inputct.getText().toString();

                Map<String, Object> cat_data = new HashMap<>();
                cat_data.put("Category", catogary);

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
