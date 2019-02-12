package com.siteclearing.model;

import com.siteclearing.constants.SquareType;

public class Square {

    private SquareType type;
    private boolean isCleared = false;

    public SquareType getType() {
        return type;
    }

    public void setType(SquareType type) {
        this.type = type;
    }

    public boolean isCleared() {
        return isCleared;
    }

    public void setCleared(boolean cleared) {
        isCleared = cleared;
    }
}
