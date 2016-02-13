package br.com.fiap.daa.fiapfood;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import br.com.fiap.daa.fiapfood.model.Restaurant;
import butterknife.Bind;
import butterknife.ButterKnife;

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

    BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
    private String picRestaurantPath;
    Restaurant restaurant;
    AddRestaurantActivity addFunc = new AddRestaurantActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_form);
        ButterKnife.bind(this);
        btUpdate.setText("Save changes");

        restaurant = (Restaurant) getIntent().getExtras().getSerializable("restaurant");
        if (restaurant != null) {
            if (!restaurant.getPicture().equals("")) {

                ivPreview.setVisibility(View.VISIBLE);
            }
            ivPreview.setImageDrawable(Drawable.createFromPath(restaurant.getPicture()));
            etRestaurantName.setText(restaurant.getName(), TextView.BufferType.EDITABLE);
            etRestaurantPhone.setText(restaurant.getPhone(), TextView.BufferType.EDITABLE);
            spType.setSelection(((ArrayAdapter<String>) spType.getAdapter()).getPosition(restaurant.getType()));
            etRestaurantPrice.setText(String.valueOf(restaurant.getPrice()), TextView.BufferType.EDITABLE);
            etRestaurantLat.setText(restaurant.getLat(), TextView.BufferType.EDITABLE);
            etRestaurantLon.setText(restaurant.getLon(), TextView.BufferType.EDITABLE);
            etRestaurantDesc.setText(restaurant.getDescription(), TextView.BufferType.EDITABLE);

        }

    }
}
