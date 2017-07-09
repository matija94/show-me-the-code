package com.geoquiz.ckilima.geoquiz;

import java.io.Serializable;

/**
 * Created by matija on 22.1.17..
 */

public class Score implements Serializable{

    private int mValue;


    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
    }

    public void incrementScore() {
        this.mValue++;
    }

    public void decrementScore() {
        this.mValue--;
    }
}
