package particulate.game.Liquids;

import particulate.game.Tile;

public class Liquid extends Tile{

    public Liquid(int x, int y, boolean isFlammable, boolean isDestructable, int toughness, int speed) {
        super(x, y, isFlammable, isDestructable, toughness, speed);
    }
    
    @Override
    public void darkenTile() {}

    @Override
    public void recieveHeat()
    {
        // turn water to steam
    }
}
