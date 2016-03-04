package br.com.fiap.daa.fiapfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import br.com.fiap.daa.fiapfood.DAO.RestaurantDAO;
import br.com.fiap.daa.fiapfood.adapter.RestaurantAdapter;
import br.com.fiap.daa.fiapfood.model.Restaurant;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by geisy_000 on 3/3/2016.
 */
public class SearchResultActivity extends AppCompatActivity {

    @Bind(R.id.rvRestaurants)
    RecyclerView rvRestaurants;
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

        List<Restaurant> restaurants = (List<Restaurant>)getIntent().getSerializableExtra("restaurants");
        RecyclerView.Adapter rvAdapter = new RestaurantAdapter(this, restaurants);
        rvRestaurants.setAdapter(rvAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i;
        switch (id){
            case R.id.mnAbout:
                i = new Intent(this, AboutActivity.class);
                startActivity(i);
                break;

            case R.id.mnAddRestarant:
                i = new Intent(this, AddRestaurantActivity.class);
                startActivity(i);
                break;

            case R.id.mnLogout:
                break;

            case R.id.mnSearch:
                i = new Intent(this, SearchActivity.class);
                startActivity(i);
                break;

            case R.id.mnSeeAll:
                i = new Intent(this, RestaurantsActivity.class);
                startActivity(i);
                break;

            case R.id.mnSeeMap:
                i = new Intent(this, RestaurantsMap.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

