package com.example.mobileportfolio.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobileportfolio.MainActivity;
import com.example.mobileportfolio.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ViewFragment extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText inputTitle, inputCat, inputDisc;
    private Button btnSave;
    private DatabaseReference dbRef;
    private FirebaseDatabase mFirebaseInstance;
    // private Firebase mRootRef;

    private String userId = "Chetha";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view, container, false);
        btnSave = (Button) v.findViewById(R.id.btn_add);
        inputTitle = (EditText) v.findViewById(R.id.txt_title);
        inputCat = (EditText) v.findViewById(R.id.txt_category);
        inputDisc = (EditText) v.findViewById(R.id.txt_discription);


        dbRef = FirebaseDatabase.getInstance().getReference();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Val = inputTitle.getText().toString();
                Log.e("TEXT", Val);
                dbRef.child("asd").child("Title").setValue(Val);
                dbRef.child("asd").child("Cat").setValue("text   " + Val);
            }
        });
        inputCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catagorylist();

            }
        });

        return v;

    }

    private void catagorylist() {
        final CharSequence[] items = {"Nature", "macro", "Potrait", "Landscape", "Arts", "mobile Capure","Test 1"};


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Catogary");

        builder.setSingleChoiceItems(items,  -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                inputCat.setText( items[item]);

                dialog.dismiss();
            }
        });
        builder.show();
    }
}