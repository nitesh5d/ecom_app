package in.fivedegree.ecomapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CheckoutProductsAdapter extends RecyclerView.Adapter<CheckoutProductsAdapter.holder> {

    ArrayList<CartProductModel> data;
    Context context;

    public CheckoutProductsAdapter(ArrayList<CartProductModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.checkout_products_layout,parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        Glide.with(context).load(data.get(position).getImageUrl()).into(holder.img);
        holder.title.setText(data.get(position).getTitle());
        holder.qty.setText("Quantity: " + data.get(position).getQty());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class holder extends RecyclerView.ViewHolder{

        public ImageView img;
        TextView title, qty;
        public holder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cartIv);
            title = itemView.findViewById(R.id.cartTitle);
            qty = itemView.findViewById(R.id.cartQty);
        }
    }

}
