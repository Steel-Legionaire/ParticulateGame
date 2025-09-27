package particulate.game.Solids.MoveableSolids;

import java.awt.Color;

import particulate.game.ParticulateGame;
import particulate.game.Tile;
import particulate.game.Liquids.Water;

public class Ash extends Tile{

    private static final Color[] COLORS = new Color[]{ new Color(230,230,230), new Color( 200, 200, 200), new Color(215, 215, 215), new Color(240, 240, 240)};

    public Ash(int x, int y) {
        super(x, y, false, true, 1, 2);
        
        setAllPossibleColors(COLORS);
        setColor();
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
    public void action() 
    {

    }
    
}
