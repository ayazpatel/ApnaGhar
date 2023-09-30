package com.example.apnaghar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SellPropertyUpload extends AppCompatActivity {
    private ImageView imageview_sell_prpperty_upload_data,imageview_sell_prpperty_upload_data2;
    private EditText editText_BS_Type, editText_BS_Sub_Type, editText_BS_Sub_Type2, editText_Price, editText_Address, editText_Landmark, editText_State,
            editText_City, editText_Description, editText_Owner, editText_Phone_No, editText_Email_Id, editText_is_Featured;
    private Button button_sell_prpperty_upload_data;
    Bitmap bitmap,bitmap2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_property_upload);
        imageview_sell_prpperty_upload_data = findViewById(R.id.imageview_sell_prpperty_upload_data);
        imageview_sell_prpperty_upload_data2 = findViewById(R.id.imageview_sell_prpperty_upload_data2);
        editText_BS_Type = findViewById(R.id.editText_BS_Type);
        editText_BS_Sub_Type = findViewById(R.id.editText_BS_Sub_Type);
        editText_BS_Sub_Type2 = findViewById(R.id.editText_BS_Sub_Type2);
        editText_Price =findViewById(R.id.editText_Price);
        editText_Address = findViewById(R.id.editText_Address);
        editText_Landmark =findViewById(R.id.editText_Landmark);
        editText_State = findViewById(R.id.editText_State);
        editText_City =findViewById(R.id.editText_City);
        editText_Description = findViewById(R.id.editText_Description);
        editText_Owner = findViewById(R.id.editText_Owner);
        editText_Phone_No =findViewById(R.id.editText_Phone_No);
        editText_Email_Id = findViewById(R.id.editText_Email_Id);
        editText_is_Featured =findViewById(R.id.editText_is_Featured);
        button_sell_prpperty_upload_data = findViewById(R.id.button_sell_prpperty_upload_data);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imageview_sell_prpperty_upload_data.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        ActivityResultLauncher<Intent> activityResultLauncher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imageview_sell_prpperty_upload_data2.setImageBitmap(bitmap2);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        imageview_sell_prpperty_upload_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });
        imageview_sell_prpperty_upload_data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher2.launch(intent);
            }
        });

        button_sell_prpperty_upload_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SellPropertyUpload.this, "Property Details Uploading...", Toast.LENGTH_SHORT).show();

                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream = new ByteArrayOutputStream();

                ByteArrayOutputStream byteArrayOutputStream2;
                byteArrayOutputStream2 = new ByteArrayOutputStream();

                String string_editText_BS_Type = editText_BS_Type.getText().toString();
                String string_editText_BS_Sub_Type = editText_BS_Sub_Type.getText().toString();
                String string_editText_BS_Sub_Type2 = editText_BS_Sub_Type2.getText().toString();
                String string_editText_Price = editText_Price.getText().toString();
                String string_editText_Address = editText_Address.getText().toString();
                String string_editText_Landmark = editText_Landmark.getText().toString();
                String string_editText_State = editText_State.getText().toString();
                String string_editText_City = editText_City.getText().toString();
                String string_editText_Description = editText_Description.getText().toString();
                String string_editText_Owner = editText_Owner.getText().toString();
                String string_editText_Phone = editText_Phone_No.getText().toString();
                String string_editText_Email_Id = editText_Email_Id.getText().toString();
                String string_editText_is_Featured = editText_is_Featured.getText().toString();

                if (bitmap != null && bitmap2 != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

                    bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
                    byte[] bytes2 = byteArrayOutputStream2.toByteArray();
                    final String base64Image2 = Base64.encodeToString(bytes2, Base64.DEFAULT);

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = "https://et.ayafitech.com/api/buy_sell_upload.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("success")) {
                                        Toast.makeText(SellPropertyUpload.this, "Property Details Uploaded!", Toast.LENGTH_SHORT).show();
                                    } else if (response.equals("failed to insert into the database")) {
                                        Toast.makeText(SellPropertyUpload.this, "Failed to insert into the database", Toast.LENGTH_SHORT).show();
                                    } else if (response.equals("failed to upload image")) {
                                        Toast.makeText(SellPropertyUpload.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                                    } else if (response.equals("image data not found")) {
                                        Toast.makeText(SellPropertyUpload.this, "Image data not found", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SellPropertyUpload.this, "Unknown response: " + response, Toast.LENGTH_SHORT).show();
                                        Log.e("Ayaz Got Error", response);
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SellPropertyUpload.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("image", base64Image);
                            paramV.put("image2", base64Image2);
                            paramV.put("BS_Type", string_editText_BS_Type);
                            paramV.put("BS_Sub_Type", string_editText_BS_Sub_Type);
                            paramV.put("BS_Sub_Type2", string_editText_BS_Sub_Type2);
                            paramV.put("Price", string_editText_Price);
                            paramV.put("Address", string_editText_Address);
                            paramV.put("Landmark", string_editText_Landmark);
                            paramV.put("State", string_editText_State);
                            paramV.put("City", string_editText_City);
                            paramV.put("Description", string_editText_Description);
                            paramV.put("Owner", string_editText_Owner);
                            paramV.put("Phone_No", string_editText_Phone);
                            paramV.put("Email_Id", string_editText_Email_Id);
                            paramV.put("is_Featured", string_editText_is_Featured);
                            paramV.put("is_Sold", "0");
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                } else {
                    Toast.makeText(SellPropertyUpload.this, "Image Not Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

