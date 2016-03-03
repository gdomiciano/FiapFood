package br.com.fiap.daa.fiapfood;//package br.com.fiap.daa.fiapfood;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Typeface iconFont = FontsManager.getTypeface(getApplicationContext(), FontsManager.FONTAWESOME);
        FontsManager.markAsIconContainer(findViewById(R.id.icons_container), iconFont);

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

    @OnClick(R.id.btSeeMap)
    public void goMap(){
        Intent i = new Intent(MainActivity.this, RestaurantsMap.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.mnAbout:
                goAbout();
                break;

            case R.id.mnAddRestarant:
                goAdd();
                break;

            case R.id.mnLogout:
                break;

            case R.id.mnSearch:
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
                break;

            case R.id.mnSeeAll:
                goSeeAll();
                break;

            case R.id.mnSeeMap:
                goMap();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
