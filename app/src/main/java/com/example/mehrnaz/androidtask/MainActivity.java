package com.example.mehrnaz.androidtask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mehrnaz.androidtask.Model.Country;
import com.example.mehrnaz.androidtask.Model.CountryORM;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_getCountries;

    //the URL having the json data
    private static final String JSON_URL = "https://api.whichapp.com/v1/countries";
    public static List<Country> countryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //to fetch countries information from server call this method
        loadCountries();

        //define and onClick method for button that start CountriesActivity
        btn_getCountries = (Button) findViewById(R.id.btn_getCountries);
        btn_getCountries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CountriesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadCountries() {
        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //getting the whole json array from the response
                            JSONArray countryArray = new JSONArray(response);

                            //fetch countries information from database
                            countryList = CountryORM.getCountries(getApplicationContext());

                            //if data is uploaded in the database or not
                            if (countryList.size() != countryArray.length()) {
                                //now looping through all the elements of the json array
                                for (int i = 0; i < countryArray.length(); i++) {

                                    try {
                                        // Pulling items from the array
                                        JSONObject countryObject = countryArray.getJSONObject(i);

                                        //creating a country object and giving them the values from json object
                                        Country country = new Country(countryObject.getString("iso"), countryObject.getString("name"), countryObject.getString("phone"));

                                        //adding the country to countryList
                                        countryList.add(country);
                                        //upload countries information into database
                                        CountryORM.insertCountry(getApplicationContext(), country);

                                    } catch (JSONException e) {
                                        // Oops
                                        e.printStackTrace();
                                    }

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
