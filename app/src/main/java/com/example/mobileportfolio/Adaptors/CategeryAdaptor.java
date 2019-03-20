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

import com.example.mobileportfolio.Fragments.Add_Category;
import com.example.mobileportfolio.Fragments.Update_Category;
import com.example.mobileportfolio.Fragments.ViewFrag;
import com.example.mobileportfolio.MainActivity;
import com.example.mobileportfolio.Models.Browse_data;
import com.example.mobileportfolio.Models.ItemData;
import com.example.mobileportfolio.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategeryAdaptor extends RecyclerView.Adapter<CategeryAdaptor.ViewHolder> {
    private List<ItemData> catList;

    private Context context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView cat_name;

        // public View layout;

        public ViewHolder(View v) {
            super(v);
            cat_name = (TextView) v.findViewById(R.id.category_text_list);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CategeryAdaptor(List<ItemData> catList, Context context) {

        this.catList = catList;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CategeryAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.catogary_list, parent, false);

        CategeryAdaptor.ViewHolder viewHolder =
                new CategeryAdaptor.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(CategeryAdaptor.ViewHolder holder, int position) {


        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final ItemData ItemData = catList.get(position);

        holder.cat_name.setText(ItemData.getname());

        final String title = ItemData.getname();
        final String catid = ItemData.getcatid();


        holder.cat_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Doc", "AsfDSfsfjdsfkjdsfgkjDSfgdsfdsfdsgfs");
//                FragmentManager manager = ((MainActivity)context).getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.browse, ViewFrag);
//                transaction.commit();
                toViewFrag(title,catid);
            }
        });

    }

    private void toViewFrag(String catName,String catid) {
        FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putString("adName", catName);
        bundle.putString("adcatid", catid);

        Update_Category addFragment = new Update_Category();
        addFragment.setArguments(bundle);

        fm.beginTransaction().replace(R.id.flContent, addFragment).addToBackStack("null").commit();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return catList.size();
    }

}

