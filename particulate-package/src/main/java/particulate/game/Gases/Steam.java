package particulate.game.Gases;

import java.awt.Color;

import particulate.game.CellularMatrix;
import particulate.game.ParticulateGame;
import particulate.game.Tile;
import particulate.game.Liquids.Water;

public class Steam extends Gases{

    public Steam(int x, int y)
    {
        super(x, y, false, true, 2, 1);
        setAllPossibleColors(new Color[]{Color.WHITE});
        setColor();
        heat = 150;
    }


    @Override
    public void action()
    {
        CellularMatrix matrix = ParticulateGame.getMatrix();

        if(framesSinceHeatUpdate == heatDispersalRate)
        {
            radiateHeat();
            
            if( heat <= 75)
            {
                Tile t = new Water(x,y);
                t.setHeat(heat);
                matrix.setTile(x, y, t);
            }
            framesSinceHeatUpdate = 0;
        }
        else
        {
            framesSinceHeatUpdate++;
        }
        
    }
    
}
