package com.example.trinhle.sflashcard;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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
import com.example.trinhle.sflashcard.adapter.CollectionAdapter;
import com.example.trinhle.sflashcard.database.CollectionHandler;
import com.example.trinhle.sflashcard.model.Collection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    private static final String TAG = "CollectionActivity";
    private static final String URL = "http://sflashcard.com/service/collection/get/";
    private Toolbar tbCollection;
    private TextView tvTitle;
    private ListView lvCollection;
    private CollectionAdapter adapter;
    private CollectionHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initCollectionView();
        setCollectionToolbar();
        handler = new CollectionHandler(this);

        // Getting data when click on Category Item
        Intent intent = getIntent();
        String categoryName = intent.getStringExtra("categoryName");
        setCollectionTitle(categoryName);
        String categoryId = intent.getStringExtra("categoryID");
        String urlJSON = URL + categoryId;
        populateCollection(urlJSON, categoryId);
        handleEvent();
    }

    // Set up to populate Collection View
    private void populateCollection(String url, String categoryId) {
        if (handler.getAllCollectionWithCategory(categoryId).size() != 0) {
            adapter = new CollectionAdapter(CollectionActivity.this, handler.getAllCollectionWithCategory(categoryId));
            lvCollection.setAdapter(adapter);
        } else if (isNetworkAvailable()) {
            requestCollectionJSON(url, categoryId);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connect", Toast.LENGTH_LONG).show();
        }
    }

    // Initial view for Collection Activity
    private void initCollectionView() {
        lvCollection = (ListView) findViewById(R.id.lv_collection);
        tbCollection = (Toolbar) findViewById(R.id.tb_collection);
        tvTitle = (TextView) findViewById(R.id.tv_collection_title);
    }

    // Set the Title for each Collection
    private void setCollectionTitle(String title) {
        tvTitle.setText(title);
    }

    // Create Collection Toolbar
    private void setCollectionToolbar() {
        setSupportActionBar(tbCollection);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbCollection.setNavigationIcon(R.drawable.back);
        tbCollection.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent upNavigationIntent = new Intent(CollectionActivity.this, MainActivity.class);
                startActivity(upNavigationIntent);
            }
        });
    }

    // Get collection data from JSON
    private void requestCollectionJSON(String url, final String Id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response" + response);
                GsonBuilder builder  = new GsonBuilder();
                Gson collectionGson = builder.create();
                Type type = new TypeToken<List<Collection>>(){}.getType();
                List<Collection> collectionList = collectionGson.fromJson(response, type);

                // save collection data to database
                for (int i = 0; i < collectionList.size(); i++) {
                    Collection temp = collectionList.get(i);
                    handler.addCollection(temp, Id);
                }

                adapter = new CollectionAdapter(CollectionActivity.this, collectionList);
                lvCollection.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"Error " + error.getMessage() );
            }
        });
        queue.add(request);
    }

    // Setting on click Collection item
    private void handleEvent() {
        lvCollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Collection selectedCollection = (Collection) lvCollection.getItemAtPosition(i);
                String collectionId = selectedCollection.getCollectionId();
                String collectionName = selectedCollection.getCollectionName();
                Intent linkToBookIntent = new Intent(CollectionActivity.this, BookActivity.class);
                linkToBookIntent.putExtra("collectionId", collectionId);
                linkToBookIntent.putExtra("collectionName", collectionName);
                startActivity(linkToBookIntent);

            }
        });
    }

    // Check internet connection
    private boolean isNetworkAvailable() {
        ConnectivityManager connect = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connect.getActiveNetworkInfo();
        return ((networkInfo != null) && (networkInfo.isConnected()));
    }
}

