package com.example.android.thirteen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreSheetActivity extends AppCompatActivity {
    // Define variables for access to buttons
    private Button btBids;
    private Button btScores;

    // Define variables for Round info
    private TextView tvRound;
    private ImageView ivRound;

<<<<<<< HEAD
    // Array for player information
    private TextView[][] Players = new TextView[4][4];

=======
>>>>>>> 4ec3633c820582b35ef26d263c18f119f8316ff6
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_sheet);

        // Bind Round info variables to screen
        tvRound = (TextView) findViewById(R.id.tvRound_scoreSheet);
        ivRound = (ImageView) findViewById(R.id.ivRound_scoreSheet);

<<<<<<< HEAD
        // Get names and add to screen
        String[] names = new String[4];
        names = ScoreKeeper.getNames();
        for (int i=0;i<names.length;i++){
            Players[0][i].setText(names[i]);
        }

        // Get round and add to screen
        setRound();

=======
>>>>>>> 4ec3633c820582b35ef26d263c18f119f8316ff6
        // Bind Button variables to buttons
        btBids = (Button) findViewById(R.id.bt_Bids);
        btScores = (Button) findViewById(R.id.bt_Scores);

        // Set click listeners for buttons
        btBids.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (btBids.isActivated()){
                    Intent bidIntent = new Intent(ScoreSheetActivity.this, BidEntryActivity.class);
                    startActivity(bidIntent);
                }
            }
        });
        btScores.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (btScores.isActivated()){
                    Intent scoreIntent = new Intent(ScoreSheetActivity.this, ScoreEntryActivity.class);
                    startActivity(scoreIntent);
                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        /*
        String[][] scoreCard = new String[4][4];
        scoreCard = ScoreKeeper.getScoreCard();
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                Players[i][j].setText(scoreCard[i][j]);
            }
        }
        */
        // Set current state of game
        if (ScoreKeeper.getSwitches()){
            btBids.setActivated(false);
            btScores.setActivated(true);
            if (this.getResources().getBoolean(R.bool.is_landscape)){
                btBids.setBackgroundResource(R.color.btn_disabled);
                btScores.setBackgroundResource(R.color.red);
            } else {
                btBids.setVisibility(View.GONE);
                btScores.setVisibility(View.VISIBLE);
            }
        } else {
            btBids.setActivated(true);
            btScores.setActivated(false);
            if (this.getResources().getBoolean(R.bool.is_landscape)){
                btBids.setBackgroundResource(R.color.red);
                btScores.setBackgroundResource(R.color.btn_disabled);
            } else {
                btBids.setVisibility(View.VISIBLE);
                btScores.setVisibility(View.GONE);
            }
        }
        // Set current round
        setRound();
<<<<<<< HEAD
=======

        // Set up ArrayList Adapter
        PlayerAdapter adapter = new PlayerAdapter(this,ScoreKeeper.players);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
>>>>>>> 4ec3633c820582b35ef26d263c18f119f8316ff6
    }

    private void setRound() {
        String[] round = new String[2];
        round = ScoreKeeper.getRound();
        tvRound.setText("Round " + round[0]);
        switch (round[1]) {
            case "UP":
                ivRound.setImageResource(R.drawable.round_up);
                break;
            case "DOWN":
                ivRound.setImageResource(R.drawable.round_down);
                break;
            case "TOP":
                ivRound.setImageResource(R.drawable.round_top);
                break;
        }
    }
}
