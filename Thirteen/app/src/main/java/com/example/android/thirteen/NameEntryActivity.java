package com.example.android.thirteen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class NameEntryActivity extends AppCompatActivity {
    // Define interactive variables
    private ImageButton enterNames;
    private EditText p1Name;
    private EditText p2Name;
    private EditText p3Name;
    private EditText p4Name;

    // Setup handling of player photos
    static final int REQUEST_IMAGE_CAPTURE=1;
    private int position;
    private ImageView gameFace1;
    private ImageView gameFace2;
    private ImageView gameFace3;
    private ImageView gameFace4;
    private ArrayList<Bitmap> photos = new ArrayList<Bitmap>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_entry);

        // Set intent for GameFace activity
        final Intent GAME_FACE_ACTIVITY = new Intent(NameEntryActivity.this,GameFaceActivity.class);

        // Initialize ScoreSheet
        ScoreKeeper.initialize();

        // Define and initialize all interactive views
        enterNames = (ImageButton) findViewById(R.id.bt_enter_names);
        p1Name = (EditText) findViewById(R.id.et_p1_name);
        p2Name = (EditText) findViewById(R.id.et_p2_name);
        p3Name = (EditText) findViewById(R.id.et_p3_name);
        p4Name = (EditText) findViewById(R.id.et_p4_name);

        // Define and initialize variables having to do with Game Face photos
        photos.clear();
        photos.add(null);
        photos.add(null);
        photos.add(null);
        photos.add(null);
        gameFace1 = (ImageView) findViewById(R.id.iv_GameFace_1);
        gameFace2 = (ImageView) findViewById(R.id.iv_GameFace_2);
        gameFace3 = (ImageView) findViewById(R.id.iv_GameFace_3);
        gameFace4 = (ImageView) findViewById(R.id.iv_GameFace_4);

        gameFace1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GAME_FACE_ACTIVITY.putExtra("position",1);
                startActivity(GAME_FACE_ACTIVITY);
            }
        });

        gameFace2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GAME_FACE_ACTIVITY.putExtra("position",2);
                startActivity(GAME_FACE_ACTIVITY);
            }
        });

        gameFace3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GAME_FACE_ACTIVITY.putExtra("position",3);
                startActivity(GAME_FACE_ACTIVITY);
            }
        });

        gameFace4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GAME_FACE_ACTIVITY.putExtra("position",4);
                startActivity(GAME_FACE_ACTIVITY);
            }
        });

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
                if (validation == "empty"){
                    Toast.makeText(getApplicationContext(), "Enter all player names to continue",
                            Toast.LENGTH_LONG).show();
                } else {
                    ScoreKeeper.assignPlayers(names, photos);
                    Intent beginIntent = new Intent(NameEntryActivity.this, ScoreSheetActivity.class);
                    startActivity(beginIntent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            photos.set(position,(Bitmap) extras.get("data"));
        }
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
        }
    }
}