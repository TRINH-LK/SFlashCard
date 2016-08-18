package com.example.trinhle.sflashcard.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Trinh Le on 08/18/16.
 */
public class BookDownloader extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    private void requestBook(String urlBook, String destinationDirectory) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlBook);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int lengthOfFile = connection.getContentLength();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d("downloadBook", "Server ResponseCode=" + connection.getResponseCode() + " ResponseMessage=" + connection.getResponseMessage());
            }

            input = connection.getInputStream();

            Log.d("downloadBook", "destinationDirectory" + destinationDirectory);
            new File(destinationDirectory).createNewFile();
            output = new FileOutputStream(destinationDirectory);

            byte data[] = new byte[1024];
            int count;
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress(""+ (int)((total*100)/lengthOfFile));
                output.write(data, 0, count);

            }

        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (connection != null) {
                connection.disconnect();
            }
        }

        File file = new File(destinationDirectory);
    }
}
