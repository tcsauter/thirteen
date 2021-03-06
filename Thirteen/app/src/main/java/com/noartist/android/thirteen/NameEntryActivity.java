package com.noartist.android.thirteen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class NameEntryActivity extends AppCompatActivity {
    // Define interactive variables
    private ImageButton enterNames;
    private EditText p1Name;
    private EditText p2Name;
    private EditText p3Name;
    private EditText p4Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_entry);

        // Initialize ScoreSheet
        ScoreKeeper.initialize();

        // Define and initialize all interactive views
        enterNames = findViewById(R.id.bt_enter_names);
        p1Name = findViewById(R.id.et_p1_name);
        p2Name = findViewById(R.id.et_p2_name);
        p3Name = findViewById(R.id.et_p3_name);
        p4Name = findViewById(R.id.et_p4_name);

        // Set what happens when the Enter button is clicked
        enterNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Store names in String array to pass to ScoreKeeper
                String[] names = new String[4];
                names[0] = p1Name.getText().toString();
                names[1] = p2Name.getText().toString();
                names[2] = p3Name.getText().toString();
                names[3] = p4Name.getText().toString();
                String validation = ScoreKeeper.editEntries(names);
                if (validation.equals("empty")){
                    Toast.makeText(getApplicationContext(), "Enter all player names to continue",
                            Toast.LENGTH_LONG).show();
                } else {
                    ScoreKeeper.assignPlayers(names);
                    Intent beginIntent = new Intent(NameEntryActivity.this, ScoreSheetActivity.class);
                    startActivity(beginIntent);
                    finish();
                }
            }
        });
    }
}