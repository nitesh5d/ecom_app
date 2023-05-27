package in.fivedegree.ecomapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

public abstract class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.holder> {

    ArrayList<CartProductModel> data;
    Context context;

    public CartProductsAdapter(ArrayList<CartProductModel> data, Context context) {
        this.data = data;
        this.context = context;
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

        holder.increaseQty.setOnClickListener(View ->{
            int newqty = Integer.parseInt(data.get(position).getQty());
            newqty++;
            data.get(position).setQty(String.valueOf(newqty));
            notifyDataSetChanged();
            updateTextandTotal();
            DBHelper db = new DBHelper(context);
            db.increaseQty(data.get(position).id, newqty);
        });

        holder.decreaseQty.setOnClickListener(View ->{
            int newqty = Integer.parseInt(data.get(position).getQty());
            if (newqty > 1){
                newqty--;
                data.get(position).setQty(String.valueOf(newqty));
                notifyDataSetChanged();
                updateTextandTotal();
                DBHelper db = new DBHelper(context);
                db.decreaseQty(data.get(position).id, newqty);
            }

        });

        holder.deleteItemBtn.setOnClickListener(View ->{
            DBHelper db = new DBHelper(context);
            db.deleteProductFromCart(data.get(holder.getAdapterPosition()).id);
            int removedPosition = holder.getAdapterPosition();
            data.remove(removedPosition);
            notifyItemRemoved(removedPosition);
            notifyItemRangeChanged(removedPosition, getItemCount() - removedPosition);
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
        Button deleteItemBtn, increaseQty, decreaseQty;

        public holder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            deleteItemBtn = itemView.findViewById(R.id.deleteItemBtn);
            increaseQty = itemView.findViewById(R.id.increaseQty);
            decreaseQty = itemView.findViewById(R.id.decreaseQty);
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
        DecimalFormat decfor = new DecimalFormat("0.00");
        totalTv.setText(decfor.format(sum));
    }
}
