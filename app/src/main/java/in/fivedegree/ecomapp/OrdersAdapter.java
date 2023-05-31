package in.fivedegree.ecomapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<holder> {

    ArrayList<OrdersModel> data;
    Context context;

    public OrdersAdapter(ArrayList<OrdersModel> data, Context context) {
        this.data = data;
        this.context = context;
    }




    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.orders_layout, parent, false);
        return new holder(v);
    }




    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

//        Setting Order Product Image here
        String productImg;
        DBHelper db = new DBHelper(context);
        if (data.get(position).productId.contains(">")){
            String[] qtyArray = data.get(position).getQty().split(">");
            String[] idArray = data.get(position).getProductId().split(">");

            CartProductModel product = db.getOrderProduct(idArray[1]);
            productImg = product.getImageUrl();
            Glide.with(context).load(productImg).into(holder.orderIv);

        }
        else {
            CartProductModel product = db.getOrderProduct(data.get(position).productId);
            productImg = product.getImageUrl();
            Glide.with(context).load(productImg).into(holder.orderIv);
        }

//        Setting Other Order Details here
        holder.orderId.setText("Order Id #" + data.get(position).getId());
        holder.orderCost.setText("Cost: ₹" + data.get(position).getCost());
//        holder.orderStatus.setText("Order Status: " + data.get(position).getOrderStatus());
//        holder.payMode.setText("Payment Method: " + data.get(position).getPayMode());

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, OrderDetailsActivity.class);
            i.putExtra("id", data.get(position).getId());
            context.startActivity(i);
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}




class holder extends RecyclerView.ViewHolder {

    ImageView orderIv, viewOrder;
    TextView orderId, orderStatus, payMode, orderCost;

    public holder(@NonNull View itemView) {
        super(itemView);

        orderId = itemView.findViewById(R.id.orderId);
//        orderStatus = itemView.findViewById(R.id.orderStatus);
//        payMode = itemView.findViewById(R.id.payMode);
        orderIv = itemView.findViewById(R.id.orderIv);
        orderCost = itemView.findViewById(R.id.orderCost);
        viewOrder = itemView.findViewById(R.id.viewOrder);



    }
}