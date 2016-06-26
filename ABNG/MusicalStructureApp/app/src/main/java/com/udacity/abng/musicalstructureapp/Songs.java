package com.udacity.abng.musicalstructureapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Songs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        Button toLibrary = (Button) findViewById(R.id.songs_tolibrary);
        Button nowPlaying = (Button) findViewById(R.id.songs_nowplaying);
        TextView playSong = (TextView) findViewById(R.id.songs_song);

        toLibrary.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the activity
                Intent numbersIntent = new Intent(Songs.this, MainActivity.class);
                // Start the new activity
                startActivity(numbersIntent);
            }
        });

        nowPlaying.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the activity
                Intent numbersIntent = new Intent(Songs.this, NowPlaying.class);
                // Start the new activity
                startActivity(numbersIntent);
            }
        });

        playSong.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the activity
                Intent numbersIntent = new Intent(Songs.this, NowPlaying.class);
                // Start the new activity
                startActivity(numbersIntent);
            }
        });
    }
}
