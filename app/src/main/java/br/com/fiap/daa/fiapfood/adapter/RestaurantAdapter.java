package br.com.fiap.daa.fiapfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.Allocation;
import android.support.v4.app.ActivityCompat;
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

import br.com.fiap.daa.fiapfood.EditRestaurantActivity;
import br.com.fiap.daa.fiapfood.R;
import br.com.fiap.daa.fiapfood.RestaurantsActivity;
import br.com.fiap.daa.fiapfood.model.Restaurant;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by geisy_000 on 2/10/2016.
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>{

    private Context c;
    private List<Restaurant> restaurants;

public static class RestaurantViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.tvRestaurantName) TextView tvRestaurantName;
    @Bind(R.id.tvRestaurantType) TextView tvRestaurantType;
    @Bind(R.id.ivRestaurant) ImageView ivRestaurant;
    @Bind(R.id.ibtDelete) ImageButton ibtDelete;
    @Bind(R.id.ibtEdit) ImageButton ibtEdit;
    @Bind(R.id.pbRestaurant)  ProgressBar pbRestaurant;



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
    public void onBindViewHolder(RestaurantViewHolder holder, int position)  {

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


    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }



}