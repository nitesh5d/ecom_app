package in.fivedegree.ecomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {

    TextView OrderId, Status, PayMode, Cost;
    RecyclerView orderRview;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        OrderId = findViewById(R.id.orderId);
        Status = findViewById(R.id.status);
        PayMode = findViewById(R.id.paymode);
        orderRview = findViewById(R.id.rView);
        Cost = findViewById(R.id.cost);


        Intent intent = getIntent();
        String id = intent.getStringExtra("id"); //accepting Order ID
        db = new DBHelper(this);
        ArrayList<OrdersModel> arrayOrder = db.getSingleOrder(id); //Getting Order Details in ArrayList from database where OrderId is (id)
        OrdersModel data = arrayOrder.get(0); //passing 1st row of details in Model class.

        //displaying order details to user.
        OrderId.setText(data.getId());
        Status.setText(data.getOrderStatus());
        PayMode.setText(data.getPayMode());
        Cost.setText(String.valueOf(data.getCost()));

        CartProductModel product;
        ArrayList<CartProductModel> productList = new ArrayList<>();
        productList.clear();
        if (data.getProductId().contains(">")){
            String[] idArray = data.getProductId().split(">");
            String[] qtyArray = data.getQty().split(">");

            for (int i = 0; i < idArray.length; i++) {
                product = db.getOrderProduct(idArray[i]);
                product.setQty(qtyArray[i]);
                productList.add(product);
            }
            productList.remove(0);
        }
        else {
            String[] idArray = new String[1];
            String[] qtyArray = new String[1];
            idArray[0] = data.getProductId();
            qtyArray[0] = data.getQty();
            product = db.getOrderProduct(idArray[0]);
            product.setQty(qtyArray[0]);
            productList.add(product);
        }
        OrdersProductsAdapter adapter = new OrdersProductsAdapter(productList, this);
        orderRview.setAdapter(adapter);
        orderRview.setLayoutManager(new LinearLayoutManager(this));

    }
}