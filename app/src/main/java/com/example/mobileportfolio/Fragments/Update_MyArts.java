package com.example.mobileportfolio.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.mobileportfolio.LoginActivity;
import com.example.mobileportfolio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class Update_MyArts extends Fragment {
    private Button save, delete;
    String imagename, Title, Discrip, Cat, imageUri;
    EditText inputTitle, inputDisc, inputCat;
    private ImageView image_view;
    FirebaseFirestore db;
    AVLoadingIndicatorView avi;
    private Context context;
    String currentFirebaseUser, catname, mydocid;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filePath;
    private RatingBar inputRate;
    private String selection = "";
    private static final int RESULT_LOAD_IMAGE = 1;
    private static int REQUEST_TAKE_PHOTO = 1;
    private static final int CAMERA_REQUEST = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    String mCurrentPhotoPath;
    private static final String TAG = "Fire base";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view, container, false);

        getActivity().setTitle("Update MyArts");

        db = FirebaseFirestore.getInstance();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //dbRef = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        inputTitle = (EditText) v.findViewById(R.id.txt_title);
        inputDisc = (EditText) v.findViewById(R.id.txt_discription);
        inputCat = (EditText) v.findViewById(R.id.txt_category);
        image_view = (ImageView) v.findViewById(R.id.imageView5);
        inputRate = (RatingBar) v.findViewById(R.id.ratingBar);

        save = (Button) v.findViewById(R.id.btn_add);
        delete = (Button) v.findViewById(R.id.btn_delte);

        avi = v.findViewById(R.id.avi);
        // avi.hide();
        save.setText("Update");

        Title = getArguments().getString("adTitlemy");
        Discrip = getArguments().getString("addiscripmy");
        Cat = getArguments().getString("adCategorymy");
        imageUri = getArguments().getString("URImy");
        mydocid = getArguments().getString("adIdmy");
        imagename = getArguments().getString("Imagemy");

        inputTitle.setText(Title);
        inputDisc.setText(Discrip);
        inputCat.setText(Cat);
        Picasso.get()
                .load(imageUri)
                .resize(640, 480)
                .centerInside()
                .placeholder(R.drawable.iconsloadpng)
                .error(R.drawable.errorcloud)
                .into(image_view);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertsignout();
            }

        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputTitle == null) {
                    Toast.makeText(getActivity(), "Title, Category fields and image should be add..", Toast.LENGTH_LONG).show();
                } else {
                    DocumentReference docRef = db.collection("Main_data").document(mydocid);
                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    progressDialog.setCancelable(false);


                    String Title = inputTitle.getText().toString();
                    String Category = inputCat.getText().toString();
                    String Discription = inputDisc.getText().toString();
                    Float Rating = inputRate.getRating();
                    String imageId = UUID.randomUUID().toString();

//                    Map<String, Object> get_data_with_image = new HashMap<>();
//                    get_data_with_image.put("Title", Title);
//                    get_data_with_image.put("Category", Category);
//                    get_data_with_image.put("Discription", Discription);
//                    get_data_with_image.put("Rating", Rating);
//                    get_data_with_image.put("date", new Timestamp(new Date()));
//                    get_data_with_image.put("image", imageId);

                    Map<String, Object> get_data_only = new HashMap<>();
                    get_data_only.put("Title", Title);

                    get_data_only.put("Category", Category);
                    get_data_only.put("Discription", Discription);
                    get_data_only.put("Rating", Rating);
                    get_data_only.put("date", new Timestamp(new Date()));



                    if (filePath != null) {


                        StorageReference ref = storageReference.child("images/" + imagename);
                        ref.delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Firebase", "DocumentSnapshot successfully deleted!");
                                        Toast.makeText(getActivity(), "Last Image successfully deleted! ", Toast.LENGTH_LONG).show();

                                        avi.hide();
//                                        FragmentManager fm = (getActivity()).getSupportFragmentManager();
//                                        MyArts addFragment = new MyArts();
//                                        fm.beginTransaction().replace(R.id.flContent, addFragment).addToBackStack("null").commit();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Firebase", "Error deleting document", e);
                                        Toast.makeText(getActivity(), "Error deleting Last Iamge", Toast.LENGTH_LONG).show();

                                    }
                                });


                        ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {


                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                                Picasso.get().load("http://news.mit.edu/sites/mit.edu.newsoffice/files/images/2016/MIT-Earth-Dish_0.jpg").resize(50, 50)
                                        .placeholder(R.drawable.iconsloadpng).resize(50, 50)
                                        .error(R.drawable.errorcloud)
                                        .into(image_view);
                                filePath = null;
                                FragmentManager fm = (getActivity()).getSupportFragmentManager();
                                MyArts addFragment = new MyArts();
                                fm.beginTransaction().replace(R.id.flContent, addFragment).addToBackStack("null").commit();

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
                                        progressDialog.setMessage("Uploading..  " + (int) progress + "%");
                                    }
                                });

                        docRef.update(get_data_only).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "Data successfully Updated!", Toast.LENGTH_LONG).show();
                                avi.hide();


                            }
                        });
                        docRef.update(get_data_only).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Firebase", "Error deleting document", e);
                                Toast.makeText(getActivity(), "Error deleting Data", Toast.LENGTH_LONG).show();
                            }
                        });

                    } else {
                        progressDialog.dismiss();

                        docRef.update(get_data_only).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "Data successfully Updated!", Toast.LENGTH_LONG).show();
                                avi.hide();
                                FragmentManager fm = (getActivity()).getSupportFragmentManager();
                                MyArts addFragment = new MyArts();
                                fm.beginTransaction().replace(R.id.flContent, addFragment).addToBackStack("null").commit();


                            }
                        });
                        docRef.update(get_data_only).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Firebase", "Error deleting document", e);
                                Toast.makeText(getActivity(), "Error deleting Data", Toast.LENGTH_LONG).show();

                            }
                        });

                    }

                }
            }
        });

        inputCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catagorylist();

            }
        });

        image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camOrstorage();
            }


        });

        image_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //   camOrstorage();
                return false;

            }


        });
        return v;

    }

    private void catagorylist() {
        // retrieveValuesFromListMethod1(list);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Catogary");

//        builder.setItems(catList, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int item) {
//                selection = (catList[item]).toString();
//                selectType();
//                dialog.dismiss();
//            }
//        });


        builder.show();
    }
