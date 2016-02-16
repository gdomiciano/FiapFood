package br.com.fiap.daa.fiapfood;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.fiap.daa.fiapfood.model.Restaurant;
import br.com.fiap.daa.fiapfood.service.CoordinatesService;

public class RestaurantsMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();



    }

    @Override
    protected void onStart() {
        super.onStart();

        client.connect();
        Action viewAction = Action.newAction(Action.TYPE_VIEW,"Restaurants Map",Uri.parse("http://host/path"),Uri.parse("android-app://br.com.fiap.daa.fiapfood/http/host/path"));
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Action viewAction = Action.newAction(Action.TYPE_VIEW,"Restaurants Map",Uri.parse("http://host/path"),Uri.parse("android-app://br.com.fiap.daa.fiapfood/http/host/path"));
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng fiap = new LatLng(-23.5740406, -46.6234089);
        mMap.addMarker(new MarkerOptions().position(fiap).title("FIAP").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fiap, 12.0f));
        mMap.setTrafficEnabled(true);
        mMap.setBuildingsEnabled(true);

        new CoordinatesService(this,mMap).execute();

    }
    public void setOnMap(List<Restaurant> restaurants, GoogleMap mMap){
//        for (Restaurant restaurant : restaurants){
//            LatLng pos = new LatLng(Double.parseDouble(restaurant.getLat()), Double.parseDouble(restaurant.getLon()));
////            mMap.addMarker(new MarkerOptions().position(pos).title("Marker in Sydney"));
//            mMap.addMarker(new MarkerOptions().position(pos).title(restaurant.getName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
//        }
        for (int i=0; i<restaurants.size();i++){
            LatLng pos = new LatLng(Double.parseDouble(restaurants.get(i).getLat()), Double.parseDouble(restaurants.get(i).getLon()));
            mMap.addMarker(new MarkerOptions().position(pos).title(restaurants.get(i).getName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        }
    }
}
