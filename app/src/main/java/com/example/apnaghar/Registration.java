package com.example.apnaghar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.chaos.view.PinView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
public class Registration extends AppCompatActivity  {
    Button reg,send;
    Button button,btn_method_run_verfication_check;
    PinView pinView;
    ProgressDialog progressDialog;
    private  LinearLayout phonenum,otpverify,form,registration_email_start,registration_email_confirm_start;
    String Registration1_fn,Registration1_ln,Registration1_ps,Registration1_pc,Registration1_ph;
    String otp;
    MediaPlayer bud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        Button send_email_confirm_link = findViewById(R.id.send_email_confirm_link);
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        phonenum=findViewById(R.id.phonenum);
        otpverify=findViewById(R.id.otpverify);
        form=findViewById(R.id.form);
        registration_email_start = findViewById(R.id.registration_email_start);
        registration_email_confirm_start =findViewById(R.id.registration_email_confirm_start);
        phonenum.setVisibility(View.GONE);
        otpverify.setVisibility(View.GONE);
        registration_email_start.setVisibility(View.GONE);
        registration_email_confirm_start.setVisibility(View.GONE);
        form.setVisibility(View.VISIBLE);
        send_email_confirm_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailField1 = findViewById(R.id.emailid1);
                EditText passField1 = findViewById(R.id.email_password1);
                EditText email_conpass1 = findViewById(R.id.email_conpass1);
                String reg_emailText = emailField1.getText().toString();
                String reg_passwordText = passField1.getText().toString();
                String reg_confirmText = email_conpass1.getText().toString();
                if(reg_passwordText.equals(reg_confirmText)) {
                    phonenum.setVisibility(View.GONE);
                    otpverify.setVisibility(View.GONE);
                    registration_email_start.setVisibility(View.GONE);
                    form.setVisibility(View.GONE);
                    registerUser();
                } else {
                    Toast.makeText(Registration.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_method_run_verfication_check = findViewById(R.id.btn_method_run_verfication_check);
        btn_method_run_verfication_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_email_verification();
            }
        });
        send=findViewById(R.id.btn_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bud.start();
                EditText phoneEditText = findViewById(R.id.phone);
                String phoneNumber = phoneEditText.getText().toString();
                if(phoneNumber.isEmpty()) {
                    phoneEditText.setError("Can't be empty!");
                }
                else if (phoneNumber.length() < 10) {
                    phoneEditText.setError("Invalid phone number!");
                } else {
                    sendOTP(phoneNumber);
                }
            }
        });
        bud=MediaPlayer.create(Registration.this,R.raw.bub);

        pinView=findViewById(R.id.pinview);
        button =findViewById(R.id.btn_verify);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bud.start();
                String enteredOTP = pinView.getText().toString();
                String generatedOTP = otp;
                if (enteredOTP.equals(generatedOTP)) {
                    otpverify.setVisibility(View.GONE);
                    phonenum.setVisibility(View.GONE);
                    otpverify.setVisibility(View.GONE);
                    registration_email_confirm_start.setVisibility(View.GONE);
                    registration_email_start.setVisibility(View.VISIBLE);
                    form.setVisibility(View.GONE);
                } else {
                    showToast("OTP verification failed. Please try again.");
                }
            }
        });
        reg=findViewById(R.id.btn_regist);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bud.start();
                String firstName = ((EditText) findViewById(R.id.firstname)).getText().toString();
                String lastName = ((EditText) findViewById(R.id.lastname)).getText().toString();
                Spinner citySpinner = findViewById(R.id.city_spinner);
                Spinner stateSpinner = findViewById(R.id.state_spinner);
                String selectedCity = citySpinner.getSelectedItem().toString();
                String selectedState = stateSpinner.getSelectedItem().toString();
                if (firstName.isEmpty() || lastName.isEmpty() || selectedCity.equals("Select city") || selectedState.equals("Select State")) {
                    showAlertDialog(Registration.this,"Validation Error","Please fill in all the required fields.",false);
                } else {
                    Registration1_fn = firstName;
                    Registration1_ln = lastName;
                    Registration1_ps = selectedState;
                    Registration1_pc = selectedCity;
                    phonenum.setVisibility(View.VISIBLE);
                    form.setVisibility(View.GONE);
                }
            }
        });

        Spinner citySpinner = findViewById(R.id.city_spinner);
        Spinner stateSpinner = findViewById(R.id.state_spinner);

        String[] state = {"Select State","Gujarat", "Maharastra"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, state);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);

        citySpinner.setContentDescription("Select a city");
        String[] cities = {"Select city","Surat", "Ahmedabad","Mumbai","Dadar","Vadodara", "Bharuch"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter1);
    }
    private void showToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
    private String generateOTP() {
        int min = 1000;
        int max = 9999;
        Random random = new Random();
        int otp = random.nextInt(max - min + 1) + min;
        return String.format("%04d", otp);
    }
    public void sendOTP(String phoneNumber) {
        otp = generateOTP();
        progressDialog.show();
        String apiUrl = "https://et.ayafitech.com/api/send_otp.php?n=" + phoneNumber + "&o=" + otp;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleErrorResponse(error);
                    }
                });
        queue.add(stringRequest);
    }
    private void handleResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("message")) {
                String message = jsonObject.getString("message");
                if (message.equals("SMS sent successfully.")) {
                    otpverify.setVisibility(View.VISIBLE);
                    phonenum.setVisibility(View.GONE);
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    showAlertDialog(Registration.this,"Registration Error","Failed to send OTP. Please try again.",false);
                }
            } else {
                progressDialog.dismiss();
                showAlertDialog(Registration.this,"Registration Error","Invalid response from the server.",false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            progressDialog.dismiss();
            showAlertDialog(Registration.this,"Registration Error","Failed to send OTP. JSON Exception: " + e.getMessage(),false);
        }
    }
    private void handleErrorResponse(VolleyError error) {
        progressDialog.dismiss();
        showAlertDialog(Registration.this,"Registration Error","Failed to send OTP. Volley Error: " + error.getMessage(),false);
    }
    private void registerUser() {
        progressDialog.show();
        String phoneNumber = ((EditText) findViewById(R.id.phone)).getText().toString();
        String firstName = ((EditText) findViewById(R.id.firstname)).getText().toString();
        String lastName = ((EditText) findViewById(R.id.lastname)).getText().toString();
        Spinner citySpinner = findViewById(R.id.city_spinner);
        Spinner stateSpinner = findViewById(R.id.state_spinner);
        String city = citySpinner.getSelectedItem().toString();
        String state = stateSpinner.getSelectedItem().toString();
        String email = ((EditText) findViewById(R.id.emailid1)).getText().toString();
        String password = ((EditText) findViewById(R.id.email_password1)).getText().toString();
        String apiUrl = "https://et.ayafitech.com/api/registration.php?" +
                "fn=" + firstName +
                "&ln=" + lastName +
                "&ps=" + state +
                "&pc=" + city +
                "&w=" + "0" +
                "&ph=" + phoneNumber +
                "&e=" + email +
                "&p=" + password;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest registrationRequest = new StringRequest(
                Request.Method.GET,
                apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleRegistrationResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleErrorResponse2(error);
                        ProgressBar progressBar = findViewById(R.id.progress_bar);
                        progressBar.setVisibility(View.GONE);
                    }
                });
        queue.add(registrationRequest);
    }
    private void handleRegistrationResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("message")) {
                String message = jsonObject.getString("message");
                if (message.equals("Confirmation email sent successfully!")) {
                    showToast("Registration successful!");
                    otpverify.setVisibility(View.GONE);
                    phonenum.setVisibility(View.GONE);
                    otpverify.setVisibility(View.GONE);
                    registration_email_confirm_start.setVisibility(View.VISIBLE);
                    registration_email_start.setVisibility(View.GONE);
                    form.setVisibility(View.GONE);
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    showAlertDialog(Registration.this,"Registration Error",message,false);
                }
            } else {
                progressDialog.dismiss();
                showAlertDialog(Registration.this,"Registration Error","Invalid response from the server.",false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            progressDialog.dismiss();
            showAlertDialog(Registration.this,"Registration Error","Failed to register user. JSON Exception: " + e.getMessage(),false);
        }
    }
    private void handleErrorResponse2(VolleyError error) {
        progressDialog.dismiss();
        showAlertDialog(Registration.this,"Registration Error","Failed to register user. Volley Error: " + error.getMessage(),false);
    }
    private void check_email_verification() {
        progressDialog.show();
        String email_for_verify = ((EditText) findViewById(R.id.emailid1)).getText().toString();
        String apiUrl = "https://et.ayafitech.com/api/check_email_verification.php?e=" + email_for_verify;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest registrationRequest = new StringRequest(
                Request.Method.GET,
                apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleEmailCheckResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleErrorResponse3(error);
                        progressDialog.show();
                    }
                });
        queue.add(registrationRequest);
    }
    private void handleEmailCheckResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("message")) {
                String message = jsonObject.getString("message");
                if (message.equals("Email is verified.")) {
                    showToast("Verified successfully!");
                    progressDialog.dismiss();
                    Intent intent = new Intent(Registration.this,Homescreeen.class);
                    startActivity(intent);
                    finish();
                } else {
                    showToast(message);
                    progressDialog.dismiss();
                }
            } else {
                progressDialog.dismiss();
                showAlertDialog(Registration.this,"Registration Error","Invalid response from the server.",false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            progressDialog.dismiss();
            showAlertDialog(Registration.this,"Registration Error","Failed to verify email. JSON Exception: " + e.getMessage(),false);
        }
    }
    private void handleErrorResponse3(VolleyError error) {
        progressDialog.dismiss();
        showAlertDialog(Registration.this,"Registration Error","Failed to verify email. Volley Error: " + error.getMessage(),false);
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