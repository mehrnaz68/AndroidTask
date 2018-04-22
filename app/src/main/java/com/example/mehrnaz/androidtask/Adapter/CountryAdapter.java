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
public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder> {

    private List<Country> countriesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView iso, name, phone;

        public MyViewHolder(View view) {
            super(view);
            iso = (TextView) view.findViewById(R.id.txt_iso);
            name = (TextView) view.findViewById(R.id.txt_name);
            phone = (TextView) view.findViewById(R.id.txt_phone);
        }
    }


    public CountryAdapter(List<Country> moviesList, Context applicationContext) {
        this.countriesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_country_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Country country = countriesList.get(position);
        holder.iso.setText(country.getIso());
        holder.name.setText(country.getName());
        holder.phone.setText(country.getPhone());
    }

    @Override
    public int getItemCount() {
        return countriesList.size();
    }
}
