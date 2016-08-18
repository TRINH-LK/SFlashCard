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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trinhle.sflashcard.database.BackLanguageHandler;
import com.example.trinhle.sflashcard.database.BookInfoHandler;
import com.example.trinhle.sflashcard.database.FrontLanguageHandler;
import com.example.trinhle.sflashcard.model.BackLanguage;
import com.example.trinhle.sflashcard.model.BookInfo;
import com.example.trinhle.sflashcard.model.FrontLanguage;
import com.example.trinhle.sflashcard.utils.DisplayImage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class InfoActivity extends AppCompatActivity implements DisplayImage{

    private static String TAG = "InfoActivity";
    private String url = "http://sflashcard.com/service/book/getinfo/";
    private Toolbar tbBookInfo;
    private TextView tvBookInfoName;
    private TextView tvNumberCard;
    private TextView tvDescription;
    private ImageView ivBookInfoImage;
    private TextView tvNumberRead;
    private ImageView ivBackLanguage;
    private ImageView ivFrontLanguage;
    private Button btnAction;
    private BookInfoHandler bookInfoHandler;
    private BackLanguageHandler backLanguageHandler;
    private FrontLanguageHandler frontLanguageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initBookInfo();
        createToolbar();
        bookInfoHandler = new BookInfoHandler(this);
        backLanguageHandler = new BackLanguageHandler(this);
        frontLanguageHandler = new FrontLanguageHandler(this);
        Intent receivedIntent = getIntent();
        String bookId = receivedIntent.getStringExtra("bookId");
        String urlJSON = url + bookId;
        populateInfo(urlJSON, bookId);
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

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void populateInfo(String url, String bookId) {
        if (bookInfoHandler.getBookInfo(bookId) != null) {
            fillData(getDataFromDB(bookId));
        } else if (checkInternetConnection()) {
            requestBookInfoJSON(url);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connect", Toast.LENGTH_LONG).show();
        }

    }
    public void initBookInfo() {
        tbBookInfo = (Toolbar) findViewById(R.id.tb_book_info);
        tvBookInfoName = (TextView) findViewById(R.id.tv_info_book_name);
        tvNumberCard = (TextView) findViewById(R.id.tv_info_num_card);
        tvDescription = (TextView) findViewById(R.id.tv_info_description);
        ivBookInfoImage = (ImageView) findViewById(R.id.iv_info_thumbnail);
        tvNumberRead = (TextView) findViewById(R.id.tv_info_num_read);
        ivBackLanguage = (ImageView) findViewById(R.id.iv_back_thumbnail);
        ivFrontLanguage = (ImageView) findViewById(R.id.iv_front_thumbnail);
        btnAction = (Button) findViewById(R.id.btn_action);

    }

    public void createToolbar () {
        setSupportActionBar(tbBookInfo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbBookInfo.setNavigationIcon(R.drawable.back);
    }

    private void requestBookInfoJSON(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response" + response);
                GsonBuilder builder = new GsonBuilder();
                Gson bookInfoGson = builder.create();
                Type type = new TypeToken<BookInfo>(){}.getType();
                BookInfo bookInfo = bookInfoGson.fromJson(response, type);
                bookInfoHandler.addBookInfo(bookInfo);
                List<BackLanguage> backLanguages = bookInfo.getBackLanguages();
                for (int i = 0; i < backLanguages.size(); i++) {
                    BackLanguage temp = backLanguages.get(i);
                    backLanguageHandler.addLanguage(temp, bookInfo.getBookId());
                }

                List<FrontLanguage> frontLanguages = bookInfo.getFrontLanguages();
                for (int i = 0; i < frontLanguages.size(); i++) {
                    FrontLanguage front = frontLanguages.get(i);
                    frontLanguageHandler.addFrontLanguage(front, bookInfo.getBookId());
                }

                fillData(bookInfo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.getMessage());
            }
        });
        queue.add(request);
    }

    private void fillData (BookInfo bookInfo) {
        tvBookInfoName.setText(bookInfo.getBookName());
        tvNumberCard.setText(bookInfo.getNumCard() + " cards");
        tvDescription.setText(bookInfo.getDescBook());
        imageLoader.displayImage(bookInfo.getUrl(), ivBookInfoImage, option);
        tvNumberRead.setText("Read by " + bookInfo.getNumRead());
        List<BackLanguage> backLanguageList = bookInfo.getBackLanguages();
        for (int i = 0; i < backLanguageList.size(); i++) {
            BackLanguage back = backLanguageList.get(i);
            switch (back.getBackId()) {
                case "1":
                    ivBackLanguage.setImageResource(R.drawable.vi);
                    break;
                case "2":
                    ivBackLanguage.setImageResource(R.drawable.en);
                    break;
                case "3":
                    ivBackLanguage.setImageResource(R.drawable.jp);
            }
        }

        List<FrontLanguage> frontLanguageList = bookInfo.getFrontLanguages();
        for (int i = 0; i < frontLanguageList.size(); i++) {
            FrontLanguage front = frontLanguageList.get(i);
            switch (front.getFrontId()) {
                case "1":
                    ivFrontLanguage.setImageResource(R.drawable.vi);
                    break;
                case "2":
                    ivFrontLanguage.setImageResource(R.drawable.en);
                    break;
                case "3":
                    ivFrontLanguage.setImageResource(R.drawable.jp);
                    break;
            }
        }
    }

    private BookInfo getDataFromDB(String bookId) {
        BookInfo bookInfo = bookInfoHandler.getBookInfo(bookId);
        bookInfo.setBackLanguages(backLanguageHandler.getListBackLanguage(bookId));
        bookInfo.setFrontLanguages(frontLanguageHandler.getListFrontLanguage(bookId));

        return bookInfo;
    }

    private boolean checkInternetConnection () {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
}
