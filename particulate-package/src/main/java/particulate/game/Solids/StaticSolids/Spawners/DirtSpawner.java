package particulate.game.Solids.StaticSolids.Spawners;
import java.awt.Color;

import particulate.game.Solids.MoveableSolids.Dirt;

public class DirtSpawner extends Spawner
{

    public DirtSpawner(int x, int y) {
        super(x, y, false, true, 8, 1);
        setAllPossibleColors(new Color[]{Color.YELLOW });
        setColor();

        setSpawnedTile(Dirt.class);
    }
}
