package com.example;
import java.awt.Color;

public class Obsidian extends Tile{

    public Obsidian(int x, int y) {
        super(x, y, false, true, 10, 0);
        setAllPossibleColors(new Color[]{ Color.BLACK });
        setColor();
    }

    @Override
    public void move() {

    }

    @Override
    public void action() {

    }
    
}
