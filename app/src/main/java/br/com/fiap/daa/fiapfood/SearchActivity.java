package br.com.fiap.daa.fiapfood;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.fiap.daa.fiapfood.DAO.RestaurantDAO;
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

        RestaurantDAO crud = new RestaurantDAO(this);
//        crud.dbSearch();
    }
}
