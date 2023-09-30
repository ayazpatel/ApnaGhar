package com.example.apnaghar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class Newsfragment extends Fragment {

    private static final String ARG_EMAIL_NEWS = "email";
    private String email;
    private List<NewsItem> newsList = new ArrayList<>();

    public Newsfragment() {
        // Required empty public constructor
    }

    public static Newsfragment newInstance(String email) {
        Newsfragment fragment = new Newsfragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL_NEWS, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(ARG_EMAIL_NEWS);
        }
        fetchDataFromApi();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newsfragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        NewsAdapter adapter = new NewsAdapter(getActivity(), newsList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void fetchDataFromApi() {
        String apiUrl = "https://et.ayafitech.com/api/news_get_region_content.php?email="+email;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("data");

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject jsonObject = dataArray.getJSONObject(i);
                                String title = jsonObject.optString("title");
                                String createdAt = jsonObject.optString("created_at");
                                String content = jsonObject.optString("content");

                                NewsItem newsItem = new NewsItem(title, createdAt, content);
                                newsList.add(newsItem);
                            }

                            if (getActivity() != null) {
                                RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerView);
                                recyclerView.getAdapter().notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("JSON Response", error.toString());
                        Toast.makeText(getActivity(), "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
    }
}
