package particulate.game.Liquids;
import java.awt.Color;

import particulate.game.CellularMatrix;
import particulate.game.ParticulateGame;
import particulate.game.Tile;
import particulate.game.Solids.MoveableSolids.Ash;
import particulate.game.Solids.StaticSolids.Obsidian;
import particulate.game.Solids.StaticSolids.TNT;
import particulate.game.Solids.StaticSolids.Wood;

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
        CellularMatrix matrix = ParticulateGame.getMatrix();

        if(!matrix.yWithinBounds(y))
        {
            matrix.setTile(x, y, null);
        }
        else
        {
            Tile bottomTile = matrix.getTile(x,y+1);
            Tile rightTile = matrix.getTile(x+1, y);
            Tile leftTile = matrix.getTile(x-1, y);

            if(framesSinceLastUpdate == speed)
            {

                if(bottomTile == null)
                {
                    matrix.setTile(x, y, null);
                    this.y++;
                    matrix.setTile(x, y, this);
                }
                else
                {
                    if(direction == 1)
                    {
                        if(rightTile == null)
                        {
                            matrix.setTile(x, y, null);
                            this.x++;
                            matrix.setTile(x, y, this);
                        }
                        else
                        {
                            direction = 0;
                        }
                    }
                    else
                    {
                        if(leftTile == null)
                        {
                            matrix.setTile(x, y, null);
                            this.x--;
                            matrix.setTile(x, y, this);
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
    }

    @Override
    public void action() {
        CellularMatrix matrix = ParticulateGame.getMatrix();

        Tile topTile = matrix.getTile(x,y-1);
        Tile rightTile = matrix.getTile(x,y-1);
        Tile leftTile = matrix.getTile(x,y-1);


        Tile bottomTile = null;

        if(matrix.yWithinBounds(y+1))
        {
            bottomTile = matrix.getTile(x, y+1);
        }

        

        if(topTile instanceof Water)
        {
            matrix.setTile(x,y, new Obsidian(x,y));
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
            matrix.setTile(x, y-1, null);
        }

        if(rightTile instanceof Water)
        {
            matrix.setTile(x,y, new Obsidian(x,y));
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
            matrix.setTile(x+1, y, null);
        }

        if(matrix.yWithinBounds(y+1) && bottomTile instanceof Water)
        {
            matrix.setTile(x,y, new Obsidian(x,y));
        }
        else if(matrix.yWithinBounds(y+1) && bottomTile instanceof TNT)
        {
            ((TNT)bottomTile).explode();
        }
        else if(matrix.yWithinBounds(y+1) && bottomTile instanceof Wood){
            ((Wood)bottomTile).onFire = true;
        }
        else if(matrix.yWithinBounds(y+1) && bottomTile instanceof Ash)
        {
            matrix.setTile(x, y+1, null);
        }

        if(leftTile instanceof Water)
        {
            matrix.setTile(x,y, new Obsidian(x,y));
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
            matrix.setTile(x-1, y, null);
        }
    }
    
}
