package particulate.game.Solids.StaticSolids.Spawners;
import java.awt.Color;

import particulate.game.Gases.Fire;

public class FireSpawner extends Spawner
{

    public FireSpawner(int x, int y) {
        super(x, y, false, true, 8, 1);
        setAllPossibleColors(new Color[]{Color.magenta });
        setColor();

        setSpawnedTile(Fire.class);
        setSpawnDirection(-1);
    }
}
