package br.com.fiap.daa.fiapfood;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by geisy_000 on 2/10/2016.
 */
public class RestaurantsActivity extends AppCompatActivity {
    @Bind(R.id.pager) ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);
    }
}
