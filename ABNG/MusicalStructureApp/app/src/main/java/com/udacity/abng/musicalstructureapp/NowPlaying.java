package com.udacity.abng.musicalstructureapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class NowPlaying extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        Button toLibrary = (Button) findViewById(R.id.nowplaying_tolibrary);

        toLibrary.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the activity
                Intent numbersIntent = new Intent(NowPlaying.this, MainActivity.class);
                // Start the new activity
                startActivity(numbersIntent);
            }
        });

    }
}
