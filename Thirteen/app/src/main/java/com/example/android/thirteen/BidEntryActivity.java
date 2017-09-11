package com.example.android.thirteen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BidEntryActivity extends AppCompatActivity {
    // Define interactive variables
    private ImageButton enterBids;
    private EditText p1Bid;
    private EditText p2Bid;
    private EditText p3Bid;
    private EditText p4Bid;
    private TextView tvRound;
    private ImageView ivRound;

    // Switches
    private boolean userWarned;

    // Player name array
    private int[] idArray =
            {R.id.tv_bid_p1_name, R.id.tv_bid_p2_name, R.id.tv_bid_p3_name, R.id.tv_bid_p4_name};
    private TextView[] nameArray = new TextView[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_entry);

        // Set switches initial values
        userWarned = false;

        // Set Round on screen
        tvRound = (TextView) findViewById(R.id.tvRound_bidEntry);
        ivRound = (ImageView) findViewById(R.id.ivRound_bidEntry);
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
        enterBids = (ImageButton) findViewById(R.id.bt_enter_bids);
        p1Bid = (EditText) findViewById(R.id.et_p1_bid);
        p2Bid = (EditText) findViewById(R.id.et_p2_bid);
        p3Bid = (EditText) findViewById(R.id.et_p3_bid);
        p4Bid = (EditText) findViewById(R.id.et_p4_bid);

        // Set what happens when the Enter button is clicked
        enterBids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Store bids in ScoreKeeper to be passed to Score Sheet
                String[] bids = new String[4];
                bids[0] = p1Bid.getText().toString();
                bids[1] = p2Bid.getText().toString();
                bids[2] = p3Bid.getText().toString();
                bids[3] = p4Bid.getText().toString();
                // Check total bid count and warn user of over or under
                int totalBid = 0;
                for (String bid : bids){
                    if (ScoreKeeper.isValidInteger(bid)){
                        totalBid += Integer.valueOf(bid);
                    } else {
                        userWarned = true;
                    }
                }
                if (!userWarned){
                    if (totalBid > Integer.valueOf(ScoreKeeper.getRound()[0])){
                        Toast.makeText(getApplicationContext(),
                                "Total bid is over what's expected. Click the Submit button again to continue",
                                Toast.LENGTH_LONG).show();
                        userWarned = true;
                    } else if (totalBid < Integer.valueOf(ScoreKeeper.getRound()[0])){
                        Toast.makeText(getApplicationContext(),
                                "Total bid is under what's expected. Click the Submit button again to continue",
                                Toast.LENGTH_LONG).show();
                        userWarned = true;
                    }
                } else {
                    userWarned = false;
                }

                // Validate entries and decide to continue app or not
                if (!userWarned){
                    String validation = ScoreKeeper.editEntries(bids);
                    if (validation == "neg"){
                        Toast.makeText(getApplicationContext(),"Negative bids not allowed",
                                Toast.LENGTH_LONG).show();
                    } else if (validation == "empty") {
                        Toast.makeText(getApplicationContext(), "Enter all bids to continue",
                                Toast.LENGTH_LONG).show();
                    } else if (validation == "overage"){
                        Toast.makeText(getApplicationContext(), "One bid is larger than expected",
                                Toast.LENGTH_LONG).show();
                    } else {
                        ScoreKeeper.putData("Bids",bids);
                        Intent bidsIntent = new Intent(BidEntryActivity.this, ScoreSheetActivity.class);
                        startActivity(bidsIntent);
                    }
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
