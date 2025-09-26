package com.example.Liquids;
import java.awt.Color;

import com.example.ParticulateGame;
import com.example.Tile;
import com.example.Solids.MoveableSolids.Ash;
import com.example.Solids.StaticSolids.Obsidian;
import com.example.Solids.StaticSolids.TNT;
import com.example.Solids.StaticSolids.Wood;

public class Lava extends Tile{

    int direction = (int)(Math.random() * 2);

    private static final Color[] COLORS = new Color[]{ new Color(255, 185, 0), new Color(255, 192, 0), new Color(255, 200, 0), new Color(255, 208, 0), new Color(255, 215, 0) };
    
    public Lava(int x, int y) {
        super(x, y, true, true, 2, 8);

        setAllPossibleColors(COLORS);
        setColor();
    }

    @Override
    public void move() {
        Tile[][] grid = ParticulateGame.grid;

        if(y+1 >= grid.length)
        {
            grid[y][x] = null;
        }
        else
        {
            if(framesSinceLastUpdate == speed)
            {

                if(grid[y+1][x] == null)
                {
                    grid[y][x] = null;
                    this.y++;
                    grid[y][x] = this;
                }
                else
                {
                    if(direction == 1)
                    {
                        if(grid[y][x+1] == null)
                        {
                            grid[y][x] = null;
                            this.x++;
                            grid[y][x] = this;
                        }
                        else
                        {
                            direction = 0;
                        }
                    }
                    else
                    {
                        if(grid[y][x-1] == null)
                        {
                            grid[y][x] = null;
                            this.x--;
                            grid[y][x] = this;
                        }
                        else
                        {
                            direction = 1;
                        }
                    }
                }
                framesSinceLastUpdate = 0;
            }
            else
            {
                framesSinceLastUpdate++;
            }
        }
        ParticulateGame.grid = grid;
    }

    @Override
    public void action() {
        Tile[][] grid = ParticulateGame.grid;

        Tile topTile = grid[y-1][x];
        Tile rightTile = grid[y][x+1];
        Tile leftTile = grid[y][x-1];


        Tile bottomTile = null;

        if(y+1 < grid.length)
        {
            bottomTile = grid[y+1][x];
        }

        

        if(topTile instanceof Water)
        {
            grid[y][x] = new Obsidian(x,y);
        }
        else if(topTile instanceof TNT)
        {
            ((TNT)topTile).explode();
        }
        else if(topTile instanceof Wood){
            ((Wood)topTile).onFire = true;
        }
        else if(topTile instanceof Ash)
        {
                grid[y-1][x] = null;
        }

        if(rightTile instanceof Water)
        {
            grid[y][x] = new Obsidian(x,y);
        }
        else if(rightTile instanceof TNT)
        {
            ((TNT)rightTile).explode();
        }
        else if(rightTile instanceof Wood){
            ((Wood)rightTile).onFire = true;
        }
        else if(rightTile instanceof Ash)
        {
                grid[y][x+1] = null;
        }

        if(y+1 < grid.length &&  bottomTile instanceof Water)
        {
            grid[y][x] = new Obsidian(x,y);
        }
        else if(y+1 < grid.length && bottomTile instanceof TNT)
        {
            ((TNT)bottomTile).explode();
        }
        else if(y+1 < grid.length && bottomTile instanceof Wood){
            ((Wood)bottomTile).onFire = true;
        }
        else if(bottomTile instanceof Ash)
        {
                grid[y+1][x] = null;
        }

        if(leftTile instanceof Water)
        {
            grid[y][x] = new Obsidian(x,y);
        }
        else if(leftTile instanceof TNT)
        {
            ((TNT)leftTile).explode();
        }
        else if(leftTile instanceof Wood){
            ((Wood)leftTile).onFire = true;
        }
        else if(leftTile instanceof Ash)
        {
                grid[y][x-1] = null;
        }
    }
    
}
