package particulate.game.Solids.StaticSolids.Spawners;
import java.awt.Color;

import particulate.game.CellularMatrix;
import particulate.game.ParticulateGame;
import particulate.game.Tile;
import particulate.game.Gases.Fire;

public class FireSpawner extends Tile
{

    public FireSpawner(int x, int y) {
        super(x, y, false, true, 8, 1);
        setAllPossibleColors(new Color[]{Color.magenta });
        setColor();
    }

    @Override
    public void move() 
    {

    }

    @Override
    public void action() 
    {
        CellularMatrix matrix = ParticulateGame.getMatrix();

        if(matrix.getTile(x,y-1) == null)
        {
            matrix.setTile(x, y-1, new Fire(x, y-1));
        }
    }
    
}
