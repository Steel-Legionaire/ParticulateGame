package particulate.game.Solids.StaticSolids.Spawners;
import java.awt.Color;

import particulate.game.CellularMatrix;
import particulate.game.ParticulateGame;
import particulate.game.Tile;
import particulate.game.Liquids.Water;

public class WaterSpawner extends Tile{

    public WaterSpawner(int x, int y) {
        super(x, y, false, true, 8, 1);
        setAllPossibleColors(new Color[]{ Color.CYAN });
        setColor();

    }

    @Override
    public void move() {

    }

    @Override
    public void action() {
        CellularMatrix matrix = ParticulateGame.getMatrix();

        if(matrix.getTile(x,y+1) == null)
        {
            matrix.setTile(x, y+1, new Water(x, y+1));
        }
    }
    
}
