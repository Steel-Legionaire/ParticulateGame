package particulate.game.Solids;

import particulate.game.Tile;

public abstract class Solid extends Tile
{
    public Solid(int x, int y, boolean isFlammable, boolean isDestructable, int toughness, int speed) {
        super(x, y, isFlammable, isDestructable, toughness, speed);
    }
}


