package particulate.game.Solids.StaticSolids.Spawners;
import java.awt.Color;

import particulate.game.Solids.MoveableSolids.Sand;

public class SandSpawner extends Spawner{

    public SandSpawner(int x, int y) {
        super(x, y, false, true, 8, 1);
        setAllPossibleColors(new Color[]{ Color.RED });
        setColor();

        setSpawnedTile(Sand.class);
    } 
}
