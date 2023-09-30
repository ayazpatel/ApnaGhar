package com.example.apnaghar;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apnaghar.R;

public class PaymentActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        String amount = getIntent().getStringExtra("amount");
        String email = getIntent().getStringExtra("email");

        Toast.makeText(this, "Received amount: " + amount + ", email: " + email, Toast.LENGTH_SHORT).show();

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String razorpayPaymentUrl = "https://et.ayafitech.com/api/razorpay/addwallet.php?email=" + email + "&amount=" + amount;

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                webView.loadUrl(request.getUrl().toString());
                return true;
            }
        });

        webView.loadUrl(razorpayPaymentUrl);
    }
}
