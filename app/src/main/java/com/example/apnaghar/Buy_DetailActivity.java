package com.example.apnaghar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;

import java.util.HashMap;
import java.util.Map;

public class Buy_DetailActivity extends AppCompatActivity {

    private ImageView buyDetail_imageView1;
    private ImageView buyDetail_imageView2;
    private TextView buyDetail_textViewBS_Type;
    private TextView buyDetail_textViewBS_Sub_Type;
    private TextView buyDetail_textViewBS_Sub_Type2;
    private TextView buyDetail_textViewBS_For;
    private TextView buyDetail_textViewPrice;
    private TextView buyDetail_textViewAddress;
    private TextView buyDetail_textViewLandmark;
    private TextView buyDetail_textViewState;
    private TextView buyDetail_textViewCity;
    private TextView buyDetail_textViewDescription;
    private TextView buyDetail_textViewOwner;
    private TextView buyDetail_textViewPhone_No;
    private TextView buyDetail_textViewEmail_Id;
    private TextView buyDetail_textViewCreated_At;
    Button btn_buynow_buydetail,btn_back_needed;
    MediaPlayer sound;
    String email_agent_reciver = "";
    ProgressDialog progressDialog;
    public String token_booking_price = "1000";
    public String prop_id = "";
    public String company_brand = "-by APNA GHAR";
    public String image1Url,landlord_phone,booker_phone,landlord_name,booker_name,landlord_email,booker_email,home_detail_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_detail);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        email_agent_reciver = preferences.getString("email_agent_reciver", "");
        buyDetail_imageView1 = findViewById(R.id.buyDetail_imageView1);
        buyDetail_imageView2 = findViewById(R.id.buyDetail_imageView2);
        buyDetail_textViewBS_Type = findViewById(R.id.buyDetail_textViewBS_Type);
        buyDetail_textViewBS_Sub_Type = findViewById(R.id.buyDetail_textViewBS_Sub_Type);
        buyDetail_textViewBS_Sub_Type2 = findViewById(R.id.buyDetail_textViewBS_Sub_Type2);
        buyDetail_textViewBS_For = findViewById(R.id.buyDetail_textViewBS_For);
        buyDetail_textViewPrice = findViewById(R.id.buyDetail_textViewPrice);
        buyDetail_textViewAddress = findViewById(R.id.buyDetail_textViewAddress);
        buyDetail_textViewLandmark = findViewById(R.id.buyDetail_textViewLandmark);
        buyDetail_textViewState = findViewById(R.id.buyDetail_textViewState);
        buyDetail_textViewCity = findViewById(R.id.buyDetail_textViewCity);
        buyDetail_textViewDescription = findViewById(R.id.buyDetail_textViewDescription);
        buyDetail_textViewOwner = findViewById(R.id.buyDetail_textViewOwner);
        buyDetail_textViewPhone_No = findViewById(R.id.buyDetail_textViewPhone_No);
        buyDetail_textViewEmail_Id = findViewById(R.id.buyDetail_textViewEmail_Id);
        buyDetail_textViewCreated_At = findViewById(R.id.buyDetail_textViewCreated_At);

        btn_back_needed = findViewById(R.id.btn_back_needed);
        btn_back_needed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Homescreeen.class);
                startActivity(intent);
                finish();
            }
        });

        sound=MediaPlayer.create(Buy_DetailActivity.this,R.raw.apple);
