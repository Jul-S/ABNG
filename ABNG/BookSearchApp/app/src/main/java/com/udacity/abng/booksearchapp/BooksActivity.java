package com.udacity.abng.booksearchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class BooksActivity extends AppCompatActivity {
    private static final String TAG = "Log_bookslist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_list);
        Intent intent = getIntent();
        ArrayList<Book> books =
                (ArrayList<Book>) intent.getSerializableExtra("BooksArrayList");

        showBooksList(books);


    }

    public void showBooksList(ArrayList<Book> books) {

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new BookAdapter(this, books));

    }
}
