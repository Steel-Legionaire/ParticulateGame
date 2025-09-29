package particulate.game.Solids.StaticSolids.Spawners;
import java.awt.Color;

import particulate.game.CellularMatrix;
import particulate.game.ParticulateGame;
import particulate.game.Tile;
import particulate.game.Solids.MoveableSolids.Sand;

public class SandSpawner extends Tile{

    public SandSpawner(int x, int y) {
        super(x, y, false, true, 8, 1);
        setAllPossibleColors(new Color[]{ Color.RED });
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
            matrix.setTile(x, y+1, new Sand(x, y+1));
        }
    }
    
}
