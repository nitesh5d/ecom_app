package in.fivedegree.ecomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    }
}