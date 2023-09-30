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

public class BuySellAdapter extends RecyclerView.Adapter<BuySellAdapter.ViewHolder> {

    private Context context;
    private List<BuySellItem> items;

    public BuySellAdapter(Context context, List<BuySellItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_buy_sell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BuySellItem item = items.get(position);
        holder.textViewIdSell.setText("ID: " + item.getId());
        holder.textViewBS_TypeSell.setText(item.getBS_Type());
        holder.textViewLandmarkSell.setText(" in " + item.getLandmark());
        holder.textViewPriceSell.setText(item.getPrice());
        holder.textViewBS_ForSell.setText(item.getBS_For());

        Glide.with(context)
                .load(item.getImage1())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(holder.imageViewSell);

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
        ImageView imageViewSell;
        TextView textViewIdSell;
        TextView textViewBS_TypeSell;
        TextView textViewLandmarkSell;
        TextView textViewPriceSell;
        TextView textViewBS_ForSell;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSell = itemView.findViewById(R.id.buySEll_imageViewSEll);
            textViewIdSell = itemView.findViewById(R.id.buySEll_textViewIdSEll);
            textViewBS_TypeSell = itemView.findViewById(R.id.buySEll_textViewBS_TypeSEll);
            textViewLandmarkSell = itemView.findViewById(R.id.buySEll_textViewLandmarkSEll);
            textViewPriceSell = itemView.findViewById(R.id.buySEll_textViewPriceSEll);
            textViewBS_ForSell = itemView.findViewById(R.id.buySEll_textViewBS_ForSEll);
        }
    }
}
