package particulate.game.Solids.StaticSolids.Spawners;
import java.awt.Color;

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
        Tile[][] grid = ParticulateGame.grid;

        if(grid[y+1][x] == null)
        {
            grid[y+1][x] = new Sand(x, y+1);
        }

        ParticulateGame.grid = grid;
    }
    
}
