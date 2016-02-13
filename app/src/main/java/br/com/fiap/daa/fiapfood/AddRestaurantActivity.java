package br.com.fiap.daa.fiapfood;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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
public class AddRestaurantActivity extends AppCompatActivity {

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
    @Bind(R.id.btSubmit) Button btSave;

    BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
    private String picRestaurantPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_form);
        ButterKnife.bind(this);
        btSave.setText("Add Restaurant");
        ArrayAdapter<CharSequence> typeAdp = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_dropdown_item);
        typeAdp.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spType.setAdapter(typeAdp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final Bitmap bmp;
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK){
                ivPreview.setVisibility(View.VISIBLE);

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

    @OnClick(R.id.btSubmit)
    public void saveRestaurant(View v){
        RestaurantDAO crud = new RestaurantDAO(v.getContext());

        Restaurant restaurant = new Restaurant();
        restaurant.setPicture(picRestaurantPath);
        restaurant.setName(tlRestaurantName.getEditText().getText().toString());
        restaurant.setPhone(tlRestaurantPhone.getEditText().getText().toString());
        restaurant.setType(spType.getSelectedItem().toString());
        restaurant.setPrice(Double.parseDouble(tlRestaurantPrice.getEditText().getText().toString()));
        restaurant.setLat(tlRestaurantLat.getEditText().getText().toString());
        restaurant.setLon(tlRestaurantLon.getEditText().getText().toString());
        restaurant.setDescription(tlRestaurantDesc.getEditText().getText().toString());

        crud.dbInsert(restaurant);

        Intent i = new Intent(AddRestaurantActivity.this, RestaurantsActivity.class);
        startActivity(i);
        Toast.makeText(this, "Restaurant was saved!",Toast.LENGTH_SHORT).show();
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
}
