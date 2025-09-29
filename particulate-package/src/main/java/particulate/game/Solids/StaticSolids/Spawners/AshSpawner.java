package particulate.game.Solids.StaticSolids.Spawners;
import java.awt.Color;

import particulate.game.CellularMatrix;
import particulate.game.ParticulateGame;
import particulate.game.Tile;
import particulate.game.Solids.MoveableSolids.Ash;

public class AshSpawner extends Tile{

    public AshSpawner(int x, int y) {
        super(x, y, false, true, 8, 1);
        setAllPossibleColors(new Color[]{ new Color(15, 125, 15) });
        setColor();

    }

    @Override
    public void move() { return; }

    @Override
    public void action() 
    {
        CellularMatrix matrix = ParticulateGame.getMatrix();

        if(matrix.getTile(x,y+1) == null)
        {
            matrix.setTile(x, y+1, new Ash(x, y+1));
        }
    }
    
}
