package particulate.game.Solids.StaticSolids.Spawners;
import java.awt.Color;

import particulate.game.Liquids.Water;

public class WaterSpawner extends Spawner{

    public WaterSpawner(int x, int y) {
        super(x, y, false, true, 8, 1);
        setAllPossibleColors(new Color[]{ Color.CYAN });
        setColor();

        setSpawnedTile(Water.class);
    }
}
