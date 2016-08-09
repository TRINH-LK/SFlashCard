package com.example.trinhle.sflashcard;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trinhle.sflashcard.adapter.CategoryAdapter;
import com.example.trinhle.sflashcard.database.CategoryHandler;
import com.example.trinhle.sflashcard.model.Category;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Toolbar tbCategory;
    private TextView tvTitle;
    private ListView lvCategory;
    private CategoryHandler handler;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        createToolbar();
        setTitle("SFlashCard");
        handler = new CategoryHandler(this);
        populateCategory();
        handleEvent();

    }

    // Set up to populate Category view
    private void populateCategory() {
        if (handler.getCategoryCount() != 0) {
            adapter = new CategoryAdapter(MainActivity.this, handler.getAllCategory());
            lvCategory.setAdapter(adapter);
        } else if (checkInternetConnect()){
            requestCategoryJSON();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connect", Toast.LENGTH_LONG).show();
        }
    }

    // Initial view
    public void initView() {
        tbCategory = (Toolbar) findViewById(R.id.tb_category);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        lvCategory = (ListView) findViewById(R.id.lv_category);
    }

    // Set title for Activity
    private void setTitle(String title) {
        tvTitle.setText(title);
    }

    // Create Category Toolbar
    private void createToolbar() {
        setSupportActionBar(tbCategory);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    // Set up for item click event
    private void handleEvent() {
        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category selectedItem = (Category) lvCategory.getItemAtPosition(i);

                Intent collectionIntent = new Intent(MainActivity.this, CollectionActivity.class);
                collectionIntent.putExtra("categoryID", selectedItem.getCategoryId());
                collectionIntent.putExtra("categoryName", selectedItem.getCategoryName());
                startActivity(collectionIntent);
            }
        });
    }

    // Get data from JSON
    private void requestCategoryJSON() {
        String url = "http://sflashcard.com/service/category/get";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response "+response);
                GsonBuilder builder = new GsonBuilder();
                Gson categoryGson = builder.create();
                Type type = new TypeToken<List<Category>>(){}.getType();
                List<Category> categories = categoryGson.fromJson(response, type);

                // save category data to database
                for (int i = 0; i < categories.size(); i++) {
                    Category item = categories.get(i);
                    handler.addCategory(item);
                }

                adapter = new CategoryAdapter(MainActivity.this, categories);
                lvCategory.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
            }
        });
        queue.add(request);
    }

    // Check internet connection
    private boolean checkInternetConnect() {
        ConnectivityManager connection = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo info = connection.getActiveNetworkInfo();
        return ((info != null) && info.isConnected());
    }
}
