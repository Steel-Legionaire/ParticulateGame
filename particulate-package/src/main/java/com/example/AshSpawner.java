package com.example;
import java.awt.Color;

public class AshSpawner extends Tile{

    public AshSpawner(int x, int y) {
        super(x, y, false, true, 8, 1);
        setAllPossibleColors(new Color[]{ Color.PINK });
        setColor();

    }

    @Override
    public void move() { return; }

    @Override
    public void action() 
    {
        Tile[][] grid = ParticulateGame.grid;

        if(grid[y+1][x] == null)
        {
            grid[y+1][x] = new Ash(x, y+1);
        }

        ParticulateGame.grid = grid;
    }
    
}
