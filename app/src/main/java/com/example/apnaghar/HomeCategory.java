package com.example.apnaghar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class HomeCategory extends AppCompatActivity implements HomeCategoryAdapter.OnItemClickListener {

    private RecyclerView recyclerViewCategory;
    private HomeCategoryAdapter categoryAdapter;
    private List<CategoryItem> categoryItems;
    private String BS_Type_ARG;
    private final String API_URL = "https://et.ayafitech.com/api/home_BS_Type.php?BS_Type=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_category);

        recyclerViewCategory = findViewById(R.id.homeCategory_recyclerView);
        categoryItems = new ArrayList<>();
        categoryAdapter = new HomeCategoryAdapter(categoryItems, this);

        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCategory.setAdapter(categoryAdapter);

        BS_Type_ARG = getIntent().getStringExtra("BS_Type_Parameter");

        if (BS_Type_ARG != null) {
            fetchCategoryItems(BS_Type_ARG);
        }

        categoryAdapter.setOnItemClickListener(this);
    }

    private void fetchCategoryItems(String category) {
        String apiUrl = API_URL + category;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String BS_Type = jsonObject.getString("BS_Type");
                                String Landmark = jsonObject.getString("Landmark");
                                String Price = jsonObject.getString("Price");
                                String BS_For = jsonObject.getString("BS_For");
                                String Image1 = jsonObject.getString("Image1");
                                CategoryItem item = new CategoryItem(id, BS_Type, Landmark, Price, BS_For, Image1);
                                categoryItems.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        categoryAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomeCategory.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public void onItemClick(CategoryItem item) {
        Intent intent = new Intent(this, Buy_DetailActivity.class);
        intent.putExtra("buy_sell_Id", item.getId());
        startActivity(intent);
    }
}
