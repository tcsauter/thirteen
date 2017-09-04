package com.example.android.thirteen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreSheetActivity extends AppCompatActivity {

    // Define variable id Array for interactive views
    private int[] idArray =
            {R.id.tv_p1_name, R.id.tv_p2_name, R.id.tv_p3_name, R.id.tv_p4_name,
             R.id.tv_p1_bid, R.id.tv_p2_bid, R.id.tv_p3_bid, R.id.tv_p4_bid,
             R.id.tv_p1_score, R.id.tv_p2_score, R.id.tv_p3_score, R.id.tv_p4_score,
             R.id.tv_p1_sandbags, R.id.tv_p2_sandbags, R.id.tv_p3_sandbags, R.id.tv_p4_sandbags};

    // Define variables for access to buttons
    private Button btBids;
    private Button btScores;

    // Define variables for Round info
    private TextView tvRound;
    private ImageView ivRound;

    // Array for player information
    private TextView[][] Players = new TextView[4][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_sheet);

        // Initialize Players array
        int x = 0;
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                Players[i][j] = (TextView) findViewById(idArray[x]);
                x++;
            }
        }

        // Bind Round info variables to screen
        tvRound = (TextView) findViewById(R.id.tvRound_scoreSheet);
        ivRound = (ImageView) findViewById(R.id.ivRound_scoreSheet);

        // Initialize Bids, Scores, and Sandbags to 0
        for (int i=1;i<=3;i++){
            for (int j=0;j<=3;j++){
                Players[i][j].setText("0");
            }
        }

        // Get names and add to screen
        String[] names = new String[4];
        names = ScoreKeeper.getNames();
        for (int i=0;i<names.length;i++){
            Players[0][i].setText(names[i]);
        }

        // Get round and add to screen
        setRound();

        // Bind Button variables to buttons
        btBids = (Button) findViewById(R.id.bt_Bids);
        btScores = (Button) findViewById(R.id.bt_Scores);

        // Set click listeners for buttons
        btBids.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent bidIntent = new Intent(ScoreSheetActivity.this, BidEntryActivity.class);
                startActivity(bidIntent);
            }
        });
        btScores.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent scoreIntent = new Intent(ScoreSheetActivity.this, ScoreEntryActivity.class);
                startActivity(scoreIntent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        String[][] scoreCard = new String[4][4];
        scoreCard = ScoreKeeper.getScoreCard();
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                Players[i][j].setText(scoreCard[i][j]);
            }
        }
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
