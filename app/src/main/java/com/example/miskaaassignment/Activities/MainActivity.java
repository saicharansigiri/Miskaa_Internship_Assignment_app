package com.example.miskaaassignment.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.miskaaassignment.Adapter.MyAdapter;
import com.example.miskaaassignment.R;
import com.example.miskaaassignment.database.DatabaseHandler;
import com.example.miskaaassignment.model.Model;
import com.example.miskaaassignment.utils.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Model> modelList;
    TextView showtext;
    private FloatingActionButton fab;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHandler.init(this);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        showtext = findViewById(R.id.showtext);
        fab = findViewById(R.id.floatingActionButton);
        recyclerView.setHasFixedSize(true);
        //setting recycle View layout
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        requestQueue = VolleySingleton.getmInstance(MainActivity.this).getRequestQueue();
        modelList = new ArrayList<>();

        if(isConnected(MainActivity.this)){
            showtext.setVisibility(View.GONE);
            fetchCountries();
        }
        else if(DatabaseHandler.checkDb()==null && !isConnected(this)){
            showtext.setText("No Data in Database");
            Toast.makeText(this, "No Internet ", Toast.LENGTH_SHORT).show();

        }
        else if(DatabaseHandler.checkDb()!=null){
            showtext.setVisibility(View.GONE);
            Toast.makeText(this, "loading data from database", Toast.LENGTH_SHORT).show();
            DatabaseHandler.loadDataFromDb().thenAccept(this::setOfflineData);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                if(DatabaseHandler.checkDb()==null){
                    Toast.makeText(MainActivity.this, "No Data To delete", Toast.LENGTH_SHORT).show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Delete all data")
                            .setMessage("Are you sure want to delete all data from database?")
                            .setPositiveButton("Delete", (dialog, which) -> {
                                deleteAll(MainActivity.this);
                            })
                            .setNegativeButton("Cancel",null)
                            .setCancelable(true);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            }
        });

    }

    private void setOfflineData(List<Model> models) {
        MyAdapter myAdapter = new MyAdapter(MainActivity.this, models);
        recyclerView.setAdapter(myAdapter);
    }

    public static boolean isConnected(Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void fetchCountries() {
        String url = "https://restcountries.eu/rest/v2/region/Asia";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String capital = jsonObject.getString("capital");
                        String region = jsonObject.getString("region");
                        String subregion = jsonObject.getString("subregion");
                        String flag = jsonObject.getString("flag");
                        int population = jsonObject.getInt("population");
                        JSONArray borders = jsonObject.getJSONArray("borders");
                        String bor = "";

                        if (borders.length() > 0) {
                            for (int j = 0; j < borders.length(); j++) {

                                String borobj = borders.getString(j);
                                bor += borobj + ",";
                            }
                            bor = bor.substring(0, bor.length() - 1);
                        } else {
                            bor = "No Borders";
                        }


                        JSONArray languages = jsonObject.getJSONArray("languages");
                        String lan = "";
                        if (languages.length() > 0) {
                            for (int k = 0; k < languages.length(); k++) {
                                JSONObject languageObject = languages.getJSONObject(k);
                                String lanobj = languageObject.getString("name");
                                lan += lanobj + ",";

                            }
                            lan = lan.substring(0, lan.length() - 1);
                        }
                        Model modelClass = new Model(name, capital, region, subregion, flag, population, bor, lan);
                        modelList.add(modelClass);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                MyAdapter myAdapter = new MyAdapter(MainActivity.this, modelList);
                recyclerView.setAdapter(myAdapter);

                //Ensuring Data is not added every Time when app restarts
                if (DatabaseHandler.checkDb() == null) {
                    DatabaseHandler.storeData(modelList);
                }


            }
        }, new Response.ErrorListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void deleteAll(Context context) {
        modelList.clear();
        MyAdapter myAdapter = new MyAdapter(context, modelList);
        DatabaseHandler.clearDatabaseAsync();
        recyclerView.setAdapter(myAdapter);
        Toast.makeText(context, "Database deleted Successfully", Toast.LENGTH_SHORT).show();
    }


}