package com.example.app_ass_2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Iterator;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiActivity extends AppCompatActivity {

     EditText editTextAmount;
     Button buttonConvert;
     Spinner spinnerCurrencies;
     TextView textViewResult;

     RequestQueue queue;
     final String API = "46e2ea0d111d7ba5f52fabb5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);
        editTextAmount = findViewById(R.id.editTextAmount);
        buttonConvert = findViewById(R.id.buttonConvert);
        spinnerCurrencies = findViewById(R.id.spinnerCurrencies);
        textViewResult = findViewById(R.id.textViewResult);

        queue = Volley.newRequestQueue(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.currency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrencies.setAdapter(adapter);
        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency();
            }
        });
    }

    private void convertCurrency() {
        String amount = editTextAmount.getText().toString();
        String url = "https://v6.exchangerate-api.com/v6/" + API + "/latest/USD";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject conversionRates = response.getJSONObject("conversion_rates");

                            // Clear previous results
                            textViewResult.setText("");

                            // Iterate through all available currencies
                            Iterator<String> keys = conversionRates.keys();
                            while (keys.hasNext()) {
                                String currencyCode = keys.next();
                                double conversionRate = conversionRates.getDouble(currencyCode);

                                double amountToConvert = Double.parseDouble(amount);
                                double convertedAmount = amountToConvert * conversionRate;

                                // Display the result for each currency
                                String result = String.format("%.2f %s", convertedAmount, currencyCode);
                                textViewResult.append(result + "\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Toast.makeText(ApiActivity.this, "Error fetching exchange rates", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(request);
    }}