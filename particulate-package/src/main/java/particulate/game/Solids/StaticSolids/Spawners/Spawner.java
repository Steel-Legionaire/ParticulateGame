package particulate.game.Solids.StaticSolids.Spawners;

import particulate.game.CellularMatrix;
import particulate.game.ParticulateGame;

import particulate.game.Solids.StaticSolids.StaticSolid;

public class Spawner extends StaticSolid{

    Class<?> spawnTile;
    int spawnDir = 1;

    public Spawner(int x, int y, boolean isFlammable, boolean isDestructable, int toughness, int speed) 
    {
        super(x, y, isFlammable, isDestructable, toughness, speed);
    }

    @Override
    public void action() 
    {
        CellularMatrix matrix = ParticulateGame.getMatrix();

        if(matrix.getTile(x,y+(1*spawnDir)) == null)
        {

            matrix.createTile(x, y+(1*spawnDir), spawnTile, 1, false);
        }
    }

    public void setSpawnedTile(Class<?> t)
    {
        spawnTile = t;
    }

    public void setSpawnDirection(int n)
    {
        spawnDir = n;
    }
}
