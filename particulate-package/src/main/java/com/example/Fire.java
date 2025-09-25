package com.example;

import java.awt.Color;

public class Fire extends Tile{

    private int lifeTime = (int)(Math.random()*20) + 50;

    private static final Color[] COLORS = new Color[]{ new Color(255,135,0), new Color(255,142,0), new Color(255,150,0), new Color(255,158,0), new Color(255,165,0) };
    private static boolean colorsInitialized = false;
    
    public Fire(int x, int y) {
        super(x, y, false, true, 1, 2);
        setAllPossibleColors(COLORS);
        setColor();
    }

    @Override
    public void move() 
    {
        Tile[][] grid = ParticulateGame.grid;



        if(framesSinceLastUpdate == speed)
        {
            // 0 - up
            // 1 - right
            // 2 - left
            if(lifeTime == 0)
            {
                grid[y][x] = null;
                return;
            }

            int randDir = (int)(Math.random() * 3);
            //System.out.println(randDir);
            if(randDir == 0)
            {
                if(grid[y-1][x] == null)
                {
                    grid[y][x] = null;
                    y--;
                    grid[y][x] = this;
                    
                }
            }
            else if(randDir == 1)
            {
                if(grid[y-1][x+1] == null && grid[y][x+1] == null)
                {
                    grid[y][x] = null;
                    y--;
                    x++;
                    grid[y][x] = this;
                }
            }
            else if(randDir == 2)
            {
                if(grid[y-1][x-1] == null && grid[y][x-1] == null)
                {
                    grid[y][x] = null;
                    y--;
                    x--;
                    grid[y][x] = this;
                }
            }

            Tile topTile = grid[y-1][x];
            Tile rightTile = grid[y][x+1];
            Tile leftTile = grid[y][x-1];


            Tile bottomTile = null;

            if(y+1 < grid.length)
            {
                bottomTile = grid[y+1][x];
            }


            if(topTile instanceof Wood)
            {
                ((Wood)grid[y-1][x]).onFire = true;
            }
            else if(topTile instanceof TNT)
            {
                ((TNT)topTile).explode();
            }

            if(rightTile instanceof Wood)
            {
                ((Wood)grid[y][x+1]).onFire = true;
            }
            else if(rightTile instanceof TNT)
            {
                ((TNT)rightTile).explode();
            }

            if(y+1 < grid.length &&  bottomTile instanceof Wood)
            {
                ((Wood)grid[y+1][x]).onFire = true;
            }
            else if(y+1 < grid.length && bottomTile instanceof TNT)
            {
                ((TNT)bottomTile).explode();
            }

            if(leftTile instanceof Wood)
            {
                ((Wood)grid[y][x-1]).onFire = true;
            }
            else if(leftTile instanceof TNT)
            {
                ((TNT)leftTile).explode();
            }

            lifeTime--;
            framesSinceLastUpdate = 0; 
        }
        else
        {
            framesSinceLastUpdate++;
        }


    }

    @Override
    public void action() 
    {

    }
    
}
