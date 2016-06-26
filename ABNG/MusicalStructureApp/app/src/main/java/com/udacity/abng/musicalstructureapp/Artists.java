package com.udacity.abng.musicalstructureapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Artists extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);

        Button toLibrary = (Button) findViewById(R.id.artists_tolibrary);
        Button nowPlaying = (Button) findViewById(R.id.artists_nowplaying);
        TextView playSong = (TextView) findViewById(R.id.artists_song);

        toLibrary.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the activity
                Intent numbersIntent = new Intent(Artists.this, MainActivity.class);
                // Start the new activity
                startActivity(numbersIntent);
            }
        });

        nowPlaying.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the activity
                Intent numbersIntent = new Intent(Artists.this, NowPlaying.class);
                // Start the new activity
                startActivity(numbersIntent);
            }
        });

        playSong.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the activity
                Intent numbersIntent = new Intent(Artists.this, NowPlaying.class);
                // Start the new activity
                startActivity(numbersIntent);
            }
        });
    }
}
