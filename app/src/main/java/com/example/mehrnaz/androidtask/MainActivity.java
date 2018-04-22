package com.example.mehrnaz.androidtask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_getCountries;

    //the URL having the json data
    private static final String JSON_URL = "https://api.whichapp.com/v1/countries";
    List<Country> countryList = new ArrayList<>();
    public static final String MY_PREFS_NAME = "CountriesPrefsFile";

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

                                    //Caching countries information by sharedPreferences
                                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putString("iso", countryObject.getString("iso"));
                                    editor.putString("name", countryObject.getString("name"));
                                    editor.putString("phone", countryObject.getString("phone"));
                                    editor.apply();

                                    try {
                                        FileOutputStream fileOutputStream = null;

                                        fileOutputStream = getApplicationContext().openFileOutput("country.txt", Context.MODE_PRIVATE);
                                        ObjectOutputStream objectOutputStream = null;
                                        objectOutputStream = new ObjectOutputStream(fileOutputStream);
                                        objectOutputStream.writeObject(countryList);
                                        objectOutputStream.close();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                } catch (JSONException e) {
                                    // Oops
                                    e.printStackTrace();
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
