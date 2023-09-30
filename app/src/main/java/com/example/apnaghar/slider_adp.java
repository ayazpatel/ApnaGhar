package com.example.apnaghar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class slider_adp extends RecyclerView.Adapter<slider_adp.slideVeiwHolder> {

    private List<slideritem> slideritems;
    private ViewPager2 viewPager2;

    slider_adp(List<slideritem> slideritems, ViewPager2 viewPager2) {
        this.slideritems = slideritems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public slideVeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new slideVeiwHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slider_image,
                        parent ,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull slideVeiwHolder holder, int position) {
        holder.setImage(slideritems.get(position));
        if (position == slideritems.size()-2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return slideritems.size();
    }

    class slideVeiwHolder extends RecyclerView.ViewHolder{
        private RoundedImageView imageView;


         slideVeiwHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_slide);
        }
        void setImage(slideritem slideritem){
            imageView.setImageResource(slideritem.getImage());
        }
    }

    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
           slideritems.addAll(slideritems);
           notifyDataSetChanged();
        }
    };
}
