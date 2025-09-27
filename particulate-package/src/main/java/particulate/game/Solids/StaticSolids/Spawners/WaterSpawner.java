package particulate.game.Solids.StaticSolids.Spawners;
import java.awt.Color;

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
        Tile[][] grid = ParticulateGame.grid;

        if(grid[y+1][x] == null)
        {
            grid[y+1][x] = new Water(x, y+1);
        }
    
        ParticulateGame.grid = grid;
    }
    
}
