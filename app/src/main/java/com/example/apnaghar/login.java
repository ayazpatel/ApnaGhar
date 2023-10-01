package com.example.apnaghar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class login extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "shared_preferences_for_email_login";
    public static final String KEY_EMAIL = "email_to_login";
    public static final String KEY_PASSWORD = "password_to_login";
    Button email,phone,btn_phone_otp_verify;
    PinView phone_otp_pinview;
    AlertDialog dialog;
    ProgressDialog progressDialog;
    private LinearLayout email_layout,phone_layout;
    String otp;
    public String savedEmail,savedPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        savedEmail = sharedPreferences.getString(KEY_EMAIL, "");
        savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");
        EditText emailEditText = findViewById(R.id.email_for_login);
        EditText passwordEditText = findViewById(R.id.password_for_login);
        if (!savedEmail.isEmpty() && !savedPassword.isEmpty()) {
            emailEditText.setText(savedEmail);
            passwordEditText.setText(savedPassword);

            try {
                loginEmail();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Auto-login Exception: " + e.toString(), Toast.LENGTH_LONG).show();
            }
        }


        email=findViewById(R.id.btn_email);
        phone=findViewById(R.id.btn_phone);
        email_layout=findViewById(R.id.email_layout);
        phone_layout=findViewById(R.id.phone_layout);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isvisible =email_layout.getVisibility();
                if(isvisible==View.VISIBLE){
                    phone_layout.setVisibility(View.GONE);
                    email_layout.setVisibility(View.VISIBLE);
                }else {
                    email_layout.setVisibility(View.VISIBLE);
                    phone_layout.setVisibility(View.GONE);
                }
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visb =phone_layout.getVisibility();
                if(visb==View.VISIBLE){
                    email_layout.setVisibility(View.VISIBLE);
                }else {
                    phone_layout.setVisibility(View.VISIBLE);
                    email_layout.setVisibility(View.GONE);
                }
            }
        });
        View view = LayoutInflater.from(login.this).inflate(R.layout.phone_otp_login_dialog, null);
        dialog = new AlertDialog.Builder(login.this)
                .setView(view).create();
        dialog.setCancelable(false);
        btn_phone_otp_verify = view.findViewById(R.id.btn_phone_otp_verify);
        phone_otp_pinview = view.findViewById(R.id.phone_otp_pinview);

        btn_phone_otp_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginEnteredOtp = phone_otp_pinview.getText().toString();
                if (otp.equals(loginEnteredOtp)) {
                    if (savedEmail != null && !savedEmail.isEmpty()) {
                        Intent intent = new Intent(login.this, Homescreeen.class);
                        intent.putExtra(KEY_EMAIL, savedEmail);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(login.this, Homescreeen.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    showAlertDialog(login.this,"Login Error","Login Failed",false);
                }
            }
        });
        Button btn_email_login=findViewById(R.id.btn_email_login);
        btn_email_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEmail();
            }
        });
        Button btn_phone_login =findViewById(R.id.btnphone_login);
        btn_phone_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPhone();
            }
        });
    }
    public void signup(View view) {
        Intent intent=new Intent(login.this,Registration.class);
        startActivity(intent);
    }

    private void loginEmail() {
        progressDialog.show();
        Button btnEmailLogin = findViewById(R.id.btn_email_login);
        String email_for_login = ((EditText) findViewById(R.id.email_for_login)).getText().toString();
        String password_for_login = ((EditText) findViewById(R.id.password_for_login)).getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email_for_login);
        editor.putString(KEY_PASSWORD, password_for_login);
        editor.apply();
        String apiUrl = "https://et.ayafitech.com/api/login_email.php?e=" + email_for_login + "&p=" + password_for_login;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest registrationRequest = new StringRequest(
                Request.Method.GET,
                apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleEmailLoginResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleEmailLoginErrorResponse(error);
                        btnEmailLogin.setEnabled(true);
                    }
                }
        );
        queue.add(registrationRequest);
    }
    private void handleEmailLoginResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("message")) {
                String message = jsonObject.getString("message");
                if (message.equals("Login successful!")) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(login.this, Homescreeen.class);
                    intent.putExtra(KEY_EMAIL, savedEmail);
                    startActivity(intent);
                    finish();
                } else if (message.equals("Email is not verified.")) {
                    progressDialog.dismiss();
                    showAlertDialog(login.this, "Warning", "We have sent a confirmation link on your email account\nPlease verify it", false);
                } else {
                    progressDialog.dismiss();
                    Log.e("Error Resolve",message);
                    showAlertDialog(login.this, "Login Error", "Login failed, try again!", false);
                }
            } else {
                progressDialog.dismiss();
                showAlertDialog(login.this, "Login Error", "Invalid response from the server.", false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            progressDialog.dismiss();
            showAlertDialog(login.this, "Login Error", "Failed to verify email. JSON Exception: \n" + e.getMessage(), false);
        }
    }
    private void handleEmailLoginErrorResponse(VolleyError error) {
        progressDialog.dismiss();
        showAlertDialog(login.this, "Login Error", "Failed to verify email. Volley Error: \n" + error.getMessage(), false);
    }



    //private void loginEmail() {
