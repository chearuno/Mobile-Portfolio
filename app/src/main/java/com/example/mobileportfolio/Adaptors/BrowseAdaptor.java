package com.example.mobileportfolio.Adaptors;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.mobileportfolio.Fragments.Browse;
import com.example.mobileportfolio.Fragments.ViewFrag;
import com.example.mobileportfolio.MainActivity;
import com.example.mobileportfolio.Models.Browse_data;
import com.example.mobileportfolio.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BrowseAdaptor extends RecyclerView.Adapter<BrowseAdaptor.ViewHolder> {
    private List<Browse_data> myDataset;
    private List<Browse_data> myDatasettFiltered;
 //   private BrowseAdaptorListener listener;

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
            title = (TextView) v.findViewById(R.id.tite_text);
            year = (TextView) v.findViewById(R.id.category_text);
            tumbnail = (ImageView) v.findViewById(R.id.photo_thumb);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BrowseAdaptor(List<Browse_data> myDataset, Context context) {

        this.myDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BrowseAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.browse_list, parent, false);

        BrowseAdaptor.ViewHolder viewHolder =
                new BrowseAdaptor.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(BrowseAdaptor.ViewHolder holder, int position) {


        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Browse_data Browse_data = myDataset.get(position);
        Picasso.get()
                .load(Browse_data.geturi())
                .resize(200, 100)
               .centerInside()
                .placeholder(R.drawable.iconsloadpng)
                .error(R.drawable.errorcloud)
                .into(holder.tumbnail);
        holder.title.setText(Browse_data.getTitle());
        holder.year.setText(Browse_data.getcategory());
        final String id = Browse_data.getdocid();
        final String title = Browse_data.getTitle();
        final String category = Browse_data.getcategory();
        final String discrip = Browse_data.getdiscrip();
        final String imageURI = Browse_data.geturi();

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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


   // @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    myDatasettFiltered = myDataset;
                } else {
                    List<Browse_data> filteredList = new ArrayList<>();
                    for (Browse_data row : myDataset) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getcategory().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    myDatasettFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = myDatasettFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                myDatasettFiltered = (ArrayList<Browse_data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myDataset.size();
    }





}

