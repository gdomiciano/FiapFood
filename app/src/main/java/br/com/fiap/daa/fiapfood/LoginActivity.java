package br.com.fiap.daa.fiapfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import br.com.fiap.daa.fiapfood.service.RestaurantService;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.tlName) TextInputLayout tlName;
    @Bind(R.id.tlPass) TextInputLayout tlPass;
    @Bind(R.id.cbConnected) CheckBox cbConnected;
    @Bind(R.id.btLogin) Button btLogin;
    @Bind(R.id.info) TextView tvInfo;
    @Bind(R.id.login_button) LoginButton btFbLogin;
    @Bind(R.id.toolbar) Toolbar toolbar;

    private final String PREF_NAME = "FIAPFOOD";
    private final String KEEPCONNECTED = "KEEPCONNECTED";
    private final String INFOLOADED = "INFOLOADED";
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if(isConnected()){
            startApp();
        }
        btFbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                tvInfo.setText("Login ok");
                keepConnected(true);
                startApp();


            }

            @Override
            public void onCancel()  {
                tvInfo.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException error)   {
                tvInfo.setText("Error"+error.toString());
            }

        });


    }

    @OnClick(R.id.btLogin)
    public void login(){
        if(validUser()){
            keepConnected( cbConnected.isChecked());
            startApp();
        }else{
            tlPass.setError(getString(R.string.err_login));
        }
    }

    private boolean validUser() {
        boolean valid = false;

        String user = tlName.getEditText().getText().toString();
        String pass = tlPass.getEditText().getText().toString();

        if(user.equals("admin") && pass.equals("123abc")){
            valid = true;
        }

        return valid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        AppEventsLogger.deactivateApp(this);
    }

    public void startApp(){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void keepConnected(Boolean connected) {
        SharedPreferences settings = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(KEEPCONNECTED,connected);
        editor.commit();
        new RestaurantService(LoginActivity.this).execute("http://heiderlopes.com.br/restaurantes/restaurantes.json");
    }

    private boolean isConnected() {
        SharedPreferences settings = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return settings.getBoolean(KEEPCONNECTED, false);
    }
}
