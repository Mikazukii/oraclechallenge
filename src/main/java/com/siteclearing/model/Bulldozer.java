package com.siteclearing.model;

import com.siteclearing.constants.MachineDirection;

public class Bulldozer {

    private int x = -1;
    private int y;
    private MachineDirection direction = MachineDirection.EAST;
    private int fuelUnitUsed;
    private int paintDamagedUnit;
    private int commUnit;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public MachineDirection getDirection() {
        return direction;
    }

    public void setDirection(MachineDirection direction) {
        this.direction = direction;
    }

    public int getFuelUnitUsed() {
        return fuelUnitUsed;
    }

    public void addFuelUnit(int nbUnits) {
        this.fuelUnitUsed += nbUnits;
    }

    public int getPaintDamagedUnit() {
        return paintDamagedUnit;
    }

    public void addPaintDamagedUnit(int nbUnit) {
        this.paintDamagedUnit += nbUnit;
    }

    public int getCommUnit() {
        return commUnit;
    }

    public void addCommUnit(int nbCommUnit) {
        this.commUnit += nbCommUnit;
    }
}
