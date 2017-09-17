package com.example.android.thirteen;

import java.util.ArrayList;

/**
 * I'm experimenting with a new class to code the functions of the ScoreKeeper class in a more object-oriented way.
 */

class Player {
    private int mPosition;
    private String mName;
    private int mCurBid;
    private int mCurTake;
    private int mSandbags;
    private int mTotScore;

    private ArrayList<Integer> mBidHistory;
    private ArrayList<Integer> mTakeHistory;
    private ArrayList<Integer> mScoreHistory;

    public Player(int position, String name) throws Error{
        mPosition = position;
        mName = name;
        mCurBid = 0;
        mCurTake = 0;
        mSandbags = 0;
        mTotScore = 0;
    }

    public void setBid(int bid){
        mCurBid = bid;
        mBidHistory.add(bid);
    }

    public int getBid(){
        return mCurBid;
    }

    public void setTake(int take){
        mCurTake = take;
        mTakeHistory.add(take);
        if ((mCurTake - mCurBid) >= 0){
            mSandbags += (mCurTake - mCurBid);
            mTotScore += (mCurBid * 10) + (mCurTake - mCurBid);
            mScoreHistory.add((mCurBid * 10) + (mCurTake - mCurBid));
        } else {
            mTotScore += -1 * (mCurBid * 10);
            mScoreHistory.add(-1 * (mCurBid * 10));
        }
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

    public void changePosition(){
        mPosition++;
    }
}
