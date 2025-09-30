package particulate.game.Solids.StaticSolids.Spawners;
import java.awt.Color;

import particulate.game.Solids.MoveableSolids.Ash;

public class AshSpawner extends Spawner{

    public AshSpawner(int x, int y) {
        super(x, y, false, true, 8, 1);
        setAllPossibleColors(new Color[]{ new Color(15, 125, 15) });
        setColor();

        setSpawnedTile(Ash.class);
    }
}
