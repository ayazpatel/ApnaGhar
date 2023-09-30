package com.example.apnaghar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class BuyRentAdapter extends RecyclerView.Adapter<BuyRentAdapter.ViewHolder> {

    private Context context;
    private List<BuySellItem> items;

    public BuyRentAdapter(Context context, List<BuySellItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_buy_rent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BuySellItem item = items.get(position);
        holder.textViewIdRent.setText("ID: " + item.getId());
        holder.textViewBS_TypeRent.setText(item.getBS_Type());
        holder.textViewLandmarkRent.setText(" in " + item.getLandmark());
        holder.textViewPriceRent.setText(item.getPrice());
        holder.textViewBS_ForRent.setText(item.getBS_For());

        Glide.with(context)
                .load(item.getImage1())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(holder.imageViewRent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Buy_DetailActivity.class);
                intent.putExtra("buy_sell_Id", item.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewRent;
        TextView textViewIdRent;
        TextView textViewBS_TypeRent;
        TextView textViewLandmarkRent;
        TextView textViewPriceRent;
        TextView textViewBS_ForRent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewRent = itemView.findViewById(R.id.buyRent_imageViewRent);
            textViewIdRent = itemView.findViewById(R.id.buyRent_textViewIdRent);
            textViewBS_TypeRent = itemView.findViewById(R.id.buyRent_textViewBS_TypeRent);
            textViewLandmarkRent = itemView.findViewById(R.id.buyRent_textViewLandmarkRent);
            textViewPriceRent = itemView.findViewById(R.id.buyRent_textViewPriceRent);
            textViewBS_ForRent = itemView.findViewById(R.id.buyRent_textViewBS_ForRent);
        }
    }
}
