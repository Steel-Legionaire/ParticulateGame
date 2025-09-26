package particulate.game.Solids.StaticSolids.Spawners;
import java.awt.Color;

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
        Tile[][] grid = ParticulateGame.grid;

        if(grid[y+1][x] == null)
        {
            grid[y+1][x] = new Ash(x, y+1);
        }

        ParticulateGame.grid = grid;
    }
    
}
