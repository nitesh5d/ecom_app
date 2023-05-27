package in.fivedegree.ecomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        DBHelper db = new DBHelper(this);
        ArrayList<CartProductModel> arrayAllProducts = db.getAllCartProducts();
        recyclerView = findViewById(R.id.cartRView);
        CartProductsAdapter adapter = new CartProductsAdapter(arrayAllProducts, this) {
        };
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateTextandTotal(arrayAllProducts);

        Button checkoutBtn = findViewById(R.id.checkoutBtn);

        checkoutBtn.setOnClickListener(View ->{
            SharedPreferences sharedPreferences = getSharedPreferences("UserAuth", MODE_PRIVATE);
            String token = sharedPreferences.getString("token", null);
            if (token != null){
                TextView totalTv = findViewById(R.id.totalTv);
                double total = Double.parseDouble(totalTv.getText().toString());
                Intent i = new Intent(CartActivity.this, CheckoutActivity.class);
                i.putExtra("total", total);
                startActivity(i);
                finish();
            }
            else {
                View rootView = findViewById(android.R.id.content);
                Snackbar snackbar
                        = Snackbar.make(rootView, "You need to log in before Checkout", Snackbar.LENGTH_LONG)
                        .setAction("Login Now", view ->{
                            Intent i = new Intent(CartActivity.this, UserActivity.class);
                            startActivity(i);
                            finish();
                        });
                snackbar.show();
            }
        });
    }

    public void updateTextandTotal(ArrayList<CartProductModel> arrayAllProducts) {

        double sum = 0;
        TextView totalTv = findViewById(R.id.totalTv);
        if (arrayAllProducts.size() >0){
            TextView tv = findViewById(R.id.noProductstv);
            tv.setVisibility(View.GONE);
            for (int i = 0; i< arrayAllProducts.size();i++){
                sum = sum + Double.parseDouble(arrayAllProducts.get(i).getPrice())*Double.parseDouble(arrayAllProducts.get(i).getQty());
            }
            totalTv.setText(String.valueOf(sum));
        }
        else {
            Button checkoutBtn = findViewById(R.id.checkoutBtn);
            checkoutBtn.setEnabled(false);
        }
    }
}