package com.example.mehrnaz.androidtask.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mehrnaz.androidtask.Model.Country;
import com.example.mehrnaz.androidtask.R;

import java.util.List;

/**
 * Created by Mehrnaz on 4/22/2018.
 */


public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private List<Country> countriesList;

    public CountryAdapter(List<Country> countryList, Context applicationContext) {
        this.countriesList = countryList;
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;

        } else if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        }

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position >= countriesList.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_country_list, viewGroup, false);
            return new ItemViewHolder(view);

        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_header, viewGroup, false);
            return new HeaderViewHolder(view);

        } else if (viewType == TYPE_FOOTER) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_footer,
                    viewGroup, false);
            return new FooterViewHolder(view);

        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof HeaderViewHolder) {

//            //set the Value from List to corresponding UI component as shown below.
//            ((HeaderViewHolder) holder).txtName.setText(mList.get(position))

            //similarly bind other UI components or perform operations

        } else if (holder instanceof ItemViewHolder) {


            // Your code here

            Country country = countriesList.get(position);
            ((ItemViewHolder) holder).iso.setText(country.getIso());
            ((ItemViewHolder) holder).name.setText(country.getName());
            ((ItemViewHolder) holder).phone.setText(country.getPhone());

        } else if (holder instanceof FooterViewHolder) {

            //your code here
        }


    }


    @Override
    public int getItemCount() {
        // Add two more counts to accomodate header and footer
        return this.countriesList.size() + 1;
    }


    // The ViewHolders for Header, Item and Footer
    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public View View;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            View = itemView;

            // add your ui components here like this below

        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView iso, name, phone;

        public ItemViewHolder(View view) {
            super(view);
            iso = (TextView) view.findViewById(R.id.txt_iso);
            name = (TextView) view.findViewById(R.id.txt_name);
            phone = (TextView) view.findViewById(R.id.txt_phone);
        }

    }


    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public View View;

        public FooterViewHolder(View v) {
            super(v);
            View = v;
            // Add your UI Components here
        }

    }


}
