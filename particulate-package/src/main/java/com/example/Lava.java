package com.example;

import java.awt.Color;

public class Lava extends Tile{

    int direction = (int)(Math.random() * 2);

    public Lava(int x, int y) {
        super(x, y, Color.ORANGE, true, true, 2, 8);
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

    }
    
}
