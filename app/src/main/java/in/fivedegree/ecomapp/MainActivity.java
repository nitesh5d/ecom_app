package in.fivedegree.ecomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    public static String API = "https://fakestoreapi.com";
    List<ProductModel> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RetrofitInstance.getInstance().apiInterface.getProducts().enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                products = response.body();
                storeProductsInDatabase(products);
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }

    private void storeProductsInDatabase(List<ProductModel> products) {
        DBHelper db = new DBHelper(this);

        for (int i = 0; i < products.size(); i++){
            String id = products.get(i).getId();
            String title = products.get(i).getTitle();
            String price = products.get(i).getPrice();
            String category = products.get(i).getCategory();
            String desc = products.get(i).getDescription();
            String img = products.get(i).getImageUrl();
            String rate = products.get(i).getRating().getRate();
            String ratecount = products.get(i).getRating().getRateCount();
            db.addProducts(id, title, price, category, desc, img, rate, ratecount);
        }
    }
}