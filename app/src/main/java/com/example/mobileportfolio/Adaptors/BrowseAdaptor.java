package com.example.mobileportfolio.Adaptors;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.mobileportfolio.Models.Browse_data;
import com.example.mobileportfolio.R;

import java.util.List;

public class BrowseAdaptor extends RecyclerView.Adapter<BrowseAdaptor.ViewHolder> {
    private List<Browse_data> myDataset;
   // private FragmentManager fm;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title, year, genre;
        // public View layout;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tite_text);
            year = (TextView) v.findViewById(R.id.category_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BrowseAdaptor(List<Browse_data> myDataset) {

        this.myDataset = myDataset;
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
        Browse_data Browse_data = myDataset.get(position);
        holder.title.setText(Browse_data.getTitle());
        holder.year.setText(Browse_data.getYear());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Doc", "AsfDSfsfjdsfkjdsfgkjDSfgdsfdsfdsgfs");
            }
        });

    }
//    private void editClassifiedAd(String adId){
//        FragmentManager fm = ((ClassifiedsActivity)context).getSupportFragmentManager();
//
//        Bundle bundle=new Bundle();
//        bundle.putString("adId", adId);
//
//        AddClassifiedFragment addFragment = new AddClassifiedFragment();
//        addFragment.setArguments(bundle);
//
//        fm.beginTransaction().replace(R.id.adds_frame, addFragment).commit();
//    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myDataset.size();
    }

}

