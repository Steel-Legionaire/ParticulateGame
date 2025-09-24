package com.example;
import java.awt.Color;

public class Obsidian extends Tile{

    public Obsidian(int x, int y) {
        super(x, y, false, true, 10, 0);
        setAllPossibleColors(new Color[]{ new Color(8, 8, 8), new Color(16, 16, 16), new Color(24, 24, 24) });
        setColor();
    }

    @Override
    public void move() {

    }

    @Override
    public void action() {

    }
    
}
