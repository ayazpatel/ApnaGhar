package com.example.apnaghar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class SuccessBooking extends AppCompatActivity {
    LottieAnimationView anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_booking);
        anim=findViewById(R.id.success_anim);
    }
    public void startAnimation(View view) {
        anim.playAnimation();
    }
}