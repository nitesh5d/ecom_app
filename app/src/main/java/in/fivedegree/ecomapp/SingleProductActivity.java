package in.fivedegree.ecomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.IconMarginSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SingleProductActivity extends AppCompatActivity {

    String currentProductId, imgUrl;

    ImageView img;

    TextView title, category, price, desc, rate, count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);

        Intent intent = getIntent();
        currentProductId = intent.getStringExtra("productId");

        img = findViewById(R.id.img);
        title = findViewById(R.id.title);
        category = findViewById(R.id.category);
        price = findViewById(R.id.price);
        desc = findViewById(R.id.description);
        rate = findViewById(R.id.rate);
        count = findViewById(R.id.count);

        loadDetails();

        Button addtocartBtn = findViewById(R.id.addtocart);
        addtocartBtn.setOnClickListener(View -> {
            addThisToCartTable();
        });
    }

    private void addThisToCartTable() {
        TextView titleTv = findViewById(R.id.title);
        TextView priceTv = findViewById(R.id.price);
        DBHelper db = new DBHelper(this);

        String id = currentProductId;
        String title = titleTv.getText().toString();
        double price = Double.parseDouble(priceTv.getText().toString());
        String img = imgUrl;
        int qty = 1;
        db.addProductsToCart(id, title, price, img, qty);
        View rootView = findViewById(android.R.id.content);
        Snackbar snackbar
                = Snackbar.make(rootView, "Product added to Cart", Snackbar.LENGTH_LONG)
                .setAction("Open Cart", View ->{
                    Intent i = new Intent(SingleProductActivity.this, CartActivity.class);
                    startActivity(i);
                    finish();
                });
        snackbar.show();

    }

    private void loadDetails() {
        DBHelper db = new DBHelper(this);
        ArrayList<ProductModel> arrayAllProducts = db.getSingleProduct(currentProductId);

        ProductModel data = arrayAllProducts.get(0);

        imgUrl = data.getImageUrl();
        Glide.with(this).load(data.getImageUrl()).into(img);
        title.setText(data.getTitle());
        price.setText(data.getPrice());
        desc.setText(data.getDescription());
        category.setText(data.getCategory());
        rate.setText(data.rating.getRate());
        count.setText("(" + data.rating.getRateCount() + ")");

    }
}