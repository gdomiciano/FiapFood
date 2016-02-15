package br.com.fiap.daa.fiapfood.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import br.com.fiap.daa.fiapfood.DAO.RestaurantDAO;
import br.com.fiap.daa.fiapfood.model.Restaurant;

/**
 * Created by geisy_000 on 2/9/2016.
 */
public class RestaurantService extends AsyncTask<String, Void, List<Restaurant>> {
    private Context context;
    ProgressDialog dialog;
    InputStream inputStream = null;
    String result = "";
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
            dialog = ProgressDialog.show(context,
                context.getString(br.com.fiap.daa.fiapfood.R.string.wait_title),
                context.getString(br.com.fiap.daa.fiapfood.R.string.get_data));

    }

    @Override
    protected void onPostExecute(List<Restaurant> restaurants) {

        this.dialog.dismiss();

    }

    public RestaurantService(Context context) {
        this.context = context;
    }

    @Override
    protected List<Restaurant> doInBackground(String... params) {
        String url_select = "http://heiderlopes.com.br/restaurantes/restaurantes.json";

        ArrayList<NameValuePair> param = new ArrayList<>();

        try {
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url_select);
            httpPost.setEntity(new UrlEncodedFormEntity(param));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            // Read content & Log
            inputStream = httpEntity.getContent();
        } catch (UnsupportedEncodingException e1) {
            Log.e("UnsupportedEncodingException", e1.toString());
            e1.printStackTrace();
        } catch (ClientProtocolException e2) {
            Log.e("ClientProtocolException", e2.toString());
            e2.printStackTrace();
        } catch (IllegalStateException e3) {
            Log.e("IllegalStateException", e3.toString());
            e3.printStackTrace();
        } catch (IOException e4) {
            Log.e("IOException", e4.toString());
            e4.printStackTrace();
        }
        // Convert response to string using String Builder
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            result = sBuilder.toString();

            return getRestaurants(result);
        } catch (Exception e) {
            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
        }
        return null;
    }

    public List<Restaurant> getRestaurants(String jsonString) {
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            JSONArray json = new JSONArray(jsonString);
            RestaurantDAO crud = new RestaurantDAO(context);
            if(crud.getRestaurantsDAO().isEmpty())
                for (int i = 0; i <= json.length(); i++) { //percorre os ids da lista
                    Restaurant restaurant = new Restaurant();
                    JSONObject json_data = json.getJSONObject(i);
                    restaurant.setName(json_data.getString("NOMERESTAURANTE"));
                    restaurant.setPicture("");
                    restaurant.setPhone(json_data.getString("TELEFONE"));
                    restaurant.setType(json_data.getString("TIPO"));
                    restaurant.setPrice(json_data.getDouble("CustoMedio"));
                    restaurant.setDescription(json_data.getString("OBSERVACAO"));
                    String[] fullLocation = json_data.getString("LOCALIZACAO").trim().split(",");
                    restaurant.setLat(fullLocation[0]);
                    restaurant.setLon(fullLocation[1]);
                    restaurants.add(restaurant);
                    crud.dbInsert(restaurant);
                }
        } catch(JSONException je) {
            je.printStackTrace();
        }
        return restaurants;
    }
}
