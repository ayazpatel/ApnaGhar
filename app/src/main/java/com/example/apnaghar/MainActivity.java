package com.example.apnaghar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
//import com.onesignal.OneSignal;
//import com.onesignal.debug.LogLevel;

public class MainActivity extends AppCompatActivity {
    LottieAnimationView lottie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent ilogo=new Intent(MainActivity.this,login.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(ilogo);
            }
        },2000);

        Constants constants = new Constants();
        constants.GET_SERVER_SIGNAL(getApplicationContext(),constants.APP_SERVER_SIGNAL_ID);
    }
}