package com.example;
import java.awt.Color;

public class Bedrock extends Tile {
    
    public Bedrock(int x, int y) {
        super(x, y, false, false, 10, 1);
        setAllPossibleColors(new Color[]{ Color.DARK_GRAY});
        setColor();
    }

    @Override
    public void move() { return; }

    @Override
    public void action() { return; }
    
}
