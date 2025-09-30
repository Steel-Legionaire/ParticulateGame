package particulate.game.Gases;

import java.awt.Color;

import particulate.game.CellularMatrix;
import particulate.game.ParticulateGame;
import particulate.game.Tile;
import particulate.game.Solids.StaticSolids.TNT;
import particulate.game.Solids.StaticSolids.Wood;

public class Fire extends Gases{

    private static final Color[] COLORS = new Color[]{ new Color(255,135,0), new Color(255,142,0), new Color(255,150,0), new Color(255,158,0), new Color(255,165,0) };
    
    public Fire(int x, int y) {
        super(x, y, false, true, 1, 2);
        setAllPossibleColors(COLORS);
        setColor();

        setLifeTime((int)(Math.random()*20) + 50);
    }

    @Override
    public void action() 
    {

        CellularMatrix matrix = ParticulateGame.getMatrix();

        Tile topTile = matrix.getTile(x,y-1);
        Tile rightTile = matrix.getTile(x+1,y);
        Tile leftTile = matrix.getTile(x-1,y);

        Tile bottomTile = null;
        if(matrix.withinBounds(x, y+1))
        {
            bottomTile = matrix.getTile(x,y+1);
        }

        if(topTile instanceof Wood)
        {
            ((Wood)topTile).onFire = true;
        }
        else if(topTile instanceof TNT)
        {
            ((TNT)topTile).explode();
        }

        if(rightTile instanceof Wood)
        {
            ((Wood)rightTile).onFire = true;
        }
        else if(rightTile instanceof TNT)
        {
            ((TNT)rightTile).explode();
        }

        if(bottomTile instanceof Wood)
        {
            ((Wood)bottomTile).onFire = true;
        }
        else if(bottomTile instanceof TNT)
        {
            ((TNT)bottomTile).explode();
        }

        if(leftTile instanceof Wood)
        {
            ((Wood)leftTile).onFire = true;
        }
        else if(leftTile instanceof TNT)
        {
            ((TNT)leftTile).explode();
        }
    }
    
}
