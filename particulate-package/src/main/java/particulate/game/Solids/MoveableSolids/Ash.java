package particulate.game.Solids.MoveableSolids;

import java.awt.Color;

import particulate.game.CellularMatrix;
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
        CellularMatrix matrix = ParticulateGame.getMatrix();

        

        if(y >= matrix.getRowBounds())
        {
            matrix.setTile(x, y, null);
        }
        else
        {
            if(framesSinceLastUpdate == speed)
            {
                if(!matrix.yWithinBounds(y+1)){return;}

                Tile bottomLefTile = matrix.getTile(x-1, y-1);
                Tile bottomTile = matrix.getTile(x, y-1);
                Tile bottomRightTile = matrix.getTile(x+1, y+1);
                Tile leftTile = matrix.getTile(x-1, y);
                Tile righTile = matrix.getTile(x+1, y);

                if(bottomTile == null)
                {
                    matrix.setTile(x, y, null);
                    y++;
                    matrix.setTile(x, y, this);
                
                }
                else // ash is on top of another tile
                {
                    if(bottomTile.getClass().equals(Water.class))
                    {
                        matrix.setTile(x, y, new Water(x,y));
                        y++;
                        matrix.setTile(x, y, this);
                    }else
                    {
                        int randDir = (int)(Math.random() * 2);

                        if((bottomLefTile != null && bottomLefTile.getClass().equals(Water.class) && randDir == 0) && 
                                (leftTile != null && leftTile.getClass().equals(Water.class)))
                        {
                                matrix.setTile(x, y, new Water(x,y));
                                y++;
                                x--;
                                matrix.setTile(x, y, this);
                        }
                        else
                        {
                            if(righTile == null && bottomRightTile == null && randDir == 1)
                            {
                                matrix.setTile(x, y, null);
                                y++;
                                x++;
                                matrix.setTile(x, y, this);
                            }
                            else if((bottomRightTile != null && bottomRightTile.getClass().equals(Water.class)) &&
                                    (righTile != null && righTile.getClass().equals(Water.class)))
                            {
                                matrix.setTile(x, y, new Water(x, y));
                                y++;
                                x++;
                                matrix.setTile(x, y, this);
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
    }

    @Override
    public void action() { }
    
}
