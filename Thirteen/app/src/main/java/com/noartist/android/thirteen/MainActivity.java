package com.noartist.android.thirteen;

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
        Button start = findViewById(R.id.bt_begin);
        Button rules = findViewById(R.id.bt_rules);
        Button help = findViewById(R.id.bt_help);

        // Set up the Rules button listener
        rules.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent rulesIntent = new Intent(MainActivity.this, RulesActivity.class);
                rulesIntent.putExtra("EXTRA_BUTTON","rules");
                startActivity(rulesIntent);
            }
        });

        help.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent rulesIntent = new Intent(MainActivity.this, RulesActivity.class);
                rulesIntent.putExtra("EXTRA_BUTTON","help");
                startActivity(rulesIntent);
            }
        });

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
