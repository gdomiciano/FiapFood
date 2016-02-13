package br.com.fiap.daa.fiapfood;//package br.com.fiap.daa.fiapfood;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import br.com.fiap.daa.fiapfood.helper.FontsManager;
import br.com.fiap.daa.fiapfood.service.RestaurantService;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by geisy_000 on 2/7/2016.
 */
public class MainActivity extends AppCompatActivity {
    @Bind(R.id.btNew) Button btNew;
    @Bind(R.id.btSeeAll) Button btSeeAll;
    @Bind(R.id.btSeeMap) Button btSeeMap;
    @Bind(R.id.btAbout) Button btAbout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Typeface iconFont = FontsManager.getTypeface(getApplicationContext(), FontsManager.FONTAWESOME);
        FontsManager.markAsIconContainer(findViewById(R.id.icons_container), iconFont);
        new RestaurantService(MainActivity.this).execute("http://heiderlopes.com.br/restaurantes/restaurantes.json");



    }

    @OnClick(R.id.btAbout)
    public void goAbout(){
        Intent i = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(i);
//        finish();
    }

    @OnClick(R.id.btSeeAll)
    public void goSeeAll(){
        Intent i = new Intent(MainActivity.this, RestaurantsActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.btNew)
    public void goAdd(){
        Intent i = new Intent(MainActivity.this, AddRestaurantActivity.class);
        startActivity(i);

    }
}
