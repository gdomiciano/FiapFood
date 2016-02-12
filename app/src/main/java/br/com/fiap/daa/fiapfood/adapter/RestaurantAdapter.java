package br.com.fiap.daa.fiapfood.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import br.com.fiap.daa.fiapfood.R;
import br.com.fiap.daa.fiapfood.model.Restaurant;

/**
 * Created by geisy_000 on 2/10/2016.
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>{

private Context c;
private List<Restaurant> restaurants;

public static class RestaurantViewHolder extends RecyclerView.ViewHolder{
    TextView tvRestaurantName, tvRestaurantType;
    ImageView ivRestaurant, ivOptions;

    ProgressBar pbRestaurant;
    public RestaurantViewHolder(View itemView) {
        super(itemView);

        this.tvRestaurantName = (TextView) itemView.findViewById(R.id.tvRestaurantName);
        this.tvRestaurantType = (TextView) itemView.findViewById(R.id.tvRestaurantType);
        this.ivRestaurant = (ImageView) itemView.findViewById(R.id.ivRestaurant);
        this.ivOptions = (ImageView) itemView.findViewById(R.id.ivOptions);
        this.pbRestaurant = (ProgressBar) itemView.findViewById(R.id.pbRestaurant);
    }
}

    //Classes Construtoras:

    public RestaurantAdapter(Context c, List<Restaurant> restaurants){
        this.c = c;
        this.restaurants = restaurants;
    }


    // Classes referente ao adapter:
    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.restaurant_item, parent, false);  //usamos o inflater para trocar o layout padrao do android para carregar o nosso
        return new RestaurantViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {

        //fazendo a troca de informações consultando a lista gerada de acordo com as informações do Json
        holder.tvRestaurantName.setText(restaurants.get(position).getName());
        holder.tvRestaurantType.setText(restaurants.get(position).getType());

        //como carregar as imagens vindas de assets:
        try {
            InputStream is = c.getAssets().open(restaurants.get(position).getPicture());
            Drawable carPhoto = Drawable.createFromStream(is, null);
            holder.ivRestaurant.setImageDrawable(carPhoto);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }


}