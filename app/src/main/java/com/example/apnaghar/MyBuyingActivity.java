package com.example.apnaghar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MyBuyingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MySellingsAdapter adapter;
    private List<MySellingItem> mySellingList;
    public String receivedemail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sellings);


        Intent intent = getIntent();
        receivedemail = intent.getStringExtra("my_email_For_you");

        recyclerView = findViewById(R.id.recycler_view_ayaz);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mySellingList = new ArrayList<>();

        fetchDataFromAPI();
    }

    private void fetchDataFromAPI() {
        String apiUrl = "https://et.ayafitech.com/api/my_buyings.php?email="+receivedemail;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("data");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject item = dataArray.getJSONObject(i);

                                String id = item.getString("id");
                                String bsType = item.getString("BS_Type");
                                String createdAt = item.getString("Created_At");
                                String price = item.getString("Price");
                                String image = item.getString("Image1");

                                MySellingItem mySellingItem = new MySellingItem(id, bsType, createdAt, price, image);
                                mySellingList.add(mySellingItem);
                            }

                            adapter = new MySellingsAdapter(mySellingList, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                            adapter.setOnItemClickListener(new MySellingsAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    MySellingItem clickedItem = mySellingList.get(position);
                                    Toast.makeText(MyBuyingActivity.this, "Clicked item ID: " + clickedItem.getId(), Toast.LENGTH_SHORT).show();

                                    String updateApiUrl = "https://et.ayafitech.com/api/update_is_sold_not.php?id=" + clickedItem.getId();
                                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                                    JsonObjectRequest updateRequest = new JsonObjectRequest(
                                            Request.Method.GET,
                                            updateApiUrl,
                                            null,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        String status = response.getString("status");
                                                        String message = response.getString("message");

                                                        if ("success".equals(status)) {
                                                            Toast.makeText(MyBuyingActivity.this, message, Toast.LENGTH_SHORT).show();

                                                            clickedItem.setSold(true);
                                                            adapter.notifyItemChanged(position);
                                                        } else {
                                                            Toast.makeText(MyBuyingActivity.this, message, Toast.LENGTH_SHORT).show();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    error.printStackTrace();
                                                    Log.e("MySellingsActivity", "Error: " + error.getMessage());
                                                }
                                            }
                                    );

                                    requestQueue.add(updateRequest);
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MyBuyingActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}
