package particulate.game.Liquids;
import java.awt.Color;

import particulate.game.CellularMatrix;
import particulate.game.ParticulateGame;
import particulate.game.Tile;
import particulate.game.Gases.Fire;
import particulate.game.Solids.StaticSolids.Wood;

public class Water extends Liquid
{
    private static final Color[] COLORS = new Color[]{ new Color(0, 0, 230), new Color(0, 0, 240), new Color(0, 0, 250), new Color(0, 0, 255), new Color(10, 10, 255) };
    
    public Water(int x, int y) {
        super(x, y, false, true, 1, 3);
        setAllPossibleColors(COLORS);
        setColor();
    }

    @Override
    public void action() 
    {
        CellularMatrix matrix = ParticulateGame.getMatrix();

        Tile topTile = matrix.getTile(x,y-1);
        Tile rightTile = matrix.getTile(x,y-1);
        Tile leftTile = matrix.getTile(x,y-1);


        Tile bottomTile = null;

        if(matrix.yWithinBounds(y+1))
        {
            bottomTile = matrix.getTile(x, y+1);
        }


        
        if(topTile instanceof Wood){
            ((Wood)topTile).onFire = false;
        }
        else if(topTile instanceof Fire)
        {
            matrix.setTile(x,y-1, null);
        }

        if(rightTile instanceof Wood){
            ((Wood)rightTile).onFire = false;
        }
        else if(rightTile instanceof Fire)
        {
            matrix.setTile(x+1,y, null);
        }

        if(matrix.yWithinBounds(y+1) && bottomTile instanceof Wood){
            ((Wood)bottomTile).onFire = false;
        }
        else if(matrix.yWithinBounds(y+1) && bottomTile instanceof Fire)
        {
            matrix.setTile(x,y+1, null);
        }

        if(leftTile instanceof Wood){
            ((Wood)leftTile).onFire = false;
        }
        else if(leftTile instanceof Fire)
        {
            matrix.setTile(x-1,y, null);
        }
    }
    
}
