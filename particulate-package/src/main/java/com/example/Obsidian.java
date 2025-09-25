package com.example;
import java.awt.Color;

public class Obsidian extends Tile{

    private static final Color[] COLORS = new Color[]{ new Color(26, 26, 26), new Color(24, 24, 24), new Color(22, 22, 22) };

    public Obsidian(int x, int y) {
        super(x, y, false, true, 10, 0);
        setAllPossibleColors(COLORS);
        setColor();
    }

    @Override
    public void move() {

    }

    @Override
    public void action() {

    }
    
}
