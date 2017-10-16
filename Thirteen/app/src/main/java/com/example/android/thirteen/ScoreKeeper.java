package com.example.android.thirteen;


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

    public static void assignPlayers(String[] names){
        player1 = new Player(0, names[0]);
        player2 = new Player(1, names[1]);
        player3 = new Player(2, names[2]);
        player4 = new Player(3, names[3]);
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
}
