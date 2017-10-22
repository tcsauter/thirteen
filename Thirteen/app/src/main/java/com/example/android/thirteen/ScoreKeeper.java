package com.example.android.thirteen;


import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Static class to maintain game details and serve them to the different views.
 */

public class ScoreKeeper {
    // Declare Player objects
    public static Player player1;
    public static Player player2;
    public static Player player3;
    public static Player player4;
    public static ArrayList<Player> players = new ArrayList<Player>(4);

    // initialize container variables for scores
    private static String[][] scoreCardString;
    private static int round = 0;

    // Switches
    private static boolean bidSwitch = false;
    private static boolean scoreSwitch = true;

    // Photo Arrays
    private static Bitmap[] player1Photos = new Bitmap[3];
    private static Bitmap[] player2Photos = new Bitmap[3];
    private static Bitmap[] player3Photos = new Bitmap[3];
    private static Bitmap[] player4Photos = new Bitmap[3];

    public static void setPlayerPhotos(int position, Bitmap[] photos){
        switch (position){
            case 1:
                player1Photos = photos;
                break;
            case 2:
                player2Photos = photos;
                break;
            case 3:
                player3Photos = photos;
                break;
            case 4:
                player4Photos = photos;
                break;
            default: break;
        }
    }

    public static void assignPlayers(String[] names, ArrayList<Bitmap> photos){
        player1 = new Player(0, names[0],player1Photos);
        player2 = new Player(1, names[1],player2Photos);
        player3 = new Player(2, names[2],player3Photos);
        player4 = new Player(3, names[3],player4Photos);
        players.clear();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        scoreCardString = new String[4][4];
        loadScoreCard();
    }

    public static void initialize(){
        round = 0;
        bidSwitch = false;
        scoreSwitch = true;
        player1 = null;
        player2 = null;
        player3 = null;
        player4 = null;
        scoreCardString = null;
    }

    private static void loadScoreCard(){
        Player[] playerList = sortPlayerArray();
        int playerPosition = 0;
        for (Player player : playerList){
            playerPosition = player.getPosition();
            scoreCardString[0][playerPosition] = player.getName();
            scoreCardString[1][playerPosition] = Integer.toString(player.getBid());
            scoreCardString[2][playerPosition] = Integer.toString(player.getScore());
            scoreCardString[3][playerPosition] = Integer.toString(player.getSandbags());
        }
    }

    public static void bidsTaken(){
        bidSwitch = true;
        scoreSwitch = false;
    }

    public static void scoresTaken(){
        bidSwitch = false;
        scoreSwitch = true;
    }

    public static boolean getSwitches(){
        return bidSwitch;
    }

    public static void incrementRound(){
        round++;
    }

    public static String[] getRound(){
        int intRound;
        intRound = round + 1;
        String[] roundString = new String[2];
        if (intRound<13){
            roundString[0] = Integer.toString(intRound);
            roundString[1] = "UP";
        } else if (intRound>13) {
            intRound = intRound - (2 * (intRound - 13));
            roundString[0] = Integer.toString(intRound);
            roundString[1] = "DOWN";
        } else {
            roundString[0] = Integer.toString(intRound);
            roundString[1] = "TOP";
        }
        return roundString;
    }

    private static Player[] sortPlayerArray(){
        Player[] playerList = new Player[4];
        playerList[player1.getPosition()] = player1;
        playerList[player2.getPosition()] = player2;
        playerList[player3.getPosition()] = player3;
        playerList[player4.getPosition()] = player4;
        return playerList;
    }

    // Populate scoreCardString with player info
    public static void putData(String type, String[] values){
        Player[] playerList = sortPlayerArray();
        switch (type){
            case "Names":
                for (int i=0;i<values.length;i++){
                    scoreCardString[0][i] = values[i];
                    playerList[i].setName(values[i]);
                }
                break;
            case "Bids":
                for (int i=0;i<values.length;i++){
                    scoreCardString[1][i] = values[i];
                    playerList[i].setBid(Integer.valueOf(values[i]));
                }
                break;
            case "Scores":
                for (int i=0;i<values.length;i++){
                    playerList[i].setTake(Integer.valueOf(values[i]));
                    scoreCardString[2][i] = Integer.toString(playerList[i].getScore());
                    scoreCardString[3][i] = Integer.toString(playerList[i].getSandbags());
                }
                break;
            default: break;
        }
    }

    public static String[] getNames(){
        String[] names = new String[4];
        for (int i=0;i<names.length;i++){
            names[i] = scoreCardString[0][i];
        }
        return names;
    }

    public static String[] getBids(){
        String[] bids = new String[4];
        for (int i=0;i<4;i++){
            bids[i] = scoreCardString[1][i];
        }
        return bids;
    }

    public static String[][] getScoreCard(){
        return scoreCardString;
    }

    public static void updateData(int index,String type,String value){
        Player[] playerList = sortPlayerArray();
        switch (type){
            case "Name":
                scoreCardString[0][index] = value;
                playerList[index].setName(value);
                break;
            case "Bid":
                scoreCardString[1][index] = value;
                playerList[index].setBid(Integer.valueOf(value));
                break;
            default: break;
        }
    }

    public static String editEntries(String[] entries){
        for (String entry : entries){
            if (entry.length() == 0){
                return "empty";
            }

            //* if validating numeric entries, check that they are not negative
            if (isValidInteger(entry)){
                if (Integer.valueOf(entry) < 0){
                    return "neg";
                }

                if (Integer.valueOf(entry) > Integer.valueOf(getRound()[0])){
                    return "overage";
                }
            }
        }
        return null;
    }

    public static Boolean isValidInteger(String value) {
        try {
            Integer val = Integer.valueOf(value);
            if (val != null)
                return true;
            else
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void rotatePlayers(){
        Player[] playerList = sortPlayerArray();
        for (Player player : playerList){
            if (player.getPosition()==3){
                player.changePosition(-3);
            } else {
                player.changePosition(1);
            }
        }
        players.set(player1.getPosition(),ScoreKeeper.player1);
        players.set(player2.getPosition(),ScoreKeeper.player2);
        players.set(player3.getPosition(),ScoreKeeper.player3);
        players.set(player4.getPosition(),ScoreKeeper.player4);
        loadScoreCard();
    }

    public static void rankPlayers(){
        int p1Score = player1.getScore();
        int p2Score = player2.getScore();
        int p3Score = player3.getScore();
        int p4Score = player4.getScore();
        int p1Rank = 1;
        int p2Rank = 1;
        int p3Rank = 1;
        int p4Rank = 1;
        if (p1Score > p2Score){
            p2Rank++;
        } else if (p1Score < p2Score){
            p1Rank++;
        }
        if (p1Score > p3Score){
            p3Rank++;
        } else if (p1Score < p3Score){
            p1Rank++;
        }
        if (p1Score > p4Score){
            p4Rank++;
        } else if (p1Score < p4Score){
            p1Rank++;
        }
        if (p2Score > p3Score){
            p3Rank++;
        } else if (p2Score < p3Score){
            p2Rank++;
        }
        if (p2Score > p4Score){
            p4Rank++;
        } else if (p2Score < p4Score){
            p2Rank++;
        }
        if (p3Score > p4Score){
            p4Rank++;
        } else if (p3Score < p4Score){
            p3Rank++;
        }
        player1.setRank(p1Rank);
        player2.setRank(p2Rank);
        player3.setRank(p3Rank);
        player4.setRank(p4Rank);
    }
}
