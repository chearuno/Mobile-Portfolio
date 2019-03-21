package com.example.mobileportfolio.Adaptors;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileportfolio.Fragments.ViewFrag;
import com.example.mobileportfolio.MainActivity;
import com.example.mobileportfolio.Models.Browse_data;
import com.example.mobileportfolio.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BrowseGridAdaptor extends RecyclerView.Adapter<BrowseGridAdaptor.ViewHolder> {
    private List<Browse_data> myDataset;

    private Context context;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title, year, genre;
        public ImageView tumbnail;
        // public View layout;

        public ViewHolder(View v) {
            super(v);
          // title = (TextView) v.findViewById(R.id.textView4);
            //year = (TextView) v.findViewById(R.id.category_text);
            tumbnail = (ImageView) v.findViewById(R.id.grid_tumb);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BrowseGridAdaptor(List<Browse_data> myDataset, Context context) {

        this.myDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BrowseGridAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.browse_grid, parent, false);

        BrowseGridAdaptor.ViewHolder viewHolder =
                new BrowseGridAdaptor.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(BrowseGridAdaptor.ViewHolder holder, int position) {


        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Browse_data Browse_data = myDataset.get(position);

        Picasso.get()
                .load(Browse_data.geturi())
                .resize(640, 480)
               .centerCrop()
                .placeholder(R.drawable.iconsloadpng)
                .error(R.drawable.errorcloud)
                .into(holder.tumbnail);
//        holder.title.setText(Browse_data.getTitle());
        //holder.year.setText(Browse_data.getcategory());
        final String id = Browse_data.getdocid();
        final String title = Browse_data.getTitle();
        final String category = Browse_data.getcategory();
        final String discrip = Browse_data.getdiscrip();
        final String imageURI = Browse_data.geturi();

        holder.tumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Doc", "AsfDSfsfjdsfkjdsfgkjDSfgdsfdsfdsgfs");
//                FragmentManager manager = ((MainActivity)context).getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.browse, ViewFrag);
//                transaction.commit();
                toViewFrag(id,title,category,discrip,imageURI);
            }
        });

    }
    private void toViewFrag(String adId, String Title, String Category,String discrip, String imageuRI){
        FragmentManager fm = ((MainActivity)context).getSupportFragmentManager();

      Bundle bundle=new Bundle();
       bundle.putString("adId", adId);
        bundle.putString("adTitle", Title);
        bundle.putString("adCategory", Category);
        bundle.putString("addiscrip", discrip);
        bundle.putString("URI", imageuRI);


        ViewFrag addFragment = new ViewFrag();
       addFragment.setArguments(bundle);

        fm.beginTransaction().replace(R.id.flContent, addFragment).addToBackStack(null).commit();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myDataset.size();
    }

}

