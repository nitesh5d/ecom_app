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

public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.holder> {

    ArrayList<ProductModel> data;
    Context context;

    public AllProductsAdapter(ArrayList<ProductModel> data, Context context) {
        this.data = data;
        this.context = context;
    }



    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_list_card, parent, false);
        RecyclerView.ViewHolder v = new holder(view);
        return new holder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.price.setText("₹ "+data.get(position).getPrice());
        Glide.with(context).load(data.get(position).getImageUrl()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }







    class holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, price;
        public holder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
        }
    }

}
