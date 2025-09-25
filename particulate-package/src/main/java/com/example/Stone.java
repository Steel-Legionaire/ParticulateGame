package com.example;
import java.awt.Color;

public class Stone extends Tile{

    public Stone(int x, int y) {
        super(x, y, false, true, 5,1);
        setAllPossibleColors(new Color[]{new Color(140, 140, 140), new Color(130, 130, 130) });
        setColor();
    }

    @Override
    public void move() {

    }

    @Override
    public void action() {

    }
    
}
