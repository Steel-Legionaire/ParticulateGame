package particulate.game.Solids.StaticSolids;

import java.awt.Color;

import particulate.game.CellularMatrix;
import particulate.game.ParticulateGame;
import particulate.game.Tile;
import particulate.game.Gases.Steam;
import particulate.game.Liquids.Water;

public class Ice extends StaticSolid{

    public Ice(int x, int y) 
    {
        super(x, y, false, true, 4, 2);
        setAllPossibleColors(new Color[]{new Color(180, 255, 255), new Color(130, 230, 255), new Color(140, 235, 255)});
        setColor();
        heat=-15;
    }

    @Override
    public void action()
    {
        CellularMatrix matrix = ParticulateGame.getMatrix();
        if(heat > 0)
        {
            Tile t = new Water(x,y);
            t.setHeat(heat);
            matrix.setTile(x, y, t);
        }
    }

    @Override
    public void recieveHeat(int h)
    {
        CellularMatrix matrix = ParticulateGame.getMatrix();

        heat+=h;
        if(heat >= 0)
        {
            Tile t = new Water(x,y);
            t.setHeat(heat);
            matrix.setTile(x, y, t);
        }
    }
}