//        sound.start();
        btn_buynow_buydetail=findViewById(R.id.btn_buynow_buydetail);
        btn_buynow_buydetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_local_user(email_agent_reciver);
            }
        });

        String buy_sell_Id = getIntent().getStringExtra("buy_sell_Id");

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://et.ayafitech.com/api/buy_sell_detail.php?id=" + buy_sell_Id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("data");

                            if (dataArray.length() > 0) {
                                JSONObject product = dataArray.getJSONObject(0);
                                prop_id = product.getString("id");
                                String bsType = product.getString("BS_Type");
                                String bs_sub_Type = product.getString("BS_Sub_Type");
                                String bs_sub_Type2 = product.getString("BS_Sub_Type2");
                                String bs_for = product.getString("BS_For");
                                String price = product.getString("Price");
                                String address = product.getString("Address");
                                String landmark = product.getString("Landmark");
                                String state = product.getString("State");
                                String city = product.getString("City");
                                String description = product.getString("Description");
                                String owner = product.getString("Owner");
                                String phoneNo = product.getString("Phone_No");
                                landlord_phone = phoneNo;
                                String emailId = product.getString("Email_Id");
                                landlord_email = emailId;
                                landlord_name = owner;
                                home_detail_address = address+"\n"+landmark+"\n"+city+"\n"+state;
                                String createdDate = product.getString("Created_At");

                                buyDetail_textViewBS_Type.setText(bsType);
                                buyDetail_textViewBS_Sub_Type.setText(bs_sub_Type);
                                buyDetail_textViewBS_Sub_Type2.setText(bs_sub_Type2);
                                buyDetail_textViewBS_For.setText(bs_for);
                                buyDetail_textViewPrice.setText(price);
                                buyDetail_textViewAddress.setText(address);
                                buyDetail_textViewLandmark.setText(landmark);
                                buyDetail_textViewState.setText(state);
                                buyDetail_textViewCity.setText(city);
                                buyDetail_textViewDescription.setText(description);
                                buyDetail_textViewOwner.setText(owner);
                                buyDetail_textViewPhone_No.setText(phoneNo);
                                buyDetail_textViewEmail_Id.setText(emailId);
                                buyDetail_textViewCreated_At.setText(createdDate);

                                image1Url = product.getString("Image1");
                                String image2Url = product.getString("Image2");

                                Glide.with(Buy_DetailActivity.this)
                                        .load(image1Url)
                                        .placeholder(R.drawable.placeholder_image)
                                        .into(buyDetail_imageView1);

                                Glide.with(Buy_DetailActivity.this)
                                        .load(image2Url)
                                        .placeholder(R.drawable.placeholder_image)
                                        .into(buyDetail_imageView2);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Buy_DetailActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);

    }
    private void showCustomAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alertdailog, null);
        alertDialogBuilder.setView(dialogView);
        alertDialogBuilder.setTitle("Book Your Dream House");
        alertDialogBuilder.setMessage("Your booking will cost Rs. 1000 as a Token money\nMoney will be deducted from your wallet!\nSure! You Want To Book?");

        alertDialogBuilder.setPositiveButton("Make Payment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                make_payment(email_agent_reciver,token_booking_price);
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void get_local_user(String local_data) {
        progressDialog.show();
        String apiUrl = "https://et.ayafitech.com/api/get_local_user.php?id="+local_data;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int userId = response.getInt("user_id");
                            String firstName = response.getString("first_name");
                            String lastName = response.getString("last_name");
                            String email = response.getString("email_id");
                            String phone = response.getString("phone_no");
                            booker_phone = phone;
                            booker_name = firstName+" "+lastName;
                            booker_email = email;
                            String wallet = response.getString("wallet");
                            String state = response.getString("prefered_state");
                            String city = response.getString("prefered_city");
                            String createdAt = response.getString("created_at");
                            progressDialog.dismiss();
                            showCustomAlertDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(Buy_DetailActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void make_payment(String local_email, String local_token_price) {
        progressDialog.show();
        String apiUrl = "https://et.ayafitech.com/api/make_payment.php?name="+booker_name+"&phone=8128077786&email="+local_email+"&amount="+local_token_price+"&property_id="+prop_id;
//        String apiUrl = "https://et.ayafitech.com/api/make_payment.php?email=" + local_email + "&amount=" + local_token_price;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                progressDialog.dismiss();
                                send_to_landlord(landlord_phone,"Your property has been successfully booked!"+
                                        "%0A%0AProperty Details: "+home_detail_address+
                                        "%0A%0AClient Details:"+
                                        "%0AName: "+booker_name+
                                        "%0APhone: "+booker_phone+
                                        "%0AEmail: "+booker_email+"%0A%0A%0A%"+company_brand,image1Url);
                                send_to_booker(booker_phone,"Property has been successfully booked!%0A%0A" +
                                        "Property Details: "+home_detail_address+"%0A%0A"+
                                        "Landlord Details: "+
                                        "%0AName: "+landlord_name+
                                        "%0APhone: "+landlord_phone+
                                        "%0AEmail: "+landlord_email+"%0A%0A%0A%"+company_brand,image1Url);
                            } else {
                                String message = response.getString("message");
                                progressDialog.dismiss();
//                                Toast.makeText(getApplicationContext(), "Payment Failed: " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void send_to_landlord(String phone, String message, String image) {
        progressDialog.show();
        String url = "https://et.ayafitech.com/api/whatsapp/send_to_landlord.php?phone="+phone+"&content="+message+"&property="+image;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String messageStatus = response.getString("message_status");
                            if(messageStatus.equals("Success")) {
                                progressDialog.dismiss();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
    private void send_to_booker(String phone, String message, String image) {
        progressDialog.show();
        String url = "https://et.ayafitech.com/api/whatsapp/send_to_landlord.php?phone="+phone+"&content="+message+"&property="+image;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Toast.makeText(Buy_DetailActivity.this, "PAymetn Sucecc", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),SuccessBooking.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Buy_DetailActivity.this, "Payment Failed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),Homescreeen.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    private void booking_table_update() {
        String url = "https://et.ayafitech.com/api/bookings.php";

// Create a Volley request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the PHP API
                        // You can parse and process the response here
                        // For example, display a success message
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here
                        // For example, display an error message
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Define the parameters to send to the PHP API
                Map<String, String> params = new HashMap<>();
                params.put("name", "Ayaz Patel");
                params.put("phone", "8128077786");
                params.put("email", "ayazpatel701@gmail.com");
                params.put("amount", "1000");
                params.put("property_id", "5");

                return params;
            }
        };

// Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
