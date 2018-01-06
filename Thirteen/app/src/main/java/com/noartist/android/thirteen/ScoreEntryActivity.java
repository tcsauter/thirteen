package com.noartist.android.thirteen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreEntryActivity extends AppCompatActivity {
    // Define interactive variables
    private ImageButton enterScores;
    private EditText p1Score;
    private EditText p2Score;
    private EditText p3Score;
    private EditText p4Score;
    private TextView tvRound;
    private ImageView ivRound;
    private LinearLayout ssLayout1;
    private LinearLayout ssLayout2;
    private LinearLayout ssLayout3;
    private LinearLayout ssLayout4;

    // Player name array
    private int[] idArray =
            {R.id.tv_score_p1_name, R.id.tv_score_p2_name, R.id.tv_score_p3_name, R.id.tv_score_p4_name};
    private TextView[] nameArray = new TextView[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_entry);

        // Set Round on screen
        tvRound = (TextView) findViewById(R.id.tvRound_scoreEntry);
        ivRound = (ImageView) findViewById(R.id.ivRound_scoreEntry);
        setRound();

        // Set Player names on screen
        for (int i=0;i<idArray.length;i++){
            nameArray[i] = (TextView) findViewById(idArray[i]);
        }
        String[] names = new String[4];
        names = ScoreKeeper.getNames();
        for (int i=0;i<names.length;i++){
            nameArray[i].setText(names[i]);
        }

        // Define and initialize all interactive views
        enterScores = (ImageButton) findViewById(R.id.bt_enter_scores);
        p1Score = (EditText) findViewById(R.id.et_p1_score);
        p2Score = (EditText) findViewById(R.id.et_p2_score);
        p3Score = (EditText) findViewById(R.id.et_p3_score);
        p4Score = (EditText) findViewById(R.id.et_p4_score);
        ssLayout1 = (LinearLayout) findViewById(R.id.scoreSet_1);
        ssLayout2 = (LinearLayout) findViewById(R.id.scoreSet_2);
        ssLayout3 = (LinearLayout) findViewById(R.id.scoreSet_3);
        ssLayout4 = (LinearLayout) findViewById(R.id.scoreSet_4);

        //Set player score entry backgrounds
        LinearLayout[] layouts = {ssLayout1,ssLayout2,ssLayout3,ssLayout4};
        int x = 0;
        for (LinearLayout layout : layouts){
            switch (ScoreKeeper.players.get(x).getOriginalPosition()){
                case 0: layout.setBackgroundResource(R.color.red);
                    break;
                case 1: layout.setBackgroundResource(R.color.yellow);
                    break;
                case 2: layout.setBackgroundResource(R.color.green);
                    break;
                case 3: layout.setBackgroundResource(R.color.blue);
                    break;
                default: break;
            }
            x++;
        }

        // Set EditText hints to show bids by player
        String[] bids = ScoreKeeper.getBids();
        p1Score.setHint(names[0] + " bid " + bids[0]);
        p2Score.setHint(names[1] + " bid " + bids[1]);
        p3Score.setHint(names[2] + " bid " + bids[2]);
        p4Score.setHint(names[3] + " bid " + bids[3]);

        // Set what happens when the Enter button is clicked
        enterScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Store bids in ScoreKeeper to be passed to Score Sheet
                String[] scores = new String[4];
                scores[0] = p1Score.getText().toString();
                scores[1] = p2Score.getText().toString();
                scores[2] = p3Score.getText().toString();
                scores[3] = p4Score.getText().toString();
                // Total scores for validation section
                int totalScore = 0;
                for (String score : scores){
                    if (ScoreKeeper.isValidInteger(score)){
                        totalScore += Integer.valueOf(score);
                    }
                }

                // Validation section
                String validation = ScoreKeeper.editEntries(scores);
                if (validation == "neg"){
                    Toast.makeText(getApplicationContext(),"Negative scores not allowed",
                            Toast.LENGTH_LONG).show();
                } else if (validation == "empty") {
                    Toast.makeText(getApplicationContext(), "Enter all scores to continue",
                            Toast.LENGTH_LONG).show();
                } else if (validation == "overage") {
                    Toast.makeText(getApplicationContext(), "One score is larger than expected",
                            Toast.LENGTH_LONG).show();
                } else if (totalScore < Integer.valueOf(ScoreKeeper.getRound()[0])){
                    Toast.makeText(getApplicationContext(), "Total score is smaller than allowed",
                            Toast.LENGTH_LONG).show();
                } else if (totalScore > Integer.valueOf(ScoreKeeper.getRound()[0])){
                    Toast.makeText(getApplicationContext(), "Total score is larger than allowed",
                            Toast.LENGTH_LONG).show();
                } else {
                    ScoreKeeper.putData("Scores",scores);
                    ScoreKeeper.scoresTaken();
                    ScoreKeeper.incrementRound();
                    ScoreKeeper.rotatePlayers();
                    finish();
                }
            }
        });
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
