package com.example.android.thirteen;

import android.os.Bundle;


/**
 * Created by Travis Sauter-Hunsberger on 8/27/2017.
 */

public class ScoreKeeper {
    // initialize container variables for scores
    private static String[][] scoreCardString = new String[4][4];
    private static int[][] scoreHistory = new int[25][4];
    private static int round = 0;
    private static int[][] mathSheet = new int[3][4];
    public static String message;

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

    // Populate scoreCardString with player names
    public static void putData(String type, String[] values){
        switch (type){
            case "Names":
                for (int i=0;i<values.length;i++){
                    scoreCardString[0][i] = values[i];
                }
                break;
            case "Bids":
                for (int i=0;i<values.length;i++){
                    scoreCardString[1][i] = values[i];
                    mathSheet[0][i] = Integer.valueOf(values[i]);
                }
                break;
            case "Scores":
                calculateScore(values);
                break;
            default: break;
        }
    }

    private static void calculateScore(String[] scores){
        int score = 0;
        int bid = 0;
        int calculatedScore;
        int sandBags;
        for (int i=0;i<scores.length;i++){
            score = Integer.valueOf(scores[i]);
            bid = mathSheet[0][i];
            calculatedScore = 0;
            sandBags = 0;
            if (score<bid){
                calculatedScore = bid * -10;
            } else if (score==bid){
                calculatedScore = bid * 10;
            } else {
                sandBags = score - bid;
                calculatedScore = (bid * 10) + sandBags;
            }
            putScores(i,calculatedScore);
            putSandbags(i,sandBags);
        }
    }

    private static void putScores(int index, int score){
        if (round == 0){
            scoreHistory[round][index] = score;
        } else {
            scoreHistory[round][index] = scoreHistory[round - 1][index] + score;
        }
        mathSheet[1][index] += score;
        scoreCardString[2][index] = Integer.toString(mathSheet[1][index]);
    }

    private static void putSandbags(int index, int sandBag){
        mathSheet[2][index] += sandBag;
        scoreCardString[3][index] = Integer.toString(mathSheet[2][index]);
    }

    public static String[] getNames(){
        String[] names = new String[4];
        for (int i=0;i<4;i++){
            names[i] = scoreCardString[0][i];
        }
        return names;
    }

    public static String[][] getScoreCard(){
        return scoreCardString;
    }

    public static void updateData(int index,String type,String value){
        switch (type){
            case "Name":
                scoreCardString[0][index] = value;
                break;
            case "Bid":
                scoreCardString[1][index] = value;
                mathSheet[0][index] = Integer.valueOf(value);
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
}
