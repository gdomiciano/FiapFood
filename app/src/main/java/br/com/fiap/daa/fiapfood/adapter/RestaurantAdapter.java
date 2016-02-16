package br.com.fiap.daa.fiapfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import br.com.fiap.daa.fiapfood.DAO.RestaurantDAO;
import br.com.fiap.daa.fiapfood.EditRestaurantActivity;
import br.com.fiap.daa.fiapfood.R;
import br.com.fiap.daa.fiapfood.RestaurantDetailsActivity;
import br.com.fiap.daa.fiapfood.RestaurantsActivity;
import br.com.fiap.daa.fiapfood.model.Restaurant;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by geisy_000 on 2/10/2016.
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>{

    private Context c;
    private List<Restaurant> restaurants;
    Restaurant restaurant;

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvRestaurantName) TextView tvRestaurantName;
        @Bind(R.id.tvRestaurantType) TextView tvRestaurantType;
        @Bind(R.id.ivRestaurant) ImageView ivRestaurant;
        @Bind(R.id.ibtDelete) ImageButton ibtDelete;
        @Bind(R.id.ibtEdit) ImageButton ibtEdit;
        @Bind(R.id.pbRestaurant)  ProgressBar pbRestaurant;
        @Bind(R.id.card_view) CardView card;



        public RestaurantViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

    public RestaurantAdapter(Context c, List<Restaurant> restaurants){
        this.c = c;
        this.restaurants = restaurants;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final RestaurantViewHolder holder, final int position)  {

        holder.tvRestaurantName.setText(restaurants.get(position).getName());
        holder.tvRestaurantType.setText(restaurants.get(position).getType());
        File file = new File(restaurants.get(position).getPicture());
        if(file.exists()){
            String picPath = file.toString();
            Drawable restaurantPicture = Drawable.createFromPath(picPath);
            holder.ivRestaurant.setImageDrawable(restaurantPicture);
        }else {
            holder.ivRestaurant.setImageResource(R.mipmap.ic_launcher);
        }

        restaurant = restaurants.get(position) ;
        holder.ibtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EditRestaurantActivity.class);
                i.putExtra("restaurant", restaurant);
                v.getContext().startActivity(i);
            }
        });

        holder.ibtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantDAO crud = new RestaurantDAO(v.getContext());
//                restaurant = restaurants.get(position) ;
                crud.dbDelete(restaurant);
//                v.getContext().finish();
                ((AppCompatActivity) v.getContext()).finish();
                v.getContext().startActivity(new Intent(v.getContext(), RestaurantsActivity.class));
            }
        });
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), RestaurantDetailsActivity.class);
//                restaurant = restaurants.get(position) ;
                i.putExtra("restaurant", restaurant);
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }



}