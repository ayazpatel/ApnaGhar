package com.example.apnaghar;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewRent;
    private RecyclerView recyclerViewSell;
    private BuyRentAdapter rentAdapter;
    private BuySellAdapter sellAdapter;
    private List<BuySellItem> rentItems;
    private List<BuySellItem> sellItems;
    private final String API_URL_RENT = "https://et.ayafitech.com/api/home_BS_For.php?BS_For=RENT";
    private final String API_URL_SELL = "https://et.ayafitech.com/api/home_BS_For.php?BS_For=SELL";
    LinearLayout switch_for_flats,switch_for_bunglow,switch_for_rowhouse,switch_for_plots;

    ViewPager2 viewPager2;

    private Handler slidehandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewRent = view.findViewById(R.id.recyclerViewRent);
        recyclerViewSell = view.findViewById(R.id.recyclerViewSell);
        rentItems = new ArrayList<>();
        sellItems = new ArrayList<>();
        rentAdapter = new BuyRentAdapter(requireContext(), rentItems);
        sellAdapter = new BuySellAdapter(requireContext(), sellItems);
        recyclerViewRent.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewRent.setAdapter(rentAdapter);
        recyclerViewSell.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSell.setAdapter(sellAdapter);
        fetchRentItems();
        fetchSellItems();

        viewPager2=view.findViewById(R.id.veiwpager);
        List<slideritem> slideritems= new ArrayList<>();
        slideritems.add(new slideritem(R.drawable.banner1));
        slideritems.add(new slideritem(R.drawable.banner2));
        slideritems.add(new slideritem(R.drawable.banner3));
        slideritems.add(new slideritem(R.drawable.banner4));

        viewPager2.setAdapter(new slider_adp(slideritems,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(4);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositionTransformer =new CompositePageTransformer();
        compositionTransformer.addTransformer(new MarginPageTransformer(30));
        compositionTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r= 1- Math.abs(position);
                page.setScaleY(0.85f + r*0.15f);
            }
        });

        viewPager2.setPageTransformer(compositionTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                slidehandler.removeCallbacks(sliderrunable);
                slidehandler.postDelayed(sliderrunable,2000);
            }
        });


        switch_for_bunglow = view.findViewById(R.id.switch_for_bunglow);
        switch_for_bunglow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHomeCategoryActivity("BUNGLOW");
            }
        });
        switch_for_flats = view.findViewById(R.id.switch_for_flats);
        switch_for_flats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHomeCategoryActivity("FLAT");
            }
        });
        switch_for_rowhouse = view.findViewById(R.id.switch_for_rowhouse);
        switch_for_rowhouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHomeCategoryActivity("ROW_HOUSE");
            }
        });
        switch_for_plots = view.findViewById(R.id.switch_for_plot);
        switch_for_plots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHomeCategoryActivity("PLOT");
            }
        });


        return view;
    }

    private Runnable sliderrunable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        slidehandler.removeCallbacks(sliderrunable);
    }

    @Override
    public void onResume() {
        super.onResume();
        slidehandler.postDelayed(sliderrunable,3000);
    }

    private void fetchRentItems() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API_URL_RENT, null,
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
                                BuySellItem item = new BuySellItem(id, BS_Type, Landmark, Price, BS_For, Image1);
                                rentItems.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        rentAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(jsonArrayRequest);
    }
    private void fetchSellItems() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API_URL_SELL, null,
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
                                BuySellItem item = new BuySellItem(id, BS_Type, Landmark, Price, BS_For, Image1);
                                sellItems.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        sellAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(jsonArrayRequest);
    }
    private void startHomeCategoryActivity(String string) {
        Intent intent = new Intent(getContext(),HomeCategory.class);
        intent.putExtra("BS_Type_Parameter",string);
        startActivity(intent);
    }
}
