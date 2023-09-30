package com.example.apnaghar;

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
import java.util.List;

public class MySellingsAdapter extends RecyclerView.Adapter<MySellingsAdapter.MySellingViewHolder> {

    private List<MySellingItem> mySellingList;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MySellingsAdapter(List<MySellingItem> mySellingList, Context context) {
        this.mySellingList = mySellingList;
        this.context = context;
    }

    @NonNull
    @Override
    public MySellingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_selling_item, parent, false);
        return new MySellingViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MySellingViewHolder holder, int position) {
        MySellingItem currentItem = mySellingList.get(position);

        holder.idTextView.setText(currentItem.getId());
        holder.bsTypeTextView.setText(currentItem.getBsType());
        holder.createdAtTextView.setText(currentItem.getCreatedAt());
        holder.priceTextView.setText(currentItem.getPrice());
        Glide.with(context)
                .load(currentItem.getImage())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.image_view_my_Selling);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mySellingList.size();
    }

    public static class MySellingViewHolder extends RecyclerView.ViewHolder {
        public TextView idTextView;
        public TextView bsTypeTextView;
        public TextView createdAtTextView;
        public TextView priceTextView;
        public ImageView image_view_my_Selling;
        public Button button;

        public MySellingViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.text_view_id);
            bsTypeTextView = itemView.findViewById(R.id.text_view_bs_type);
            createdAtTextView = itemView.findViewById(R.id.text_view_created_at);
            priceTextView = itemView.findViewById(R.id.text_view_price);
            image_view_my_Selling = itemView.findViewById(R.id.image_view_my_Selling);
            button = itemView.findViewById(R.id.item_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
