package com.example.mehrnaz.androidtask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mehrnaz.androidtask.Adapter.CountryAdapter;
import com.example.mehrnaz.androidtask.Model.Country;
import com.example.mehrnaz.androidtask.Model.CountryORM;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mehrnaz on 4/22/2018.
 */
public class CountriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CountryAdapter countryAdapter;
    private List<Country> countryList = new ArrayList<>();
    //the URL having the json data
    private static final String JSON_URL = "https://api.whichapp.com/v1/countries";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //fetch countries information from database
        countryList = CountryORM.getCountries(getApplicationContext());

        //creating custom adapter object
        countryAdapter = new CountryAdapter(countryList, getApplicationContext());

        //adding the adapter to listview
        recyclerView.setAdapter(countryAdapter);
    }
}
