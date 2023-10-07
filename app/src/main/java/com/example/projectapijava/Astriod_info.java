package com.example.projectapijava;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Astriod_info extends AppCompatActivity {
    String period;
    String asteroid_id;
    String api_key = "z5hrIaovsbrqYH3QqpfWPzS4sx1d3PIFqzNKAJdf";
    String url;
    Asteriods as;
    SharedPreferences settings;
    private AsteroidsView asteroidsView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astriod_info);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        as = (Asteriods) b.getSerializable("asteroid");
        asteroid_id = as.getId();
        settings = getSharedPreferences("PREFS_LIKE", MODE_PRIVATE);
        this.asteroidsView = findViewById(R.id.asteroidsView);

        //Like config
        showLike();

        url = "https://api.nasa.gov/neo/rest/v1/neo/"+ asteroid_id + "?api_key=" + api_key;
        callNasaApi();
        clickableLike();
        createDraw();

    }

    @SuppressLint("StringFormatMatches")
    private void getInfo(Asteriods as) {

        TextView nameTextView = (TextView) findViewById(R.id.info_Name);
        TextView magnitudeTextView = (TextView) findViewById(R.id.info_Magnitude);
        TextView distanceTextView = (TextView) findViewById(R.id.info_Distance);
        TextView periodeTextView = (TextView) findViewById(R.id.info_periode);

        nameTextView.setText(as.getName());
        magnitudeTextView.setText(String.format(getString(R.string.magnitude),as.getMagnitude()));
        distanceTextView.setText(String.format(getString(R.string.distance), as.getDistance()));
        periodeTextView.setText(String.format(getString(R.string.p_riode_orbitale), Double.valueOf(period)));
    }

    public void callNasaApi(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonNasa = new JSONObject(response);
                        JSONObject str = jsonNasa.getJSONObject("orbital_data");
                        period = str.getString("orbital_period");
                        System.out.println(period);
                        getInfo(as);
                        asteroidsView.setAsteroids(as);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Error no response", Toast.LENGTH_SHORT).show()
        );
        queue.add(stringRequest);
    }

    public void createDraw(){
        AsteroidsView asteroidsView = findViewById(R.id.asteroidsView);
        asteroidsView.setTag(as);
    }

    public void clickableLike(){
        ImageView imageView = (ImageView) findViewById(R.id.likeView2);
        SharedPreferences.Editor editor = settings.edit();
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println(settings.getBoolean(asteroid_id, false));
                if(settings.getBoolean(asteroid_id, true)){
                    editor.putBoolean(asteroid_id, false);
                    imageView.setImageResource(android.R.drawable.btn_star_big_off);
                }
                else{
                    editor.putBoolean(asteroid_id, true);
                    imageView.setImageResource(android.R.drawable.btn_star_big_on);
                }
            }

        });
    }

    public void showLike(){
        ImageView imageView = (ImageView) findViewById(R.id.likeView2);

        if(settings.contains(asteroid_id)){
            if(settings.getBoolean(asteroid_id, true)){
                imageView.setImageResource(android.R.drawable.btn_star_big_on);
            }
            else{
                imageView.setImageResource(android.R.drawable.btn_star_big_off);
            }
        }
        else{
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(asteroid_id, false);
        }
    }

}
