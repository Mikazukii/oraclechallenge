package com.siteclearing.model;

public class Site {

    private final int length;
    private final int width;
    private final Square[][] squares;
    private int squaresToClear;
    private int nbTreeDestructed;

    public Site(int rows, int columns) {
        this.length = columns;
        this.width = rows;
        squares = new Square[rows][columns];
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public Square[][] getSquares() {
        return squares;
    }

    public Square getSquare(int x, int y) {

        // WARN : reverse axes
        return squares[y][x];
    }

    public int getSquaresToClear() {
        return squaresToClear;
    }

    public void addSquareToClear(int nbSquare) {
        this.squaresToClear += nbSquare;
    }

    public int getNbTreeDestructed() {
        return nbTreeDestructed;
    }

    public void addDestructedTree(int nbCredit) {
        this.nbTreeDestructed += nbCredit;
    }
}
