package br.com.fiap.daa.fiapfood;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.fiap.daa.fiapfood.DAO.RestaurantDAO;
import br.com.fiap.daa.fiapfood.model.Restaurant;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by geisy_000 on 2/13/2016.
 */
public class AddRestaurantActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    //Picture Vars
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int RESULT_LOAD_IMAGE = 200;
    private static final String IMAGE_DIRECTORY_NAME = "FiapFood";
    private Uri fileUri;


    //Location Vars
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private static final long INTERVAL = 10000;
    private static final long FASTEST_INTERVAL = 5000;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    private static int DISPLACEMENT = 10; // 10 meters

    //Elements
    @Bind(R.id.ibtTakePic) ImageButton ibtTakePic;
    @Bind(R.id.ibtGallery) ImageButton ibtGallery;
    @Bind(R.id.ivPreview) ImageView ivPreview;
    @Bind(R.id.tlRestaurantName) TextInputLayout tlRestaurantName;
    @Bind(R.id.tlRestaurantPhone) TextInputLayout tlRestaurantPhone;
    @Bind(R.id.spType) Spinner spType;
    @Bind(R.id.tlRestaurantPrice) TextInputLayout tlRestaurantPrice;
    @Bind(R.id.tlRestaurantLat) TextInputLayout tlRestaurantLat;
    @Bind(R.id.tlRestaurantLon) TextInputLayout tlRestaurantLon;
    @Bind(R.id.tlRestaurantDesc) TextInputLayout tlRestaurantDesc;
    @Bind(R.id.etRestaurantName) EditText etRestaurantName;
    @Bind(R.id.etRestaurantPhone) EditText etRestaurantPhone;
    @Bind(R.id.etRestaurantPrice) EditText etRestaurantPrice;
    @Bind(R.id.etRestaurantLat) EditText etRestaurantLat;
    @Bind(R.id.etRestaurantLon) EditText etRestaurantLon;
    @Bind(R.id.etRestaurantDesc) EditText etRestaurantDesc;
    @Bind(R.id.btSubmit) Button btSave;

    BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
    private String picRestaurantPath = "";
    Double latitude;
    Double longitude;
    GoogleMap mMap;
    String price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_form);
        ButterKnife.bind(this);

        btSave.setText("Add Restaurant");

        ArrayAdapter<CharSequence> typeAdp = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_dropdown_item);
        typeAdp.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spType.setAdapter(typeAdp);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapInput);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        if (checkPlayServices()) {
            buildGoogleApiClient();
            createLocationRequest();
//            displayLocation();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final Bitmap bmp;
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ivPreview.setVisibility(View.VISIBLE);

                bmp = BitmapFactory.decodeFile(fileUri.getPath(),
                        bmpOptions);
                showPreview(bmp);
                picRestaurantPath = fileUri.getPath();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "You cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Couldn't take the picture", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ivPreview.setVisibility(View.VISIBLE);
            bmp = BitmapFactory.decodeFile(picturePath);
            showPreview(bmp);
            picRestaurantPath = picturePath;
        }

    }
    @Override
    public void onStart() {
        super.onStart();
//        Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
//        Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
//        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    @OnClick(R.id.ibtTakePic)
    public void takePicture() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = Uri.fromFile(getPicturePath());
        i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(i, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @OnClick(R.id.ibtGallery)
    public void getFromGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    public boolean isValid(Restaurant restaurant){

        if (!(tlRestaurantName.getEditText().getText().toString().equals("")) && (mLastLocation != null) && !(tlRestaurantPrice.getEditText().getText().toString().equals(""))) {
            restaurant.setPicture(picRestaurantPath);
            price = tlRestaurantPrice.getEditText().getText().toString();
            return true;
        }else{
            if(mLastLocation == null){
                showSettingsAlert();
            }
            price = "0";
            return false;
        }
//         tlRestaurantPrice.getEditText().getText().toString();

    }
    @OnClick(R.id.btSubmit)
    public void saveRestaurant(View v) {
        RestaurantDAO crud = new RestaurantDAO(v.getContext());
        Restaurant restaurant = new Restaurant();
        if(isValid(restaurant)){

            restaurant.setName(tlRestaurantName.getEditText().getText().toString());
            restaurant.setPhone(tlRestaurantPhone.getEditText().getText().toString());
            restaurant.setType(spType.getSelectedItem().toString());
            restaurant.setPrice(Double.parseDouble(price));
            restaurant.setLat(tlRestaurantLat.getEditText().getText().toString());
            restaurant.setLon(tlRestaurantLon.getEditText().getText().toString());
            restaurant.setDescription(tlRestaurantDesc.getEditText().getText().toString());

            crud.dbInsert(restaurant);
            Intent i = new Intent(AddRestaurantActivity.this, RestaurantsActivity.class);
            startActivity(i);
            Toast.makeText(this, "Restaurant was saved!", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "Couldn't save the Restaurant!", Toast.LENGTH_SHORT).show();
        }

    }

    @OnItemSelected(R.id.spType)
    public void onItemSelected(int position) {
        spType.getItemAtPosition(position);

    }


    public static File getPicturePath() {
        File restaurantImgDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
        if (!restaurantImgDir.exists()) {
            if (!restaurantImgDir.mkdirs()) {
                Toast.makeText(new AddRestaurantActivity().getApplicationContext(), "Oops! Couldn't create the directory " + IMAGE_DIRECTORY_NAME, Toast.LENGTH_LONG).show();

                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm", Locale.getDefault()).format(new Date());
        File picture = new File(restaurantImgDir.getPath() + File.separator + "img_" + timeStamp + ".jpg");

        return picture;
    }

    public void showPreview(Bitmap bmp) {
        bmpOptions.inSampleSize = 8;
        ivPreview.setImageBitmap(bmp);
        ibtTakePic.setVisibility(View.GONE);
        ibtGallery.setVisibility(View.GONE);

    }

    @Override
    public void onConnected(Bundle bundle) {
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "GPS Connection Failed", Toast.LENGTH_LONG).show();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }

            return false;
        }

        return true;
    }

    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            etRestaurantLat.setText(String.valueOf(latitude), TextView.BufferType.EDITABLE);
            etRestaurantLon.setText(String.valueOf(longitude), TextView.BufferType.EDITABLE);
            LatLng currentPos = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(currentPos).title("You're here!"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPos));
        } else {
            showSettingsAlert();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void showSettingsAlert(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);


        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setIcon(R.drawable.common_full_open_on_phone);

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        displayLocation();
//        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng currentPos = new LatLng(latitude, longitude);
//        mMap.addMarker(new MarkerOptions().position(currentPos).title("You're here!"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPos));
    }

}
