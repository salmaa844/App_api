package com.example.app_ass_2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class prayertime extends AppCompatActivity {

         TextView textViewFajr, textViewDhuhr, textViewAsr, textViewMaghrib, textViewIsha;
        Button buttonGetTimings;
         RequestQueue queue;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_prayertime);
            textViewFajr = findViewById(R.id.textViewFajr);
            textViewDhuhr = findViewById(R.id.textViewDhuhr);
            textViewAsr = findViewById(R.id.textViewAsr);
            textViewMaghrib = findViewById(R.id.textViewMaghrib);
            textViewIsha = findViewById(R.id.textViewIsha);
            buttonGetTimings = findViewById(R.id.buttonGetTimings);

            queue = Volley.newRequestQueue(this);

            buttonGetTimings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        String url = "https://api.aladhan.com/v1/timingsByAddress/28-12-2023?address=London%2C+UK&method=99&methodSettings=18.5%2Cnull%2C17.5";

                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            JSONObject data = response.getJSONObject("data");
                                            JSONObject timings = data.getJSONObject("timings");

                                            String fajr = timings.getString("Fajr");
                                            String dhuhr = timings.getString("Dhuhr");
                                            String asr = timings.getString("Asr");
                                            String maghrib = timings.getString("Maghrib");
                                            String isha = timings.getString("Isha");

                                            textViewFajr.setText("Fajr: " + fajr);
                                            textViewDhuhr.setText("Dhuhr: " + dhuhr);
                                            textViewAsr.setText("Asr: " + asr);
                                            textViewMaghrib.setText("Maghrib: " + maghrib);
                                            textViewIsha.setText("Isha: " + isha);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(prayertime.this, "Error fetching data", Toast.LENGTH_SHORT).show();

                                    }
                                });

                        queue.add(request);
                    }

            });
        }





    }
