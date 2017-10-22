package com.example.android.thirteen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameFaceActivity extends AppCompatActivity {
    // Create variables for all of the interactive elements
    private TextView tvMessage;
    private TextView btnPositive;
    private TextView btnNegative;
    private ImageView ivHappyFace;
    private ImageView ivSadFace;
    private ImageView ivGameFace;

    // Class Constants
    static final int REQUEST_HAPPY_FACE = 1;
    static final int REQUEST_SAD_FACE= 2;
    static final int REQUEST_GAME_FACE = 3;

    // Image Containers
    private Bitmap bmHappyFace = null;
    private Bitmap bmSadFace = null;
    private Bitmap bmGameFace = null;

    // Camera app intent
    static final Intent TAKE_PHOTO_INTENT = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_face);
        final int POSITION = getIntent().getExtras().getInt("position");

        // Link interactive element variables with layout elements
        tvMessage = (TextView) findViewById(R.id.tv_gf_message);
        btnPositive = (TextView) findViewById(R.id.btn_positive);
        btnNegative = (TextView) findViewById(R.id.btn_negative);
        ivHappyFace = (ImageView) findViewById(R.id.iv_gf_happy);
        ivSadFace = (ImageView) findViewById(R.id.iv_gf_sad);
        ivGameFace = (ImageView) findViewById(R.id.iv_gf_gameface);

        // Set starting positions
        tvMessage.setText(R.string.happyFace);
        ivSadFace.setVisibility(View.INVISIBLE);
        ivGameFace.setVisibility(View.INVISIBLE);
        ivHappyFace.setVisibility(View.VISIBLE);

        // Set ClickListeners for photo frames
        ivHappyFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TAKE_PHOTO_INTENT.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(TAKE_PHOTO_INTENT,REQUEST_HAPPY_FACE);
                }
            }
        });

        ivSadFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TAKE_PHOTO_INTENT.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(TAKE_PHOTO_INTENT,REQUEST_SAD_FACE);
                }
            }
        });

        ivGameFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TAKE_PHOTO_INTENT.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(TAKE_PHOTO_INTENT,REQUEST_GAME_FACE);
                }
            }
        });

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap[] photos = {bmHappyFace,bmSadFace,bmGameFace};
                ScoreKeeper.setPlayerPhotos(POSITION,photos);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            switch (requestCode){
                case REQUEST_HAPPY_FACE:
                    bmHappyFace = (Bitmap) extras.get("data");
                    if (bmHappyFace != null && bmSadFace == null && bmGameFace == null){
                        tvMessage.setText(R.string.sadFace);
                        ivHappyFace.setImageBitmap(bmHappyFace);
                        ivSadFace.setVisibility(View.VISIBLE);
                    } else if (bmHappyFace != null) {
                        ivHappyFace.setImageBitmap(bmHappyFace);
                    } else {
                        ivHappyFace.setImageResource(R.drawable.add);
                    }
                    break;
                case REQUEST_SAD_FACE:
                    bmSadFace = (Bitmap) extras.get("data");
                    if (bmSadFace != null && bmGameFace == null){
                        tvMessage.setText(R.string.gameFace);
                        ivSadFace.setImageBitmap(bmSadFace);
                        ivGameFace.setVisibility(View.VISIBLE);
                    } else if (bmSadFace != null){
                        ivSadFace.setImageBitmap(bmSadFace);
                    } else {
                        ivSadFace.setImageResource(R.drawable.add);
                    }
                    break;
                case REQUEST_GAME_FACE:
                    bmGameFace = (Bitmap) extras.get("data");
                    if (bmGameFace != null){
                        tvMessage.setText(R.string.gfAllDone);
                        ivGameFace.setImageBitmap(bmGameFace);
                    } else {
                        ivGameFace.setImageResource(R.drawable.add);
                    }
                    break;
                default: break;
            }
        } else if (resultCode == RESULT_CANCELED){
            switch (requestCode){
                case REQUEST_HAPPY_FACE:
                    bmHappyFace = null;
                    break;
                case REQUEST_SAD_FACE:
                    bmSadFace = null;
                    break;
                case REQUEST_GAME_FACE:
                    bmGameFace = null;
                    break;
                default: break;
            }
        }
    }
}
