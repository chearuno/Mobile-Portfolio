package com.example.mobileportfolio.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.mobileportfolio.MainActivity;
import com.example.mobileportfolio.Models.ItemData;
import com.example.mobileportfolio.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class Add extends Fragment {
    public List<ItemData> CatItems = new ArrayList<>();
    private static final String TAG = "Fire base";
    private EditText inputTitle, inputCat, inputDisc;
    private Button btnSave;
    private RatingBar inputRate;
    private ImageView image_main;
    String currentFirebaseUser;
    private DatabaseReference dbRef;
    private FirebaseDatabase mFirebaseInstance;
    // private Firebase mRootRef;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageReference;

    private static final int RESULT_LOAD_IMAGE = 1;
    private static int REQUEST_TAKE_PHOTO = 1;
    private static final int CAMERA_REQUEST = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    String mCurrentPhotoPath;
    private Uri filePath;
    boolean isImageFitToScreen;
    private String selection = "";
    AVLoadingIndicatorView avi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view, container, false);
        btnSave = (Button) v.findViewById(R.id.btn_add);
        inputTitle = (EditText) v.findViewById(R.id.txt_title);
        inputCat = (EditText) v.findViewById(R.id.txt_category);
        inputDisc = (EditText) v.findViewById(R.id.txt_discription);
        inputRate = (RatingBar) v.findViewById(R.id.ratingBar);
        image_main = (ImageView) v.findViewById(R.id.imageView5);
        avi = v.findViewById(R.id.avi);

        Picasso.get().load("http://news.mit.edu/sites/mit.edu.newsoffice/files/images/2016/MIT-Earth-Dish_0.jpg").resize(50, 50)

                .placeholder(R.drawable.iconsloadpng).resize(50, 50)
                .error(R.drawable.errorcloud).resize(50, 50)
        .into(image_main);

        CatItems = new ArrayList<>();
        ItemData data = new ItemData("Nature");
        CatItems.add(data);
        data = new ItemData("macro");
        CatItems.add(data);
        data = new ItemData("Potrait");
        CatItems.add(data);


        db = FirebaseFirestore.getInstance();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //dbRef = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Uploading...");
                progressDialog.show();


                String Title = inputTitle.getText().toString();
                String Category = inputCat.getText().toString();
                String Discription = inputDisc.getText().toString();
                Float Rating = inputRate.getRating();
                String imageId = UUID.randomUUID().toString();

                Map<String, Object> city = new HashMap<>();
                city.put("Title", Title);
                city.put("UID", currentFirebaseUser);
                city.put("Category", Category);
                city.put("Discription", Discription);
                city.put("Rating", Rating);
                city.put("date", new Timestamp(new Date()));
                city.put("image", imageId);
                StorageReference ref = storageReference.child("images/" + imageId);
                ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });


                db.collection("Main_data").document()
                        .set(city)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //progressDialog.dismiss();
                                Toast.makeText(getActivity(), "DocumentSnapshot successfully written!", Toast.LENGTH_LONG).show();

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
        inputCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catagorylist();

            }
        });

        image_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }


        });

        image_main.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                camOrstorage();
                return false;

            }


        });

        return v;

    }


    private void catagorylist() {
       //final CharSequence[] items1 = {"Nature", "macro", "Potrait", "Landscape", "Arts", "mobile Capure", "Test 1"};
        final ArrayList<String> items = new ArrayList<String>();

//        for(ItemData temp : CatItems){
//            items.add(temp.getname());
//        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Catogary");

        builder.setSingleChoiceItems((ListAdapter) items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String che = items.get(which);
                inputCat.setText(che);
            }
        });
//        builder.setSingleChoiceItems(items1, -1, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int item) {
//                inputCat.setText(CatItems.get(item).getname());
//
//                dialog.dismiss();
//            }
//        });
        builder.show();
    }

    private void camOrstorage() {
        final CharSequence[] items = {"Camera", "Device"};


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Input Methord");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                selection = (items[item]).toString();
                selectType();

                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void selectType() {
        if (selection == "Camera") {
            dispatchTakePictureIntent();
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            //  if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST);


        } else {
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                image_main.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Log.i(TAG, "dispatchTakePictureIntent entered: ");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i(TAG, "IOException: " + ex);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                Log.i(TAG, "Got here: " + Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                Log.i(TAG, "Picture successfully saved: " + takePictureIntent);
            }
        }
        getActivity();
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
        Log.e("gallery ", "saved");
    }

}