//    public void retrieveValuesFromListMethod1(List list)
//    {
//        Iterator itr = list.iterator();
//        while(itr.hasNext())
//        {
//            //System.out.println(itr.next());
//            final CharSequence[] set = {itr.next().toString()};
//        }
//    }

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
                image_view.setImageBitmap(bitmap);
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
                // startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                Log.i(TAG, "Picture successfully saved: " + takePictureIntent);
                Toast.makeText(getActivity(), "Picture successfully saved: " + takePictureIntent, Toast.LENGTH_LONG).show();
            }
        }
        getActivity();
    }

    public void alertsignout() {
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
        //imageUri = getArguments().getString("URImy");
        StorageReference ref = storageReference.child("images/" + imagename);
        // DocumentReference docRef = db.collection("xxx").document("sf");
        // catList = new ArrayList<>();

        ref.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firebase", "DocumentSnapshot successfully deleted!");
                        Toast.makeText(getActivity(), "Image successfully deleted! ", Toast.LENGTH_LONG).show();

                        avi.hide();
                        FragmentManager fm = (getActivity()).getSupportFragmentManager();
                        MyArts addFragment = new MyArts();
                        fm.beginTransaction().replace(R.id.flContent, addFragment).addToBackStack("null").commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firebase", "Error deleting document", e);
                        Toast.makeText(getActivity(), "Error deleting Iamge", Toast.LENGTH_LONG).show();

                    }
                });

        db.collection("Main_data").document(mydocid).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firebase", "DocumentSnapshot successfully deleted!");
                        Toast.makeText(getActivity(), "Data successfully deleted!", Toast.LENGTH_LONG).show();
                        avi.hide();
//                        FragmentManager fm = (getActivity()).getSupportFragmentManager();
//                        MyArts addFragment = new MyArts();
//                        fm.beginTransaction().replace(R.id.flContent, addFragment).addToBackStack("null").commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firebase", "Error deleting document", e);
                        Toast.makeText(getActivity(), "Error deleting Image", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
