package com.example.Solids.StaticSolids;
import java.awt.Color;

import com.example.Tile;

public class Stone extends Tile{

    private static final Color[] COLORS = new Color[]{new Color(140, 140, 140), new Color(130, 130, 130) };
    
    public Stone(int x, int y) {
        super(x, y, false, true, 5,1);
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
