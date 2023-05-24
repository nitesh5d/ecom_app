package in.fivedegree.ecomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        DBHelper db = new DBHelper(this);
        ArrayList<CartProductModel> arrayAllProducts = db.getAllCartProducts();
        if (arrayAllProducts.size() >0){
            TextView tv = findViewById(R.id.noProductstv);
            tv.setVisibility(View.GONE);
        }
        recyclerView = findViewById(R.id.cartRView);
        CartProductsAdapter adapter = new CartProductsAdapter(arrayAllProducts, this) {
        };
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}