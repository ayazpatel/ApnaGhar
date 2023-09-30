package com.example.apnaghar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.CategoryViewHolder> {

    private List<CategoryItem> categoryItems;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(CategoryItem item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public HomeCategoryAdapter(List<CategoryItem> categoryItems, Context context) {
        this.categoryItems = categoryItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryItem currentItem = categoryItems.get(position);
        holder.textViewId.setText(currentItem.getId());
        holder.textViewBS_Type.setText(currentItem.getBS_Type());
        holder.textViewLandmark.setText(currentItem.getLandmark());
        holder.textViewPrice.setText(currentItem.getPrice());
        holder.textViewBS_For.setText(currentItem.getBS_For());
        Glide.with(holder.imageView.getContext()).load(currentItem.getImage1()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textViewBS_Type;
        public TextView textViewLandmark;
        public TextView textViewPrice;
        public TextView textViewId;
        public TextView textViewBS_For;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.HOME_CATEGORY_imageViewHOME_CATEGORY);
            textViewBS_Type = itemView.findViewById(R.id.HOME_CATEGORY_textViewBS_TypeHOME_CATEGORY);
            textViewLandmark = itemView.findViewById(R.id.HOME_CATEGORY_textViewLandmarkHOME_CATEGORY);
            textViewPrice = itemView.findViewById(R.id.HOME_CATEGORY_textViewPriceHOME_CATEGORY);
            textViewId = itemView.findViewById(R.id.HOME_CATEGORY_textViewIdHOME_CATEGORY);
            textViewBS_For = itemView.findViewById(R.id.HOME_CATEGORY_textViewBS_ForHOME_CATEGORY);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(categoryItems.get(position));
                        }
                    }
                }
            });
        }
    }
}
