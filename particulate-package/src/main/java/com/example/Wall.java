package com.example;

import java.awt.Color;

public class Wall extends Tile{

    public Wall(int x, int y) {
        super(x, y, Color.GRAY, false, true, 5,1);
    }

    @Override
    public void move() {

    }

    @Override
    public void action() {

    }
    
}
