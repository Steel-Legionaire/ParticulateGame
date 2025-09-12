package com.example;
import java.awt.Color;

public class Sand extends Tile {
    
    public Sand(int x, int y) {
        super(x, y, Color.YELLOW, false, true, 2,1);
    }

    @Override
    public void move() 
    { 
        Tile[][]grid = ParticulateGame.grid;

        if(y+1 >= grid.length)
        {
            grid[y][x] = null;
        }
        else
        {
            if(framesSinceLastUpdate == speed)
            {
                if(y+1 > grid.length){return;}

                if(grid[y+1][x] == null)
                {
                    grid[y][x] = null;
                    y++;
                    grid[y][x] = this;

                
                }
                else // sand is on top of another tile
                {
                    if(grid[y+1][x].getClass().equals(Water.class))
                    {
                        grid[y][x] = new Water(x, y);
                        y++;
                        grid[y][x] = this;
                    }else
                    {
                        int randDir = (int)(Math.random() * 2);

                        if(grid[y][x-1] == null && grid[y+1][x-1] == null && randDir == 0)
                        {
                            grid[y][x] = null;
                            y++;
                            x--;
                            grid[y][x] = this;
                        }
                        else if((grid[y+1][x-1] != null && grid[y+1][x-1].getClass().equals(Water.class) && randDir == 0) && 
                                (grid[y][x-1] != null && grid[y][x-1].getClass().equals(Water.class)))
                        {
                                grid[y][x] = new Water(x, y);
                                y++;
                                x--;
                                grid[y][x] = this;
                        }
                        else
                        {
                            if(grid[y][x+1] == null && grid[y+1][x+1] == null && randDir == 1)
                            {
                                grid[y][x] = null;
                                y++;
                                x++;
                                grid[y][x] = this;
                            }
                            else if((grid[y+1][x+1] != null && grid[y+1][x+1].getClass().equals(Water.class)) &&
                                    (grid[y][x+1] != null && grid[y][x+1].getClass().equals(Water.class)))
                            {
                                grid[y][x] = new Water(x, y);
                                y++;
                                x++;
                                grid[y][x] = this;
                            }
                        }
                    } 
                }
                framesSinceLastUpdate = 0;

            }
            else{
                framesSinceLastUpdate++;
            }
        }
        ParticulateGame.grid = grid;
    }

    @Override
    public void action() { return; }
    
}
