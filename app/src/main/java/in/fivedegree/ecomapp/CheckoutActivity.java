package in.fivedegree.ecomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

public class CheckoutActivity extends AppCompatActivity implements PaymentResultListener {

    RecyclerView recyclerView;
    TextView totalCheckout, placedTv;
    RadioGroup radioGroup;
    RadioButton radioBtn;
    Button placeOrder;
    EditText confirm;

    ProgressBar progressBar;
    DBHelper db;
    SharedPreferences sharedPreferences;
    String UserToken;
    String productsId = "";
    String productsQty = "";
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        recyclerView = findViewById(R.id.rvCheckout);
        totalCheckout = findViewById(R.id.totalCheckout);
        radioGroup = findViewById(R.id.radiogrp);
        placeOrder = findViewById(R.id.placeOrder);
        confirm = findViewById(R.id.confirmInp);
        placedTv = findViewById(R.id.placedTv);
        progressBar = findViewById(R.id.progressBar);
        db = new DBHelper(this);

        ArrayList<CartProductModel> arrayAllProducts = db.getAllCartProducts();
        CheckoutProductsAdapter adapter = new CheckoutProductsAdapter(arrayAllProducts, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        total = intent.getDoubleExtra("total", 0);
        totalCheckout.setText("Total Payable ₹" + String.valueOf(total));

        placeOrder.setOnClickListener(View ->{
            if (radioGroup.getCheckedRadioButtonId() == -1)
            {
                Toast.makeText(this, "Please select a Payment Method.", Toast.LENGTH_SHORT).show();
            }
            else if (!confirm.getText().toString().toLowerCase().equals("confirm order"))
            {
                confirm.setError("Please Confirm order.");
            }
            else
            {
                progressBar.setVisibility(android.view.View.VISIBLE);
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioBtn = findViewById(radioId);
                String paymentId = getResources().getResourceEntryName(radioBtn.getId());
                if (arrayAllProducts.size()>1){
                    for (int i = 0; i<arrayAllProducts.size();i++){
                        productsId = productsId + ">" + arrayAllProducts.get(i).getId();
                        productsQty = productsQty + ">" + arrayAllProducts.get(i).getQty();
                    }
                }
                else {
                    productsId = arrayAllProducts.get(0).getId();
                    productsQty = arrayAllProducts.get(0).getQty();
                }

                sharedPreferences = getSharedPreferences("UserAuth", MODE_PRIVATE);
                UserToken = sharedPreferences.getString("token", null);
                if (paymentId.equals("pod")){
                    if(db.placeOrder(UserToken, productsId, "Placed", "Pay On Delivery", total, productsQty,"-")){
                        placedTv.setVisibility(android.view.View.VISIBLE);
                        placedTv.setText("Order Placed!!");
                        progressBar.setVisibility(android.view.View.GONE);
                        Intent i = new Intent(CheckoutActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                        db.emptyCart();
                    }
                    else {
                        placedTv.setVisibility(android.view.View.VISIBLE);
                        placedTv.setText("Order Not Placed! Try again.");
                        progressBar.setVisibility(android.view.View.GONE);
                    }
                }
                else {
                    Checkout checkout = new Checkout();
                    checkout.setKeyID("rzp_test_H3mRkdxMxl3urA");
                    checkout.setImage(R.drawable.baseline_shopping_cart_24);

                    JSONObject object = new JSONObject();
                    try {
                        object.put("name", "ECOM PLUS");
                        object.put("description", "Shopping");
                        object.put("amount", Math.round(total*100));
                        object.put("prefill.contact", "+919699550419");
                        object.put("prefill.email", "niteshgupta288@gmail.com");
                        checkout.open(CheckoutActivity.this, object);
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public void onPaymentSuccess(String s) {
        if(db.placeOrder(UserToken, productsId, "Placed", "Online", total, productsQty,s)){
            placedTv.setVisibility(android.view.View.VISIBLE);
            placedTv.setText("Order Placed!!");
            progressBar.setVisibility(View.GONE);
            Intent i = new Intent(CheckoutActivity.this, MainActivity.class);
            startActivity(i);
            finish();
            db.emptyCart();
        }
        else {
            placedTv.setVisibility(android.view.View.VISIBLE);
            placedTv.setText("Payment Successful. Order not PLaced");
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        placedTv.setVisibility(android.view.View.VISIBLE);
        placedTv.setText("Payment Failed! Try again.");
        progressBar.setVisibility(View.GONE);
    }

}
