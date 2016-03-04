package br.com.fiap.daa.fiapfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.daa.fiapfood.R;
import br.com.fiap.daa.fiapfood.model.Restaurant;

/**
 * Created by geisy_000 on 2/9/2016.
 */
public class RestaurantDAO {

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String PICTURE = "picture";
    public static final String PHONE = "phone";
    public static final String TYPE = "type";
    public static final String LAT = "lat";
    public static final String LON = "lon";
    public static final String PRICE = "price";
    public static final String DESCRIPTION = "description";

    private DB database;
    private SQLiteDatabase db;
    private Context context;
    public RestaurantDAO(Context context) {
        database = new DB(context);
    }

    public String dbInsert(Restaurant restaurant) {
        ContentValues values = new ContentValues();
        values.put(NAME, restaurant.getName());
        values.put(PICTURE, restaurant.getPicture());
        values.put(PHONE, restaurant.getPhone());
        values.put(TYPE, restaurant.getType());
        values.put(LAT, restaurant.getLat());
        values.put(LON, restaurant.getLon());
        values.put(PRICE, restaurant.getPrice());
        values.put(DESCRIPTION, restaurant.getDescription());

        db = database.getWritableDatabase();

        long result = db.insert(DB.TABLE_RESTAURANT, null, values);

        db.close();

        if (result == -1) {
            return "Error when tried add";
        }else{
            return "Restaurant added";
        }
    }

    public String dbUpdate(Restaurant restaurant) {
        ContentValues values = new ContentValues();
        values.put(ID, restaurant.getId());
        values.put(NAME, restaurant.getName());
        values.put(PICTURE, restaurant.getPicture());
        values.put(PHONE, restaurant.getPhone());
        values.put(TYPE, restaurant.getType());
        values.put(LAT, restaurant.getLat());
        values.put(LON, restaurant.getLon());
        values.put(PRICE, restaurant.getPrice());
        values.put(DESCRIPTION, restaurant.getDescription());
        Log.d("oi", String.valueOf(restaurant.getId()));
        db = database.getWritableDatabase();

        long result = db.update(DB.TABLE_RESTAURANT, values, ID + " = " + restaurant.getId(), null);

        db.close();

        if (result == -1) {
            return context.getString(R.string.error_edit);
        }

        return "Restaurant edited";
    }

    public List<Restaurant> dbSearch(String name, String type, String price) {
        Cursor cursor;
        List<Restaurant> restaurants = new ArrayList<>();
        db = database.getReadableDatabase();

        String queryString = TYPE + " = '" + type + "'";
            if(!name.equals("")) {
               queryString += " AND " + NAME + " LIKE '%" + name + "%' ";
            }
            if(!price.equals("")) {
                queryString += " AND " + PRICE + " LIKE '%" + price + "%' ";
            }

        cursor = db.query(DB.TABLE_RESTAURANT, null, queryString, null, null, null, null);

        Log.d("CURSOR",cursor.getColumnName(cursor.getColumnIndex(NAME)));
        if (cursor != null && cursor.getColumnCount() != -1) {
            //cursor.moveToFirst();
            while (cursor.moveToNext()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                restaurant.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                restaurant.setPicture(cursor.getString(cursor.getColumnIndex(PICTURE)));
                restaurant.setPhone(cursor.getString(cursor.getColumnIndex(PHONE)));
                restaurant.setType(cursor.getString(cursor.getColumnIndex(TYPE)));
                restaurant.setLat(cursor.getString(cursor.getColumnIndex(LAT)));
                restaurant.setLon(cursor.getString(cursor.getColumnIndex(LON)));
                restaurant.setPrice(cursor.getDouble(cursor.getColumnIndex(PRICE)));
                restaurant.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
                restaurants.add(restaurant);
            }
        }else{
//            Toast
            Log.d("OI","Não Foi");
        }
        db.close();
        return restaurants;

    }

    public String dbDelete(Restaurant restaurant) {
        ContentValues values = new ContentValues();
        values.put(ID, restaurant.getId());
        db = database.getWritableDatabase();

        long result = db.delete(DB.TABLE_RESTAURANT, ID + " = " + restaurant.getId(), null);

        db.close();

        if (result == -1) {
            return context.getString(R.string.error_delete);
        }

        return context.getString(R.string.success_delete);
    }

    public List<Restaurant> getRestaurantsDAO() {
        List<Restaurant> restaurants = new ArrayList<>();
        Cursor cursor;

        db = database.getReadableDatabase();
        cursor = db.query(DB.TABLE_RESTAURANT, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                restaurant.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                restaurant.setPicture(cursor.getString(cursor.getColumnIndex(PICTURE)));
                restaurant.setPhone(cursor.getString(cursor.getColumnIndex(PHONE)));
                restaurant.setType(cursor.getString(cursor.getColumnIndex(TYPE)));
                restaurant.setLat(cursor.getString(cursor.getColumnIndex(LAT)));
                restaurant.setLon(cursor.getString(cursor.getColumnIndex(LON)));
                restaurant.setPrice(cursor.getDouble(cursor.getColumnIndex(PRICE)));
                restaurant.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
                restaurants.add(restaurant);
            }
        }
        db.close();
        return restaurants;
    }
}
