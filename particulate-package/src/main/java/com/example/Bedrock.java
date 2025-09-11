package com.example;

import java.awt.Color;

public class Bedrock extends Tile {
    
    public Bedrock(int x, int y) {
        super(x, y, Color.DARK_GRAY, false, false, 10, 1);
    }

    @Override
    public void move() { return; }

    @Override
    public void action() { return; }
    
}
