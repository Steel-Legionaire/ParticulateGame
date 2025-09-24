package com.example;
import java.awt.Color;

public class Stone extends Tile{

    public Stone(int x, int y) {
        super(x, y, false, true, 5,1);
        setAllPossibleColors(new Color[]{ new Color(200, 200, 200), new Color(190, 190, 190), new Color(180, 180, 180), new Color(170, 170, 170), new Color(160, 160, 160) });
        setColor();
    }

    @Override
    public void move() {

    }

    @Override
    public void action() {

    }
    
}
