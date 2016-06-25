package com.udacity.abng.capitalsquizzapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    // Calculating text result here

    public void calcResult(View view) {

        RadioButton q1a = (RadioButton) findViewById(R.id.q1c);
        CheckBox q2a = (CheckBox) findViewById(R.id.q2a);
        CheckBox q2b = (CheckBox) findViewById(R.id.q2b);
        CheckBox q2c = (CheckBox) findViewById(R.id.q2c);
        EditText q3a = (EditText) findViewById(R.id.q3a);

        RadioButton q4a = (RadioButton) findViewById(R.id.q4b);
        CheckBox q5a = (CheckBox) findViewById(R.id.q5a);
        CheckBox q5b = (CheckBox) findViewById(R.id.q5b);
        CheckBox q5c = (CheckBox) findViewById(R.id.q5c);
        EditText q6a = (EditText) findViewById(R.id.q6a);

        int res = 0;
        // Checking for correct answers
        if (q1a.isChecked())
            res++;
        if (q2a.isChecked() && q2b.isChecked() && !q2c.isChecked())
            res++;
        if (q3a.getText().toString().compareTo("Dakar") == 0)
            res++;
        if (q4a.isChecked())
            res++;
        if (!q5a.isChecked() && q5b.isChecked() && q5c.isChecked())
            res++;
        if (q6a.getText().toString().compareTo("Bulgaria") == 0)
            res++;

        // Displaying the result

        String resulttext = "You`ve got " + res + " out of 6";

        Toast toast = Toast.makeText(this, resulttext, Toast.LENGTH_LONG);
        toast.show();
    }
}
