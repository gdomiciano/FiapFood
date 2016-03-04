package br.com.fiap.daa.fiapfood;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.daa.fiapfood.DAO.RestaurantDAO;
import br.com.fiap.daa.fiapfood.model.Restaurant;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by geisy_000 on 3/3/2016.
 */
public class SearchActivity extends AppCompatActivity{
    @Bind(R.id.tlSearchName) TextInputLayout tlSearchName;
    @Bind(R.id.spSearchType) Spinner spSearchType;
    @Bind(R.id.tlSearchPrice) TextInputLayout tlSearchPrice;
    @Bind(R.id.etSearchName) EditText etSearchName;
    @Bind(R.id.etSearchPrice) EditText etSearchPrice;
    @Bind(R.id.btSearch) Button btSearch;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private String name, type, price = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acticity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ArrayAdapter<CharSequence> typeAdp = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_dropdown_item);
        typeAdp.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spSearchType.setAdapter(typeAdp);
    }

    @OnClick(R.id.btSearch)
    public void search(){
        List<Restaurant> restaurants;
        RestaurantDAO crud = new RestaurantDAO(this);
        name = tlSearchName.getEditText().getText().toString();
        type = spSearchType.getSelectedItem().toString();
        price = tlSearchPrice.getEditText().getText().toString();
        restaurants = crud.dbSearch(name, type, price);
        Intent i = new Intent(this, SearchResultActivity.class);
        i.putExtra("restaurants", (Serializable) restaurants);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
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
