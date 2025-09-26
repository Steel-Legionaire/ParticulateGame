package com.example.Solids.StaticSolids.Spawners;
import java.awt.Color;

import com.example.ParticulateGame;
import com.example.Tile;
import com.example.Liquids.Lava;

public class LavaSpawner extends Tile{

    public LavaSpawner(int x, int y){
        super(x, y, false, true, 8, 0);
        setAllPossibleColors(new Color[]{ Color.GREEN });
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
            grid[y+1][x] = new Lava(x, y+1);
        }
    
        ParticulateGame.grid = grid;
    }
    
}
