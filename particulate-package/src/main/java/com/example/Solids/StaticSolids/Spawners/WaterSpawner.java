package com.example.Solids.StaticSolids.Spawners;
import java.awt.Color;

import com.example.ParticulateGame;
import com.example.Tile;
import com.example.Liquids.Water;

public class WaterSpawner extends Tile{

    public WaterSpawner(int x, int y) {
        super(x, y, false, true, 8, 1);
        setAllPossibleColors(new Color[]{ Color.CYAN });
        setColor();

    }

    @Override
    public void move() {

    }

    @Override
    public void action() {
        Tile[][] grid = ParticulateGame.grid;

        if(grid[y+1][x] == null)
        {
            grid[y+1][x] = new Water(x, y+1);
        }
    
        ParticulateGame.grid = grid;
    }
    
}
