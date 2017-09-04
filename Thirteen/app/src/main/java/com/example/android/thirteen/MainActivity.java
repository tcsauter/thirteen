package com.example.android.thirteen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define and initialize the Start button
        Button start = (Button) findViewById(R.id.bt_begin);

        // Set up the Start button click listener
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent startIntent = new Intent(MainActivity.this, NameEntryActivity.class);
                startActivity(startIntent);
            }
        });
    }
}
