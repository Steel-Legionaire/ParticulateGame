package com.example;

import java.awt.Color;

public class Water extends Tile
{
    int direction;

    public Water(int x, int y) {
        super(x, y, Color.CYAN, false, true, 1, 3);

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

    }
    
}
