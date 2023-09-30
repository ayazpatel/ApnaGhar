package com.example.apnaghar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.chaos.view.PinView;

public class otp extends AppCompatActivity {
    Button button;
    PinView pinView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        pinView=findViewById(R.id.pinview);
        button =findViewById(R.id.btn_verify);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp=pinView.getText().toString();
                Intent intent=new Intent(otp.this,email_verify.class);
                Toast.makeText(otp.this, otp, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });




    }
}