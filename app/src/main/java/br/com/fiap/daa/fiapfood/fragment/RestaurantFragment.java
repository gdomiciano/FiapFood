package br.com.fiap.daa.fiapfood.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.fiap.daa.fiapfood.DAO.RestaurantDAO;
import br.com.fiap.daa.fiapfood.R;
import br.com.fiap.daa.fiapfood.adapter.RestaurantAdapter;
import br.com.fiap.daa.fiapfood.model.Restaurant;
import br.com.fiap.daa.fiapfood.service.RestaurantService;

/**
 * Created by geisy_000 on 2/10/2016.
 */
public class RestaurantFragment extends Fragment {

    private List<Restaurant> restaurants;
    private RecyclerView rvRestaurants;
    private LinearLayoutManager layoutManager; // criado para indicar em qual tipo de layou o recycle view será carregadp
    private int id;



    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);//chamamos a classe que ele esta extendendo
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_restaurants, container, false);
        rvRestaurants = (RecyclerView) v.findViewById(R.id.rvRestaurants);
        layoutManager = new LinearLayoutManager(getContext()); //instanciando o layout manager que é quem fara todas as informa aparecem como uma lista

        rvRestaurants.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);
//        RestaurantService  restaurantService = new RestaurantService(getContext());
//        restaurants = restaurantService.getRestaurants("id");

       rvRestaurants.setAdapter(new RestaurantAdapter(getContext(),restaurants));


    }


}
