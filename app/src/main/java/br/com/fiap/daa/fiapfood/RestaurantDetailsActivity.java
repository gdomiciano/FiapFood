package br.com.fiap.daa.fiapfood;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.fiap.daa.fiapfood.model.Restaurant;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by geisy_000 on 2/14/2016.
 */
public class RestaurantDetailsActivity extends AppCompatActivity {

    @Bind(R.id.nameRestaurant) TextView restaurantName;
    @Bind(R.id.priceRestaurant) TextView restaurantPrice;
    @Bind(R.id.typeRestaurant) TextView restaurantType;
    @Bind(R.id.phoneRestaurant) TextView restaurantPhone;
    @Bind(R.id.descRestaurant) TextView restaurantDesc;
    @Bind(R.id.ivRestaurantDetail) ImageView restaurantPicture;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_restaurant_details);
        restaurant = (Restaurant) intent.getSerializableExtra("restaurant");

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        restaurantName.setText(restaurant.getName());
        restaurantPrice.setText("Average Cost: " + restaurant.getPrice());
        restaurantType.setText("Phone: " + restaurant.getPhone());
        restaurantPhone.setText("Type: " + restaurant.getType());
        restaurantDesc.setText("Description: " + restaurant.getDescription());
        restaurantPicture.setImageDrawable(Drawable.createFromPath(restaurant.getPicture()));
    }
}
