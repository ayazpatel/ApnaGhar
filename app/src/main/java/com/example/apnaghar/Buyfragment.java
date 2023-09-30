package com.example.apnaghar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
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

public class Buyfragment extends Fragment {

    private RecyclerView recyclerView;
    private Buy_MyAdapter adapter;
    private List<Buy_MyDataModel> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buyfragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_buy_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        data = new ArrayList<>();
        adapter = new Buy_MyAdapter(getContext(), data);
        recyclerView.setAdapter(adapter);

        SearchView searchView = view.findViewById(R.id.searchView_buy_fragment);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });

        loadDefaultData();

        return view;
    }

    private void loadDefaultData() {
        String url = "https://et.ayafitech.com/api/buy_sell_retrieve.php?state=Gujarat&city=Surat";

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            data.clear();

                            JSONArray dataArray = response.getJSONArray("data");

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject item = dataArray.getJSONObject(i);

                                String id = item.getString("id");
                                String bsType = item.getString("BS_Type");
                                String bs_sub_type = item.getString("BS_Sub_Type");
                                String bs_sub_type2 = item.getString("BS_Sub_Type2");
                                String bs_for = item.getString("BS_For");
                                String landmark = item.getString("Landmark");
                                String image1 = item.getString("Image1");
                                String price = item.getString("Price");
                                String isFeatured = item.getString("is_Featured");

                                Buy_MyDataModel dataModel = new Buy_MyDataModel(id, bsType, bs_sub_type, bs_sub_type2, bs_for, landmark, image1, price, isFeatured);
                                data.add(dataModel);
                            }

                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
private void performSearch(String query) {
    if (query.isEmpty()) {
        loadDefaultData();
        return;
    }

    String url = "https://et.ayafitech.com/api/search_anything.php?query=" + query;

    RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        data.clear();

                        JSONArray dataArray = response.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject item = dataArray.getJSONObject(i);

                            String id = item.getString("id");
                            String bsType = item.getString("BS_Type");
                            String bs_sub_type = item.getString("BS_Sub_Type");
                            String bs_sub_type2 = item.getString("BS_Sub_Type2");
                            String bs_for = item.getString("BS_For");
                            String landmark = item.getString("Landmark");
                            String image1 = item.getString("Image1");
                            String price = item.getString("Price");
                            String isFeatured = item.getString("is_Featured");

                            Buy_MyDataModel dataModel = new Buy_MyDataModel(id, bsType, bs_sub_type, bs_sub_type2, bs_for, landmark, image1, price, isFeatured);
                            data.add(dataModel);
                        }

                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }
    );

    requestQueue.add(jsonObjectRequest);
}
}

