package in.fivedegree.ecomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
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

        ProgressBar loading = findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        RetrofitInstance.getInstance().apiInterface.getProducts().enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                loading.setVisibility(View.GONE);
                products = response.body();
                if (products != null){
                    storeProductsInDatabase(products);
                    listAllProducts();
                }
            }
            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ImageView acc = findViewById(R.id.acc);
        acc.setOnClickListener(View ->{
            Intent i = new Intent(MainActivity.this, UserActivity.class);
            startActivity(i);
        });

        Button searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(View ->{
            EditText searchInp = findViewById(R.id.searchInp);
            String searchText = searchInp.getText().toString();
            if (searchText.equals("")) {
                Toast.makeText(getApplicationContext(), "Field cannot be empty.", Toast.LENGTH_SHORT).show();
            }
            else {
                searchForProduct(searchText);
            }
        });

        Button sortBtn  = findViewById(R.id.sort);
        sortBtn.setOnClickListener(View ->{
            LinearLayout sortBox = findViewById(R.id.sortBox);
            sortBox.setVisibility(android.view.View.VISIBLE);
        });

        ImageView closeBtn  = findViewById(R.id.closeSort);
        closeBtn.setOnClickListener(View ->{
            LinearLayout sortBox = findViewById(R.id.sortBox);
            sortBox.setVisibility(android.view.View.GONE);
        });


        LinearLayout priceLowToHigh = findViewById(R.id.pricelowtohigh);
        LinearLayout priceHighToLow = findViewById(R.id.pricehightolow);
        LinearLayout mostPopular = findViewById(R.id.mostpopular);
        LinearLayout leastPopular = findViewById(R.id.leastpopular);
        LinearLayout highestRated = findViewById(R.id.highestrated);

        priceLowToHigh.setOnClickListener(View ->{
            listLowToHigh();
        });

        priceHighToLow.setOnClickListener(View ->{
            listHighToLow();
        });

        mostPopular.setOnClickListener(View ->{
            listMostPopular();
        });

        leastPopular.setOnClickListener(View ->{
            listLeastPopular();
        });

        highestRated.setOnClickListener(View ->{
            listHighestRated();
        });
    }

    private void listLeastPopular() {
        DBHelper db = new DBHelper(this);
        ArrayList <ProductModel> arrayAllProducts = db.getLeastPopular();
        recyclerView = findViewById(R.id.recyclerView);
        AllProductsAdapter adapter = new AllProductsAdapter(arrayAllProducts, this, new AllProductsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ProductModel product) {
                Intent intent = new Intent(MainActivity.this, SingleProductActivity.class);
                intent.putExtra("productId", product.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        LinearLayout sortBox = findViewById(R.id.sortBox);
        sortBox.setVisibility(View.GONE);
    }

    private void listMostPopular() {
        DBHelper db = new DBHelper(this);
        ArrayList <ProductModel> arrayAllProducts = db.getMostPopular();
        recyclerView = findViewById(R.id.recyclerView);
        AllProductsAdapter adapter = new AllProductsAdapter(arrayAllProducts, this, new AllProductsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ProductModel product) {
                Intent intent = new Intent(MainActivity.this, SingleProductActivity.class);
                intent.putExtra("productId", product.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        LinearLayout sortBox = findViewById(R.id.sortBox);
        sortBox.setVisibility(View.GONE);
    }

    private void listHighestRated() {
        DBHelper db = new DBHelper(this);
        ArrayList <ProductModel> arrayAllProducts = db.getHighestRated();
        recyclerView = findViewById(R.id.recyclerView);
        AllProductsAdapter adapter = new AllProductsAdapter(arrayAllProducts, this, new AllProductsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ProductModel product) {
                Intent intent = new Intent(MainActivity.this, SingleProductActivity.class);
                intent.putExtra("productId", product.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        LinearLayout sortBox = findViewById(R.id.sortBox);
        sortBox.setVisibility(View.GONE);
    }

    private void listHighToLow() {
        DBHelper db = new DBHelper(this);
        ArrayList <ProductModel> arrayAllProducts = db.getProductHighToLow();
        recyclerView = findViewById(R.id.recyclerView);
        AllProductsAdapter adapter = new AllProductsAdapter(arrayAllProducts, this, new AllProductsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ProductModel product) {
                Intent intent = new Intent(MainActivity.this, SingleProductActivity.class);
                intent.putExtra("productId", product.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        LinearLayout sortBox = findViewById(R.id.sortBox);
        sortBox.setVisibility(View.GONE);
    }

    private void listLowToHigh() {
        DBHelper db = new DBHelper(this);
        ArrayList <ProductModel> arrayAllProducts = db.getProductLowToHigh();
        recyclerView = findViewById(R.id.recyclerView);
        AllProductsAdapter adapter = new AllProductsAdapter(arrayAllProducts, this, new AllProductsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ProductModel product) {
                Intent intent = new Intent(MainActivity.this, SingleProductActivity.class);
                intent.putExtra("productId", product.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        LinearLayout sortBox = findViewById(R.id.sortBox);
        sortBox.setVisibility(View.GONE);
    }

    private void searchForProduct(String title) {
        DBHelper db = new DBHelper(this);
        ArrayList <ProductModel> arrayAllProducts = db.getSearchProduct(title);
        recyclerView = findViewById(R.id.recyclerView);
        AllProductsAdapter adapter = new AllProductsAdapter(arrayAllProducts, this, new AllProductsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ProductModel product) {
                Intent intent = new Intent(MainActivity.this, SingleProductActivity.class);
                intent.putExtra("productId", product.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }

    private void listAllProducts() {
        DBHelper db = new DBHelper(this);
        ArrayList <ProductModel> arrayAllProducts = db.getAllProducts();
        recyclerView = findViewById(R.id.recyclerView);
        AllProductsAdapter adapter = new AllProductsAdapter(arrayAllProducts, this, new AllProductsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ProductModel product) {
                Intent intent = new Intent(MainActivity.this, SingleProductActivity.class);
                intent.putExtra("productId", product.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }

    private void storeProductsInDatabase(List<ProductModel> products) {
        DBHelper db = new DBHelper(this);

        for (int i = 0; i < products.size(); i++){
            String id = products.get(i).getId();
            String title = products.get(i).getTitle();
            double price = Double.parseDouble(products.get(i).getPrice());
            String category = products.get(i).getCategory();
            String desc = products.get(i).getDescription();
            String img = products.get(i).getImageUrl();
            double rate = Double.parseDouble(products.get(i).getRating().getRate());
            int ratecount = Integer.parseInt(products.get(i).getRating().getRateCount());
            db.addProducts(id, title, price, category, desc, img, rate, ratecount);
        }
    }
}