package com.udacity.abng.booksearchapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Log_main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = (Button) findViewById(R.id.button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressBar mProgress = (ProgressBar) findViewById(R.id.progress_bar);
                EditText searchText = (EditText) findViewById(R.id.edittext);
                String newText = searchText.getText().toString();
                Button searchButton = (Button) findViewById(R.id.button);
                if (newText.length() > 0) {
                    searchButton.setText("Searching...");
                    mProgress.setVisibility(View.VISIBLE);

                    ConnectivityManager connMgr = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        searchBooks(newText);
                    } else {
                        // display error
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();

                    }
                }
            }

            private void searchBooks(String newText) {

                // fetch data
                new BookSearchTask().execute(newText);
            }
        });

    }

    private class BookSearchTask extends AsyncTask<String, Void, String> {

        ArrayList<Book> bookList = new ArrayList<>();

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "Start search in background");
            String newText = params[0];
            newText.replace(' ', '+');

            String strurl = "https://www.googleapis.com/books/v1/volumes?q=";
            strurl = strurl + newText;
            Log.d(TAG, "url:" + strurl);
            HttpURLConnection urlConnection = null;


            try {
                URL url = new URL(strurl);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                Log.d(TAG, "Establishing connection" + in.toString());

                try {
                    JSONObject object = new JSONObject(responseStrBuilder.toString());
                    JSONArray array = object.getJSONArray("items");
                    Log.d(TAG, "Retrieving JSON");

                    for (int i = 0; i < array.length(); i++) {
                        Book book = new Book();
                        JSONObject item = array.getJSONObject(i);

                        JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                        if (volumeInfo.has("title")) {
                            String title = volumeInfo.getString("title");
                            book.setBookName(title);
                        }
                        if (volumeInfo.has("authors")) {
                            JSONArray authors = volumeInfo.getJSONArray("authors");
                            String author = authors.getString(0);
                            book.setAuthorName(author);
                        }
                        if (volumeInfo.has("imageLinks")) {
                            JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                            if (imageLinks.has("smallThumbnail")) {
                                String imageLink = imageLinks.getString("smallThumbnail");
                                book.setImageUrl(imageLink);
                            }
                        }
                        bookList.add(book);
                        Log.d(TAG, book.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "Unable to retrieve JSONObject." + e);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Unable to retrieve web page. URL may be invalid.");
            } finally {
                urlConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // Create a new intent to open the {@link BooksActivity}
            Intent booksIntent = new Intent(MainActivity.this, BooksActivity.class);
            booksIntent.putExtra("BooksArrayList", bookList);

            // Start the new activity
            startActivity(booksIntent);
            ProgressBar mProgress = (ProgressBar) findViewById(R.id.progress_bar);
            Button searchButton = (Button) findViewById(R.id.button);
            searchButton.setText("Search");
            mProgress.setVisibility(View.INVISIBLE);
            Log.d(TAG, "Updating View");
        }
    }

}
