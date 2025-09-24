package com.example;
import java.awt.Color;

public class Wall extends Tile{

    public Wall(int x, int y) {
        super(x, y, false, true, 5,1);
        setAllPossibleColors(new Color[]{ Color.GRAY });
        setColor();
    }

    @Override
    public void move() {

    }

    @Override
    public void action() {

    }
    
}
