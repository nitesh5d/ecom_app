package in.fivedegree.ecomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.IconMarginSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SingleProductActivity extends AppCompatActivity {

    String currentProductId;

    ImageView img;
    TextView title, category, price, desc, rate, count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);

        img = findViewById(R.id.img);
        title = findViewById(R.id.title);
        category = findViewById(R.id.category);
        price = findViewById(R.id.price);
        desc = findViewById(R.id.description);
        rate = findViewById(R.id.rate);
        count = findViewById(R.id.count);

        Intent intent = getIntent();
        currentProductId = intent.getStringExtra("productId");

        loadDetails();
    }

    private void loadDetails() {
        DBHelper db = new DBHelper(this);
        ArrayList<ProductModel> arrayAllProducts = db.getSingleProduct(currentProductId);

        ProductModel data = arrayAllProducts.get(0);

        Glide.with(this).load(data.getImageUrl()).into(img);
        title.setText(data.getTitle());
        price.setText("₹" + data.getPrice());
        desc.setText(data.getDescription());
        category.setText(data.getCategory());
        rate.setText(data.rating.getRate());
        count.setText("(" + data.rating.getRateCount() + ")");

    }
}