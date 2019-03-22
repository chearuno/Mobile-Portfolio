package com.example.mobileportfolio.Fragments;


import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mobileportfolio.R;
//import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;



public class FullScreen extends Fragment {
    private TouchImageView image_view;
    String imageUri;
    boolean isImageFitToScreen;

//    Matrix matrix = new Matrix();
//    Matrix savedMatrix = new Matrix();
//    PointF startPoint = new PointF();
//    PointF midPoint = new PointF();
//    float oldDist = 1f;
//    static final int NONE = 0;
//    static final int DRAG = 1;
//    static final int ZOOM = 2;
//    int mode = NONE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.layout_fullscreen_image, container, false);
        image_view =  v.findViewById(R.id.imgDisplay);
        imageUri = getArguments().getString("imageURI");
        Log.d("111", imageUri);
        Picasso.get()
                .load(imageUri)
//                .resize(640, 480)
//                .centerInside()
                .placeholder(R.drawable.download)
                .error(R.drawable.error)
                .into(image_view);

        fullScreen();

//

//        image_view.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
//
//            @Override
//            public void onMove() {
//                PointF point = image_view.getScrollPosition();
//                RectF rect = image_view.getZoomedRect();
//                float currentZoom = image_view.getCurrentZoom();
//                boolean isZoomed = image_view.isZoomed();
////                scrollPositionTextView.setText("x: " + df.format(point.x) + " y: " + df.format(point.y));
////                zoomedRectTextView.setText("left: " + df.format(rect.left) + " top: " + df.format(rect.top)
////                        + "\nright: " + df.format(rect.right) + " bottom: " + df.format(rect.bottom));
////                currentZoomTextView.setText("getCurrentZoom(): " + currentZoom + " isZoomed(): " + isZoomed);
//            }
//        });

return v;
    }
    public void fullScreen() {

       // getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
       // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//set content view AFTER ABOVE sequence (to avoid crash)
       // getActivity().setContentView(R.layout.layout_fullscreen_image);

        // BEGIN_INCLUDE (get_current_ui_flags)
        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getActivity().getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("TAG", "Turning immersive mode mode off. ");
        } else {
            Log.i("TAG", "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getActivity().getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)
    }


    public void onWindowFocusChanged(boolean hasFocus) {
        super.getActivity().onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {

        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}