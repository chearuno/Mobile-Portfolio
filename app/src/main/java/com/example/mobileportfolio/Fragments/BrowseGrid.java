package com.example.mobileportfolio.Fragments;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mobileportfolio.Adaptors.BrowseAdaptor;
import com.example.mobileportfolio.Adaptors.BrowseGridAdaptor;
import com.example.mobileportfolio.Models.Browse_data;
import com.example.mobileportfolio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;


public class BrowseGrid extends Fragment {
    public List<Browse_data> myDataset = new ArrayList<>();
    public List<Browse_data> myDatasetfilter;
    private RecyclerView recyclerView;
    private BrowseGridAdaptor mAdapter;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;
    private Context context;
    private String imageuri;
    private Button btngrid;
    private String selection = "";
    private SearchView searchView;
    int spancout;
    private String selectionfr;
    private String selectedgrid;
    private Button btncat, btnaz;


    AVLoadingIndicatorView avi;
    private String DOWNLOAD_DIR = Environment.getExternalStoragePublicDirectory
            (Environment.DIRECTORY_DOWNLOADS).getPath();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Browse");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse_grid, container, false);
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        getActivity().setSupportActionBar(toolbar);
        avi = view.findViewById(R.id.avi);
        btngrid = (Button) view.findViewById(R.id.button_grid);
        btncat = (Button) view.findViewById(R.id.button_cat);
        btnaz = (Button) view.findViewById(R.id.A_Z);

        btngrid.setText("List");
        setHasOptionsMenu(true);

        setDatalist();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        selectedgrid = getArguments().getString("selgrid");

        if (selectedgrid == "one") {
            spancout = 1;
        } else if (selectedgrid == "two") {
            spancout = 2;
        } else if (selectedgrid == "three") {
            spancout = 3;
        } else if (selectedgrid == "four") {
            spancout = 4;
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.rvg_browseData);

        btngrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = (getActivity()).getSupportFragmentManager();
                Browse addFragment = new Browse();
                fm.beginTransaction().replace(R.id.flContent, addFragment).addToBackStack(null).commit();


            }
        });

        btngrid.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                gridLayout();
                return false;

            }


        });

        btnaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sortnow();

            }
        });

        btncat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catagorylist();
            }
        });

        return view;

    }

    private void catagorylist() {
        // retrieveValuesFromListMethod1(list);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Catogary");


        Log.e("CAT SIZE", "--> " + myDataset.size());

        final ArrayList<String> strBrandList = new ArrayList<String>();
        for (int i = 0; i < myDataset.size(); i++) {
            strBrandList.add(myDataset.get(i).getcategory());
        }

        final CharSequence[] chars = strBrandList.toArray(new CharSequence[strBrandList.size()]);

        builder.setItems(chars, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                selection = (chars[item]).toString();
                mAdapter.getFilter().filter(selection);
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void Sortnow() {
        final CharSequence[] items = {"A-Z", "Z-A", "Date Asc", "Date Dsc"};


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Sort Methord");

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


        } else {

        }
    }

    private void gridLayout() {
        final CharSequence[] items = {"One Row", "Two Rows", "Three Rows", "Four Rows"};


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Grid Layout");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                selection = (items[item]).toString();
                selectTypegrid();

                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void selectTypegrid() {
        if (selection == "One Row") {
            FragmentManager fm = (getActivity()).getSupportFragmentManager();
            selectionfr = "one";
            Bundle bundle = new Bundle();
            bundle.putString("selgrid", selectionfr);


            BrowseGrid addFragment = new BrowseGrid();
            addFragment.setArguments(bundle);
            fm.beginTransaction().replace(R.id.flContent, addFragment).addToBackStack(null).commit();


        } else if (selection == "Two Rows") {
            FragmentManager fm = (getActivity()).getSupportFragmentManager();
            selectionfr = "two";
            Bundle bundle = new Bundle();
            bundle.putString("selgrid", selectionfr);


            BrowseGrid addFragment = new BrowseGrid();
            addFragment.setArguments(bundle);
            fm.beginTransaction().replace(R.id.flContent, addFragment).addToBackStack(null).commit();


        } else if (selection == "Three Rows") {
            FragmentManager fm = (getActivity()).getSupportFragmentManager();
            selectionfr = "three";
            Bundle bundle = new Bundle();
            bundle.putString("selgrid", selectionfr);


            BrowseGrid addFragment = new BrowseGrid();
            addFragment.setArguments(bundle);
            fm.beginTransaction().replace(R.id.flContent, addFragment).addToBackStack(null).commit();


        } else if (selection == "Four Rows") {
            FragmentManager fm = (getActivity()).getSupportFragmentManager();
            selectionfr = "four";
            Bundle bundle = new Bundle();
            bundle.putString("selgrid", selectionfr);


            BrowseGrid addFragment = new BrowseGrid();
            addFragment.setArguments(bundle);
            fm.beginTransaction().replace(R.id.flContent, addFragment).addToBackStack(null).commit();


        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbarmenu, menu);
        super.onCreateOptionsMenu(menu, inflater);


        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getActivity(), "Data", Toast.LENGTH_LONG).show();
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //Toast.makeText(getActivity(), "deleting Data", Toast.LENGTH_LONG).show();
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
    }


    private void setDatalist() {
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
                                        Browse_data data = new Browse_data(Title, Category, image, Docid, discrip, asd);
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
                           // Toast.makeText(getActivity(), "Error getting Data", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

//    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
//        this.spanCount = spanCount;
//        this.spacing = spacing;
//        this.includeEdge = includeEdge;
//    }
//
//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        int position = parent.getChildAdapterPosition(view); // item position
//        int column = position % spanCount; // item column
//
//        if (includeEdge) {
//            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
//            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
//
//            if (position < spanCount) { // top edge
//                outRect.top = spacing;
//            }
//            outRect.bottom = spacing; // item bottom
//        } else {
//            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
//            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
//            if (position >= spanCount) {
//                outRect.top = spacing; // item top
//            }
//        }
//    }


    public void addToAdapter() {
//        int spanCount = 3; // 3 columns
//        int spacing = 50; // 50px
//        boolean includeEdge = false;
        mAdapter = new BrowseGridAdaptor(myDataset, getContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), spancout);

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        avi.setVisibility(View.GONE);

    }

}
