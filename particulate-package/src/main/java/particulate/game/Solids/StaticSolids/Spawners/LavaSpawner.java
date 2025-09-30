package particulate.game.Solids.StaticSolids.Spawners;
import java.awt.Color;

import particulate.game.Liquids.Lava;

public class LavaSpawner extends Spawner{

    public LavaSpawner(int x, int y){
        super(x, y, false, true, 8, 0);
        setAllPossibleColors(new Color[]{ Color.GREEN });
        setColor();

        setSpawnedTile(Lava.class);
    }
}
