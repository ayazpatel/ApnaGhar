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

public class Buy_MyAdapter extends RecyclerView.Adapter<Buy_MyAdapter.ViewHolder> {

    private List<Buy_MyDataModel> data;
    private Context context;

    public Buy_MyAdapter(Context context, List<Buy_MyDataModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buy_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Buy_MyDataModel item = data.get(position);
        holder.textViewId.setText(item.getId());
        holder.textViewBSType.setText(item.getBS_Type());
        holder.textViewBS_Sub_Type.setText(item.getBS_Sub_Type());
        holder.textViewBS_Sub_Type2.setText(item.getBS_Sub_Type2());
        holder.textViewBS_For.setText(item.getBS_For());
        holder.textViewLandmark.setText(item.getLandmark());
        holder.textViewPrice.setText(item.getPrice());
        holder.textViewIsFeatured.setText(item.getIs_Featured());

        if (item.getIs_Featured().equals("1")) {
            holder.imageView1.setVisibility(View.VISIBLE);
        } else {
            holder.imageView1.setVisibility(View.GONE);
        }

        Glide.with(context)
                .load(item.getImage1())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.imageView);

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
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId;
        TextView textViewBSType;
        TextView textViewBS_Sub_Type;
        TextView textViewBS_Sub_Type2;
        TextView textViewBS_For;
        TextView textViewLandmark;
        TextView textViewPrice;
        TextView textViewIsFeatured;
        ImageView imageView,imageView1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewBSType = itemView.findViewById(R.id.textViewBSType);
            textViewBS_Sub_Type = itemView.findViewById(R.id.textViewBS_Sub_Type);
            textViewBS_Sub_Type2 = itemView.findViewById(R.id.textViewBS_Sub_Type2);
            textViewBS_For = itemView.findViewById(R.id.textViewBS_For);
            textViewLandmark = itemView.findViewById(R.id.textViewLandmark);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewIsFeatured = itemView.findViewById(R.id.textViewIsFeatured);
            imageView = itemView.findViewById(R.id.imageView_buy_recycler_view);
            imageView1 = itemView.findViewById(R.id.hotDealImageView);
        }
    }
}
