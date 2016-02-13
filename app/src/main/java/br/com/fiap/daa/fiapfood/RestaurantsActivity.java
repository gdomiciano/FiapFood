package br.com.fiap.daa.fiapfood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.com.fiap.daa.fiapfood.DAO.RestaurantDAO;
import br.com.fiap.daa.fiapfood.adapter.RestaurantAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by geisy_000 on 2/10/2016.
 */
public class RestaurantsActivity extends AppCompatActivity {
    @Bind(R.id.rvRestaurants) RecyclerView rvRestaurants;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager lmRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);

        rvRestaurants.setHasFixedSize(true);
        lmRestaurants = new LinearLayoutManager(this);
        rvRestaurants.setLayoutManager(lmRestaurants);


        RestaurantDAO restaurantDAO = new RestaurantDAO(this);
        rvAdapter = new RestaurantAdapter(this, restaurantDAO.getRestaurants());
        rvRestaurants.setAdapter(rvAdapter);



    }
}