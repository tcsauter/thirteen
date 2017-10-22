package com.example.android.thirteen;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by travi on 10/1/2017.
 */

public class PlayerAdapter extends ArrayAdapter<Player> {
    public PlayerAdapter(Activity context, ArrayList<Player> player){
        super(context, 0, player);
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.score_card_list_item,
                    parent, false);
        }

        Player currentPlayer = getItem(position);

        ImageView ivPlayerPhoto = (ImageView) listItemView.findViewById(R.id.iv_player_photo);
        Bitmap playerPhoto = selectPhoto(currentPlayer);
        if (playerPhoto == null){
            ivPlayerPhoto.setVisibility(View.INVISIBLE);
        } else {
            ivPlayerPhoto.setVisibility(View.VISIBLE);
            ivPlayerPhoto.setImageBitmap(playerPhoto);
        }

        TextView tvName = (TextView) listItemView.findViewById(R.id.name);
        TextView tvBid = (TextView) listItemView.findViewById(R.id.bid);
        TextView tvSandbags = (TextView) listItemView.findViewById(R.id.sandbags);
        TextView tvScore = (TextView) listItemView.findViewById(R.id.score);
        LinearLayout item = (LinearLayout) listItemView.findViewById(R.id.sc_list_item);
        tvName.setText(currentPlayer.getName());
        tvBid.setText(Integer.toString(currentPlayer.getBid()));
        tvSandbags.setText(Integer.toString(currentPlayer.getSandbags()));
        tvScore.setText(Integer.toString(currentPlayer.getScore()));

        switch (currentPlayer.getOriginalPosition()){
            case 0: item.setBackgroundResource(R.color.red);
                break;
            case 1: item.setBackgroundResource(R.color.yellow);
                break;
            case 2: item.setBackgroundResource(R.color.green);
                break;
            case 3: item.setBackgroundResource(R.color.blue);
                break;
            default: break;
        }


        return listItemView;
    }

    private Bitmap selectPhoto(Player player){
        int rank = player.getRank();
        Bitmap photo;
        switch (rank){
            case 1:
                photo = player.getHappyFace();
                break;
            case 4:
                photo = player.getSadFace();
                break;
            default:
                photo = player.getGameFace();
                break;
        }
        return photo;
    }
}
