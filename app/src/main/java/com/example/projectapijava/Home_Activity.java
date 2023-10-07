package com.example.projectapijava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Home_Activity extends AppCompatActivity {
    private RequestQueue requestQueue;
    String api_key = "z5hrIaovsbrqYH3QqpfWPzS4sx1d3PIFqzNKAJdf";
    String url = "https://api.nasa.gov/neo/rest/v1/feed?start_date=2023-09-23&end_date=2023-09-30&api_key=" + api_key;
    ArrayList<String> names = new ArrayList<String>();
    List<String> id = new ArrayList<>();
    List<String> distances = new ArrayList<String>();
    List<Double> magnitudes = new ArrayList<>();
    List<String> orbitalPeriod = new ArrayList<String>();
    ArrayList<Asteriods> asteroid = new ArrayList();
    final String[] count = {""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setContentView(R.layout.activity_home);
        callNasaApi();

        Button button2 = (Button) findViewById(R.id.buttonLink1);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.test_layout);

                ListView linearLayout = (ListView) findViewById(R.id.ListView2);
                CustomAdapter adapter1 = new CustomAdapter(getApplicationContext(), asteroid);
                linearLayout.setAdapter(adapter1);
            }
        });
    }

    public void callNasaApi(){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        @SuppressLint("SetTextI18n") StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Toast.makeText(getApplicationContext(), "Received data", Toast.LENGTH_SHORT).show();

                    try {
                        JSONObject jsonNasa = new JSONObject(response);
                        JSONObject str = jsonNasa.getJSONObject("near_earth_objects");
                        System.out.println(jsonNasa.getString("element_count"));
                        count[0] = jsonNasa.getString("element_count");
                        str.names();
                        Iterator<String> keys = str.keys();
                        while(keys.hasNext()){
                            JSONArray array = str.getJSONArray(keys.next());
                            for(int i = 0; i< array.length(); i++){
                                JSONObject obj = (JSONObject) array.get(i);
                                names.add((String) obj.get("name"));
                                JSONArray dist = (JSONArray) obj.get("close_approach_data");
                                JSONObject dist_o = (JSONObject) dist.get(0);
                                JSONObject dist_object = (JSONObject) dist_o.get("miss_distance");
                                distances.add((String) dist_object.get("kilometers"));
                                magnitudes.add((Double) obj.get("absolute_magnitude_h"));
                                id.add((String) obj.get("id"));
                                asteroid.add(new Asteriods((String) obj.get("id"), (String) obj.get("name"),
                                        (String) dist_object.get("kilometers"), (Double) obj.get("absolute_magnitude_h")));
                            }
                        }


                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    ListView listView = findViewById(R.id.ListView1);
                    ArrayAdapter<Asteriods> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, asteroid);
                    listView.setAdapter(adapter);
                    TextView textView = (TextView) findViewById(R.id.textView1);
                    textView.setText("Nombre d'astériodes : "+ count[0]);

                },
                error -> Toast.makeText(getApplicationContext(), "Error no response", Toast.LENGTH_SHORT).show()
        );
        queue.add(stringRequest);

    }


}