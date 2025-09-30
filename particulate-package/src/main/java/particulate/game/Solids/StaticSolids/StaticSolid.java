package particulate.game.Solids.StaticSolids;

import particulate.game.Solids.Solid;

public class StaticSolid extends Solid
{

    public StaticSolid(int x, int y, boolean isFlammable, boolean isDestructable, int toughness, int speed) {
        super(x, y, isFlammable, isDestructable, toughness, speed);
    }

    @Override
    public void move()
    {

    }
    
}
