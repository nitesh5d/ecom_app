package in.fivedegree.ecomapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public abstract class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.holder> {

    ArrayList<CartProductModel> data;
    Context context;

    Activity cartAct;

    public CartProductsAdapter(ArrayList<CartProductModel> data, Context context, Activity cartAct) {
        this.data = data;
        this.context = context;
        this.cartAct = cartAct;
    }


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cart_products_layout, parent, false);
        RecyclerView.ViewHolder v = new holder(view);
        return new holder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        Glide.with(context).load(data.get(position).getImageUrl()).into(holder.img);
        holder.title.setText(data.get(position).getTitle());
        holder.price.setText(data.get(position).getPrice());
        holder.qty.setText(data.get(position).getQty());
        holder.deleteItemBtn.setOnClickListener(View ->{
            DBHelper db = new DBHelper(context);
            db.deleteProductFromCart(data.get(position).id);
            data.remove(position);
            notifyItemChanged(position);
            updateTextandTotal();
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }



    class holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, price, qty;
        Button deleteItemBtn;
        public holder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            deleteItemBtn = itemView.findViewById(R.id.deleteItemBtn);
        }
    }

    public void updateTextandTotal() {
        double sum = 0;
        TextView totalTv = ((Activity)context).findViewById(R.id.totalTv);
        TextView tv = ((Activity)context).findViewById(R.id.noProductstv);

        if (data.size() == 0){
            tv.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i< data.size();i++){
            sum = sum + Double.parseDouble(data.get(i).getPrice())*Double.parseDouble(data.get(i).getQty());
        }
        totalTv.setText(String.valueOf(sum));
    }
}
