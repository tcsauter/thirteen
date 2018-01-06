package com.noartist.android.thirteen;

import java.util.ArrayList;

/**
 * I'm experimenting with a new class to code the functions of the ScoreKeeper class in a more object-oriented way.
 */

class Player {
    private int mPosition;
    private int mOrigPosition;
    private String mName;
    private int mCurBid;
    private int mCurTake;
    private int mSandbags;
    private int mTotScore;

    private ArrayList<Integer> mBidHistory;
    private ArrayList<Integer> mTakeHistory;
    private ArrayList<Integer> mScoreHistory;

    // Position-only constructor
    public Player(int position){
        mPosition = position;
        mOrigPosition = position;
        mName = null;
        mCurBid = 0;
        mCurTake = 0;
        mSandbags = 0;
        mTotScore = 0;
        mBidHistory = new ArrayList<Integer>(25);
        mTakeHistory = new ArrayList<Integer>(25);
        mScoreHistory = new ArrayList<Integer>(25);
    }

    // Constructor that declares position and name up front
    public Player(int position, String name){
        mPosition = position;
        mOrigPosition = position;
        mName = name;
        mCurBid = 0;
        mCurTake = 0;
        mSandbags = 0;
        mTotScore = 0;
        mBidHistory = new ArrayList<Integer>(25);
        mTakeHistory = new ArrayList<Integer>(25);
        mScoreHistory = new ArrayList<Integer>(25);
    }

    public void setBid(int bid){
        mCurBid = bid;
        mBidHistory.add(bid);
    }

    public int getBid(){
        return mCurBid;
    }

    public int getOriginalPosition(){
        return mOrigPosition;
    }

    public void setTake(int take){
        mCurTake = take;
        mTakeHistory.add(take);
        if (mCurTake >= mCurBid){
            mSandbags += (mCurTake - mCurBid);
            mTotScore += (mCurBid * 10) + (mCurTake - mCurBid);
            mScoreHistory.add((mCurBid * 10) + (mCurTake - mCurBid));
        } else {
            mTotScore += -1 * (mCurBid * 10);
            mScoreHistory.add(-1 * (mCurBid * 10));
        }
        mCurBid = 0;
    }

    public int getScore(){
        return mTotScore;
    }

    public int getSandbags(){
        return mSandbags;
    }

    public ArrayList<Integer> getBidHistory(){
        return mBidHistory;
    }

    public ArrayList<Integer> getScoreHistory(){
        return mScoreHistory;
    }

    public ArrayList<Integer> getTakeHistory(){
        return mTakeHistory;
    }

    public String getName(){
        return mName;
    }

    public void setName(String pName){
        mName = pName;
    }

    public void changePosition(int positionIncrement){
        mPosition += positionIncrement;
    }

    public int getPosition(){
        return mPosition;
    }
}
