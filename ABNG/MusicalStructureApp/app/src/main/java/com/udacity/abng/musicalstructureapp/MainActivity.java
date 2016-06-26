package com.udacity.abng.musicalstructureapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding the Views that shows the category
        TextView artists = (TextView) findViewById(R.id.artists);
        TextView albums = (TextView) findViewById(R.id.albums);
        TextView songs = (TextView) findViewById(R.id.songs);
        TextView nowplaying = (TextView) findViewById(R.id.nowplaying);

        // Set a click listener on each View
        artists.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link Artists}
                Intent numbersIntent = new Intent(MainActivity.this, Artists.class);
                // Start the new activity
                startActivity(numbersIntent);
            }
        });

        albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(MainActivity.this, Albums.class);
                startActivity(numbersIntent);
            }
        });

        songs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(MainActivity.this, Songs.class);
                startActivity(numbersIntent);
            }
        });

        nowplaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(MainActivity.this, NowPlaying.class);
                startActivity(numbersIntent);
            }
        });
    }
}
