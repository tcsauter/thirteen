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
    private TextView tvRound;
    private ImageView ivRound;

    // Player name array
    private int[] idArray =
            {R.id.tv_score_p1_name, R.id.tv_score_p2_name, R.id.tv_score_p3_name, R.id.tv_score_p4_name};
    private TextView[] nameArray = new TextView[4];

    //Player colors
    private int[] idArray2 =
            {R.id.scoreSet_1, R.id.scoreSet_2, R.id.scoreSet_3, R.id.scoreSet_4};
    private ImageView[] colorArray = new ImageView[4];

    //EditText Hints
    private int[] idArray3 =
            {R.id.et_p1_score, R.id.et_p2_score, R.id.et_p3_score, R.id.et_p4_score};
    private EditText[] hintArray = new EditText[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_entry);

        // Set Round on screen
        tvRound = findViewById(R.id.tvRound_scoreEntry);
        ivRound = findViewById(R.id.ivRound_scoreEntry);
        setRound();

        // Set Player names on screen
        for (int i=0;i<idArray.length;i++){
            nameArray[i] = findViewById(idArray[i]);
            colorArray[i] = findViewById(idArray2[i]);
            hintArray[i] = findViewById(idArray3[i]);
        }

        // Define and initialize all interactive views
        enterScores = findViewById(R.id.bt_enter_scores);

        Player[] players = ScoreKeeper.sortPlayerArray();
        int x = 0;
        for (Player player : players){
            nameArray[x].setText(player.getName());
            colorArray[x].setBackgroundResource(player.getColor());
            hintArray[x].setHint(player.getName() + " bid " + player.getBid());
            x++;
        }

        // Set what happens when the Enter button is clicked
        enterScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Store bids in ScoreKeeper to be passed to Score Sheet
                String[] scores = new String[4];
                scores[0] = hintArray[0].getText().toString();
                scores[1] = hintArray[1].getText().toString();
                scores[2] = hintArray[2].getText().toString();
                scores[3] = hintArray[3].getText().toString();
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
