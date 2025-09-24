package com.example;
import java.awt.Color;

public class Water extends Tile
{
    int direction;

    public Water(int x, int y) {
        super(x, y, false, true, 1, 3);
        setAllPossibleColors(new Color[]{ Color.BLUE });
        setColor();

        direction = (int)(Math.random() *2); // spawn with a random direction

    }

    @Override
    public void move() 
    {
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

                ParticulateGame.grid = grid;

                framesSinceLastUpdate = 0;
            }
            else
            {
                framesSinceLastUpdate++;
            }
        }
    }

    @Override
    public void action() 
    {
        Tile[][] grid = ParticulateGame.grid;

        Tile topTile = grid[y-1][x];
        Tile rightTile = grid[y][x+1];
        Tile leftTile = grid[y][x-1];


        Tile bottomTile = null;

        if(y+1 < grid.length)
        {
            bottomTile = grid[y+1][x];
        }


        
        if(topTile instanceof Wood){
            ((Wood)topTile).onFire = false;
        }
        else if(topTile instanceof Fire)
        {
            grid[y-1][x] = null;
        }

        if(rightTile instanceof Wood){
            ((Wood)rightTile).onFire = false;
        }
        else if(rightTile instanceof Fire)
        {
            grid[y][x+1] = null;
        }

        if(y+1 < grid.length && bottomTile instanceof Wood){
            ((Wood)bottomTile).onFire = false;
        }
        else if(y+1 < grid.length && bottomTile instanceof Fire)
        {
            grid[y+1][x] = null;
        }

        if(leftTile instanceof Wood){
            ((Wood)leftTile).onFire = false;
        }
        else if(leftTile instanceof Fire)
        {
            grid[y][x-1] = null;
        }
    }
    
}