//    progressDialog.show();
//    Button btnEmailLogin = findViewById(R.id.btn_email_login);
//    String email_for_login = ((EditText) findViewById(R.id.email_for_login)).getText().toString();
//    String password_for_login = ((EditText) findViewById(R.id.password_for_login)).getText().toString();
//
//    SharedPreferences.Editor editor = sharedPreferences.edit();
//    editor.putString(KEY_EMAIL, email_for_login);
//    editor.putString(KEY_PASSWORD, password_for_login);
//    editor.apply();
//
//    String apiUrl = "https://et.ayafitech.com/api/login_email.php?e=" + email_for_login + "&p=" + password_for_login;
//    RequestQueue queue = Volley.newRequestQueue(this);
//    StringRequest registrationRequest = new StringRequest(
//            Request.Method.GET,
//            apiUrl,
//            new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    handleEmailLoginResponse(response);
//                }
//            },
//            new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    handleEmailLoginErrorResponse(error);
////                    Toast.makeText(login.this, error.toString(), Toast.LENGTH_SHORT).show();
//                    btnEmailLogin.setEnabled(true);
//                }
//            });
//    queue.add(registrationRequest);
//}
//    private void handleEmailLoginResponse(String response) {
//        String temp_email_agent = savedEmail;
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            if (jsonObject.has("message")) {
//                String message = jsonObject.getString("message");
//                if (message.equals("Login successful!")) {
//                    progressDialog.dismiss();
//                    if (savedEmail != null && !savedEmail.isEmpty()) {
//                        Intent intent = new Intent(login.this, Homescreeen.class);
//                        intent.putExtra(KEY_EMAIL, savedEmail);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Intent intent = new Intent(login.this, Homescreeen.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                } else if (message.equals("Email is not verified.")) {
//                    progressDialog.dismiss();
//                    showAlertDialog(login.this,"Warning","We have sent a confirmation link on your email account\nPlease verify it",false);
//                } else {
//                    progressDialog.dismiss();
//                    showAlertDialog(login.this,"Login Error","Login failed, try again!",false);
//                }
//            } else {
//                progressDialog.dismiss();
//                showAlertDialog(login.this,"Login Error","Invalid response from the server.",false);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            progressDialog.dismiss();
//            showAlertDialog(login.this,"Login Error","Failed to verify email. JSON Exception: \n" + e.getMessage(),false);
//        }
//    }
//    private void handleEmailLoginErrorResponse(VolleyError error) {
//        progressDialog.dismiss();
//        showAlertDialog(login.this,"Login Error","Failed to verify email. Volley Error: \n" + error.getMessage(),false);
//    }
    private String generateOTP_for_phone() {
        int min = 1000;
        int max = 9999;
        Random random = new Random();
        int otp = random.nextInt(max - min + 1) + min;
        return String.format("%04d", otp);
    }
    private void loginPhone() {
        progressDialog.show();
        String phoneno_to_login = ((EditText) findViewById(R.id.phoneno_to_login)).getText().toString();
        otp = generateOTP_for_phone();
        String apiUrl = "https://et.ayafitech.com/api/login_phone.php?n=" + phoneno_to_login + "&o=" + otp;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handlePhoneLoginResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handlePhoneLoginErrorResponse(error);
                    }
                });
        queue.add(stringRequest);
    }
    private void handlePhoneLoginResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("message")) {
                String message = jsonObject.getString("message");
                if (message.equals("SMS sent successfully.")) {
                    showToast(message);
                    dialog.show();
                } else if (message.equals("Email is not verified.")) {
                    progressDialog.dismiss();
                    showAlertDialog(login.this,"Email Verification","We have sent a confirmation link on your email account\nPlease verify it",false);
                } else {
                    progressDialog.dismiss();
                    showAlertDialog(login.this,"Login Error","Failed to send OTP. Please try again",false);
                }
            } else {
                progressDialog.dismiss();
                showAlertDialog(login.this,"Login Error","Invalid response from the server.",false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            progressDialog.dismiss();
            showAlertDialog(login.this,"Login Error","Failed to send OTP. JSON Exception: " + e.getMessage(),false);
        }
    }
    private void handlePhoneLoginErrorResponse(VolleyError error) {
        progressDialog.dismiss();
        showAlertDialog(login.this,"Login Error","Failed to send OTP. Volley Error: "+ error.getMessage(),false);
    }
    private void showToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
    private void showAlertDialog(Context context, String title, String message,
                                       Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        if (status != null)
            alertDialog.setIcon((status) ? R.drawable.verified_ayaz : R.drawable.warning_ayaz);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}