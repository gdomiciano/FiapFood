package br.com.fiap.daa.fiapfood;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
public class EditRestaurantActivity extends AppCompatActivity {

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int RESULT_LOAD_IMAGE = 200;
    private static final String IMAGE_DIRECTORY_NAME = "FiapFood";
    private Uri fileUri;

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
    @Bind(R.id.btSubmit) Button btUpdate;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
    private String picRestaurantPath = null;
    Restaurant restaurant;
    String price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_form);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        btUpdate.setText("Save changes");
        ArrayAdapter<CharSequence> typeAdp = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_dropdown_item);
        typeAdp.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spType.setAdapter(typeAdp);


        restaurant = (Restaurant) getIntent().getExtras().getSerializable("restaurant");
        if (restaurant != null) {
            ivPreview.setImageDrawable(Drawable.createFromPath(restaurant.getPicture()));
            ivPreview.setVisibility(View.VISIBLE);
            etRestaurantName.setText(restaurant.getName(), TextView.BufferType.EDITABLE);
            etRestaurantPhone.setText(restaurant.getPhone(), TextView.BufferType.EDITABLE);
            spType.setSelection(typeAdp.getPosition(restaurant.getType()));
            etRestaurantPrice.setText(String.valueOf(restaurant.getPrice()), TextView.BufferType.EDITABLE);
            etRestaurantLat.setText(restaurant.getLat(), TextView.BufferType.EDITABLE);
            etRestaurantLon.setText(restaurant.getLon(), TextView.BufferType.EDITABLE);
            etRestaurantDesc.setText(restaurant.getDescription(), TextView.BufferType.EDITABLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final Bitmap bmp;
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK){
                ivPreview.setVisibility(View.VISIBLE);
                bmpOptions.inSampleSize = 8;
                bmp = BitmapFactory.decodeFile(fileUri.getPath(),
                        bmpOptions);

                showPreview(bmp);
                picRestaurantPath = fileUri.getPath();
            }else if (resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "You cancelled", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Couldn't take the picture", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
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
    @OnClick(R.id.ibtTakePic)
    public void takePicture(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = Uri.fromFile(getPicturePath());
        i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(i, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @OnClick(R.id.ibtGallery)
    public void getFromGallery(){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    public boolean isValid(Restaurant restaurant){

        if (!(tlRestaurantName.getEditText().getText().toString().equals("")) && !(tlRestaurantPrice.getEditText().getText().toString().equals(""))) {
            restaurant.setPicture(picRestaurantPath);
            price = tlRestaurantPrice.getEditText().getText().toString();
            return true;
        }else{
            price = "0";
            return false;
        }
//         tlRestaurantPrice.getEditText().getText().toString();

    }
    @OnClick(R.id.btSubmit)
    public void updateRestaurant(View v){
        RestaurantDAO crud = new RestaurantDAO(v.getContext());

        if(isValid(restaurant)){
            if (picRestaurantPath != null){
                restaurant.setPicture(picRestaurantPath);
            }
            restaurant.setName(tlRestaurantName.getEditText().getText().toString());
            restaurant.setPhone(tlRestaurantPhone.getEditText().getText().toString());
            restaurant.setType(spType.getSelectedItem().toString());
            restaurant.setPrice(Double.parseDouble(tlRestaurantPrice.getEditText().getText().toString()));
            restaurant.setLat(tlRestaurantLat.getEditText().getText().toString());
            restaurant.setLon(tlRestaurantLon.getEditText().getText().toString());
            restaurant.setDescription(tlRestaurantDesc.getEditText().getText().toString());

            crud.dbUpdate(restaurant);

            Intent i = new Intent(EditRestaurantActivity.this, RestaurantsActivity.class);
            startActivity(i);
            Toast.makeText(this, R.string.success_restaurant, Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, R.string.error_update, Toast.LENGTH_SHORT).show();
        }

    }

    @OnItemSelected(R.id.spType)
    public void onItemSelected(int position) {
        spType.getItemAtPosition(position);

    }


    public static File getPicturePath(){
        File restaurantImgDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
        if (!restaurantImgDir.exists()) {
            if (!restaurantImgDir.mkdirs()) {
                Toast.makeText(new AddRestaurantActivity().getApplicationContext(),"Oops! Couldn't create the directory " + IMAGE_DIRECTORY_NAME,Toast.LENGTH_LONG).show();

                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm", Locale.getDefault()).format(new Date());
        File picture = new File(restaurantImgDir.getPath() + File.separator + "img_" + timeStamp + ".jpg");

        return picture;
    }

    public void showPreview(Bitmap bmp){
        bmpOptions.inSampleSize = 8;
        ivPreview.setImageBitmap(bmp);
        ibtTakePic.setVisibility(View.GONE);
        ibtGallery.setVisibility(View.GONE);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i;
        switch (id){
            case R.id.mnAbout:
                i = new Intent(this, AboutActivity.class);
                startActivity(i);
                break;

            case R.id.mnAddRestarant:
                i = new Intent(this, AddRestaurantActivity.class);
                startActivity(i);
                break;

            case R.id.mnLogout:
                break;

            case R.id.mnSearch:
                i = new Intent(this, SearchActivity.class);
                startActivity(i);
                break;

            case R.id.mnSeeAll:
                i = new Intent(this, RestaurantsActivity.class);
                startActivity(i);
                break;

            case R.id.mnSeeMap:
                i = new Intent(this, RestaurantsMap.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
