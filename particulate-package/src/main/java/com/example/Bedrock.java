package com.example;
import java.awt.Color;

public class Bedrock extends Tile {
    
    public Bedrock(int x, int y) {
        super(x, y, false, false, 10, 1);
        setAllPossibleColors(new Color[]{new Color(50, 50, 50), new Color(58, 58, 58), new Color(64, 64, 64), new Color(70, 70, 70), new Color(78, 78, 78)});
        setColor();
    }

    @Override
    public void move() { return; }

    @Override
    public void action() { return; }
    
}
