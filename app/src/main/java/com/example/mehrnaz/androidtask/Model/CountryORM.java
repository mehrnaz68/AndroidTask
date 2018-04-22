package com.example.mehrnaz.androidtask.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mehrnaz.androidtask.Helper.DatabaseWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mehrnaz on 4/22/2018.
 */
public class CountryORM {
    private static final String TAG = "CountryORM";

    private static final String TABLE_NAME = "country";

    private static final String COMMA_SEP = ", ";

    private static final String COLUMN_ID_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT ";
    private static final String COLUMN_ID = "id";

    private static final String COLUMN_COUNTRY_ISO_TYPE = "TEXT";
    private static final String COLUMN_COUNTRY_ISO = "iso";

    private static final String COLUMN_COUNTRY_NAME_TYPE = "TEXT";
    private static final String COLUMN_COUNTRY_NAME = "name";

    private static final String COLUMN_COUNTRY_PHONE_TYPE = "TEXT";
    private static final String COLUMN_COUNTRY_PHONE = "phone";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " " + COLUMN_ID_TYPE + COMMA_SEP +
                    COLUMN_COUNTRY_ISO + " " + COLUMN_COUNTRY_ISO_TYPE + COMMA_SEP +
                    COLUMN_COUNTRY_NAME + " " + COLUMN_COUNTRY_NAME_TYPE + COMMA_SEP +
                    COLUMN_COUNTRY_PHONE + " " + COLUMN_COUNTRY_PHONE_TYPE +
                    ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static void insertCountry(Context context, Country country) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getWritableDatabase();

        ContentValues values = postToContentValues(country);
        long postId = database.insert(CountryORM.TABLE_NAME, "null", values);
        Log.i(TAG, "Inserted new Post with ID: " + postId);

        database.close();
    }

    /**
     * Packs a Country object into a ContentValues map for use with SQL inserts.
     */
    private static ContentValues postToContentValues(Country country) {
        ContentValues values = new ContentValues();
        values.put(CountryORM.COLUMN_COUNTRY_ISO, country.getIso());
        values.put(CountryORM.COLUMN_COUNTRY_NAME, country.getName());
        values.put(CountryORM.COLUMN_COUNTRY_PHONE, country.getPhone());
        return values;
    }


    public static List<Country> getCountries(Context context) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + CountryORM.TABLE_NAME, null);

        Log.i(TAG, "Loaded " + cursor.getCount() + " Posts...");
        List<Country> postList = new ArrayList<Country>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Country country = cursorToCountry(cursor);
                postList.add(country);
                cursor.moveToNext();
            }
            Log.i(TAG, "Countries loaded successfully.");
        }

        database.close();

        return postList;
    }

    /**
     * Populates a Post object with data from a Cursor
     *
     * @param cursor
     * @return
     */
    private static Country cursorToCountry(Cursor cursor) {
        Country country = new Country(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY_ISO)), cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY_PHONE)));
        country.setIso(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY_ISO)));
        country.setName(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY_NAME)));
        country.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY_PHONE)));

        return country;
    }
}
