package br.com.fiap.daa.fiapfood.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;

import java.util.List;

import br.com.fiap.daa.fiapfood.DAO.RestaurantDAO;
import br.com.fiap.daa.fiapfood.RestaurantsMap;
import br.com.fiap.daa.fiapfood.model.Restaurant;

/**
 * Created by geisy_000 on 2/14/2016.
 */
public class CoordinatesService extends AsyncTask<String, Void, List<Restaurant>> {// o primeiro cara é o que vai entrar no doing background, o segundo  vai trabalar com o progress bar ,  e o terceiro entra no post execute, q e o resultado
    private Context context;
    private GoogleMap map;
    private ProgressDialog pd;
//     mapView;mapView

    public CoordinatesService(Context context, GoogleMap googleMap) {
        this.context = context;
        this.map = googleMap;
    }

    @Override
    protected void onPreExecute() {
//            super.onPreExecute();
        pd = ProgressDialog.show(context, "Wait...", "Loading Restaurants...");
    }

    @Override
    protected void onPostExecute(List<Restaurant> restaurants) {
//            super.onPostExecute(s);
        try{
            if(restaurants != null){
                new RestaurantsMap().setOnMap(restaurants,map);
            }
        }finally {
            pd.dismiss();
        }
    }

    @Override
    protected List<Restaurant> doInBackground(String... params) {
        RestaurantDAO restaurantDAO = new RestaurantDAO(context);
        return restaurantDAO.getRestaurantsDAO();
    }


}
