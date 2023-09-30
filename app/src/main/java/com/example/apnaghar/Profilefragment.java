package com.example.apnaghar;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Profilefragment extends Fragment {
    private static final String ARG_EMAIL = "email";
    private String email;

    public String firstName,lastName,phoneNo,wallet;
    public Profilefragment() {
    }

    public static Profilefragment newInstance(String email) {
        Profilefragment fragment = new Profilefragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(ARG_EMAIL);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (email == null) {
            System.exit(0);
        }
        View view = inflater.inflate(R.layout.fragment_profilefragment, container, false);
        makeGetRequest();

        Button mybtn = view.findViewById(R.id.profile_wallet_card_show_money_add_money);
        mybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAmountInputDialog();
            }
        });

        CardView my_orders_card = view.findViewById(R.id.my_orders_card);
        my_orders_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MySellingsActivity.class);
                intent.putExtra("my_email_For_you",email);
                startActivity(intent);
            }
        });

        TextView walletBalanceText = view.findViewById(R.id.profile_wallet_card_show_money);
        walletBalanceText.setText(wallet);
        return view;
    }
    private void makeGetRequest() {
        String apiUrl = "https://et.ayafitech.com/api/profile_user_get_detail.php?email=" + email;
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject userData = response.getJSONObject("data");
                            int userId = userData.getInt("user_id");
                            firstName = userData.getString("first_name");
                            lastName = userData.getString("last_name");
                            phoneNo = userData.getString("phone_no");
                            wallet = userData.getString("wallet");
                            TextView walletBalanceText = getView().findViewById(R.id.profile_wallet_card_show_money);
                            walletBalanceText.setText(wallet);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void openRazorpayPaymentPage(String userEmail, String userWallet) {
        String razorpayPaymentUrl = "https://et.ayafitech.com/api/razorpay/addwallet.php?email=" + userEmail + "&amount=" + userWallet;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(razorpayPaymentUrl));
        startActivity(browserIntent);
    }
    private void showAmountInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Enter Amount");

        final EditText input = new EditText(getContext());
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Add Balance", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String amount = input.getText().toString();
                if (!amount.isEmpty()) {
                    openRazorpayPaymentPage(email, amount);
                } else {
                    Toast.makeText(getContext(), "Please enter an amount.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
