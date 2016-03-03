package br.com.fiap.daa.fiapfood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import br.com.fiap.daa.fiapfood.DAO.RestaurantDAO;
import br.com.fiap.daa.fiapfood.adapter.RestaurantAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by geisy_000 on 2/10/2016.
 */
public class RestaurantsActivity extends AppCompatActivity {

    @Bind(R.id.rvRestaurants) RecyclerView rvRestaurants;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        rvRestaurants.setHasFixedSize(true);
        RecyclerView.LayoutManager lmRestaurants = new LinearLayoutManager(this);
        rvRestaurants.setLayoutManager(lmRestaurants);


        RestaurantDAO restaurantDAO = new RestaurantDAO(this);
        RecyclerView.Adapter rvAdapter = new RestaurantAdapter(this, restaurantDAO.getRestaurantsDAO());
        rvRestaurants.setAdapter(rvAdapter);

    }
}
