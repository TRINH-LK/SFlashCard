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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trinhle.sflashcard.adapter.BookAdapter;
import com.example.trinhle.sflashcard.database.BookHandler;
import com.example.trinhle.sflashcard.model.Book;
import com.example.trinhle.sflashcard.model.Collection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    private static final String TAG = "BookActivity";
    private static final String url = "http://sflashcard.com/service/book/get/";
    private Toolbar tbBook;
    private TextView tvTitleBook;
    private ListView lvBook;
    private BookHandler bookHandler;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        initialBook();
        createBookToolBar();
        Intent infoIntent = getIntent();
        String collectionId = infoIntent.getStringExtra("collectionId");
        String urlJSON = url + collectionId;
        String collectionName = infoIntent.getStringExtra("collectionName");
        setBookTitle(collectionName);

        bookHandler = new BookHandler(this);
        populateBook(urlJSON, collectionId);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Set up to populate Book view
    private void populateBook(String url, String collectionId) {
        if (bookHandler.getListBookWithCollectionId(collectionId).size() != 0) {
            bookAdapter = new BookAdapter(BookActivity.this, bookHandler.getListBookWithCollectionId(collectionId));
            lvBook.setAdapter(bookAdapter);
        } else if (isNetworkAvailable()) {
            requestBookJSON(url, collectionId);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connect", Toast.LENGTH_LONG).show();
        }
    }

    // Initial Book Screen
    public void initialBook() {
        lvBook = (ListView) findViewById(R.id.lv_book);
        tbBook = (Toolbar) findViewById(R.id.tb_book);
        tvTitleBook = (TextView) findViewById(R.id.tv_title_book);
    }

    // Set Title for Book screen
    private void setBookTitle (String title) {
        tvTitleBook.setText(title);
    }

    // Create toolbar for Book screen
    private void createBookToolBar() {
        setSupportActionBar(tbBook);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbBook.setNavigationIcon(R.drawable.back);
    }


    // Getting Book data from JSON
    private void requestBookJSON(String url, final String collectionId) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response"  + response);
                GsonBuilder builder = new GsonBuilder();
                Gson bookGson = builder.create();
                Type type = new TypeToken<List<Book>>(){}.getType();
                List<Book> bookList = bookGson.fromJson(response, type);

                for (int i = 0; i < bookList.size(); i ++) {
                    Book temp = bookList.get(i);
                    bookHandler.addBook(temp, collectionId);
                }

                bookAdapter = new BookAdapter(BookActivity.this, bookList);
                lvBook.setAdapter(bookAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.getMessage());
            }
        });
        queue.add(request);
    }

    // Check Internet Connection
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
}
