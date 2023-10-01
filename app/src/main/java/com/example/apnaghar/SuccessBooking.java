package com.example.apnaghar;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
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

        MediaPlayer sound;
        sound=MediaPlayer.create(SuccessBooking.this,R.raw.apple);
        sound.start();
    }
    public void startAnimation(View view) {
        anim.playAnimation();
    }
}