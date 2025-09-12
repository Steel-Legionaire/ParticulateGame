package com.example;
import java.awt.Color;

public class WaterSpawner extends Tile{

    public WaterSpawner(int x, int y) {
        super(x, y, Color.BLUE, false, true, 8, 1);

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
