package com.noartist.android.thirteen;

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

    // Player Card variables
    int[] playerCardFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_sheet);

        // Bind Round info variables to screen
        tvRound = (TextView) findViewById(R.id.tvRound_scoreSheet);
        ivRound = (ImageView) findViewById(R.id.ivRound_scoreSheet);

        // Bind Button variables to buttons
        btBids = (Button) findViewById(R.id.bt_Bids);
        btScores = (Button) findViewById(R.id.bt_Scores);

        // Store references to player card variables
        playerCardFields = new int[]{R.id.iv_pc_color_1,
                R.id.iv_pc_color_2,
                R.id.iv_pc_color_3,
                R.id.iv_pc_color_4,
                R.id.tv_pc_name_1,
                R.id.tv_pc_name_2,
                R.id.tv_pc_name_3,
                R.id.tv_pc_name_4,
                R.id.tv_pc_score_1,
                R.id.tv_pc_score_2,
                R.id.tv_pc_score_3,
                R.id.tv_pc_score_4,
                R.id.tv_pc_bid_1,
                R.id.tv_pc_bid_2,
                R.id.tv_pc_bid_3,
                R.id.tv_pc_bid_4,
                R.id.tv_pc_sandbags_1,
                R.id.tv_pc_sandbags_2,
                R.id.tv_pc_sandbags_3,
                R.id.tv_pc_sandbags_4};

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
            btBids.setBackgroundResource(R.color.btn_disabled);
            btScores.setBackgroundResource(R.color.btn_enabled);
        } else {
            btBids.setActivated(true);
            btScores.setActivated(false);
            btBids.setBackgroundResource(R.color.btn_enabled);
            btScores.setBackgroundResource(R.color.btn_disabled);
        }
        // Set current round
        setRound();

        Player[] players = new Player[4];
        players = ScoreKeeper.sortPlayerArray();
        ArrayList<ImageView> ivPlayerColor = new ArrayList<ImageView>();
        ArrayList<TextView> tvPlayerName = new ArrayList<TextView>();
        ArrayList<TextView> tvPlayerScore = new ArrayList<TextView>();
        ArrayList<TextView> tvPlayerBid = new ArrayList<TextView>();
        ArrayList<TextView> tvPlayerSandbags = new ArrayList<TextView>();
        int i = 0;
        for (Player player : players){
            ivPlayerColor.add(i,(ImageView) findViewById(playerCardFields[i]));
            tvPlayerName.add(i, (TextView) findViewById(playerCardFields[i+4]));
            tvPlayerScore.add(i, (TextView) findViewById(playerCardFields[i+8]));
            tvPlayerBid.add(i, (TextView) findViewById(playerCardFields[i+12]));
            tvPlayerSandbags.add(i, (TextView) findViewById(playerCardFields[i+16]));

            ivPlayerColor.get(i).setBackgroundResource(player.getColor());
            tvPlayerName.get(i).setText(player.getName());
            tvPlayerScore.get(i).setText(Integer.toString(player.getScore()));
            tvPlayerBid.get(i).setText(Integer.toString(player.getBid()));
            tvPlayerSandbags.get(i).setText(Integer.toString(player.getSandbags()));

            i++;
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
