package com.example.mobileportfolio.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mobileportfolio.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;


public class ViewFrag extends Fragment {
    String Id,Title,Discrip,Cat;
    EditText inputTitle,inputDisc,inputCat;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view, container, false);
        inputTitle = (EditText) v.findViewById(R.id.txt_title);
        inputDisc = (EditText) v.findViewById(R.id.txt_discription);
        inputCat = (EditText) v.findViewById(R.id.txt_category);



            Title = getArguments().getString("adTitle");
            Discrip = getArguments().getString("addiscrip");
            Cat = getArguments().getString("adCategory");


        inputTitle.setText(Title);
        inputDisc.setText(Discrip);
        inputCat.setText(Cat);

        return v;


    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//
////            image_main.setImageBitmap(BitmapFactory.decodeFile(picturePath));
////            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
////            fragmentTransaction.replace(R.id.main_content,new com.example.mobileportfolio.Fragments.View());
////            fragmentTransaction.commit();
//
//        } if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            image_main.setImageBitmap(photo);
//
//
//        }
//
//
//    }
//
////    @Override
////    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
////        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
////            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
////                Intent cameraIntent = new
////                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivityForResult(cameraIntent, CAMERA_REQUEST);
////            } else {
////                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
////            }
////
////        }
////    }
//
//
////            public void onActivityResult ( int requestCode, int resultCode, Intent data){
////                if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
////                    Bitmap photo = (Bitmap) data.getExtras().get("data");
////                    image_main.setImageBitmap(photo);
////                    galleryAddPic();
////
////
////                }
////
////            }
//
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  // prefix
//                ".jpg",         // suffix
//                storageDir      // directory
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
//        return image;
//    }
//
//    private void dispatchTakePictureIntent() {
//        Log.i(TAG, "dispatchTakePictureIntent entered: ");
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//                Log.i(TAG, "IOException: " + ex);
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(photoFile));
//                Log.i(TAG, "Got here: " + Uri.fromFile(photoFile));
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//                Log.i(TAG, "Picture successfully saved: " + takePictureIntent);
//            }
//        }
//        getActivity();
//    }
//
//    private void galleryAddPic() {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(mCurrentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        getActivity().sendBroadcast(mediaScanIntent);
//        Log.e("gallery ", "saved");
//    }

}







